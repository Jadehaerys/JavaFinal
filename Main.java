import java.util.*;
import java.io.*;
import java.net.*;

// INTERFACE
interface Calculable {
    double calculate();
}

// ABSTRACT CLASS
abstract class Transaction implements Calculable {

    protected String description;
    protected double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public abstract String getType();
}

// INHERITANCE
class Income extends Transaction {

    public Income(String description, double amount) {
        super(description, amount);
    }

    @Override
    public double calculate() {
        return amount;
    }

    @Override
    public String getType() {
        return "INCOME";
    }
}

class Expense extends Transaction {

    public Expense(String description, double amount) {
        super(description, amount);
    }

    @Override
    public double calculate() {
        return -amount;
    }

    @Override
    public String getType() {
        return "EXPENSE";
    }
}

// MAIN CLASS
public class Main {

    static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== BUDGET TRACKER SYSTEM =====");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Send to Server");
            System.out.println("8. Get Financial Tip");
            System.out.println("9. Exit");

            System.out.print("Enter Choice: ");
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
                    sendToServer();
                    break;

                case 8:
                    getFinancialTip();
                    break;

                case 9:
                    System.out.println("Exiting system...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ADD TRANSACTION
    static void addTransaction(Scanner sc, boolean isIncome) {

        System.out.print("Enter Description: ");
        String desc = sc.nextLine();

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        Transaction t;

        if (isIncome) {
            t = new Income(desc, amount);
        } else {
            t = new Expense(desc, amount);
        }

        transactions.add(t);

        System.out.println("Transaction added successfully!");
    }

    // VIEW TRANSACTIONS
    static void viewTransactions() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("\n===== TRANSACTION HISTORY =====");

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

    // POLYMORPHISM + COMPUTATION
    static void viewBalance() {

        double total = 0;

        for (Transaction t : transactions) {
            total += t.calculate();
        }

        System.out.println("Current Balance: " + total);
    }

    // JAVA IO - SAVE
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

            System.out.println("Transactions saved successfully!");

        } catch (Exception e) {

            System.out.println("Error saving transactions.");
        }
    }

    // JAVA IO - LOAD
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

            System.out.println("Transactions loaded successfully!");

        } catch (Exception e) {

            System.out.println("Error loading transactions.");
        }
    }

    // JAVA NETWORKING
    static void sendToServer() {

        Client.sendData(transactions);
    }

    // API REQUEST
    static void getFinancialTip() {

        try {

            URL url = new URL("https://api.adviceslip.com/advice");

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String json = response.toString();

            int start = json.indexOf("advice\":\"") + 9;
            int end = json.indexOf("\"}", start);

            String advice = json.substring(start, end);

            System.out.println("\nFinancial Tip:");
            System.out.println(advice);

        } catch (Exception e) {

            System.out.println("Failed to fetch financial tip.");
        }
    }
}