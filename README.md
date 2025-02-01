# Pacman-Style Game
This repository contains a Java implementation of a Pacman-style game with a graphical user interface. 
The game includes dynamic upgrade mechanics, a robust scoring system, and a GUI built with Java Swing.

## Features
- **Dynamic Board Sizes**: Configure the game board size anywhere from 10x10 to 100x100 cells. 
- **Custom Upgrades**: Includes   upgrades that appear randomly, enhancing gameplay and strategy.
- **Graphical Interface**: Utilizes `JTable` for rendering the game board.
- **Score and Time Tracking**: Real-time updates for score, time, and lives within the game interface.
- **High Score Management**: High scores are recorded and persisted using Java's `Serializable` interface.
- **Keyboard Shortcuts**: Interrupt gameplay with a Ctrl+Shift+Q shortcut to quickly return to the main menu.

## Technologies used
- **Java 19**
- **Java Swing** - GUI framework (JFrame, JPanel, JTable, JList, JDialog)
- **Multithreading (Thread)**
- **Serialization (Serializable)** - Saves and loads app related data(high scores).
- **File Handling (InputStream, OutputStream)** - Manages data persistence.
- **MVC Architecture**


## Usage
Upon launching the application, the main menu will be displayed with the following options:
- **New Game**: Start a new game session. You will be prompted to select the board size.
- **High Scores**: View the high scores from previous sessions.
- **Exit**: Close the game application.

## Game Controls
- **Arrow Keys**: Move your character around the board.
- **Ctrl+Shift+Q**: Interrupt the game and return to the main menu.

## Known issues
- **Note:** If a large board is generated (e.g., 50x50), 
rendering the window may take some time. Consider using smaller values, such as 20x20, for better performance.

- Pac-Man sometimes appears slightly misaligned with the grid, shifting horizontally or vertically, and occasionally rendering above walls.

## Prerequisites
- **Java Development Kit (JDK)** 19.
- **GIT** for cloning the repository.
- A terminal or IDE (e.g., IntelliJ IDEA).

## Steps to Start the Application
### 1. Clone the Repository
Open a terminal and run:

```bash
git clone https://github.com/yourusername/your-repository.git
```
Replace yourusername and your-repository with your actual GitHub username and repository name.

### 2. Navigate to the Project Directory
Open a terminal and navigate to the root directory of your project:

```bash
cd /path/to/your/project
```

### 3. Compile the Project
Use the Java compiler to build the application:

- **macOS/Linux & Windows(PowerShell):**:
  ```bash
  javac -d out -cp src (Get-ChildItem -Path src -Recurse -Filter *.java).FullName
  ```
- **Windows (CMD only):**:
  ```bash
  javac -d out -cp src $(for /R %i in (*.java) do @echo %i)
  ```

### 4. Run the Application
Start the application using following command:
 ```bash
  java -cp out Logic.Main
  ```