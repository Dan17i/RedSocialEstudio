# Diagrama de clases

Los diagramas están escritos en [Mermaid](https://mermaid.js.org/): GitHub los muestra como imagen
al abrir este archivo. Para exportarlos a PNG/PDF (por ejemplo para la entrega) se puede pegar cada
bloque en <https://mermaid.live>.

Se dividen en tres vistas para que sean legibles:

1. [Modelo de dominio](#1-modelo-de-dominio)
2. [Estructuras de datos propias](#2-estructuras-de-datos-propias)
3. [Servicios](#3-servicios)

---

## 1. Modelo de dominio

```mermaid
classDiagram
    direction TB

    class Usuario {
        -String id
        -String nombre
        -String email
        -String contrasena
        -ListaEnlazada~String~ intereses
        -ListaEnlazada~Contenido~ historialContenidos
        -ListaEnlazada~Valoracion~ valoraciones
        +agregarInteres(String)
        +recibirMensaje(Mensaje)
    }

    class Estudiante {
        -ColaPrioridad~SolicitudAyuda~ solicitudesAyuda
        -ListaEnlazada~GrupoEstudio~ gruposEstudio
        -ListaEnlazada~Mensaje~ bandejaEntrada
        +publicarContenido(Contenido, GestorContenidos)
        +valorarContenido(Contenido, int, String)
        +solicitarAyuda(SolicitudAyuda)
        +procesarSiguienteSolicitud() SolicitudAyuda
        +unirseAGrupo(GrupoEstudio)
        +enviarMensaje(Estudiante, String)
    }

    class Moderador {
        -boolean accesoCompleto
        -ListaEnlazada~String~ areasResponsabilidad
        +generarReporteComunidades()
        +generarReporte(TipoReporte) Reporte
        +generarReporteContenidosMasValorados()
        +generarReporteEstudiantesMasConectados()
        +generarReporteCaminosMasCortos(String, String)
        +generarReporteParticipacion()
    }

    class Contenido {
        -String id
        -String tema
        -String descripcion
        -TipoContenido tipo
        -LocalDateTime fechaCreacion
        -ListaEnlazada~Valoracion~ valoraciones
        +agregarValoracion(Valoracion)
        +promedioValoraciones() double
    }

    class ArchivoMultimedia {
        -String nombreArchivo
        -String rutaRelativa
        -String tipoMime
        -long tamanio
    }

    class Valoracion {
        -String id
        -int puntuacion
        -String comentario
        -LocalDateTime fechaValoracion
    }

    class GrupoEstudio {
        -String id
        -String tema
        -ListaEnlazada~Estudiante~ miembros
        -ListaEnlazada~Contenido~ publicaciones
        -ColaPrioridad~SolicitudAyuda~ solicitudesAyudaGrupo
        +agregarMiembro(Estudiante)
        +eliminarMiembro(Estudiante)
    }

    class SolicitudAyuda {
        -String id
        -String tema
        -int urgencia
        -String descripcion
        -EstadoSolicitud estado
        -LocalDateTime fechaSolicitud
    }

    class Mensaje {
        -String texto
        -LocalDateTime fecha
        +enviar()
    }

    class Conversacion {
        -String id
    }

    class Reporte~T~ {
        -String id
        -TipoReporte tipo
        -LocalDateTime fechaGeneracion
        -ListaEnlazada~T~ datos
    }

    class HistorialDeContenido {
        -String idUsuario
        -ListaEnlazada~Contenido~ contenidos
        -ListaEnlazada~LocalDateTime~ fechasAcceso
    }

    class RegistroVisualizacion {
        -LocalDateTime fechaVisualizacion
    }

    class Tematico {
        <<interface>>
        +getTema() String
    }

    class TipoContenido {
        <<enumeration>>
    }
    class EstadoSolicitud {
        <<enumeration>>
    }
    class TipoReporte {
        <<enumeration>>
    }

    Usuario <|-- Estudiante
    Usuario <|-- Moderador
    Tematico <|.. Contenido

    Estudiante "1" --> "0..*" Contenido : publica
    Estudiante "1" --> "0..*" Valoracion : realiza
    Contenido "1" o-- "0..*" Valoracion : recibe
    Contenido "1" --> "0..1" ArchivoMultimedia : adjunta
    Contenido --> TipoContenido

    GrupoEstudio "1" o-- "0..*" Estudiante : miembros
    GrupoEstudio "1" o-- "0..*" Contenido : publicaciones
    GrupoEstudio "1" o-- "0..*" SolicitudAyuda : cola de ayuda

    Estudiante "1" o-- "0..*" SolicitudAyuda : cola de ayuda
    SolicitudAyuda --> Estudiante : solicitante
    SolicitudAyuda --> EstadoSolicitud

    Mensaje --> Estudiante : remitente
    Mensaje --> Usuario : destinatario
    Conversacion "1" o-- "0..*" Mensaje

    Reporte --> TipoReporte
    Moderador ..> Reporte : genera
    RegistroVisualizacion --> Usuario
    RegistroVisualizacion --> Contenido
    HistorialDeContenido o-- Contenido
```

---

## 2. Estructuras de datos propias

El proyecto no usa colecciones de `java.util`: todas las estructuras están implementadas en
`models/structures`.

```mermaid
classDiagram
    direction LR

    class ListaEnlazada~T~ {
        -NodoLista~T~ cabeza
        -int tamanio
        +agregar(T)
        +agregarInicio(T)
        +insertarEn(int, T)
        +obtener(int) T
        +eliminar(int)
        +eliminar(T) boolean
        +contiene(T) boolean
        +invertir()
        +ordenar(Comparator)
        +clonar()
        +sublista(int, int)
    }
    class NodoLista~T~ {
        -T dato
        -NodoLista~T~ siguiente
    }
    ListaEnlazada "1" o-- "0..*" NodoLista

    class ArbolBinarioBusqueda~T~ {
        -NodoABB~T~ raiz
        +insertar(String, T)
        +buscar(String) T
        +eliminar(String)
        +eliminarValor(String, T)
        +listarTodos() ListaEnlazada
        +listarContenidosPorTema(String)
    }
    class NodoABB~T~ {
        -String clave
        -T valor
        -NodoABB izquierda
        -NodoABB derecha
    }
    ArbolBinarioBusqueda "1" o-- "0..*" NodoABB

    class ColaPrioridad~T~ {
        -ListaEnlazada~NodoPrioridad~ elementos
        +encolar(T, int)
        +desencolar() T
        +estaVacia() boolean
        +tamanio() int
    }
    class NodoPrioridad~T~ {
        -T dato
        -int prioridad
    }
    ColaPrioridad "1" o-- "0..*" NodoPrioridad
    ColaPrioridad ..> ListaEnlazada

    class TablaHash~K,V~ {
        -Entrada[] tabla
        -int tamanio
        +poner(K, V) V
        +obtener(K) V
        +eliminar(K) V
        +contieneClave(K) boolean
        +claves() ListaEnlazada
        +valores() ListaEnlazada
        +entradas() ListaEnlazada
    }
    class ConjuntoHash~T~ {
        +agregar(T) boolean
        +contiene(T) boolean
        +eliminar(T) boolean
        +elementos() ListaEnlazada
    }
    ConjuntoHash "1" *-- "1" TablaHash

    class IGrafo~T~ {
        <<interface>>
        +agregarNodo(T)
        +agregarArista(T, T, double)
        +eliminarNodo(T) boolean
        +buscarRutaCorta(T, T) ListaEnlazada
        +detectarComunidades() ListaEnlazada
    }
    class GrafoImpl~T~ {
        -ListaEnlazada~NodoGrafo~ nodos
        -TablaHash mapaDeNodos
        -boolean esDirigido
    }
    class GrafoNoDirigido~T~ {
        +obtenerVecinos(T) ListaEnlazada
    }
    class NodoGrafo~T~ {
        -T dato
        -TablaHash~NodoGrafo, Double~ adyacentes
    }
    IGrafo <|.. GrafoImpl
    GrafoImpl <|-- GrafoNoDirigido
    GrafoImpl "1" o-- "0..*" NodoGrafo
    GrafoImpl ..> TablaHash
    NodoGrafo ..> TablaHash
```

| Requisito del enunciado | Estructura | Dónde se usa |
|---|---|---|
| Grafo no dirigido de afinidad | `GrafoNoDirigido` | `RedAfinidad`, `GestorRedSocial`, `GestorGrupos` |
| Árbol binario de búsqueda de contenidos | `ArbolBinarioBusqueda` | `GestorContenidos` (indexa por tema) |
| Cola de prioridad de solicitudes de ayuda | `ColaPrioridad` | `Estudiante`, `GrupoEstudio`, `SistemaAyuda` |
| Listas enlazadas (historial, valoraciones, grupos) | `ListaEnlazada` | Todo el modelo |
| (apoyo) tabla hash y conjunto | `TablaHash`, `ConjuntoHash` | Grafo, Dijkstra, recorridos, estadísticas |

---

## 3. Servicios

```mermaid
classDiagram
    direction TB

    class ISistemaAutenticacion { <<interface>> }
    class SistemaAutenticacion {
        -ListaEnlazada~Usuario~ usuariosRegistrados
        +registrarEstudiante(nombre, email, contrasena) Estudiante
        +iniciarSesion(email, contrasena) Usuario
    }
    class ClaveHash {
        <<utility>>
        +hashear(String) String
        +verificar(String, String) boolean
    }
    class ConfigModerador {
        <<utility>>
        +esModerador(String, String) boolean
    }
    class FiltroAcceso {
        <<WebFilter>>
        +evaluar(ruta, usuario) Decision
        +origenValido(metodo, origin, referer, host) boolean
    }

    class IGestorContenidos { <<interface>> }
    class GestorContenidos {
        <<singleton>>
        -ArbolBinarioBusqueda arbolContenidos
        -ListaEnlazada listaDeContenidos
        +agregarContenido(Contenido)
        +eliminarContenido(String) boolean
        +buscarPorTema(String)
        +obtenerContenidosMasValorados()
    }

    class IRedAfinidad { <<interface>> }
    class RedAfinidad {
        <<singleton>>
        -GrafoNoDirigido~Estudiante~ grafoEstudiantes
        +estanConectados(Estudiante, Estudiante) boolean
        +actualizarConexiones()
        +sugerirCompanerosAvanzado(Estudiante)
    }

    class IGestorRedSocial { <<interface>> }
    class GestorRedSocial {
        +detectarComunidades()
        +obtenerEstudiantesMasConectados()
        +calcularCaminosMasCortos(String, String)
        +obtenerNivelesParticipacion()
    }

    class IGestorGrupos~T~ { <<interface>> }
    class GestorGrupos~T~ {
        +crearGruposPorAfinidadConObjetos(String)
    }

    class IGestorSugerencias { <<interface>> }
    class GestorSugerencias {
        +sugerirAmigos(Estudiante)
    }

    class ISistemaAyuda { <<interface>> }
    class SistemaAyuda {
        +agregarSolicitud(SolicitudAyuda)
        +atenderSolicitud() SolicitudAyuda
    }

    class IGestorUsuarios { <<interface>> }
    class GestorUsuarios

    class SistemaRecomendaciones {
        +recomendarCOntenidos(Estudiante)
        +recomendarCompanieros(Estudiante)
    }

    ISistemaAutenticacion <|.. SistemaAutenticacion
    IGestorContenidos <|.. GestorContenidos
    IRedAfinidad <|.. RedAfinidad
    IGestorRedSocial <|.. GestorRedSocial
    IGestorGrupos <|.. GestorGrupos
    IGestorSugerencias <|.. GestorSugerencias
    ISistemaAyuda <|.. SistemaAyuda
    IGestorUsuarios <|.. GestorUsuarios

    SistemaAutenticacion ..> ClaveHash
    SistemaAutenticacion ..> RedAfinidad : registra estudiantes
    SistemaAutenticacion --> GestorContenidos
    GestorRedSocial --> RedAfinidad : lee el grafo
    GestorSugerencias ..> GrafoNoDirigido
    GestorGrupos ..> GrafoNoDirigido
    RedAfinidad --> GrafoNoDirigido
    SistemaRecomendaciones --> RedAfinidad
    SistemaRecomendaciones --> GestorContenidos
    Moderador --> IGestorUsuarios
    Moderador --> GestorContenidos
    Moderador --> IGestorRedSocial
```

### Capa web (servlets)

Los servlets del paquete `drivers` se registran con `@WebServlet` y delegan en los servicios de arriba.
Los principales:

| Área | Servlets |
|---|---|
| Acceso | `LoginServlet`, `RegistroServlet`, `CerrarSesionServlet` |
| Contenidos | `CrearPublicacionServlet`, `PublicacionServlet`, `FiltrarPublicacionesServlet`, `ValoracionServlet`, `ArchivoServlet` |
| Perfil e intereses | `PerfilServlet`, `DashboardServlet`, `AgregarInteresServlet`, `EliminarInteresServlet` |
| Grupos | `MostrarGruposServlet`, `FormarGruposServlet`, `SugerirGruposServlet`, `UnirseGrupoServlet`, `DetalleGrupoServlet`, `ChatGrupoServlet`, `EnviarMensajeGrupoServlet`, `PublicarContenidoGrupoServlet`, `SolicitarAyudaGrupoServlet` |
| Mensajería | `ChatServlet`, `ChatMessagesServlet`, `EnviarMensajeServlet` |
| Moderador | `GestionUsuariosServlet`, `GestionContenidosServlet`, `GrafoAfinidadServlet`, `SugerenciasServlet`, `ReporteServlet`, `ComunidadesServlet`, `ContenidosValoradosServlet`, `ParticipacionServlet`, `EstudiantesConectadosServlet`, `GenerarDatosServlet` |

`AppInitListener` crea al arrancar el contexto el sistema de autenticación, las colecciones globales y
publica en el `ServletContext` las mismas estructuras que mantiene `GestorContenidos`.
