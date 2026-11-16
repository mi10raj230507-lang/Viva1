/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva.pkg1;

/**
 *
 * @author Jivesh
 */
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initial Hub Battery capacity in kWh
        double hubBattery = 150.00;

        // Vehicle specifications array data
        // Index 0: Not used, Index 1-4 correspond to vehicle options
        String[] vehicleNames = { "", "Compact Hatch", "Family SUV", "Delivery Van", "E-Scooter" };
        double[] capacities = { 0, 40.0, 75.0, 90.0, 4.0 };
        double[] maxPowers = { 0, 50.0, 120.0, 80.0, 3.0 };
        boolean[] isCharged = { false, false, false, false, false }; // Track [DONE] status

        // Final Report Trackers
        int vehiclesChargedCount = 0;
        double totalEnergyDelivered = 0.0;
        double totalRevenue = 0.0;

        // Main charging loop runs while hub has energy (> 0)
        while (hubBattery > 0.001) { // Using 0.001 tolerance for floating point accuracy
            
            // ==========================================
            // 1. DISPLAY MENU
            // ==========================================
            System.out.println("===== Solar EV Charging Hub =====");
            System.out.printf("Hub Battery: %.2f kWh\n", hubBattery);
            
            for (int i = 1; i <= 4; i++) {
                System.out.print(i + ". " + vehicleNames[i] + " (" + (int)capacities[i] + " kWh, max " + (int)maxPowers[i] + " kW)");
                if (isCharged[i]) {
                    System.out.print(" [DONE]");
                }
                System.out.println();
            }
            System.out.println("0. Close hub");

            // ==========================================
            // 2. INPUT CHOICE & VALIDATION
            // ==========================================
            int choice = -1;
            while (true) {
                System.out.print("Select a vehicle (0-4): ");
                String input = scanner.nextLine().trim();

                try {
                    choice = Integer.parseInt(input);
                    if (choice < 0 || choice > 4) {
                        System.out.println("Error: Invalid vehicle selection.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter an integer from 0 to 4.");
                }
            }

            // Check if operator wants to close hub
            if (choice == 0) {
                System.out.println("\nHub closed by operator.");
                break;
            }

            // Check if vehicle has already been charged
            if (isCharged[choice]) {
                System.out.println("Error: This vehicle has already been charged.\n");
                continue;
            }

            // ==========================================
            // 3. BATTERY LEVEL INPUT & VALIDATION
            // ==========================================
            int currentLevel = -1;
            while (true) {
                System.out.print("Enter current battery level (0-99): ");
                String input = scanner.nextLine().trim();
                try {
                    currentLevel = Integer.parseInt(input);
                    if (currentLevel < 0 || currentLevel > 99) {
                        System.out.println("Error: Current level must be between 0 and 99.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter an integer.");
                }
            }

            int targetLevel = -1;
            while (true) {
                System.out.print("Enter target level (" + (currentLevel + 1) + "-100): ");
                String input = scanner.nextLine().trim();
                try {
                    targetLevel = Integer.parseInt(input);
                    if (targetLevel <= currentLevel || targetLevel > 100) {
                        System.out.println("Error: Target must be higher than the current level and at most 100.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter an integer.");
                }
            }

            // ==========================================
            // 4. ENERGY CALCULATION (Accounting for 10% loss)
            // ==========================================
            double capacity = capacities[choice];
            double deliveredNeeded = capacity * (targetLevel - currentLevel) / 100.0;
            double drawnNeeded = deliveredNeeded / 0.90; // 10% lost as heat, so hub gives more

            boolean isPartial = false;
            double actualDelivered = deliveredNeeded;
            double actualDrawn = drawnNeeded;
            int finalTargetLevel = targetLevel;

            // ==========================================
            // 6. PARTIAL CHARGE CHECK (If Hub is Depleted)
            // ==========================================
            if (drawnNeeded > hubBattery) {
                isPartial = true;
                actualDrawn = hubBattery;
                actualDelivered = hubBattery * 0.90; // Car receives 90% of remaining hub energy
                
                // Calculate actual percentage reached
                double percentageAdded = (actualDelivered / capacity) * 100.0;
                double exactReached = currentLevel + percentageAdded;
                
                // Cap final target formatting cleanly
                finalTargetLevel = (int) exactReached;
            }

            // ==========================================
            // 5. CHARGING POWER & TIME CALCULATION
            // ==========================================
            double vehicleMaxPower = maxPowers[choice];
            double chargingPower = Math.min(vehicleMaxPower, 60.0); // Station max is 60 kW
            double chargingTimeMinutes = (actualDelivered / chargingPower) * 60.0;

            // ==========================================
            // 7. COST CALCULATION (RM 1.20/kWh with 5% discount if > 30 kWh)
            // ==========================================
            double cost = actualDelivered * 1.20;
            if (actualDelivered > 30.0) {
                cost *= 0.95; // Apply 5% discount
            }

            // Update Hub Battery and bookkeeping
            hubBattery -= actualDrawn;
            if (hubBattery < 0) hubBattery = 0.0; // Clean up any micro negative float values

            isCharged[choice] = true; // Mark vehicle as done
            vehiclesChargedCount++;
            totalEnergyDelivered += actualDelivered;
            totalRevenue += cost;

            // ==========================================
            // PRINT CHARGING SUMMARY
            // ==========================================
            System.out.println("\n--- Charging Summary ---");
            System.out.println("Vehicle: " + vehicleNames[choice]);
            System.out.println("Battery: " + currentLevel + "% -> " + (isPartial ? String.format("%.2f", (currentLevel + (actualDelivered / capacity) * 100.0)) + "%" : targetLevel + "%"));
            System.out.println("Charging Power: " + (int)chargingPower + " kW");
            System.out.printf("Energy Delivered: %.2f kWh (drawn from hub: %.2f kWh)\n", actualDelivered, actualDrawn);
            System.out.printf("Charging Time: %.2f minutes\n", chargingTimeMinutes);
            System.out.printf("Cost: RM %.2f\n", cost);
            System.out.printf("Remaining Hub Battery: %.2f kWh\n", hubBattery);
            System.out.println("------------------------\n");

            // If partial charge happened due to depletion, trigger termination message and break loop
            if (isPartial) {
                System.out.printf("Hub Battery Depleted! Vehicle only reached %.2f%%.\n", (currentLevel + (actualDelivered / capacity) * 100.0));
                System.out.println("------------------------\n");
                break;
            }
        }

        // ==========================================
        // 8. FINAL HUB REPORT
        // ==========================================
        System.out.println("\n===== Hub Report =====");
        System.out.println("Vehicles Charged: " + vehiclesChargedCount);
        System.out.printf("Total Energy Delivered: %.2f kWh\n", totalEnergyDelivered);
        System.out.printf("Total Revenue: RM %.2f\n", totalRevenue);
        System.out.printf("Hub Battery Left: %.2f kWh\n", hubBattery);

        scanner.close();
    }
}