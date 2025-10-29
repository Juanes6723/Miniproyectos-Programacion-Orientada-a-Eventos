# Mini proyecto 2

Elaborado por: 
- Josue David Cocoma Tascon - 2477087
- Juan Esteban Martinez Orobio - 2569452

Este es un juego de rol por turnos basado en Dragon Quest VIII desarrollado en Java, donde el jugador controla un grupo de héroes que enfrentan enemigos en combate estratégico. El sistema incluye habilidades, defensa, orden de turnos, efectos de estado y la aparición dinámica de un mini jefe.

## Características

- Bucle de combate por turnos con iniciativa dinámica basada en la velocidad del personaje.
- IA enemiga automática que selecciona objetivos válidos y ejecuta acciones.
- Procesamiento de efectos de estado al comienzo de cada turno.
- Sistema de habilidades que utiliza enumeraciones y validación del costo de MP.
- Lógica de aparición de minijefes y enemigos.
- Visualización del estado del combate en tiempo real con porcentajes de HP/MP.
- Opciones de defensa y huida para tomar decisiones tácticas.
- Sistema de menús interactivo para iniciar batallas e inspeccionar las estadísticas del grupo.
- 
##  Arquitectura del proyecto

### `App.java`

Archivo de entrada principal. Su única función es lanzar el juego.


### Clases de soporte
-   `Characters.java`: Clase base para todos los combatientes    
-   `Heroes.java`: Subclase para personajes controlados por el jugador    
-   `Enemy.java`: Subclase para enemigos con IA    
-   `SkillsList.java`: Enum que define las habilidades disponibles    
-   `TypeEnemy.java`: Enum que clasifica los tipos de enemigos (ej. SORCERER, HEALER, MINI_BOSS)

## Lógica del combate

1.  **Inicialización**   
    -   Se crean héroes y enemigos con estadísticas y habilidades        
    -   Se calcula el orden de ataque según la velocidad        
2.  **Bucle de combate**    
    -   Cada personaje vivo actúa en orden        
    -   Se procesan efectos de estado antes de cada acción        
    -   Los héroes permiten entrada del jugador; los enemigos actúan automáticamente        
3.  **Mini jefe**    
    -   Cuando todos los enemigos normales han sido derrotados, aparece el mini jefe        
    -   Se agrega al campo de batalla y al orden de ataque        
4.  **Condiciones de victoria**    
    -   El combate termina cuando todos los héroes o todos los enemigos han sido derrotados        
    -   Se muestra un mensaje de victoria o derrota

### `InterfazCombate.java`
Pantalla de combate con fondo personalizado. Contiene cuatro botones invisibles listos para activar acciones (como atacar, usar habilidades, etc.).

Al abrir esta interfaz, se reproduce música de batalla (`musicaBatalla.wav`).


### `InterfazPersonajes.java`
Muestra información detallada de los héroes del juego. Cada botón invisible está posicionado sobre un personaje en la imagen de fondo. Al hacer clic, se actualiza un panel transparente con:
- Nombre
- HP / MP
- Ataque / Defensa / Velocidad
- Probabilidad y daño crítico
- Habilidades

También incluye un botón para regresar a `InterfazInicio`.

### `InterfazEnemigos.java`
Similar a la interfaz de personajes, pero muestra enemigos. Cada botón invisible está posicionado sobre un enemigo en la imagen de fondo. Al hacer clic, se muestra su información en el panel transparente.

Incluye botón para regresar a `InterfazInicio`.

### `Audio.java`
Clase utilitaria para manejar música de fondo:
- `reproducir(String ruta)` — reproduce un archivo `.wav`
- `detener()` — detiene la música actual
- `mutear()` / `desmutear()` — controla el volumen
- `aplicarVolumen(int porcentaje)` — ajusta el volumen en decibeles

Esta clase se usa en cada interfaz para reproducir la música correspondiente.


## Recursos

- Imágenes: ubicadas en `img/`
- Música: ubicada en `music/`
  - `musicaInicio.wav`
  - `musicaBatalla.wav`

## Lógica general

- Cada interfaz extiende `JFrame` y se maximiza a pantalla completa.
- Se usan `JPanel` personalizados para mostrar imágenes de fondo.
- Los botones invisibles permiten interacción sin alterar la estética.
- La música cambia automáticamente según la pantalla.
- La información de personajes y enemigos se muestra dinámicamente en paneles transparentes.

