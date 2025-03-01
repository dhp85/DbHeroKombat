# Dragon Ball Hero Kombat Simulator

## Descripción
Este proyecto es una aplicación móvil que simula batallas de Dragon Ball, cumpliendo con los requisitos de Fundamentos Android. 
Utiliza la API proporcionada para obtener los personajes y permite al usuario interactuar con ellos en un entorno de batalla.

## Características Principales
- **Inicio de sesión**: El usuario debe autenticarse con usuario y contraseña.
- **Selección de personaje**: Se muestran todos los personajes obtenidos de la API con atributos adicionales de "Vida máxima" y "Vida actual".
- **Simulador de batalla**: Los personajes pueden recibir daño y curarse.
- **Gestor de estados**: Si un personaje tiene 0 puntos de vida, no puede ser seleccionado y se indica visualmente.
- **Diseño libre** con un enfoque en la usabilidad y la estética.
- **Arquitectura MVVM** para una mejor organización del código.
- **Mapeo de datos**: Uso de clases `PersonajeDTO` (representa la estructura JSON de la API) y `Personaje` (estructura propia con atributos adicionales como puntos de vida).
.

## Tecnologías Utilizadas
- **Lenguaje**: Kotlin/Java
- **Arquitectura**: MVVM
- **Networking**: OkHttpClient para consumo de API
- **Carga y manejo de Imagenes**: Glide
- **UI**: XML
- **Testing**: JUnit
- **Otros**: Utilizacion de ViewModels y Fragments para las distintas vistas de Lista y detalle de heroes.

## Instalación y Configuración
1. Clona este repositorio:
   ```bash
   git clone https://github.com/dhp85/DbHeroKombat.git
   ```
2. Abre el proyecto en Android Studio.
3. Configura las dependencias necesarias en `build.gradle`.
4. Ejecuta la aplicación en un emulador o dispositivo físico.

## Uso
1. Inicia sesión con usuario y contraseña.
2. Selecciona un personaje con vida disponible.
3. En el detalle, interactúa con la barra de vida, usando los botones "Curarse" y "Recibir daño".
4. Si la vida del personaje llega a 0, regresa a la selección de personajes.

## Creditos
Desarrollado por Diego Herreros Parrón. 
- **LinKedln**: www.linkedin.com/in/diego-herreros-parrón-b73a0121b

