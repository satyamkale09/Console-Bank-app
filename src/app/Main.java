package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
//Multi Line String- """Text"""
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        System.out.println("Welcome to Console Bank");
        boolean running = true;
        while(running) {
            System.out.println("""
                            1) Open Account
                            2) Deposit
                            3) Withdraw
                            4) Transfer
                            5) Account Statement
                            6) List Accounts
                            7) Search Accounts by Customer Name
                            0) Exit
                    """);

            System.out.print("CHOOSE: ");
            String choice = sc.nextLine().trim();
            System.out.println("CHOICE: " + choice);

            switch(choice){
                case "1":
                    openAccount(sc, bankService);
                    break;
                case "2":
                    deposit(sc, bankService);
                    break;
                case "3":
                    withdraw(sc, bankService);
                    break;
                case "4":
                    transfer(sc, bankService);
                    break;
                case "5":
                    statement(sc, bankService);
                    break;
                case "6":
                    listAccounts(sc, bankService);
                    break;
                case "7":
                    searchAccounts(sc, bankService);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }



    }

    private static void openAccount(Scanner sc, BankService bankService) {
        System.out.print("Customer name: ");
        String name = sc.nextLine().trim();
        System.out.print("Customer email: ");
        String email = sc.nextLine().trim();
        System.out.print("Account Type (SAVINGS/CURRENT): ");
        String type = sc.nextLine().trim();
        System.out.print("Initial deposit (optional, blank for 0): ");
        String amountStr = sc.nextLine().trim();
        if (amountStr.isBlank()) amountStr = "0";
        Double initial = Double.valueOf(amountStr);
        String accountNumber = bankService.openAccount(name, email,type);
        if(initial > 0) bankService.deposit(accountNumber, initial, "Initial Deposit");
        System.out.println("Account opened: " + accountNumber);
    }

    private static void deposit(Scanner sc, BankService bankService) {
        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine().trim();
        System.out.print("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.deposit(accountNumber, amount, "Deposit");
        System.out.println("Deposited");
    }

    private static void withdraw(Scanner sc, BankService bankService) {
        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine().trim();
        System.out.print("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.withdraw(accountNumber, amount, "Withdrawal");
        System.out.println("Withdrawn");
    }

    private static void transfer(Scanner sc, BankService bankService) {
        System.out.print("From Account: ");
        String from = sc.nextLine().trim();
        System.out.print("To Account: ");
        String to = sc.nextLine().trim();
        System.out.print("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.transfer(from, to, amount, "Transfer");
        System.out.println("Transfer Completed");
    }

    private static void statement(Scanner sc, BankService bankService) {
        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine().trim();
        bankService.getStatement(accountNumber).forEach(t -> {
            System.out.println(t.getTimestamp() + "| "+ t.getType()+ "| "+t.getAmount()+" | "+t.getNote());
        });
    }

    private static void listAccounts(Scanner sc, BankService bankService) {
        bankService.listAccounts().forEach( a -> {
            System.out.println(a.getAccountNumber()+" | "+ a.getAccountType()+" | "+a.getBalance());
        });
    }

    private static void searchAccounts(Scanner sc, BankService bankService) {
        System.out.print("Customer name contains: ");
        String q = sc.nextLine().trim();
        bankService.searchAccountByCustomerName(q).forEach(account ->
                System.out.println(account.getAccountNumber()+" | " + account.getAccountType()+" | "+account.getBalance())
        );
    }
}
