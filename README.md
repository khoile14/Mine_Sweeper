# Minesweeper Game

## Overview

This project implements a Minesweeper game with a graphical user interface (GUI). The game is built using several classes, including a dynamic array list, a 2D grid, and a cell representation. The project aims to practice fundamental skills learned in Data Structure and Algorithms. The project has been reviewed and tested extensively to ensure smooth functionality and an enjoyable user experience.

The provided MineGUI is implemented using JavaFX. In order to compile and run MineGUI.java, the following steps need to be followed:

### Prerequisites

- Java 11 or higher
- JavaFX runtime 17.0.8 (SDK version) for your machine and operating system. Download from [here](https://gluonhq.com/products/javafx/).
  - For Windows, options are x86 (for 32-bit processors) and x64 (for 64-bit processors).
  - For Mac, options are x64 (for Intel processors) and aarch64 (for apple processors).
  - For Linux, options are x64(for Intel processors), arm32 (for 32-bit ARM processors), and aarch64(for 64-bit ARM processors).

### Steps to Compile and Run

1. Unzip the downloaded JavaFX file to a desired location outside of your project working folder. This is your JavaFX folder.
2. Add an environment variable pointing to the lib folder inside the JavaFX folder. Use the absolute path to your JavaFX folder to make the following steps easier.
   - For Linux/Mac, use command:
     ```
     export PATH_TO_FX=path/to/JavaFX/lib
     ```
     Example: on a Mac computer with JavaFX installed at /Users/projects/javafx-sdk-17.0.8/, you would type:
     ```
     export PATH_TO_FX=/Users/projects/javafx-sdk-17.0.8/lib
     ```
   - For Windows, use command:
     ```
     set PATH_TO_FX="path\to\JavaFX\lib"
     ```
     Example: on a Windows computer with JavaFX installed at c:\projects\javafx-sdk-17.0.8, you would type:
     ```
     set PATH_TO_FX="c:\projects\javafx-sdk-17.0.8\lib"
     ```
   - **Note:** Setting a path variable on the command line only works for that one command prompt session. For any new or additional prompts you open, you will need to keep using the "set" or "export" command. If you want to, you can also set the environmental variable JavaFX permanently by changing your system settings.
3. Compile MineGUI.java by specifying modules included.
   - For Linux/Mac, use command:
     ```
     javac --module-path $PATH_TO_FX --add-modules javafx.controls MineGUI.java
     ```
   - For Windows, use command:
     ```
     javac --module-path %PATH_TO_FX% --add-modules javafx.controls MineGUI.java
     ```
4. Run the generated MineGUI class in a similar way.
   - For Linux/Mac, use command:
     ```
     java --module-path $PATH_TO_FX --add-modules javafx.controls MineGUI
     ```
   - For Windows, use command:
     ```
     java --module-path %PATH_TO_FX% --add-modules javafx.controls MineGUI
     ```
