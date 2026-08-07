# 🎓 University Student Grade Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![CSV](https://img.shields.io/badge/CSV-Data-blue?style=for-the-badge)
![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)

**A robust, professional-grade Java console application for managing student records, calculating grades, generating reports, and handling CSV data persistence.**

![Project Showcase](assets/images/1.%20MAIN%20MENU.png)

</div>

---

## 📚 Table of Contents
- [✨ Features](#-features)
- [📂 Project Structure](#-project-structure)
- [🖥️ How to Run](#-how-to-run)
- [📸 Screenshots](#-screenshots)
- [⚙️ Technical Details](#️-technical-details)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)

---

## ✨ Features

<div align="center">

| 🧑‍🎓 Student Operations | 📊 Analytics & Reports | 🛠️ Utilities |
| :---: | :---: | :---: |
| **CRUD** (Add, View, Update, Delete) | **Class Statistics** (Mean, Median, SD) | **Grading Scale** (A+ to F) |
| **Merit List** (Top N students) | **Generate Individual Reports** | **Input Validation** (Robust error handling) |
| **Subject-wise Marks Entry** | **Generate Class Summary** | **CSV Import/Export** (Data persistence) |
| **Auto GPA & Percentage Calc** | **Attendance Tracking** | **Emoji-enhanced Console UI** |

</div>

---

## 📂 Project Structure

The project follows a strict **MVC (Model-View-Controller)** inspired architecture to ensure clean separation of concerns, scalability, and maintainability.

```text
StudentGradeCalculator/
├── 📁 src/
│   └── 📁 main/
│       └── 📁 java/
│           └── 📁 com/
│               └── 📁 university/
│                   ├── 📁 model/              # Data Entities
│                   │   ├── Student.java       # Core student entity with getters/setters
│                   │   └── Grade.java         # Enum for Grade calculation (A+, A, B+, etc.)
│                   ├── 📁 service/            # Business Logic & Services
│                   │   ├── StudentService.java # Main operations (Add, Delete, Update)
│                   │   └── CsvService.java    # Handles CSV saving and loading
│                   ├── 📁 util/               # Helper Utilities
│                   │   └── InputValidator.java # Validates numeric inputs, semesters, etc.
│                   └── Main.java              # Entry point & CLI Menu Driver
├── 📁 assets/
│   └── 📁 images/                             # Project screenshots for README
├── 📁 data/
│   └── students.csv                           # Auto-generated CSV storage file
├── 📁 reports/                                # Auto-generated folder for text reports
└── README.md                                  # Project documentation (You are here!)
```

---

## 🖥️ How to Run

Follow these simple steps to get the application running on your local machine.

### Prerequisites
- **Java Development Kit (JDK)** (Version 8 or higher recommended).
- **Git** (optional, for cloning).

### Steps
1. **Clone the repository (or download ZIP):**
   ```bash
   git clone https://github.com/mahnoor-yasir/CrixsoftSolution_Internship.git
   ```
2. **Navigate to the project directory:**
   ```bash
   cd CrixsoftSolution_Internship/StudentGradeCalculator
   ```
3. **Compile the Java files (if necessary):**
   ```bash
   javac -d bin src/main/java/com/university/**/*.java
   ```
4. **Run the application:**
   ```bash
   java -cp bin com.university.Main
   ```

---

## 📸 Screenshots

> 🖼️ *Below are the actual outputs of the running application.*

<details>
<summary>📋 <b>Click to Expand Screenshot Gallery</b></summary>
<br>

| Main Menu | Add Student (Mahnoor) |
| :---: | :---: |
| ![Main Menu](assets/images/1.%20MAIN%20MENU.png) | ![Add Mahnoor](assets/images/2.%20Add%20New%20Student%20(Mahnoor).png) |

| Add Student (Ali) | View All Students |
| :---: | :---: |
| ![Add Ali](assets/images/3.%20Add%20New%20Student%20(Ali).png) | ![View All](assets/images/4.%20View%20All%20Students.png) |

| Student Details | Update Student |
| :---: | :---: |
| ![Student Details](assets/images/5.%20View%20Student%20Details.png) | ![Update](assets/images/6.%20Update%20Student.png) |

| Delete Student | Class Statistics |
| :---: | :---: |
| ![Delete](assets/images/7.%20Delete%20Student%20(Cancelled).png) | ![Statistics](assets/images/8.%20Display%20Class%20Statistics.png) |

| Generate Reports | Merit List |
| :---: | :---: |
| ![Reports](assets/images/9.%20Generate%20Reports.png) | ![Merit List](assets/images/10.%20Show%20Merit%20List.png) |

| Save Data to CSV | Load Data from CSV |
| :---: | :---: |
| ![Save CSV](assets/images/11.%20Save%20Data%20to%20CSV.png) | ![Load CSV](assets/images/12.%20Load%20Data%20from%20CSV.png) |

| Grading Scale | Exit Application |
| :---: | :---: |
| ![Grading Scale](assets/images/13.%20Show%20Grading%20Scale.png) | ![Exit](assets/images/14.%20Exit.png) |

</details>

---

## ⚙️ Technical Details

*   **Language:** Java (Object-Oriented Programming).
*   **Data Handling:** `ArrayList` for runtime storage, `BufferedReader` / `BufferedWriter` for CSV file I/O operations.
*   **Grade Logic:** Custom `Grade` enum calculates A+, A, B+, B, etc., based on dynamic percentage thresholds.
*   **Reports:** Generates `.txt` reports inside the `/reports` directory using file streams.
*   **Error Handling:** `InputValidator` class ensures user inputs (IDs, Semesters, Marks) are strict numerical values, preventing system crashes.
*   **UI Design:** Clean, ASCII-lined console interface with emojis for an enhanced user experience.

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

<div align="center">
⭐ **If you found this project helpful, please give it a star!** ⭐
</div>
```
