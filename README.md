# APP HISTORIAL DE PRODUCTO - REVI MACHINERY 

## Tabla de Contenidos
* Descripción
* Funcionalidades principales
* Arquitectura del proyecto
* Requisitos del sistema
* Ejecución del proyecto
* Permisos requeridos
* Tecnologias utilizadas

## Descripción
### Aplicación Android desarrollada en Java
### que permite consultar información de maquinaria industrial a partir de un número de serie o código QR
### mostrando los datos técnicos de la máquina y su historial de mantenimiento, conectando con una base de datos MySQL con la query 

## Funcionalidades principales
* Introducción manual del número de serie.
* Lectura de código QR mediante la cámara.
* Consulta de datos de la máquina.
* Visualización de historial de mantenimiento.
* Navegación por pestañas (ViewPager + TabLayout)
* Comunicación con servidor remoto mediante HTTP.
* Arquitectura basada en Fragments + ViewModel.

## Arqyitectura del proyecto
### La aplicación sigue una arquitectura por capas:
* View (UI): Fragments y layouts XML
* ViewModel: SharedViewModel para compartir datos entre fragments
* Data/Network: Clase Tools para peticiones HTTP
* Backend: API REST en PHP
* Base de datos: MySQL

## Requisitos del sistema
### Para ejecutar la alicación se necesita:
* Sistema operativo: Windows 10
* Android Studio, recomendado la última versión estable.
* JDK 11 o superior.
* Dispositivo Android fisico, mejor que emulador, ya que se usa la cámara física para leer códido QR.
* Conexión a Internet.
* Cámara para lectura código QR.
### Para ejecutar el backend:
* Servidor web 
* PHP 7.4 o superior
* MySQL / MariaDB
* Acceso a red desde el dispositivo Android.

## Ejecución del proyecto:
1. Abrir en Android Studio el proyecto existente.
2. Android Studio descargará automáticamente:
* AndroidX
* Material Design
* CameraX
* Esperar a que termine la sincronización.
3. Ejecutar la aplicación:
* Conectar un dispositivo Android (preferible físico) o emulador.
* Pulsar Run.
* Seleccionar el dispositivo.
* La app se instalara automaticamente.
4. Configurar el backend:
* Colocar el archivo query.php en un servidor accesible, por ejemplo: http://tu-servidor.com/query.php

## Permisos requeridos:
* Estan declarados en AndroidManifest.xml:
1. <uses-permission android:name="android.permission.CAMERA"/>
2. <uses-permission android:name="android.permission.INTERNET"/>

## Tencnologias utilizadas:
* Java
* Android SDK
* Material Design
* CameraX
* PHP
* MySQL
* JSON
* HTTP REST
