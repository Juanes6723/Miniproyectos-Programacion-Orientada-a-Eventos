# Santiago Torres Rojas - 2380301 
# Josue David Cocoma Tascon - 2477087
# Juan Esteban Martinez Orobio - 2569452

# Miniproyectos-Programacion-Orientada-a-Eventos
# Se realizo la clase de personajes con sus atributos principales nombres, hp, mg, ataque, velocidad, suerte, defensa, daño critico entres otros

# Estructura del proyecto:

├── App.java               // Clase principal: ejecuta el combate y gestiona el flujo
├── Characters.java        // Clase base para héroes y enemigos
├── The_Hero.java          // Subclase de Characters: personaje principal
├── Yangus.java            // Subclase de Characters: personaje aliado
├── Jessica.java           // Subclase de Characters: personaje aliado
├── Angelo.java            // Subclase de Characters: personaje aliado
├── Enemy.java             // Subclase de Characters: enemigos con IA y tipos
├── TypeEnemy.java         // Enum que define el comportamiento de los enemigos

# Caracteristicas:

# - Sistema de combate por turnos basado en velocidad.
# - 4 personajes jugables: The Hero, Yangus, Jessica y Angelo.
# - 4 tipos de enemigos: AGGRESSIVE, HEALER, DEFENSIVE, SORCERER.
# - IA enemiga: decide acciones según su tipo y estado.

# Acciones disponibles:
# - Atacar
# - Defenderse
# - Usar habilidad
# - Huir

# Has entrado en combate
# Te enfrentas a: Limo Berenjeno Pinchorugo Labibabosa
# Primero en atacar será: Jessica (velocidad: 10)

# Turno de: Jessica
# ¿Qué deseas hacer?
1. Atacar
2. Defenderse
3. Usar habilidad
4. Huir
# Elige una opción:


