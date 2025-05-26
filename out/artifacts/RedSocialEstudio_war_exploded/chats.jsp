<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <!--CAMBIAAAAAR-->
    <meta charset="UTF-8">
    <title>Visualización de Contenido - EduSocial</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .star {
            color: #ccc;
            font-size: 24px;
            cursor: pointer;
            transition: color 0.2s ease;
            margin-right: 5px;
        }
        .star.active, .star:hover {
            color: #ffcc00;
        }
        .commenter-photo, .author-photo {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            overflow: hidden;
            background-color: #ddd;
        }
    </style>
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand fw-bold" href="#">EduSocial</a>
        <div class="d-flex align-items-center">
            <img src="images/avatar.png" alt="Usuario" class="rounded-circle" width="40" height="40">
        </div>
    </div>
</nav>

<!-- Contenido -->
<div class="container mt-4">
    <div class="card shadow-sm">
        <div class="card-header bg-white">
            <h4 class="text-primary mb-0">Introducción a la Programación Orientada a Objetos</h4>
            <div class="d-flex align-items-center mt-2">
                <div class="author-photo me-3">
                    <img src="images/avatar.png" alt="Autor" width="50" height="50">
                </div>
                <div>
                    <strong>Profesor García</strong><br>
                    <small class="text-muted">Publicado el 10 de abril de 2025</small>
                </div>
            </div>
        </div>

        <div class="card-body">
            <p>Este contenido ofrece una introducción completa a los conceptos fundamentales de la Programación Orientada a Objetos (POO)...</p>
            <p>El material incluye ejemplos de código en Java y ejercicios interactivos...</p>
        </div>

        <div class="card-footer bg-light d-flex justify-content-between align-items-center flex-wrap">
            <div>
                <span class="star active">★</span>
                <span class="star active">★</span>
                <span class="star active">★</span>
                <span class="star active">★</span>
                <span class="star">★</span>
                <small class="ms-2">(4.0/5.0 - 28 valoraciones)</small>
            </div>
            <div>
                <button class="btn btn-outline-primary btn-sm">Valorar</button>
                <button class="btn btn-primary btn-sm">Comentar</button>
            </div>
        </div>

        <!-- Sección de comentarios -->
        <div class="p-4 border-top">
            <h5>Comentarios (3)</h5>
            <div class="d-flex mb-3">
                <textarea class="form-control me-2" rows="2" placeholder="Escribe un comentario..."></textarea>
                <button class="btn btn-primary">Enviar</button>
            </div>

            <!-- Comentario 1 -->
            <div class="mb-4 border-bottom pb-3">
                <div class="d-flex align-items-center mb-2">
                    <div class="commenter-photo me-2">
                        <img src="images/avatar.png" width="40" height="40" alt="María López">
                    </div>
                    <strong class="me-2">María López</strong>
                    <small class="text-muted">Hace 2 días</small>
                </div>
                <p>¡Excelente contenido! Los ejemplos prácticos me ayudaron mucho a comprender el concepto de herencia...</p>
            </div>

            <!-- Comentario 2 -->
            <div class="mb-4 border-bottom pb-3">
                <div class="d-flex align-items-center mb-2">
                    <div class="commenter-photo me-2">
                        <img src="images/avatar.png" width="40" height="40" alt="Carlos Mendoza">
                    </div>
                    <strong class="me-2">Carlos Mendoza</strong>
                    <small class="text-muted">Hace 3 días</small>
                </div>
                <p>Muy buena explicación sobre encapsulamiento. Me gustaría ver más ejemplos...</p>
            </div>

            <!-- Comentario 3 -->
            <div class="mb-2">
                <div class="d-flex align-items-center mb-2">
                    <div class="commenter-photo me-2">
                        <img src="images/avatar.png" width="40" height="40" alt="Ana Torres">
                    </div>
                    <strong class="me-2">Ana Torres</strong>
                    <small class="text-muted">Hace 5 días</small>
                </div>
                <p>Los ejercicios interactivos son útiles para practicar. Me gustaría una explicación más profunda de polimorfismo.</p>
            </div>
        </div>
    </div>
</div>

<!-- JS de Bootstrap y lógica ligera -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.querySelectorAll('.star').forEach((star, index) => {
        star.addEventListener('click', () => {
            document.querySelectorAll('.star').forEach((s, i) => {
                s.classList.toggle('active', i <= index);
            });
        });
    });

    document.querySelector('.btn-primary').addEventListener('click', () => {
        document.querySelector('textarea').focus();
    });
</script>
</body>
</html>
