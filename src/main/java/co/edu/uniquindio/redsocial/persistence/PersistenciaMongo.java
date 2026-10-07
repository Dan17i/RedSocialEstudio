package co.edu.uniquindio.redsocial.persistence;

import co.edu.uniquindio.redsocial.models.structures.ConjuntoHash;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.TablaHash;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;

// java.util.List/ArrayList solo en la frontera con el driver (filtros de MongoDB); ver MapeadorDocumentos.
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Persistencia en MongoDB. Guarda el estado como un documento por entidad (ver {@link MapeadorDocumentos}).
 * <ul>
 *   <li>{@link #cargar}: lee todas las colecciones y reconstruye el estado en memoria.</li>
 *   <li>{@link #guardar}: sube solo los documentos que cambiaron desde el último guardado y borra de la base
 *       los que ya no existen en memoria.</li>
 * </ul>
 * Si no hay conexión al crearla, lanza {@link IllegalStateException} con un mensaje claro (la aplicación no arranca).
 */
public class PersistenciaMongo implements Persistencia {

    private static final Logger LOG = Logger.getLogger(PersistenciaMongo.class.getName());

    private final MongoClient cliente;
    private final MongoDatabase baseDatos;
    private final MapeadorDocumentos mapeador = new MapeadorDocumentos();

    /** Último contenido (JSON) guardado de cada documento, clave "coleccion/id": evita reescribir lo que no cambió. */
    private final TablaHash<String, String> ultimoGuardado = new TablaHash<>();

    /**
     * Conecta con MongoDB y comprueba que responde.
     *
     * @param uri       Cadena de conexión, por ejemplo {@code mongodb://localhost:27017}.
     * @param nombreBd  Nombre de la base de datos.
     * @throws IllegalStateException si no se puede conectar.
     */
    public PersistenciaMongo(String uri, String nombreBd) {
        MongoClientSettings ajustes = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .applyToClusterSettings(c -> c.serverSelectionTimeout(3, TimeUnit.SECONDS))
                .build();
        this.cliente = MongoClients.create(ajustes);
        this.baseDatos = cliente.getDatabase(nombreBd);
        try {
            baseDatos.runCommand(new Document("ping", 1));
        } catch (MongoException e) {
            cliente.close();
            throw new IllegalStateException("No se pudo conectar a MongoDB en " + ocultarCredenciales(uri)
                    + ". Inicia MongoDB (por ejemplo: docker start redsocial-mongo) o define "
                    + "REDSOCIAL_PERSISTENCIA=memoria para trabajar sin guardar datos.", e);
        }
        LOG.info("Persistencia MongoDB activa: base '" + nombreBd + "' en " + ocultarCredenciales(uri));
    }

    @Override
    public void cargar(EstadoAplicacion estado) {
        try {
            mapeador.restaurar(estado,
                    leer(MapeadorDocumentos.USUARIOS),
                    leer(MapeadorDocumentos.CONTENIDOS),
                    leer(MapeadorDocumentos.GRUPOS),
                    leer(MapeadorDocumentos.CONVERSACIONES),
                    leer(MapeadorDocumentos.SOLICITUDES));
        } catch (MongoException e) {
            throw new IllegalStateException("No se pudieron leer los datos guardados en MongoDB", e);
        }
        LOG.info("Datos cargados desde MongoDB: "
                + estado.getAutenticacion().getEstudiantesRegistrados().getTamanio() + " estudiantes, "
                + estado.getContenidos().obtenerTodosLosContenidos().getTamanio() + " contenidos, "
                + estado.getGrupos().getTamanio() + " grupos, "
                + estado.getConversaciones().getTamanio() + " conversaciones");
    }

    @Override
    public synchronized void guardar(EstadoAplicacion estado) {
        try {
            sincronizar(MapeadorDocumentos.USUARIOS, mapeador.usuarios(estado));
            sincronizar(MapeadorDocumentos.CONTENIDOS, mapeador.contenidos(estado));
            sincronizar(MapeadorDocumentos.GRUPOS, mapeador.grupos(estado));
            sincronizar(MapeadorDocumentos.CONVERSACIONES, mapeador.conversaciones(estado));
            sincronizar(MapeadorDocumentos.SOLICITUDES, mapeador.solicitudes(estado));
        } catch (MongoException e) {
            throw new IllegalStateException("No se pudieron guardar los datos en MongoDB", e);
        }
    }

    @Override
    public void cerrar() {
        cliente.close();
    }

    private ListaEnlazada<Document> leer(String coleccion) {
        ListaEnlazada<Document> docs = new ListaEnlazada<>();
        for (Document d : baseDatos.getCollection(coleccion).find()) {
            docs.agregar(d);
        }
        return docs;
    }

    /** Deja la colección igual al estado en memoria: inserta/actualiza lo nuevo o cambiado y borra lo sobrante. */
    private void sincronizar(String nombre, ListaEnlazada<Document> docs) {
        MongoCollection<Document> coleccion = baseDatos.getCollection(nombre);
        List<String> ids = new ArrayList<>();
        ConjuntoHash<String> vigentes = new ConjuntoHash<>();

        for (Document d : docs) {
            String id = d.getString("_id");
            ids.add(id);
            vigentes.agregar(id);

            String json = d.toJson();
            String clave = nombre + "/" + id;
            if (!json.equals(ultimoGuardado.obtener(clave))) {
                coleccion.replaceOne(Filters.eq("_id", id), d, new ReplaceOptions().upsert(true));
                ultimoGuardado.poner(clave, json);
            }
        }

        coleccion.deleteMany(Filters.nin("_id", ids));

        // Olvida lo que ya no existe para que, si vuelve a aparecer con el mismo id, se guarde de nuevo
        String prefijo = nombre + "/";
        for (String clave : ultimoGuardado.claves()) {
            if (clave.startsWith(prefijo) && !vigentes.contiene(clave.substring(prefijo.length()))) {
                ultimoGuardado.eliminar(clave);
            }
        }
    }

    /** Quita usuario y contraseña de la cadena de conexión antes de escribirla en un mensaje o en el log. */
    static String ocultarCredenciales(String uri) {
        return uri.replaceAll("//[^/@]*@", "//***@");
    }
}
