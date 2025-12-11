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
<img width="450" height="205" alt="KakaoTalk_Photo_2025-12-12-02-47-59" src="https://github.com/user-attachments/assets/2ed6acc9-50a2-4e7c-ac73-37b99547288d" />

---

<img width="281" height="568" alt="KakaoTalk_Photo_2025-12-12-02-49-25" src="https://github.com/user-attachments/assets/34f4e3da-6f4a-4d87-b9fc-aa30bc81d91b" />

<img width="272" height="259" alt="KakaoTalk_Photo_2025-12-12-02-49-32" src="https://github.com/user-attachments/assets/342afca4-fbe4-41ac-8c8c-860847ac47af" />


---

## 🎮 How to Play

Minimanimo is a console-based collection of arcade mini-games. Follow the instructions below to start playing.

### 1️. Start & Login
When you run the program (`GameLauncher`), the authentication menu will appear.
* **New User?** Select `2` to **Register**.
  * *Rule:* Nicknames must contain **letters and numbers only** (no spaces or special characters).
* **Returning User?** Select `1` to **Login** with your registered nickname.

```text
[Authentication Menu]
───────────────────────────
1. Login (Existing User)
2. Register (New User)
───────────────────────────
0. Exit
>> 2
Enter New Nickname (or 'q' to cancel) >> user1
Registration successful! You are now logged in.
```

### 2️. Main Menu
After logging in, choose a game from the list by entering its number.

```text
★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★
  Please select a game to play:
★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★
1. ChamChamCham
2. RPS (Rock Paper Scissors)
3. Baseball (Number Baseball)
4. UpDown
0. Exit
>> 3
```

### 3️. Game Rules

#### 👆 1. ChamChamCham
A game of reflexes where you try to look in a different direction than the computer.
* **Input:** Enter `L` (Left), `C` (Center), or `R` (Right).
* **Win:** If you look in a **different** direction than the computer, you gain **+1 point**.
* **Lose:** If you look in the **same** direction, the game ends.
* **Quit:** Enter `0` to stop.

#### ✊ 2. RPS (Rock Paper Scissors Survival)
A survival mode game where you accumulate points until you lose.
* **Input:** `1` (Rock), `2` (Paper), `3` (Scissors).
* **Scoring:** Win (**+3 points**), Draw (**+1 point**), Lose (**Game Over**).

#### ⚾ 3. Number Baseball
A logic puzzle to guess a secret 3-digit number.
* **Goal:** Guess 3 unique digits (1-9) (e.g., `123`).
* **Hints:**
  * **Strike:** Correct digit in the correct position.
  * **Ball:** Correct digit but in the wrong position.
  * **Out:** No matching digits.
* **Life:** You have **10 attempts**.

#### ⬆️ 4. UpDown
Guess a random number between 1 and 100.
* **Goal:** Find the secret number within **10 attempts**.
* **Hints:** The game will tell you if the target number is **Higher (Up)** or **Lower (Down)** than your guess.

### 4️. Results & Ranking
After a game ends, your score is displayed along with the **Top 5 Ranking** for that game.
* New high scores are automatically saved to the database.

```text
Good game! (Your Score: 8, Best Record: 5)
★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★
[INFO]  Top 5 users for Baseball:
★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★
  Rank  |      Nickname      |  Score 
--------------------------------------
   1    |  master            |      9 
   2    |  user1             |      8 
...
```

### 5️. Next Steps
After checking the ranking, choose your next action:
* `1. Play Again`: Restart the current game immediately.
* `2. Main Menu`: Return to the menu to choose a different game.
* `0. Exit Program`: Close Minimanimo.

---

### Tips
* **Quit Anytime:** Enter `0` during any game to quit or return to the previous menu.
* **Data Persistence:** All user profiles and high scores are saved in `users.csv`, so your records remain even after restarting the program.

---

## 3. Architecture & Data Structure

### 3.1. Core Class Structure & Roles

| Class/Module | Role |
|--------------|-------|
| MiniGame (Interface) | Defines the basic contract of game features and provides the required methods that each mini-game must implement. |
| User (Class) | Stores the user's nickname and best score data for each game. |
| UserManager (Class) | Handles CSV data management, nickname authentication, score updates, and ranking logic. |
| GameLauncher (Class) | Entry point of the program; manages user menus and main game flow. |



---


### 3.2. Current Game Collection

All mini-games are played against the computer (random logic).  
The game ends either upon a loss (ChamChamCham, Rock-Paper-Scissors) or upon success (Number Baseball, Number Up-Down).

| No. | Game Name | Ranking Criteria (Goal) |
|-----|-----------|-------------------------|
| 1 | Cham Cham Cham | Number of successful rounds (consecutive) |
| 2 | Rock Paper Scissors | Total Score (Win +3, Draw +1) |
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
  The project MVP consists of 11 issues and detailed role assignments and the full roadmap can be found in Sections 4 & 6 of CONTRIBUTING.md.


- **Kanban Board**:  
  We use GitHub Projects (Kanban board) to track all issues.  
  Each task moves through the workflow: **Ready → In Progress → In Review → Done**.


---


## 5. Help & Support
  
  If you need help or have any questions regarding the project, please use the following channels.


### 🐞 Technical Issues & Bug Reports  

  Use the Issues tab on GitHub.


### ⚠️ Urgent or Sensitive Communications  

  For security vulnerabilities or Code of Conduct violations, please contact:

| Responsibility      | Name           | Email                  |
|----------------------------|----------------|------------------------|
| Interface Design (`MiniGame`), `Number Baseball` Logic, Console UI, Updated README. | Kim Gyeongyoon | gracekim6531@naver.com |
| `UserManager` (CSV I/O), `Number UpDown`, Console UI, Wrote CODE_OF_CONDUCT. | Choi Juyoung | hahalove79@naver.com |
| `User` Class, `Cham Cham Cham`, Kanban Mgmt, Code cleanup & organization, Managed LICENSE and .gitignore. | Kwon Sihyeon | sihyun050305@gmail.com |
| `GameLauncher` (Menu/Login), `Rock Paper Scissors`, Created initial folder & file structure, Wrote CONTRIBUTING.md. | Park Soyeon | amypark310@gmail.com |



---



## 6. License

This project is distributed under the MIT License.  
For more details, refer to the LICENSE file. 
