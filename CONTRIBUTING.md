# Contributing to Minimanimo

Hi everyone! Welcome to the **Minimanimo** project. 
This guide is here to help us work together smoothly and keep our code organized. 
Please take a moment to read this before you start coding!

---

## 1. Project Architecture & Directory

We are sticking to the standard Maven layout to keep things familiar. 
A quick review of folders:

```text
minimanimo/
├── .github/                        
│   ├── workflows/
│   │   └── maven.yml               
│   └── PULL_REQUEST_TEMPLATE.md   
│
├── src/
│   ├── main/java/minimanimo/      
│   │   ├── GameLauncher.java       
│   │   ├── User.java               
│   │   ├── UserManager.java       
│   │   └── game/                  
│   │       ├── MiniGame.java         
│   │       ├── RockPaperScissors.java  
│   │       ├── NumberBaseball.java     
│   │       ├── UpDown.java           
│   │       └── ChamChamCham.java      
│   │
│   └── test/java/minimanimo/
│       ├── GameLauncherTest.java   
│       ├── UserTest.java
│       ├── UserManagerTest.java
│       └── game/
│           ├── RockPaperScissorsTest.java
│           ├── NumberBaseballTest.java
│           ├── UpDownTest.java
│           └── ChamChamChamTest.java
│
├── users.csv                      
├── pom.xml                       
├── .gitignore
├── LICENSE
├── README.md
├── CONTRIBUTING.md
└── CODE_OF_CONDUCT.md
```

---

## 2. Collaboration Workflow

To avoid conflicts and keep our `main` branch stable, let's stick to this workflow for every new feature:

### Step 1: Issue & Project Board (Kanban)
1.  Go to the **Projects** tab in our GitHub Repo.
2.  Create an **Issue** for your task (Refer to the Roadmap in Section 6).
3.  **Assign Labels**: Add appropriate labels (e.g., `feature`, `bug`, `good first issue`) to categorize the task.
4.  Move the card to the **To Do** column.

### Step 2: Branching & Coding
1.  **Create a Branch** linked to your issue.
    -   Naming Convention: `feature/task-name` (e.g., `feature/game-baseball`)
    -   **Important:** Please don't push directly to `main`!
2.  Move your Kanban card to **In Progress**.

### Step 3: Local Testing
Before you push your code, make sure to run the tests locally. We want to keep our build green! 
```bash
mvn test
```

### Step 4: Pull Request (PR) & Review
1.  Push your branch and open a **Pull Request** to `main`.
2.  **Fill out the PR Template**: It will pop up automatically. Just check the boxes for Commit Type and Testing.
3.  **Assign Reviewers & Assignees**:
    -   **Reviewers**: Pick another team member.
    -   **Assignees**: Click "assign yourself".
4.  The Reviewer will leave comments or approve the PR.
5.  Once it's merged, move your Kanban card to **Done**. 

---

## 3. CI/CD & Build Instructions

We've set up **GitHub Actions** to test our code automatically.

### Continuous Integration (GitHub Actions)
Whenever you push code, GitHub will run the build and tests for us.
If the test **fails**, fix the errors before merging.

### How to Build and Run
When you need to build the final JAR file for submission, run this command:

```bash
# 1. Build
mvn clean package

# 2. Run
java -jar target/minimanimo-1.0-SNAPSHOT.jar
```

---

## 4. Team Roles & Responsibilities (R&R)

Here is who is doing what. 

| Name | Assigned Issues | Responsibilities |
|:---:|:---:|:---|
| **Kim Gyeongyoon** | **Issue #1** | Interface Design (`MiniGame`), `Number Baseball` Logic. |
| **Choi Juyoung** | **Issue #2, #3** | `UserManager` (CSV I/O), Unit Tests, `Number UpDown`. |
| **Kwon Sihyeon** | **Issue #4, #5** | `User` Class, Ranking Logic, `Cham Cham Cham`, Kanban & CI Mgmt. |
| **Park Soyeon** | **Issue #6, #7** | `GameLauncher` (Menu/Login), `Rock Paper Scissors` |

---

## 5. Coding & Commit Conventions

To maintain a clean history, please follow these conventions:

### Commit Message Rules
Start your commit message with one of the following tags:
- `[FEAT]`: New feature (e.g., `[FEAT] Add Baseball logic`)
- `[FIX]`: Bug fix (e.g., `[FIX] Resolve CSV parsing error`)
- `[REFACTOR]`: Code restructuring without behavior changes
- `[CHORE]`: Build tasks, library updates, config changes
- `[TEST]`: Adding or updating tests
- `[DOCS]`: Documentation changes (README, etc.)

### Label Conventions
Please assign at least one label when creating an issue:
- `feature`: Implementation of new features (Matches `[FEAT]`).
- `bug`: Something isn't working (Matches `[FIX]`).
- `documentation`: Improvements or additions to documentation.
- `good first issue`: Good for newcomers or simple tasks.
- `help wanted`: Extra attention is needed.

### Code Style
-   **java version**: `JDK 17` or higher.
-   **Naming**: `PascalCase` for Classes, `camelCase` for methods.
-   **Encoding**: All files must be **UTF-8**.
-   **Testing**: At least **one JUnit test** is required.
-   **Data**: Do not change the `users.csv` header. (Nickname,ChamChamCham,RPS,Baseball,UpDown)

---

## 6. Development Roadmap

This is our plan for the MVP. Please find your name below, create the corresponding Issue on GitHub, and start your branch from there.

### Kim Gyeongyoon
* **Issue #1**: Implement Number Baseball Game Logic
    * **Description**: Implement logic for generating random numbers and calculating strikes/balls.
    * **Branch**: `feature/game-baseball`

### Choi Juyoung
* **Issue #2**: Implement UserManager & Unit Tests
    * **Description**: Implement `loadUsers()`/`saveUsers()` for CSV I/O and write `UserManagerTest.java`.
    * **Branch**: `feature/system-manager`
* **Issue #3**: Implement UpDown Game Logic
    * **Description**: Implement UpDown game logic (1-100) and test integration with UserManager.
    * **Branch**: `feature/game-updown`

### Kwon Sihyeon
* **Issue #4**: Implement User Class & Ranking System
    * **Description**: Define `User` class (nickname, scoreMap) and implement `showTop5` sorting logic in Manager.
    * **Branch**: `feature/system-ranking`
* **Issue #5**: Implement ChamChamCham Game Logic
    * **Description**: Implement probability-based ChamChamCham game logic.
    * **Branch**: `feature/game-chamchamcham`

### Park Soyeon
* **Issue #6**: Implement GameLauncher (Menu & Login)
    * **Description**: Implement `main()` entry point, user login input, and infinite menu loop.
    * **Branch**: `feature/system-launcher`
* **Issue #7**: Implement Rock Paper Scissors Game Logic
    * **Description**: Implement Rock-Paper-Scissors victory/defeat logic.
    * **Branch**: `feature/game-rps`

**Eeny, meeny, miny, moe! Happy Coding!**
