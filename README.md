# RedSocialEstudio

Red social de aprendizaje colaborativo, proyecto final de **Estructura de Datos** (Universidad del Quindío).
Los estudiantes comparten recursos, se valoran entre sí los contenidos, se conectan según sus afinidades y
se organizan en grupos de estudio; un moderador analiza la red y genera reportes.

La particularidad del proyecto es que **todas las estructuras de datos son propias** (sin colecciones de
`java.util`): lista enlazada, árbol binario de búsqueda, cola de prioridad, tabla hash, conjunto y grafo.

## Tecnologías

- Java 17 y Maven
- MongoDB 7 (persistencia; se levanta con Docker)
- Servlets 4.0 + JSP (Tomcat 9), JSTL 1.2
- Gson, JUnit 5, JaCoCo

## Requisitos

- JDK 17 o superior
- Maven 3.8+
- Apache Tomcat **9** (el proyecto usa `javax.servlet`; Tomcat 10+ no es compatible)
- Docker (o un MongoDB instalado) para la base de datos

## Compilar y probar

```bash
mvn clean package        # compila, ejecuta las pruebas y genera target/RedSocialEstudio-1.0-SNAPSHOT.war
mvn test                 # solo pruebas (informe de cobertura JaCoCo en target/site/jacoco)
```

## Base de datos (MongoDB)

Los datos se guardan en MongoDB y se cargan al arrancar la aplicación. Levanta la base con Docker (una sola vez):

```bash
docker run -d --name redsocial-mongo -p 27017:27017 -v redsocial-mongo-data:/data/db mongo:7
```

Las próximas veces basta con `docker start redsocial-mongo`. Los datos quedan en el volumen `redsocial-mongo-data`.

| Variable | Significado | Valor por defecto |
|---|---|---|
| `REDSOCIAL_MONGO_URI` | Cadena de conexión | `mongodb://localhost:27017` |
| `REDSOCIAL_MONGO_DB` | Nombre de la base | `redsocialestudio` |
| `REDSOCIAL_PERSISTENCIA` | `mongo` o `memoria` (sin guardar nada) | `mongo` |
| `REDSOCIAL_UPLOADS` | Carpeta de archivos subidos | `~/redsocial-uploads` |

- **Si MongoDB no responde, la aplicación no arranca** y el log explica por qué. Para trabajar sin base de datos,
  elige `REDSOCIAL_PERSISTENCIA=memoria` de forma explícita (los datos se pierden al detener el servidor).
- Se guardan: estudiantes (con el hash de su contraseña), contenidos y valoraciones, grupos con sus mensajes y ayuda,
  conversaciones de chat y solicitudes de ayuda. El grafo de afinidad no se guarda: se recalcula.
- Los archivos adjuntos se guardan en `REDSOCIAL_UPLOADS`, **fuera** de la aplicación, para que sobrevivan a un nuevo despliegue.
- El guardado ocurre después de cada petición que modifica datos y al detener la aplicación.

## Ejecutar

1. Genera el WAR con `mvn clean package`.
2. Copia `target/RedSocialEstudio-1.0-SNAPSHOT.war` a la carpeta `webapps` de Tomcat 9
   (por ejemplo renombrándolo a `RedSocialEstudio.war`) e inicia Tomcat.
   En IntelliJ también sirve importar el proyecto como *Maven* y crear una configuración *Tomcat Server (Local)*
   con el artefacto `RedSocialEstudio:war exploded`.
3. Abre `http://localhost:8080/RedSocialEstudio/` (te lleva a la pantalla de inicio de sesión).

Los datos se conservan al reiniciar el servidor (ver *Base de datos*).

### Datos de prueba

Inicia sesión como moderador y pulsa **Generar datos de prueba**: crea 10 estudiantes
(`est1@correo.com` … `est10@correo.com`, contraseña `pass123`) con intereses, contenidos, valoraciones
y grupos. Después de generarlos, el grafo de afinidad ya tiene conexiones.

### Moderador

Las credenciales se leen de variables de entorno:

| Variable | Significado | Valor por defecto (solo desarrollo) |
|---|---|---|
| `REDSOCIAL_MOD_EMAIL` | Correo del moderador | `moderador@redsocial.com` |
| `REDSOCIAL_MOD_PASS` | Contraseña del moderador | `moderador123` |

Define ambas variables antes de iniciar Tomcat en cualquier entorno que no sea tu equipo local.

## Qué puede hacer cada rol

**Estudiante** (menú lateral de `inicio.jsp`):

| Sección | Qué ofrece |
|---|---|
| Home | Explorar contenidos, filtrar por tema, autor o tipo, y valorarlos |
| Perfil | Intereses, contenidos publicados y valoraciones |
| Mis Grupos / Grupos sugeridos | Grupos de estudio formados automáticamente por intereses; detalle con chat, publicaciones y ayuda del grupo |
| Chats | Mensajería entre estudiantes |
| **Descubrir** | Tus conexiones, "amigos de amigos", compañeros con intereses afines, contenidos recomendados y la **ruta más corta** hacia otro estudiante |
| **Ayuda** | Pedir ayuda en un tema con nivel de urgencia (1 = más urgente), ver la cola de la comunidad ordenada por urgencia y ayudar a otros |
| Crear publicación | Publicar archivos, imágenes, videos o enlaces |

**Moderador** (`moderador.jsp`): gestión de usuarios (baja y renombrar) y de contenidos, grafo de afinidad,
comunidades, contenidos más valorados, estudiantes más conectados, niveles de participación,
**ruta más corta entre dos estudiantes** y botón de datos de prueba.

## Cómo funciona el grafo de afinidad

- Cada estudiante registrado es un nodo del grafo no dirigido que mantiene `RedAfinidad`.
- Dos estudiantes quedan **conectados** si han valorado al menos un contenido en común o comparten
  al menos un grupo de estudio. Las conexiones se recalculan al abrir el grafo y antes de cada reporte.
- La **ruta más corta** entre dos estudiantes (Dijkstra) minimiza el número de conexiones.
- Los **grupos de estudio sugeridos** se forman con las comunidades (componentes conexos) de un grafo de intereses.
- La cola de prioridad atiende primero la solicitud de ayuda con el número de urgencia **menor**
  (1 = más urgente, 10 = menos urgente).

## Estructura del código

```
src/main/java/co/edu/uniquindio/redsocial
├── drivers/                  Servlets (capa web) y AppInitListener
├── models/                   Dominio: Usuario, Estudiante, Moderador, Contenido, GrupoEstudio, ...
│   ├── Enums/
│   ├── services/interf/      Interfaces de los servicios
│   ├── services/implement/   Gestores y sistemas (autenticación, contenidos, red de afinidad, ...)
│   └── structures/           Estructuras de datos propias
├── persistence/              Persistencia en MongoDB (mapeador a documentos, guardado y carga) y carpeta de subidas
└── security/                 Hash de contraseñas, filtro de acceso, escape HTML, config del moderador
src/main/webapp               JSP, CSS e imágenes
src/test/java                 Pruebas (modelos, estructuras, seguridad, red de afinidad y persistencia; las de MongoDB se omiten si no hay base)
docs/diagrama-clases.md       Diagrama de clases
```

Diagrama de clases: [docs/diagrama-clases.md](docs/diagrama-clases.md).

## Seguridad

- Contraseñas con PBKDF2-HMAC-SHA256 y sal aleatoria (nunca en texto plano).
- Filtro de acceso por rol: las páginas y servlets del moderador solo responden a un moderador;
  el resto exige sesión iniciada.
- Salida HTML escapada en todos los JSP (prevención de XSS).
- Los POST deben provenir del mismo origen (mitigación de CSRF), id de sesión regenerado al iniciar sesión,
  sesión de 30 minutos con cookie `HttpOnly`.

## Autores

Daniel Jurado, Sebastián Torres y Juan Soto.

---

# Enunciado original

Proyecto Final de Semestre: Red Social de Aprendizaje Colaborativo
1. Descripción General:
El proyecto consiste en desarrollar una plataforma que simule una red social educativa, en la cual los usuarios (estudiantes) puedan compartir recursos de aprendizaje, participar en grupos de estudio, evaluar contenidos y establecer conexiones con otros estudiantes que tengan intereses similares. El sistema debe hacer uso de estructuras de datos personalizadas para modelar usuarios, contenidos, relaciones de afinidad y sistemas de recomendación.
Los contenidos estarán organizados mediante árboles de búsqueda, mientras que las interacciones y conexiones entre estudiantes se representarán mediante un grafo no dirigido, donde los nodos son estudiantes y las aristas indican intereses académicos en común. Además, se utilizará una cola de prioridad para gestionar solicitudes de ayuda según urgencia académica.
2. Características del Sistema:
Registro y autenticación de estudiantes.
Publicación y valoración de contenidos educativos (archivos, enlaces, videos, etc.).
Formación de grupos de estudio automáticos con base en intereses compartidos.
Generación automática de conexiones entre usuarios (grafo), si han valorado contenidos similares o han estado en el mismo grupo de estudio.
Sugerencias de compañeros de estudio basadas en el grafo (amigos de amigos).
Búsqueda de rutas más cortas en el grafo entre dos estudiantes.
Solicitudes de ayuda académica gestionadas por prioridad (nivel de urgencia).


3. Roles del Sistema:
3.1 Estudiante:
Buscar contenidos por tema, autor o tipo.
Publicar y valorar contenidos educativos.
Solicitar ayuda en un tema específico (con nivel de urgencia).
Ver sugerencias de compañeros con intereses similares.
Participar en grupos de estudio sugeridos automáticamente.
Enviar mensajes a otros estudiantes.


3.2 Moderador:
Gestionar usuarios y contenidos.
Visualizar y analizar el grafo de afinidad entre estudiantes.
Generar reportes como:


Contenidos más valorados.
Estudiantes con más conexiones.
Caminos más cortos entre dos estudiantes.
Detección de comunidades de estudio (clústeres).
Niveles de participación.


4. Interfaz Gráfica (GUI):
Pestañas:


Inicio (exploración de contenidos y acceso a perfil).
Panel del estudiante (contenidos publicados, valoraciones, sugerencias, solicitudes de ayuda).
Grupos de estudio sugeridos.
Mensajería entre estudiantes.
Panel del moderador (gestión de usuarios y visualización del grafo).


Visualización gráfica del grafo de afinidad entre estudiantes.


5. Funcionalidades Específicas:
Registro y autenticación.
Publicación y valoración de contenidos.
Gestión de solicitudes de ayuda (prioridad).
Formación automática de grupos de estudio.
Recomendación de compañeros.
Visualización del grafo de afinidad.
Botón para carga de datos de prueba.


6. Estructuras de Datos:
Grafo no dirigido: Para representar la red de afinidad entre estudiantes.
Árbol Binario de Búsqueda (ABB): Para organizar los contenidos por tema o autor.
Cola de prioridad: Para gestionar solicitudes de ayuda según urgencia.
Listas enlazadas: Para el historial de contenidos compartidos, valoraciones y grupos de estudio.


7. Requerimientos de Desarrollo:
Implementar al menos 7 pruebas unitarias.
Uso exclusivo de estructuras de datos desarrolladas por los estudiantes.
Grupos de hasta 3 integrantes.
Repositorio en Git/GitHub (Cada integrante del grupo debe tener un mínimo de 12 commits, para evidenciar el trabajo colaborativo y trazabilidad del proyecto).
Diagrama de clases. 
8. Condiciones Generales:
Entrega con demostración funcional y sustentación.
GUI funcional e intuitiva.
Visualización del grafo de afinidad.
Carga de datos iniciales desde archivo o botón de prueba.
