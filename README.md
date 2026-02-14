# 📊 Dashboard de Gestión Integral (Panadería)

Este proyecto es una aplicación de escritorio desarrollada en **Java** utilizando **NetBeans**, diseñada para centralizar la gestión operativa de un negocio (basado en el archivo `panaderia.db`).

## 🚀 Funcionalidades Principales
* **Módulo de Ventas:** Registro y seguimiento de transacciones comerciales (`Venta.java`).
* **Control de Inventario:** Gestión completa del catálogo de productos (`Producto.java`).
* **Administración de Usuarios:** Sistema de autenticación y perfiles de acceso (`LoginFrame.java`, `Usuario.java`).
* **Interfaz Gráfica Dinámica:** Dashboard intuitivo construido con **Swing** para facilitar la navegación.

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java 17+
* **IDE:** Apache NetBeans 23
* **Base de Datos:** [INSERTAR AQUÍ, ej: SQLite]
* **Gestión de Dependencias:** Maven (vía `pom.xml`)

## 📂 Estructura del Proyecto
* `src/main/java/com/mycompany/proyecto_dashboard`: Contiene toda la lógica de negocio y las interfaces gráficas.
* `panaderia.db`: Base de datos local con la persistencia de la información.
* `README.md`: Documentación del proyecto.
* `.gitignore`: Configuración para evitar subir archivos temporales de NetBeans a GitHub.

## ⚙️ Instalación y Ejecución
1. Clonar el repositorio.
2. Abrir el proyecto en **NetBeans 23**.
3. Asegurarse de tener configuradas las dependencias de Maven.
4. Ejecutar `Main.java` para iniciar la aplicación.