import java.io.*;
import java.util.*;

class BankAccount {
    private String accountNumber;
    private String holderName;
    private int pin;
    private double balance;
    private List<String> transactionHistory;

    BankAccount(String accountNumber, String holderName, int pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean verifyPIN(int inputPin) {
        return this.pin == inputPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            String entry = "Deposited: Rs. " + amount + " | Balance: Rs. " + balance;
            transactionHistory.add(entry);
            logTransaction(entry);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            String entry = "Withdrawn: Rs. " + amount + " | Balance: Rs. " + balance;
            transactionHistory.add(entry);
            logTransaction(entry);
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public void checkBalance() {
        System.out.println("Current Balance: Rs. " + balance);
    }

    public void showTransactions() {
        System.out.println("\nTransaction History:");
        try (BufferedReader reader = new BufferedReader(new FileReader(accountNumber + "_transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No transaction history found!");
        }
    }

    private void logTransaction(String transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountNumber + "_transactions.txt", true))) {
            writer.write(new Date() + " - " + transaction);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction.");
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter account number: ");
        String accNum = sc.nextLine();

        System.out.print("Enter holder name: ");
        String name = sc.nextLine();

        System.out.print("Set 4-digit PIN: ");
        int pin = sc.nextInt();

        BankAccount account = new BankAccount(accNum, name, pin);

        System.out.print("\nLogin PIN: ");
        int enteredPin = sc.nextInt();
        if (!account.verifyPIN(enteredPin)) {
            System.out.println("Incorrect PIN. Access denied!");
            sc.close();
            return;
        }

        int choice;
        do {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transactions");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double dep = sc.nextDouble();
                    account.deposit(dep);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double wd = sc.nextDouble();
                    account.withdraw(wd);
                    break;
                case 3:
                    account.checkBalance();
                    break;
                case 4:
                    account.showTransactions();
                    break;
                case 5:
                    System.out.println("Thank you for using our banking system!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }
}
