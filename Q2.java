/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva.pkg1;

/**
 *
 * @author adhora
 */
import java.util.Locale;
import java.util.Scanner;

/** Question 2: Credit Card Debt & Payment Holiday Simulator. */
public class Q2 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner input = new Scanner(System.in);
        double balance;

        // The assignment requires a do-while loop for balance validation.
        do {
            while (true) {
                System.out.print("Enter outstanding balance (RM): ");
                if (input.hasNextDouble()) {
                    balance = input.nextDouble();
                    input.nextLine();
                    if (Double.isFinite(balance)) {
                        break;
                    }
                } else {
                    input.nextLine();
                }
                System.out.println("Error: Enter a valid number in the allowed range.");
            }
            if (balance < 500 || balance > 50000) {
                System.out.println("Error: Balance must be between RM 500 and RM 50000.");
            }
        } while (balance < 500 || balance > 50000);

        double annualRate;
        while (true) {
            System.out.print("Enter annual interest rate (%): ");
            if (input.hasNextDouble()) {
                annualRate = input.nextDouble();
                input.nextLine();
                if (Double.isFinite(annualRate) && annualRate >= 0) {
                    break;
                }
            } else {
                input.nextLine();
            }
            System.out.println("Error: Enter a valid number in the allowed range.");
        }
        double plannedPayment;
        while (true) {
            System.out.print("Enter planned monthly payment (RM): ");
            if (input.hasNextDouble()) {
                plannedPayment = input.nextDouble();
                input.nextLine();
                if (Double.isFinite(plannedPayment) && plannedPayment >= 0) {
                    break;
                }
            } else {
                input.nextLine();
            }
            System.out.println("Error: Enter a valid number in the allowed range.");
        }
        String holiday;
        do {
            System.out.print("Take a payment holiday? (Y/N): ");
            holiday = input.nextLine().trim();
            if (!holiday.equalsIgnoreCase("Y") && !holiday.equalsIgnoreCase("N")) {
                System.out.println("Error: Enter Y or N.");
            }
        } while (!holiday.equalsIgnoreCase("Y") && !holiday.equalsIgnoreCase("N"));

        int holidayMonth = 0;
        if (holiday.equalsIgnoreCase("Y")) {
            double month;
            do {
                while (true) {
                    System.out.print("Enter holiday month (1-36): ");
                    if (input.hasNextDouble()) {
                        month = input.nextDouble();
                        input.nextLine();
                        if (Double.isFinite(month)) {
                            break;
                        }
                    } else {
                        input.nextLine();
                    }
                    System.out.println("Error: Enter a valid number in the allowed range.");
                }
                if (month < 1 || month > 36 || month != Math.floor(month)) {
                    System.out.println("Error: Holiday month must be an integer from 1 to 36.");
                }
            } while (month < 1 || month > 36 || month != Math.floor(month));
            holidayMonth = (int) month;
        }

        double monthlyRate = annualRate / (100 * 12);
        double totalInterest = 0;
        double totalLateFees = 0;
        double totalPaid = 0;
        int raisedMonths = 0;
        double[] yearEndBalances = new double[3];

        for (int month = 1; month <= 36; month++) {
            double interest = balance * monthlyRate;
            balance += interest;
            totalInterest += interest;

            if (month == holidayMonth) {
                balance += 25;
                totalLateFees += 25;
                System.out.printf("Month %d: Payment holiday - late fee RM 25.00 charged.%n", month);
            } else {
                double minimumPayment = Math.max(balance * 0.05, 50);
                double payment = plannedPayment;
                if (payment < minimumPayment) {
                    payment = minimumPayment;
                    raisedMonths++;
                }
                payment = Math.min(payment, balance);
                balance -= payment;
                totalPaid += payment;
            }

            if (balance < 0.005) {
                balance = 0;
            }
            if (month % 12 == 0) {
                yearEndBalances[month / 12 - 1] = balance;
            }
            if (balance == 0) {
                System.out.printf("Debt fully cleared in Month %d!%n", month);
                break; // Future year-end balances remain zero.
            }
        }

        for (int year = 0; year < yearEndBalances.length; year++) {
            System.out.printf("End of Year %d Balance: RM %.2f%n", year + 1, yearEndBalances[year]);
        }
        System.out.printf("Total Interest Charged: RM %.2f%n", totalInterest);
        System.out.printf("Total Late Fees: RM %.2f%n", totalLateFees);
        System.out.printf("Total Amount Paid: RM %.2f%n", totalPaid);
        System.out.println("Months Raised to Minimum Payment: " + raisedMonths);
        if (balance > 0) {
            System.out.println("Debt not cleared after 36 months.");
        }
        input.close();
    }
}
