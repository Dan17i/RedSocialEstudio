# RedSocialEstudio
proyecto final de estructura de datos 
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
