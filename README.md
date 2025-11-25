# 🕹️ Minimanimo: Console Game Arcade (Java CLI & OOP Practice)

A collection of small, delightful mini-games developed for quick fun. Dive into our 'minimanimo' world of bite-sized entertainment.

<img width="512" height="280" alt="KakaoTalk_Photo_2025-11-25-22-28-29" src="https://github.com/user-attachments/assets/70bbab27-876f-4099-a039-ccf42ea348d2" />

Minimanimo is a console (CLI) mini-game collection project designed to practice Java’s object-oriented programming principles and interface-based architecture. Users can enjoy several simple games after nickname authentication, while the Maven build system and CSV-based ranking system provide a structured environment for progression and competition.



## 1. Project Overview & Goals (What & Why)

### 1.1. What does this project do?

MiniManiMo provides four mini-games that run in a console environment: ChamChamCham, Rock-Paper-Scissors, Number Baseball, and Number Up-Down.  
Each game uses Java’s basic console I/O functions and implements the `MiniGame` interface, ensuring modular and maintainable structure.  
User data (nickname and best scores for each game) is stored and managed through local CSV files.


### 1.2. Why is this project useful? (Goals)

This project goes beyond simple game development—its primary goal is to enhance junior developers' technical capabilities through structured design.

- **OOP Design Practice (Interface-Centric)**  
    The separation of roles between `MiniGame`, `User`, and `UserManager` demonstrates **the Open–Closed Principle (OCP)** and encourages writing modular, extendable code.


- **Mastering Data Persistence**  
    Instead of a full database, CSV file I/O is used to safely save and load data, allowing developers to gain experience with lightweight persistence handling.


Let’s continue adding more mini-games to make MiniManiMo even more fun!



---



## 2. Getting Started (How do I get started?)

### 2.1. Prerequisites

- Java Development Kit (JDK): Version 17 or higher  

- Build Tool: Maven  

- Git: Installed and configured  


### 2.2. Cloning the Repository


```bash

git clone https://github.com/bbirribbarribbo/minimanimo.git

cd minimanimo

```


### 2.3. Build & Run

  **Build (Create Runnable JAR)**

```bash

mvn clean package

```


Executable output path: `target/minimanimo-1.0-SNAPSHOT.jar`


🎮 **Run the Game**


```bash

java -jar target/minimanimo-1.0-SNAPSHOT.jar

```


---


## 3. Architecture & Data Structure

### 3.1. Core Class Structure & Roles

| Class/Module | Role |
|--------------|-------|
| MiniGame (Interface) | Defines the basic contract of game features and provides the required methods that each mini-game must implement. |
| User (Class) | Stores the user's nickname and best score data for each game. |
| UserManager | Handles CSV data management, nickname authentication, score updates, and ranking logic. |
| GameLauncher | Entry point of the program; manages user menus and main game flow. |



---


### 3.2. Current Game Collection

All mini-games are played against the computer (random logic).  
The game ends either upon a loss (ChamChamCham, Rock-Paper-Scissors) or upon success (Number Baseball, Number Up-Down).

| No. | Game Name | Ranking Criteria (Goal) |
|-----|-----------|-------------------------|
| 1 | Cham Cham Cham | Number of successful rounds (consecutive) |
| 2 | Rock Paper Scissors | Win streak count | 
| 3 | Number Baseball | Remaining attempts (10 – attempts used) | 
| 4 | Number UpDown | Remaining attempts (10 – attempts used) | 



---



## 4. Contribution & Collaboration

Our team ensures project stability through a professional collaboration workflow.

- **Workflow**:  
  We follow the GitHub Flow defined in the CONTRIBUTING.md file.  
  All contributions start from a new branch and go through a pull request and code review before being merged into the `main` branch.


- **CI/CD**:  
  Every piece of code is automatically built and tested using GitHub Actions.


- **Team Roadmap**:  
  The project MVP consists of 7 issues and detailed role assignments and the full roadmap can be found in Sections 4 & 6 of CONTRIBUTING.md.


- **Kanban Board**:  
  We use GitHub Projects (Kanban board) to track all issues.  
  Each task moves through the workflow: **To Do → In Progress → Review → Done**.


---


## 5. Help & Support
  
  If you need help or have any questions regarding the project, please use the following channels.


### 🐞 Technical Issues & Bug Reports  

  Use the Issues tab on GitHub.


### ⚠️ Urgent or Sensitive Communications  

  For security vulnerabilities or Code of Conduct violations, please contact:

| Responsibility      | Name           | Email                  |
|----------------------------|----------------|------------------------|
| Interface Design (`MiniGame`), `Number Baseball` Logic. | Kim Gyeongyoon | gracekim6531@naver.com |
| `UserManager` (CSV I/O), Unit Tests, `Number UpDown`. | Choi Juyoung | hahalove79@naver.com |
| `User` Class, Ranking Logic, `Cham Cham Cham`, Kanban & CI Mgmt. | Kwon Sihyeon | sihyun050305@gmail.com |
| `GameLauncher` (Menu/Login), `Rock Paper Scissors`, Branch Protection. | Park Soyeon | amypark310@gmail.com |



---



## 6. License

This project is distributed under the MIT License.  
For more details, refer to the LICENSE file. 
