# Mini proyecto 1

Elaborado por: 
- Josue David Cocoma Tascon - 2477087
- Juan Esteban Martinez Orobio - 2569452

Este es un juego de rol por turnos basado en Dragon Quest VIII desarrollado en Java, donde el jugador controla un grupo de héroes que enfrentan enemigos en combate estratégico. El sistema incluye habilidades, defensa, orden de turnos, efectos de estado y la aparición dinámica de un mini jefe.

## Características

- Bucle de combate por turnos con iniciativa dinámica basada en la velocidad del personaje.
- IA enemiga automática que selecciona objetivos válidos y ejecuta acciones.
- Procesamiento de efectos de estado al comienzo de cada turno.
- Sistema de habilidades que utiliza enumeraciones y validación del costo de MP.
- Lógica de aparición de minijefes que se activa cuando se derrotan a todos los enemigos normales.
- Visualización del estado del combate en tiempo real con porcentajes de HP/MP.
- Opciones de defensa y huida para tomar decisiones tácticas.
- Sistema de menús interactivo para iniciar batallas e inspeccionar las estadísticas del grupo.
- 
##  Arquitectura del proyecto

### `App.java`

Archivo de entrada principal. Su única función es lanzar el juego:

    public  class  App  { 
        public  static  void  main(String[] args)  { 
            Game  game  =  new  Game(); 
            game.creation(); // Inicializa personajes y combate
            game.showMenu(); // Muestra el menú principal  
            } 
	}

### `Game.java`

Clase central que contiene toda la lógica del juego:
-   Creación de personajes (`creation()`)
-    Bucle de combate (`start()`) 
-   Gestión de turnos (`turnPlayer()`, `turnEnemy()`)    
-   Activación del mini jefe (`verifyMiniBoss()`)    
-   Visualización del estado (`showStatus()`)    
-   Menú principal (`showMenu()`)
    

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