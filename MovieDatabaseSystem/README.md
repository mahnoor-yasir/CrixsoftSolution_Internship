<div align="center">

# 🎬 MOVIE DATABASE MANAGEMENT SYSTEM

### A Professional Java-Based Movie Management Application

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![VS Code](https://img.shields.io/badge/IDE-VS%20Code-blue.svg)](https://code.visualstudio.com/)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Mac%20%7C%20Linux-lightgrey.svg)]()

</div>

---

# 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Installation & Setup](#-installation--setup)
- [How to Run](#-how-to-run)
- [Application Features](#-application-features)
- [Screenshots](#-screenshots)
- [API Reference](#-api-reference)
- [Troubleshooting](#-troubleshooting)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)
- [Author](#-author)

---

# 📖 Overview

The **Movie Database Management System** is a professional, feature-rich Java console application designed to manage a movie collection efficiently. It provides a complete CRUD (Create, Read, Update, Delete) interface with advanced search capabilities, statistics, and data export functionality.

## 🎯 Purpose

This application serves as a comprehensive solution for:

- **Movie Enthusiasts** – Organize and track movie collections.
- **Students** – Learn Java programming through a real-world project.
- **Developers** – Understand object-oriented programming, MVC architecture, collections, and file handling.

---

# ✨ Features

## 📊 Core Features

| Feature | Description | Status |
|---------|-------------|--------|
| 📺 **Show All Movies** | Display complete movie list with details | ✅ |
| 🔍 **Search by Title** | Find movies using partial or full title | ✅ |
| 🎭 **Filter by Genre** | View movies by selected genre | ✅ |
| ⭐ **Sort by Rating** | Sort movies based on IMDb rating | ✅ |
| 📊 **View Statistics** | Display movie database statistics | ✅ |
| 🎬 **Watch Movie** | Open the movie's official YouTube link | ✅ |
| ➕ **Add New Movie** | Add movies with complete information | ✅ |
| 🗑️ **Delete Movie** | Remove movies from the database | ✅ |
| ✏️ **Edit Movie** | Update existing movie information | ✅ |
| 💾 **Save to File** | Save all movie data locally | ✅ |
| 📤 **Export to CSV** | Export movie records as CSV | ✅ |
| 🔍 **Advanced Search** | Search using multiple filters | ✅ |

## 🎯 Advanced Features

- Auto-generated Movie IDs
- Input Validation
- Exception Handling
- Local File Storage
- CSV Export
- Browser Integration
- Dynamic Statistics
- Professional Console Interface

---

# 🛠️ Technology Stack

## Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17+ | Programming Language |
| **Java I/O** | Built-in | File Handling |
| **Java Collections** | Built-in | Data Management |
| **Object-Oriented Programming** | Java | Application Architecture |

## Development Tools

| Tool | Purpose |
|------|---------|
| **Visual Studio Code** | Code Editor |
| **Java Extension Pack** | Java Development |
| **Git** | Version Control |
| **GitHub** | Project Hosting |

## Java Packages Used

| Package | Purpose |
|---------|---------|
| `java.util.*` | Scanner, Collections, Utilities |
| `java.io.*` | File Reading & Writing |
| `java.util.stream.*` | Stream API |
| `java.awt.Desktop` | Open Movie Links in Browser |

---

# 📁 Project Structure

```text
MovieDatabaseSystem/
│
├── src/
│   └── com/
│       └── moviedb/
│           ├── Main.java
│           ├── model/
│           │   └── Movie.java
│           ├── service/
│           │   └── MovieService.java
│           ├── utils/
│           │   └── DataGenerator.java
│           └── ui/
│               └── ConsoleUI.java
│
└── README.md
```

## Package Description

| Package | Purpose |
|----------|---------|
| `com.moviedb` | Application Entry Point |
| `com.moviedb.model` | Entity Classes |
| `com.moviedb.service` | Business Logic |
| `com.moviedb.utils` | Utility Classes |
| `com.moviedb.ui` | Console User Interface |

---

# 🚀 Installation & Setup

## Prerequisites

Before running this project, install the following:

- Java JDK 17 or above
- Visual Studio Code
- Java Extension Pack for VS Code
- Git (Optional)

## Verify Java Installation

```bash
java -version
javac -version
```

Both commands should display Java version **17 or higher**.

## Clone the Repository

```bash
git clone https://github.com/yourusername/MovieDatabaseSystem.git
```

## Navigate to the Project

```bash
cd MovieDatabaseSystem
```

## Compile the Project

```bash
javac -d bin src/com/moviedb/*.java src/com/moviedb/model/*.java src/com/moviedb/service/*.java src/com/moviedb/ui/*.java src/com/moviedb/utils/*.java
```

## Run the Project

```bash
java -cp bin com.moviedb.Main
```

---
# 🎮 How to Run

## Method 1: Using VS Code (Recommended)

1. Open **Visual Studio Code**.
2. Open the **MovieDatabaseSystem** project folder.
3. Make sure the **Extension Pack for Java** is installed.
4. Open:

```text
src/com/moviedb/Main.java
```

5. Click the **▶ Run** button in the top-right corner.

The application will automatically compile and launch.

---

## Method 2: Using Terminal

Navigate to the project directory:

```bash
cd MovieDatabaseSystem
```

Compile the project:

```bash
javac -d bin src/com/moviedb/*.java src/com/moviedb/model/*.java src/com/moviedb/service/*.java src/com/moviedb/ui/*.java src/com/moviedb/utils/*.java
```

Run the application:

```bash
java -cp bin com.moviedb.Main
```

---

## Method 3: One-Line Command

```bash
cd MovieDatabaseSystem && javac -d bin src/com/moviedb/*.java src/com/moviedb/model/*.java src/com/moviedb/service/*.java src/com/moviedb/ui/*.java src/com/moviedb/utils/*.java && java -cp bin com.moviedb.Main
```

---

# 📱 Application Features

## Main Menu

```text
============================================
        🎬 MOVIE DATABASE SYSTEM
============================================

📋 MAIN MENU

1. 📺 Show All Movies
2. 🔍 Search Movie by Title
3. 🎭 Filter Movies by Genre
4. ⭐ Sort Movies by IMDb Rating
5. 📊 View Statistics
6. 🎬 Watch Movie
7. ➕ Add New Movie
8. 🗑️ Delete Movie
9. ✏️ Edit Movie
10. 💾 Save Database
11. 📤 Export to CSV
12. 🔍 Advanced Search
0. 🚪 Exit

Enter your choice:
```

---

# 📺 Feature Descriptions

## 1. Show All Movies

Displays every movie available in the database.

Each record includes:

- Movie ID
- Movie Title
- Release Year
- Genres
- Director
- IMDb Rating
- Status

---

## 2. Search Movie by Title

Allows searching by:

- Full Movie Name
- Partial Movie Name

Example:

```text
Search:

The Godfather

or

Godfather
```

---

## 3. Filter by Genre

Displays movies belonging to a specific genre.

Supported genres include:

- Action
- Adventure
- Animation
- Biography
- Comedy
- Crime
- Drama
- Fantasy
- Horror
- Mystery
- Romance
- Sci-Fi
- Thriller
- War

---

## 4. Sort Movies

Sort movies using IMDb rating.

Available options:

- Highest Rating First
- Lowest Rating First

---

## 5. View Statistics

Displays important database statistics.

Example:

```text
Total Movies

Average IMDb Rating

Highest Rated Movie

Lowest Rated Movie

Latest Movie

Oldest Movie
```

---

## 6. Watch Movie

Enter a Movie ID to:

- View complete movie information
- Open the official YouTube movie link in your default browser

---

## 7. Add New Movie

Users can add a new movie by entering:

- Movie Title
- Release Year
- Genres
- Director
- Cast Members
- IMDb Rating
- YouTube Movie Link
- Description

Movie IDs are automatically generated.

---

## 8. Delete Movie

Delete a movie using its unique Movie ID.

Confirmation is requested before deletion.

---

## 9. Edit Movie

Update existing information including:

- Title
- Release Year
- Genres
- Director
- IMDb Rating
- YouTube Link
- Description

Press **Enter** to keep the current value.

---

## 10. Save Database

Stores all movie records in a local binary file.

Default file:

```text
movies.dat
```

The application automatically loads this file during startup if it exists.

---

## 11. Export to CSV

Exports all movies into CSV format.

The exported file can be opened using:

- Microsoft Excel
- Google Sheets
- LibreOffice Calc

---

## 12. Advanced Search

Search movies using multiple filters.

Supported filters:

- Movie Title
- Genre
- Director
- Release Year
- Minimum IMDb Rating

Filters can be combined for more accurate search results.

---
# 📸 Screenshots

## Dashboard

```text
==========================================================
                 🎬 MOVIE DATABASE SYSTEM
==========================================================

               📋 MAIN MENU

1. 📺 Show All Movies
2. 🔍 Search Movie by Title
3. 🎭 Filter by Genre
4. ⭐ Sort by IMDb Rating
5. 📊 View Statistics
6. 🎬 Watch Movie
7. ➕ Add New Movie
8. 🗑️ Delete Movie
9. ✏️ Edit Movie
10. 💾 Save Database
11. 📤 Export to CSV
12. 🔍 Advanced Search
0. 🚪 Exit

==========================================================
```

---

## Movie Listing

```text
===============================================================================================
ID        Title                     Year   Genre               Rating   Director
===============================================================================================
MV-0001   The Shawshank Redemption  1994   Drama               9.3      Frank Darabont
MV-0002   The Dark Knight           2008   Action, Crime       9.0      Christopher Nolan
MV-0003   Inception                 2010   Action, Sci-Fi      8.8      Christopher Nolan
MV-0004   Interstellar              2014   Adventure, Sci-Fi   8.7      Christopher Nolan
===============================================================================================
```

---

## Statistics

```text
=================================================
              DATABASE STATISTICS
=================================================

Total Movies            : 100

Average IMDb Rating     : 8.45

Highest Rated Movie     : The Shawshank Redemption

Highest Rating          : 9.3

Lowest Rated Movie      : Movie X

Lowest Rating           : 6.2

=================================================
```

---

# 🔧 API Reference

## Movie Class

```java
public class Movie {

    private String id;
    private String title;
    private int year;
    private List<String> genres;
    private String director;
    private List<String> cast;
    private double imdbRating;
    private String youtubeLink;
    private String description;
    private String status;

}
```

---

## MovieService Class

```java
public class MovieService {

    // CRUD Operations
    public List<Movie> getAllMovies();

    public Movie getMovieById(String id);

    public boolean addMovie(Movie movie);

    public boolean deleteMovie(String id);

    public boolean updateMovie(Movie movie);

    // Search Operations
    public List<Movie> searchByTitle(String title);

    public List<Movie> filterByGenre(String genre);

    public List<Movie> advancedSearch(
            String title,
            String genre,
            String director,
            Double minimumRating,
            Integer releaseYear);

    // Statistics
    public Map<String, Object> getStatistics();

    // File Handling
    public void saveMovies();

    public void loadMovies();

    public void exportCSV();

    // Utilities
    public String generateMovieId();

}
```

---

# 🐞 Troubleshooting

| Problem | Solution |
|---------|----------|
| Java not recognized | Install Java JDK 17+ and add it to PATH |
| Main class not found | Ensure `Main.java` is inside `src/com/moviedb/` |
| Package does not exist | Verify package names match the folder structure |
| Movie not found | Enter a valid Movie ID |
| Save failed | Check file permissions |
| CSV export failed | Make sure the output folder exists |
| Browser doesn't open | Verify Desktop API is supported on your OS |

---

## Common Commands

### Check Java Version

```bash
java -version

javac -version
```

---

### Clean Project

```bash
rmdir /s /q bin

mkdir bin
```

---

### Compile Again

```bash
javac -d bin src/com/moviedb/*.java src/com/moviedb/model/*.java src/com/moviedb/service/*.java src/com/moviedb/ui/*.java src/com/moviedb/utils/*.java
```

---

### Run Project

```bash
java -cp bin com.moviedb.Main
```

---

### Correct Folder Structure

```text
MovieDatabaseSystem/

src/

com/

moviedb/

Main.java

model/

Movie.java

service/

MovieService.java

utils/

DataGenerator.java

ui/

ConsoleUI.java
```

---
# 🚀 Future Enhancements

The following features can be implemented in future versions to improve the functionality and user experience of the Movie Database Management System.

## 🎯 Planned Features

- [ ] User Authentication System
- [ ] Multiple User Roles (Admin & User)
- [ ] Password Encryption
- [ ] Movie Poster Management
- [ ] Banner Image Support
- [ ] Actor Management
- [ ] Director Management
- [ ] Producer Management
- [ ] Genre Management
- [ ] Review & Rating System
- [ ] Favorite Movies
- [ ] Personal Watchlist
- [ ] Movie Recommendation System
- [ ] Recently Added Movies
- [ ] Trending Movies
- [ ] Dashboard Analytics
- [ ] Interactive Charts
- [ ] Import Movies from CSV
- [ ] Backup & Restore Database
- [ ] Multi-language Support
- [ ] Dark & Light Theme
- [ ] REST API Integration
- [ ] TMDB API Integration
- [ ] IMDb API Integration
- [ ] Cloud Database Support
- [ ] Mobile Application
- [ ] Web Application using Spring Boot

---

## 💻 Technical Improvements

- [ ] MySQL Database Integration
- [ ] PostgreSQL Support
- [ ] SQLite Support
- [ ] Spring Boot Migration
- [ ] Hibernate ORM
- [ ] Unit Testing using JUnit
- [ ] Logging Framework
- [ ] Configuration Files
- [ ] Docker Support
- [ ] Maven Build System
- [ ] Gradle Build System
- [ ] Continuous Integration (GitHub Actions)
- [ ] Performance Optimization
- [ ] Better Exception Handling
- [ ] Code Documentation using JavaDoc

---

# 🤝 Contributing

Contributions are always welcome.

If you would like to improve this project, please follow these steps.

## Step 1

Fork this repository.

## Step 2

Create a new branch.

```bash
git checkout -b feature/YourFeatureName
```

---

## Step 3

Commit your changes.

```bash
git commit -m "Added a new feature"
```

---

## Step 4

Push your branch.

```bash
git push origin feature/YourFeatureName
```

---

## Step 5

Open a Pull Request on GitHub.

---

## Contribution Guidelines

Please follow these guidelines before submitting your contribution.

- Write clean and readable code.
- Follow Java naming conventions.
- Keep methods small and reusable.
- Add comments where necessary.
- Test your code before submitting.
- Update the README if required.

---

# 📄 License

This project is licensed under the **MIT License**.

```text
MIT License

Copyright (c) 2026

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
```

---

# 👨‍💻 Author

**Mahnoor Yasir**

**Bachelor of Science in Computer Science (BSCS)**

University of Management and Technology (UMT)

### Connect with Me

**GitHub**

```
https://github.com/mahnoor-yasir
```

**LinkedIn**

```[
https://www.linkedin.com/in/mahnoor-yasir
```

**Email**

```
mahnooryasir04@gmail.com
```

---

# 🙏 Acknowledgements

Special thanks to the following technologies and communities that made this project possible.

- Oracle Java
- Visual Studio Code
- Java Extension Pack
- Git
- GitHub
- Open Source Community

---

# 📞 Support

If you encounter any issues while using this project, please create an issue in the GitHub repository.

GitHub Issues

```
https://github.com/yourusername/MovieDatabaseSystem/issues
```

You can also contact the author through GitHub or LinkedIn.

---

<div align="center">

## ⭐ If you found this project helpful, please consider giving it a star.

### Thank you for visiting this repository!

**Made with ❤️ using Java**

</div>
