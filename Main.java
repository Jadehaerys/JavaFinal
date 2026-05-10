import java.util.*;
import java.io.*;

// INTERFACE Miss
interface Calculable {
    double calculate();
}

// ABSTRACT CLASS Miss
abstract class Transaction implements Calculable {
    protected String description;
    protected double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public abstract String getType();
}

// INHERITANCE Miss
class Income extends Transaction {
    public Income(String description, double amount) {
        super(description, amount);
    }

    public double calculate() {
        return amount;
    }

    public String getType() {
        return "INCOME";
    }
}

class Expense extends Transaction {
    public Expense(String description, double amount) {
        super(description, amount);
    }

    public double calculate() {
        return -amount;
    }

    public String getType() {
        return "EXPENSE";
    }
}

public class Main {

    static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n==== MENU ====");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit");

            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addTransaction(sc, true);
                    break;

                case 2:
                    addTransaction(sc, false);
                    break;

                case 3:
                    viewTransactions();
                    break;

                case 4:
                    viewBalance();
                    break;

                case 5:
                    saveToFile();
                    break;

                case 6:
                    loadFromFile();
                    break;

                case 7:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void addTransaction(Scanner sc, boolean isIncome) {

        System.out.print("Description: ");
        String desc = sc.nextLine();

        System.out.print("Amount: ");
        double amount = sc.nextDouble();

        Transaction t;

        if (isIncome) {
            t = new Income(desc, amount);
        } else {
            t = new Expense(desc, amount);
        }

        transactions.add(t);

        System.out.println("Transaction added!");
    }

    static void viewTransactions() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n=== TRANSACTIONS ===");

        for (Transaction t : transactions) {
            System.out.println(
                t.getType() +
                " | " +
                t.description +
                " | " +
                t.amount
            );
        }
    }

    static void viewBalance() {

        double total = 0;

        for (Transaction t : transactions) {
            total += t.calculate(); // POLYMORPHISM MISS
        }

        System.out.println("Current Balance: " + total);
    }

    // JAVA IO MISS
    static void saveToFile() {

        try {

            PrintWriter writer = new PrintWriter("transactions.txt");

            for (Transaction t : transactions) {

                writer.println(
                    t.getType() + "," +
                    t.description + "," +
                    t.amount
                );
            }

            writer.close();

            System.out.println("Transactions saved!");

        } catch (Exception e) {

            System.out.println("Error saving file.");
        }
    }

    // JAVA IO GIHAPON MISS
    static void loadFromFile() {

        try {

            File file = new File("transactions.txt");

            Scanner reader = new Scanner(file);

            transactions.clear();

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                String[] data = line.split(",");

                String type = data[0];
                String desc = data[1];
                double amount = Double.parseDouble(data[2]);

                if (type.equals("INCOME")) {
                    transactions.add(new Income(desc, amount));
                } else {
                    transactions.add(new Expense(desc, amount));
                }
            }

            reader.close();

            System.out.println("Transactions loaded!");

        } catch (Exception e) {

            System.out.println("Error loading file.");
        }
    }
}