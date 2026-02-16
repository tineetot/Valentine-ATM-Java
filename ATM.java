import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    
    // THEME COLORS
    private static final String PINK = "\033[38;5;206m";
    private static final String BLUE = "\033[34m";
    private static final String RED = "\033[31m";
    private static final String RESET = "\u001B[0m";
    private static final String WHITE = "\033[1;37m";
    private static final String CYAN = "\033[36m";

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {}
    }

    // PROGRESS BAR FEATURE
    private static void showProgressBar(String action) throws InterruptedException {
        System.out.print(WHITE + action + " [");
        for (int i = 0; i <= 20; i++) {
            System.out.print(BLUE + "█" + RESET);
            Thread.sleep(60); 
        }
        System.out.println(WHITE + "] 100%" + RESET);
    }

    private static int getIntInput(Scanner atm, String prompt, String errorMsg, boolean minMaxValidation, int min, int max) {
        int input = 0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print(prompt);
                input = atm.nextInt();
                atm.nextLine(); 
                if (minMaxValidation) {
                    if (input >= min && input <= max) valid = true;
                    else System.out.println(RED + "Invalid. Range: " + min + "-" + max + RESET);
                } else valid = true;
            } catch (InputMismatchException e) {
                System.out.println(RED + errorMsg + RESET);
                atm.nextLine();
            }
        }
        return input;
    }

    private static double getDoubleInput(Scanner atm, String prompt, String errorMsg, boolean positiveValidation) {
        double input = 0.0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print(prompt);
                input = atm.nextDouble();
                atm.nextLine();
                if (positiveValidation && input <= 0) System.out.println(RED + "Amount must be > 0." + RESET);
                else valid = true;
            } catch (InputMismatchException e) {
                System.out.println(RED + errorMsg + RESET);
                atm.nextLine();
            }
        }
        return input;
    }

    private static boolean confirmPin(Scanner atm, int correctPin, int maxRetries) {
        for (int retry = maxRetries; retry > 0; retry--) {
            try {
                System.out.print(WHITE + "Enter PIN to authorize (Attempts left: " + retry + "): " + RESET);
                int enteredPin = atm.nextInt();
                atm.nextLine();
                if (enteredPin == correctPin) return true;
                System.out.println(RED + "ACCESS DENIED." + RESET);
            } catch (Exception e) {
                System.out.println(RED + "Invalid input." + RESET);
                atm.nextLine();
            }
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner atm = new Scanner(System.in);
        double bal = 5000.0;
        int pin = 1234;
        String lastTransaction = "None";
        Random rand = new Random();
        int accountNumber = 100000 + rand.nextInt(900000);

        // INITIAL WELCOME
        clearScreen();
        System.out.println(PINK + "╔══════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(PINK + "║  ██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗  ║" + RESET);
        System.out.println(PINK + "║  ██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝  ║" + RESET);
        System.out.println(PINK + "║  ██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗    ║" + RESET);
        System.out.println(PINK + "║  ██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝    ║" + RESET);
        System.out.println(PINK + "║  ╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗  ║" + RESET);
        System.out.println(PINK + "║   ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝  ║" + RESET);
        System.out.println(PINK + "║                                                                  ║" + RESET);
        System.out.println(PINK + "║                    " + WHITE + "to Valen(tine) ATM" + PINK + "                            ║" + RESET);
        System.out.println(PINK + "╚══════════════════════════════════════════════════════════════════╝" + RESET);
        
        System.out.print(CYAN + "\nLogin as: " + RESET);
        String accountHolder = atm.nextLine().toUpperCase();
        showProgressBar("Connecting to Valen(tine) Network");

        while (true) {
            clearScreen();
            // USER DASHBOARD 
            System.out.println(BLUE + "┌──────────────────────────────────────────────────────────┐" + RESET);
            System.out.println(PINK + "  DASHBOARD " + WHITE + ">> " + CYAN + accountHolder + RESET);
            System.out.println(WHITE + "  ACCOUNT: " + BLUE + accountNumber + WHITE + " | BALANCE: " + BLUE + "PHP " + bal + RESET);
            System.out.println(WHITE + "  LAST ACTIVITY: " + CYAN + lastTransaction + RESET);
            System.out.println(BLUE + "└──────────────────────────────────────────────────────────┘" + RESET);

            System.out.println(PINK + "╔═════════════════════════════════════════╗" + RESET);
            System.out.println(WHITE + "║ [1]  WITHDRAWAL    [2]  DEPOSIT         ║" + RESET);
            System.out.println(WHITE + "║ [3]  BALANCE       [4]  TRANSFER        ║" + RESET);
            System.out.println(WHITE + "║ [5]  ACCOUNT INFO  [6]  CHANGE PIN      ║" + RESET);
            System.out.println(WHITE + "║ [7]  EXIT                               ║" + RESET);
            System.out.println(PINK + "╚═════════════════════════════════════════╝" + RESET);

            int select = getIntInput(atm, CYAN + "Input Choice > " + RESET, "Numbers only.", true, 1, 7);

            switch (select) {
                case 1 -> { // WITHDRAWAL 
                    clearScreen();
                    System.out.println(PINK + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(PINK + "║  ██╗    ██╗██╗████████╗██╗  ██╗██████╗ ██████╗  █████╗ ██╗    ██╗ █████╗ ██╗      ║" + RESET);
                    System.out.println(PINK + "║  ██║    ██║██║╚══██╔══╝██║  ██║██╔══██╗██╔══██╗██╔══██╗██║    ██║██╔══██╗██║      ║" + RESET);
                    System.out.println(PINK + "║  ██║ █╗ ██║██║   ██║   ███████║██║  ██║██████╔╝███████║██║ █╗ ██║███████║██║      ║" + RESET);
                    System.out.println(PINK + "║  ██║███╗██║██║   ██║   ██╔══██║██║  ██║██╔══██╗██╔══██║██║███╗██║██╔══██║██║      ║" + RESET);
                    System.out.println(PINK + "║  ╚███╔███╔╝██║   ██║   ██║  ██║██████╔╝██║  ██║██║  ██║╚███╔███╔╝██║  ██║███████╗ ║" + RESET);
                    System.out.println(PINK + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
                    double amount = getDoubleInput(atm, WHITE + "Enter Withdrawal Amount: " + RESET, "Invalid format.", true);
                    if (amount % 100 != 0) System.out.println(RED + "Error: Multiples of 100 only." + RESET);
                    else if (amount > bal) System.out.println(RED + "Error: Insufficient balance." + RESET);
                    else if (confirmPin(atm, pin, 3)) {
                        showProgressBar("Processing Cash");
                        bal -= amount;
                        lastTransaction = "Withdrawal: -" + amount;
                    }
                }
                case 2 -> { // DEPOSIT 
                    clearScreen();
                    System.out.println(PINK + "╔═════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(PINK + "║  ██████╗ ███████╗██████╗  ██████╗ ███████╗██╗████████╗  ║" + RESET);
                    System.out.println(PINK + "║  ██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔════╝██║╚══██╔══╝  ║" + RESET);
                    System.out.println(PINK + "║  ██║  ██║█████╗  ██████╔╝██║   ██║███████╗██║   ██║     ║" + RESET);
                    System.out.println(PINK + "║  ██║  ██║██╔══╝  ██╔═══╝ ██║   ██║╚════██║██║   ██║     ║" + RESET);
                    System.out.println(PINK + "║  ██████╔╝███████╗██║     ╚██████╔╝███████║██║   ██║     ║" + RESET);
                    System.out.println(PINK + "╚═════════════════════════════════════════════════════════╝" + RESET);
                    double dep = getDoubleInput(atm, WHITE + "Insert Bills: " + RESET, "Invalid format.", true);
                    showProgressBar("Validating Deposit");
                    bal += dep;
                    lastTransaction = "Deposit: +" + dep;
                }
                case 3 -> { // BALANCE 
                    clearScreen();
                    System.out.println(PINK + "╔═════════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(PINK + "║  ██████╗  █████╗ ██╗      █████╗ ███╗  ██╗ ██████╗███████╗  ║" + RESET);
                    System.out.println(PINK + "║  ██╔══██╗██╔══██╗██║     ██╔══██╗████╗ ██║██╔════╝██╔════╝  ║" + RESET);
                    System.out.println(PINK + "║  ██████╔╝███████║██║     ███████║██╔██╗ ██║██║     █████╗    ║" + RESET);
                    System.out.println(PINK + "║  ██╔══██╗██╔══██║██║     ██╔══██║██║╚██╗██║██║     ██╔══╝    ║" + RESET);
                    System.out.println(PINK + "║  ██████╔╝██║  ██║███████╗██║  ██║██║ ╚████║╚██████╗███████╗  ║" + RESET);
                    System.out.println(PINK + "╚═════════════════════════════════════════════════════════════╝" + RESET);
                    System.out.println(WHITE + "NET BALANCE: " + BLUE + "PHP " + bal + RESET);
                }
                case 4 -> { // TRANSFER 
                    clearScreen();
                    System.out.println(PINK + "╔═════════════════════════════════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(PINK + "║ ███████╗██╗   ██╗███╗  ██╗██████╗      ████████╗██████╗  █████╗ ███╗  ██╗███████╗   ║" + RESET);
                    System.out.println(PINK + "║ ██╔════╝██║   ██║████╗ ██║██╔══██╗     ╚══██╔══╝██╔══██╗██╔══██╗████╗ ██║██╔════╝   ║" + RESET);
                    System.out.println(PINK + "║ █████╗  ██║   ██║██╔██╗ ██║██║  ██║        ██║   ██████╔╝███████║██╔██╗ ██║███████╗ ║" + RESET);
                    System.out.println(PINK + "║ ██╔══╝  ██║   ██║██║╚██╗██║██║  ██║        ██║   ██╔══██╗██╔══██║██║╚██╗██║╚════██║ ║" + RESET);
                    System.out.println(PINK + "║ ██║     ╚██████╔╝██║ ╚████║██████╔╝        ██║   ██║  ██║██║  ██║██║ ╚████║███████║ ║" + RESET);
                    System.out.println(PINK + "╚═════════════════════════════════════════════════════════════════════════════════════╝" + RESET);
                    System.out.print(WHITE + "Recipient Account (6-digits): " + RESET);
                    String target = atm.next();
                    double trans = getDoubleInput(atm, WHITE + "Amount to Transfer: " + RESET, "Invalid format.", true);
                    if (trans <= bal && confirmPin(atm, pin, 3)) {
                        showProgressBar("Sending Funds");
                        bal -= trans;
                        lastTransaction = "Transfer to " + target;
                        System.out.println(CYAN + "Transfer Successful." + RESET);
                    } else {
                        System.out.println(RED + "Transfer Denied." + RESET);
                    }
                }
                case 5 -> { // ACCOUNT INFO 
                    clearScreen();
                    System.out.println(PINK + "╔══════════════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(PINK + "║  ██████╗██╗  ██╗███████╗ ██████╗██╗ ██╗  █████╗  ██████╗ ██████╗ ║" + RESET);
                    System.out.println(PINK + "║ ██╔════╝██║  ██║██╔════╝██╔════╝██║██╔╝ ██╔══██╗██╔════╝██╔════╝ ║" + RESET);
                    System.out.println(PINK + "║ ██║     ███████║█████╗  ██║     █████╗  ███████║██║     ██║      ║" + RESET);
                    System.out.println(PINK + "║ ██║     ██╔══██║██╔══╝  ██║     ██╔═██╗ ██╔══██║██║     ██║      ║" + RESET);
                    System.out.println(PINK + "║ ╚██████╗██║  ██║███████╗╚██████╗██║  ██╗██║  ██║╚██████╗╚██████╗ ║" + RESET);
                    System.out.println(PINK + "╚══════════════════════════════════════════════════════════════════╝" + RESET);
                    System.out.println(WHITE + "HOLDER NAME  : " + CYAN + accountHolder + RESET);
                    System.out.println(WHITE + "ACC NUMBER   : " + BLUE + accountNumber + RESET);
                    System.out.println(WHITE + "CURRENT DATE : " + BLUE + LocalDate.now() + RESET);
                }
                case 6 -> { // CHANGE PIN
                    clearScreen();
                    System.out.println(PINK + "╔══════════════════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(PINK + "║  ██████╗██╗  ██╗ █████╗ ███╗   ██╗ ██████╗ ███████╗    ██████╗ ██╗███╗   ██╗ ║" + RESET);
                    System.out.println(PINK + "║ ██╔════╝██║  ██║██╔══██╗████╗  ██║██╔════╝ ██╔════╝    ██╔══██╗██║████╗  ██║ ║" + RESET);
                    System.out.println(PINK + "║ ██║     ███████║███████║██╔██╗ ██║██║  ███╗█████╗      ██████╔╝██║██╔██╗ ██║ ║" + RESET);
                    System.out.println(PINK + "║ ██║     ██╔══██║██╔══██║██║╚██╗██║██║   ██║██╔══╝      ██╔═══╝ ██║██║╚██╗██║ ║" + RESET);
                    System.out.println(PINK + "║ ╚██████╗██║  ██║██║  ██║██║ ╚████║╚██████╔╝███████╗    ██║     ██║██║ ╚████║ ║" + RESET);
                    System.out.println(PINK + "║  ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝    ╚═╝     ╚═╝╚═╝  ╚═══╝ ║" + RESET);
                    System.out.println(PINK + "╚══════════════════════════════════════════════════════════════════════╝" + RESET);
                    if (confirmPin(atm, pin, 3)) {
                        pin = getIntInput(atm, WHITE + "New 4-Digit PIN: " + RESET, "Numeric only.", true, 1000, 9999);
                        System.out.println(CYAN + "PIN change confirmed." + RESET);
                    }
                }
                case 7 -> {
                    clearScreen();
                    System.out.println(PINK + "THANK YOU FOR BANKING WITH VALEN(TINE) ATM. GOODBYE!");
                    System.exit(0);
                }
            }
            System.out.print(WHITE + "\nPress ENTER to continue..." + RESET);
            atm.nextLine();
        }
    }
}