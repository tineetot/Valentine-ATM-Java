# 🏧 Valen(tine) ATM - Java Finals Project

## 🎓 Project Overview
This repository contains my final project for the **Fundamentals of Programming** course during my **1st Semester**. The task was to create a functional ATM system that goes beyond basic logic by implementing robust error handling and a user-centric console interface.

### 💖 Branding: Valen(tine) ATM
The project features a unique **Reddish-Pink and Blue** aesthetic, using ANSI escape codes to create a "Neon" terminal experience.

## 🛠️ Technical Implementation
As a first-semester project, this application focuses on several key programming pillars:

1. **Input Validation:** Uses `try-catch` blocks and `InputMismatchException` to prevent the program from crashing when a user enters text instead of numbers.
2. **Modular Logic:** Functions are broken down into reusable methods (e.g., `getIntInput`, `confirmPin`, `showProgressBar`) to keep the `main` method clean.
3. **Data Simulation:** Implements `Random` class for account number generation and `LocalDate` for real-time transaction stamping.
4. **UX/UI Design:** - **ASCII Art:** Custom headers for every transaction module.
   - **Progress Bars:** Simulated loading times to mimic real-world server communication.
   - **Clear Screen:** Uses `ProcessBuilder` to clear the terminal, keeping the workspace tidy for the user.

## 🚀 Features
- **Secure Access:** 4-digit PIN authorization for sensitive transactions.
- **Withdrawal:** Validates against current balance and ensures multiples of 100.
- **Deposit:** Real-time balance updating.
- **Transfer:** Capability to "send" funds to 6-digit account numbers.
- **Account Management:** View account holder details and change your security PIN.
- **Transaction Tracking:** Dashboard displays the last activity performed.

## 💻 How to Run
1. Clone the repository or download `ATM.java`.
2. Open your terminal or command prompt.
3. Compile the code:
   ```bash
   javac ATM.java
   
![Project Preview](ATM-preview.png)
