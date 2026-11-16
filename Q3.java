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

public class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Counters and accumulators for the final report after EXIT
        int parcelsProcessed = 0;
        double totalShippingFees = 0.0;
        int heaviestChargeableWeight = 0;

        while (true) {
            // Prompt for destination postcode
            System.out.print("Enter destination postcode (or EXIT to stop): ");
            String rawPostcode = scanner.nextLine().trim();

            // Check for exit condition (case-insensitive)
            if (rawPostcode.equalsIgnoreCase("EXIT")) {
                break;
            }

            // ==========================================
            // 1. NORMALIZATION & VALIDATION OF POSTCODE
            // ==========================================
            // Remove spaces, hyphens, and convert to uppercase
            String postcode = rawPostcode.replace(" ", "").replace("-", "").toUpperCase();

            // Remove leading "MY" if present (e.g., my-88000 -> 88000)
            if (postcode.startsWith("MY")) {
                postcode = postcode.substring(2);
            }

            boolean isInternational = false;
            String zone = "";
            String countryCode = "";
            int areaCode = -1;

            // Check if it starts with TWO letters (International)
            if (postcode.length() >= 2 && Character.isLetter(postcode.charAt(0)) && Character.isLetter(postcode.charAt(1))) {
                isInternational = true;
                countryCode = postcode.substring(0, 2);
                zone = "International (" + countryCode + ")";
            } else {
                // Otherwise, must be exactly 5 digits
                if (postcode.length() != 5 || !postcode.matches("\\d{5}")) {
                    System.out.println("Error: Invalid postcode. Parcel skipped.\n");
                    continue;
                }

                // Extract first two digits for area classification
                areaCode = Integer.parseInt(postcode.substring(0, 2));

                // Validate area code range (01 to 98)
                if (areaCode < 1 || areaCode > 98) {
                    System.out.println("Error: Invalid postcode. Parcel skipped.\n");
                    continue;
                }

                // Classify domestic zones based on area code table
                if (areaCode >= 50 && areaCode <= 60) {
                    zone = "Klang Valley";
                } else if ((areaCode >= 1 && areaCode <= 49) || (areaCode >= 61 && areaCode <= 86)) {
                    zone = "Peninsular";
                } else if (areaCode >= 87 && areaCode <= 98) {
                    zone = "East Malaysia";
                }
            }

            // ==========================================
            // 3. WEIGHT INPUT & VALIDATION
            // ==========================================
            int weightGrams = 0;
            while (true) {
                System.out.print("Enter parcel weight (g): ");
                String weightInput = scanner.nextLine().trim();

                try {
                    weightGrams = Integer.parseInt(weightInput);
                    if (weightGrams <= 0) {
                        System.out.println("Error: Weight must be greater than 0.");
                        continue;
                    }
                    break; // Valid integer > 0
                } catch (NumberFormatException e) {
                    System.out.println("Error: Weight must be an integer.");
                }
            }

            // ==========================================
            // 4. DIMENSIONS INPUT & VALIDATION (LxWxH)
            // ==========================================
            int length = 0, width = 0, height = 0;
            while (true) {
                System.out.print("Enter dimensions LxWxH (cm): ");
                String dimInput = scanner.nextLine().trim();

                // Validate format: must contain exactly two 'x' or 'X' characters
                // and numbers separated by x
                String[] parts = dimInput.toLowerCase().split("x");
                if (parts.length != 3) {
                    System.out.println("Error: Use the format LxWxH with positive numbers, e.g. 30x20x10.");
                    continue;
                }

                try {
                    length = Integer.parseInt(parts[0].trim());
                    width = Integer.parseInt(parts[1].trim());
                    height = Integer.parseInt(parts[2].trim());

                    if (length <= 0 || width <= 0 || height <= 0) {
                        System.out.println("Error: Use the format LxWxH with positive numbers, e.g. 30x20x10.");
                        continue;
                    }
                    break; // Valid dimensions format and positive numbers
                } catch (NumberFormatException e) {
                    System.out.println("Error: Use the format LxWxH with positive numbers, e.g. 30x20x10.");
                }
            }

            // ==========================================
            // 5. CHARGEABLE WEIGHT CALCULATION
            // ==========================================
            double actualKg = weightGrams / 1000.0;
            double volumetricKg = (length * width * height) / 5000.0;
            
            // Chargeable weight is the larger of the two, rounded UP to a whole kg
            int chargeableWeight = (int) Math.ceil(Math.max(actualKg, volumetricKg));

            // ==========================================
            // 6. TIERED ADJUSTMENT & SHIPPING FEE CALCULATION
            // ==========================================
            double baseFee = 0.0;
            if (isInternational) {
                // International: RM 35.00 per kg (flat)
                baseFee = 35.00 * chargeableWeight;
            } else {
                double firstKgRate = 0.0;
                double additionalKgRate = 0.0;

                if (zone.equals("Klang Valley")) {
                    firstKgRate = 6.00;
                    additionalKgRate = 1.50;
                } else if (zone.equals("Peninsular")) {
                    firstKgRate = 8.00;
                    additionalKgRate = 2.00;
                } else if (zone.equals("East Malaysia")) {
                    firstKgRate = 14.00;
                    additionalKgRate = 4.50;
                }

                // Calculate base fee: First kg + (additional kg * rate)
                if (chargeableWeight == 1) {
                    baseFee = firstKgRate;
                } else {
                    baseFee = firstKgRate + (chargeableWeight - 1) * additionalKgRate;
                }
            }

            // Apply Tiered Adjustment / Surcharge / Discount
            String adjustmentDesc = "None";
            double finalFee = baseFee;

            if (chargeableWeight >= 5 && chargeableWeight <= 10) {
                adjustmentDesc = "5% bulk discount";
                finalFee = baseFee * 0.95;
            } else if (chargeableWeight > 10) {
                adjustmentDesc = "15% heavy parcel surcharge";
                finalFee = baseFee * 1.15;
            }

            // ==========================================
            // PRINT INDIVIDUAL PARCEL SUMMARY
            // ==========================================
            System.out.println("\n=== Shipping Summary ===");
            // Wait, let's output the normalized postcode properly matching sample output:
            // In sample output, Postcode shown is: 50603, 88000, SG238801
            // Let's ensure postcode display uses the exact clean normalized string:
            // For international it's country code + digits, for domestic it's 5 digits.
            // Let's refine the print statement below:
            
            // Re-adjusting postcode display cleanly:
            String displayPostcode = isInternational ? countryCode + postcode.substring(2) : postcode;
            // Wait, postcode variable already holds the cleaned uppercase version without MY.
            
            // Let's write out the exact summary block:
            System.out.println("Postcode: " + postcode);
            System.out.println("Zone: " + zone);
            System.out.printf("Actual Weight: %.2f kg | Volumetric Weight: %.2f kg\n", actualKg, volumetricKg);
            System.out.println("Chargeable Weight: " + chargeableWeight + " kg");
            System.out.println("Adjustment: " + adjustmentDesc);
            System.out.printf("Shipping Fee: RM %.2f\n\n", finalFee);

            // Update accumulators for final report
            parcelsProcessed++;
            totalShippingFees += finalFee;
            if (chargeableWeight > heaviestChargeableWeight) {
                heaviestChargeableWeight = chargeableWeight;
            }
        }

        // ==========================================
        // 7. FINAL REPORT AFTER EXIT
        // ==========================================
        System.out.println("Parcels Processed: " + parcelsProcessed);
        System.out.printf("Total Shipping Fees: RM %.2f\n", totalShippingFees);
        System.out.println("Heaviest Chargeable Weight: " + heaviestChargeableWeight + " kg");

        scanner.close();
    }
}