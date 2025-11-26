Gson (JSON) persistence setup
================================

This project uses Google Gson to serialize and deserialize Java objects as JSON for saving and loading application data.

What was added:
- `src/backend/Database.java` — Implements Gson serialization of lists and file-based persistence. It provides: `saveStudentProfiles`, `loadStudentProfiles`, `saveTeacherClasses`, `loadTeacherClasses`.
- `StudentProfile` & `ArtClass` — Swing components that are GUI objects were marked `transient` and `rebuildButton()` methods were added to reconstruct GUI components after deserialization.
- `frontend/StudentProfileOverview.java` — Loads profiles at startup and saves profiles when added/removed.
- `backend/Teacher.java` — Loads teacher classes and saves them when added.

Important: Gson dependency
-------------------------
This project does not include a dependency manager (Maven/Gradle). You must add the gson JAR to the classpath before compiling and running.

Download gson (example):
https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar

Run with the jar on the classpath from PowerShell (example):
```powershell
javac -cp ".;lib/gson-2.10.1.jar" -d bin src\**\*.java
java -cp ".;lib/gson-2.10.1.jar;bin" Main
```

If you prefer Maven or Gradle, add the gson dependency: `com.google.code.gson:gson:2.10.1`.

Behavior & Notes
----------------
- Saved files are located in the `data` directory in the project root. Files: `data/profiles.json`, `data/classes.json`.
- GUI components (JButton) are recreated after loading with `rebuildButton()`.
- `LocalDateTime` values are serialized/deserialized via registered Gson adapters.

If anything fails to compile, make sure `gson` is properly in your classpath.
## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
