# Labyrinth

A 3D horror maze game in OpenGL and Java.

## Features

* Infinite generated mazes
    * The maze gets bigger with each maze
* Lighting-per-fragment (pixel)
* Spotlights
    * The player has a headlight
    * Towers have lights pointing to the maze
    * The orb has a light beneath it
* Minimap
* Mouse control
* Amazing collisions
* Creepy sounds and atmosphere
* Death from above included!
* Settings file
    * Change the game to your liking
        * Whacky effects may occur
* Optimization
    * No redundant vertices
    * Only the inner walls of the maze are drawn
    * Only the top visable vertices are drawn for the minimap
    * Only check collisions for walls that are close to the player

## Getting Started

In order to run Labyrinth on your own machine, you will need to compile the source code.

You will have to set the main class to: **src/desktop/src/com.ru.tgra.desktop.DesktopLauncher**.

At this point you can compile the game, but not run it without crashing. You also have to set the working directory to the **src/core/assets** directory.

### Prerequisites

You will need Java 8 in order to compile the game.

## Controls

Use WASD to move the character.

Use the arrow keys or the mouse to look around.

The controls are also displayed on the main menu of the game.

## Development

We used JetBrains IntelliJ Idea as our IDE of choice coding this game.

The game uses OpenGL and we use LibGDX in order to access OpenGL functionality.

### Built With

* [IntelliJ Idea](https://www.jetbrains.com/idea/) - IDE
* [LibGDX](https://libgdx.badlogicgames.com/) - OpenGL library
* [Java](https://www.java.com/en/) - Language

## Authors

* [Christian A. Jacobsen](https://github.com/ChristianJacobsen/)
* [Hilmar Tryggvason](https://github.com/Indexu/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Special thanks to Kári Halldórsson for teaching us collision checking, shaders and OpenGL.

### Font

* [Sinister Fonts](https://www.dafont.com/october-crow.font?text=Labyrinth) - October Crow

### Audio

* [ThrillShowX](https://www.youtube.com/watch?v=nUyrTDvA5SY) - Horror ambience
* [SoundJay](https://www.soundjay.com/heartbeat-sound-effect.html) - Heartbeat
* [SoundBible](http://soundbible.com/tags-gory.html) - Spear hit sound
* [FreeSound](https://freesound.org/) - Sound effects
    * [Spear hit ground](https://freesound.org/people/Benboncan/sounds/103628/)
    * [Portal](https://freesound.org/people/Bzourk/sounds/322059/)