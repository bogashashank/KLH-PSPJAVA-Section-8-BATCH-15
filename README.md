# Music Playlist & Listening Stats Manager

A Java-based console application for organizing playlists, tracking play
counts, and analyzing listening habits.

## Project Overview

The **Music Playlist & Listening Stats Manager** is a lightweight,
offline Java console application designed to manage personal music data
and demonstrate core Java and Object-Oriented Programming concepts.

The project models songs and playlists, tracks listening activity, and
provides simple statistics through a menu-driven command-line interface.

## Team

-   **Boga Shashank** --- 2620040175
-   **Yarlagadda Druveen** --- 2620030528
-   **Batch:** 15
-   **Subject Code:** 26SC1101E

## Objectives

The project focuses on six main objectives:

1.  **Model Music Data in OOP**
    -   Use `Song` and `Playlist` classes.
    -   Encapsulate data using fields, constructors, and helper methods.
2.  **Playlist Management**
    -   Create and manage named playlists.
    -   Add and remove songs.
3.  **Listening Behaviour Tracking**
    -   Increment play counts whenever a song is played.
    -   Maintain play-count information for analysis.
4.  **Listening Statistics**
    -   Calculate total songs and plays.
    -   Calculate total listening time.
    -   Identify the most-played song and top artists.
5.  **Search and Filtering**
    -   Search songs by title or artist.
    -   Sort playlists by play count.
6.  **Favorites**
    -   Mark songs as favorites.
    -   View favorited tracks across playlists.

## Current Demonstration Features

The demonstrated console program exposes four menu options:

### 1. Analyze Play Counts

-   Accepts the number of tracks.
-   Stores play counts in a **1D array**.
-   Calculates the total number of streams.
-   Finds the most-played track.

### 2. Genre Breakdown Matrix

-   Uses a **4 × 3 two-dimensional array**.
-   Represents four weeks across three genres:
    -   Pop
    -   Rock
    -   Hip-Hop
-   Uses nested loops to calculate genre totals.

### 3. Superfan Streak

-   Accepts a consecutive listening streak in days.
-   Uses **recursion** to calculate a factorial-based Superfan
    Multiplier.
-   Example: a 5-day streak produces `5! = 120`.

### 4. Exit

-   Displays a closing message.
-   Closes the `Scanner`.
-   Terminates the program cleanly.

## Java Concepts Demonstrated

The project demonstrates several fundamental Java programming concepts:

-   Classes and Objects
-   Object-Oriented Programming
-   Encapsulation
-   Constructors and methods
-   `java.util.Scanner`
-   1D Arrays
-   2D Arrays
-   Nested `for` loops
-   `switch` statements
-   Recursion
-   Conditional statements
-   Console input/output

## Architecture

The presentation describes a three-layer architecture:

### Presentation / User Interface Layer

A text-based menu loop accepts user input using `java.util.Scanner` and
displays formatted responses in the terminal.

### Application / Business Logic Layer

Contains operations such as:

-   Create
-   Add
-   Remove
-   Play
-   Calculate statistics
-   Search
-   Sort
-   Toggle favorites

### Data / Model Layer

The project uses `Song` and `Playlist` POJO classes to represent runtime
music data.

## Example Output

### Play Count Analysis

For play counts:

``` text
120
345
89
```

The demonstrated output is:

``` text
Total Playlist Streams: 554
Most Played Track Count: 345
```

### Genre Matrix

The demonstrated four-week genre scenario produces:

``` text
Pop: 210
Rock: 90
Hip-Hop: 130
```

### Superfan Streak

For a 5-day streak:

``` text
Superfan Multiplier for 5 days: 120
```

This is based on:

``` text
5! = 5 × 4 × 3 × 2 × 1 = 120
```

## Requirements

-   Java Development Kit (JDK) 11 or later
-   Command-line terminal
-   No external database is required.
-   The presentation demonstrates the application using OpenJDK 21.

## How to Run

### 1. Clone or download the project

Place the Java source files in a project directory.

### 2. Open the terminal

Navigate to the directory containing the Java source file.

### 3. Compile

If the main source file is `Main.java`:

``` bash
javac Main.java
```

### 4. Run

``` bash
java Main
```

### 5. Use the menu

Select an option from the displayed menu:

``` text
1. Analyze Playlist Play Counts
2. Monthly Genre Breakdown Matrix
3. Calculate Superfan Streak
4. Exit
```

Enter the requested values and follow the console prompts.

## Project Structure

The presentation describes the application around the following model
classes:

``` text
Music Playlist & Listening Stats Manager
│
├── Main.java
├── Song.java
└── Playlist.java
```

The exact source-file organization may vary depending on the submitted
implementation.

## Future Scope

The presentation proposes the following enhancements:

1.  **File-backed Storage**
    -   Save and load playlists using CSV or JSON.
    -   Use Java I/O and potentially Jackson for JSON handling.
2.  **JavaFX Desktop UI**
    -   Replace the console interface with a graphical desktop
        application.
    -   Add tables, buttons, and visual elements.
3.  **Real Audio Playback**
    -   Integrate JavaFX Media or JLayer.
    -   Play actual MP3 files instead of only simulating play events.
4.  **Cloud Sync & Sharing**
    -   Connect playlists to Firebase or a REST backend.
    -   Synchronize libraries across devices.
5.  **Smart Recommendations**
    -   Recommend songs based on listening history.
    -   Potentially use a collaborative-filtering model with a Python
        backend.

## Key Takeaways

-   The project demonstrates how Java OOP can be used to model music
    data.
-   Arrays and loops are used for playlist and genre statistics.
-   Recursion is demonstrated through the Superfan Streak calculation.
-   The application is designed to be lightweight and offline.
-   The project provides a foundation that can later be extended with
    persistence, GUI, audio playback, cloud synchronization, and
    recommendations.

## Presentation Summary

The accompanying presentation covers:

1.  Introduction
2.  Problem Statement
3.  Project Objectives
4.  Existing System
5.  Proposed System
6.  System Architecture
7.  Functionalities
8.  Java Implementation
9.  Program Output
10. Future Scope
11. Conclusion

## License

This project was created as an academic Java/OOP project.
