# Space Invaders - TP POO

Implementación del clásico juego Space Invaders desarrollado en Java como trabajo práctico de Programación Orientada a Objetos.

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Funcionalidades](#funcionalidades)
- [Arquitectura](#arquitectura)
- [Patrones de Diseño](#patrones-de-diseño)
- [Buenas Prácticas](#buenas-prácticas)
- [Compilación y Ejecución](#compilación-y-ejecución)
- [Controles](#controles)

## 🎮 Descripción

Space Invaders es un juego de acción arcade donde el jugador controla una nave que debe destruir oleadas de enemigos alienígenas mientras evita sus proyectiles. El juego incluye:

- Sistema de niveles progresivos con dificultad creciente
- Muros de energía defensivos
- Sistema de puntuación y ranking
- Interfaz gráfica moderna usando Swing

## 📁 Estructura del Proyecto

```
src/main/java/org/example/
├── app/                    # Componentes de interfaz gráfica
│   ├── GamePanel.java      # Panel principal del juego
│   ├── MenuPanel.java      # Panel del menú principal
│   ├── GameOverPanel.java  # Panel de fin de partida
│   ├── RankingPanel.java   # Panel de ranking
│   ├── GameWindow.java     # Ventana principal de la aplicación
│   └── Main.java           # Punto de entrada de la aplicación
│
├── controlador/            # Lógica de control y coordinación
│   └── ControladorJuego.java  # Controlador principal (MVC)
│
├── modelo/                 # Modelo de datos y lógica del juego
│   ├── Juego.java          # Clase principal del juego
│   ├── Ranking.java        # Sistema de ranking
│   ├── EntradaRanking.java # Entrada individual del ranking
│   ├── JugadorEnJuego.java # Estado del jugador durante partida
│   ├── Partida.java        # Información de una partida
│   │
│   └── entidad/            # Entidades del juego
│       ├── Nave.java       # Clase abstracta base para naves
│       ├── NaveJugador.java    # Nave controlada por el jugador
│       ├── NaveEnemiga.java   # Naves enemigas
│       ├── Proyectil.java      # Proyectiles del juego
│       ├── MuroEnergia.java    # Muros defensivos
│       ├── AlienFormation.java # Gestión de formación enemiga
│       └── ResolverColisiones.java  # Sistema de colisiones

```

## ⚙️ Funcionalidades

### Core del Juego
- **Movimiento del jugador**: Control horizontal con flechas o WASD
- **Sistema de disparo**: Cooldown para evitar spam de proyectiles
- **Formación enemiga**: Movimiento coordinado con rebote en bordes
- **Progresión de niveles**: Dificultad creciente, más enemigos por nivel
- **Sistema de vidas**: 3 vidas iniciales, se pierde una por impacto enemigo
- **Muros de energía**: Defensas destructibles que protegen al jugador

### Sistema de Ranking
- **Top 5 puntuaciones**: Almacena las 5 mejores puntuaciones
- **Registro automático**: Si la puntuación entra en el top 5, solicita nombre
- **Visualización**: Tabla con posición, nombre, niveles superados y puntuación

### Interfaz de Usuario
- **Menú principal**: Acceso a nueva partida y ranking
- **Pantalla de juego**: HUD con puntuación, nivel y vidas
- **Pantalla de Game Over**: Muestra resultados y opciones para continuar
- **Panel de ranking**: Visualización ordenada de mejores puntuaciones

## 🏗️ Arquitectura

El proyecto sigue una arquitectura **MVC (Modelo-Vista-Controlador)**:

### Modelo (`modelo/`)
Contiene toda la lógica del juego y los datos:
- **Juego**: Estado y reglas del juego
- **Entidades**: Objetos del juego (naves, proyectiles, muros)
- **Ranking**: Sistema de persistencia de puntuaciones
- **Estado del jugador**: Vidas y puntuación

### Vista (`app/`)
Componentes de interfaz gráfica usando Swing:
- Paneles especializados para cada pantalla
- Renderizado del juego
- Interacción con el usuario

### Controlador (`controlador/`)
Coordina entre modelo y vista:
- **ControladorJuego**: Gestiona el flujo de la aplicación
- Bucle de juego a 60 FPS
- Detección de eventos y cambios de estado
- Transiciones entre pantallas

### Separación de Responsabilidades

```
┌─────────────────┐
│   Controlador   │  ← Coordina todo
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
┌───▼───┐ ┌──▼────┐
│Modelo │ │ Vista │
└───────┘ └───────┘
```

## 🎨 Patrones de Diseño

### 1. **MVC (Modelo-Vista-Controlador)**
- Separación clara de responsabilidades
- Facilita mantenimiento y extensibilidad

### 2. **Patrón Template Method**
- Clase abstracta `Nave` define estructura común
- Clases hijas implementan detalles específicos

### 3. **Patrón Factory Method**
- `Proyectil.playerBullet()` y `Proyectil.enemyBullet()`
- `MuroEnergia.createBlock()`
- Encapsula la creación de objetos

### 4. **Patrón Singleton (implícito)**
- `ControladorJuego` actúa como único punto de entrada
- `Juego` se crea una vez y se reutiliza

### 5. **Utility Class**
- `ResolverColisiones`: Métodos estáticos para lógica de colisiones
- `AlienFormation`: Métodos estáticos para gestión de formación

### 6. **Comparable**
- `EntradaRanking` implementa `Comparable` para ordenamiento automático

## ✅ Buenas Prácticas

### 1. **Encapsulación**
- Campos privados con acceso controlado
- Métodos públicos solo cuando es necesario
- Uso de `protected` en clase abstracta para herencia

### 2. **Principio de Responsabilidad Única (SRP)**
- Cada clase tiene una responsabilidad específica
- `Juego`: Lógica del juego
- `Ranking`: Gestión de puntuaciones
- `ResolverColisiones`: Solo colisiones

### 3. **Inmutabilidad donde es posible**
- Constantes finales (`WIDTH`, `HEIGHT`)
- Campos finales donde no cambian (`speedPixelsPerSecond`)

### 4. **Thread Safety**
- `ConcurrentHashMap` para teclas presionadas
- `volatile` para flags de control de hilos
- Uso de `SwingUtilities.invokeLater()` para actualizaciones de UI

### 5. **Documentación**
- JavaDoc completo en todas las clases públicas
- Comentarios explicativos en código complejo
- README con estructura y arquitectura

### 6. **Nomenclatura**
- Nombres descriptivos y en español (requerimiento del TP)
- Convenciones Java estándar
- Uso de verbos para métodos (`agregarEntrada`, `perderVida`)

### 7. **Manejo de Estados**
- Estados claramente definidos ("MENU", "EN_JUEGO", "GAME_OVER")
- Transiciones de estado controladas

### 8. **Separación de Concerns**
- Lógica de renderizado separada de lógica de juego
- Detección de colisiones centralizada
- UI desacoplada del modelo

## 🔧 Compilación y Ejecución

### Requisitos
- Java 24 o superior
- Maven (para gestión de dependencias)

### Compilar
```bash
mvn clean compile
```

### Ejecutar
```bash
mvn exec:java -Dexec.mainClass="org.example.app.Main"
```

O directamente:
```bash
java -cp target/classes org.example.app.Main
```

## 🎮 Controles

| Acción | Tecla |
|--------|-------|
| Mover izquierda | ← o **A** |
| Mover derecha | → o **D** |
| Disparar | **Espacio** |

## 📊 Flujo del Juego

1. **Inicio**: Menú principal con opciones
2. **Nueva Partida**: Inicializa nivel 1 con 3 vidas
3. **Juego**: 
   - Movimiento y disparos
   - Eliminación de enemigos (10 puntos c/u)
   - Protección con muros
   - Avance de nivel al eliminar todos los enemigos
4. **Game Over**: 
   - Se pierden todas las vidas
   - Si la puntuación entra en top 5, solicita nombre
   - Muestra panel con opciones
5. **Ranking**: Visualización de mejores puntuaciones

## 🔄 Ciclo de Vida de una Partida

```
MENU → INICIALIZAR PARTIDA → EN_JUEGO 
  ↓
COMPLETAR NIVEL → TRANSICION_NIVEL → EN_JUEGO (nivel siguiente)
  ↓
PERDER TODAS LAS VIDAS → GAME_OVER → (Ranking?) → MENU/NUEVA PARTIDA
```

## 🎯 Características Técnicas

- **FPS**: 60 frames por segundo
- **Resolución**: 800x600 píxeles
- **Threading**: Bucle de juego en hilo separado
- **Detección de colisiones**: Sistema basado en `Rectangle.intersects()`
- **Persistencia**: Ranking en memoria (se puede extender a archivo)

## 📝 Notas de Desarrollo

- El proyecto utiliza Swing para la interfaz gráfica
- El bucle de juego usa delta time para movimiento independiente de FPS
- El sistema de colisiones se resuelve en cada frame
- Los muros de energía se destruyen por bloques para mayor realismo

## 👥 Autores

Lucas Tadeo Garcia Lauman
Lucia España
Matias Barzaghi

## 📄 Licencia

Trabajo práctico académico - UADE

---

**Versión**: 1.0  
**Fecha**: 2025

