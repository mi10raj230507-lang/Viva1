/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva.pkg1;

/**
 *
 * @author Arivalagan
 */
import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int X, Y, B;

        // Input Loop
        while (true) {
            System.out.print("Enter width X: ");
            X = scan.nextInt();
            System.out.print("Enter height Y: ");
            Y = scan.nextInt();
            System.out.print("Enter block size B: ");
            B = scan.nextInt();

            if (X >= 7 && X <= 29 && X % 2 != 0 &&
                Y >= 7 && Y <= 29 && Y % 2 != 0 &&
                B >= 1 && B <= 3) {
                break; 
            }
            else{
                System.out.println("Error: X and Y must be odd numbers between 7 and 29, and B must be between 1 and 3.");
                System.out.println();
            }
        }

        // Calculate Centre Positions
        int centreRow = Y / 2;
        int centreCol = X / 2;

        // Print Grid Row by Row
        for (int r = 0; r < Y; r=r+1) {
            // Calculate diagonal columns for row r
            int diag1 = (int) Math.round((double) r * (X - 1) / (Y - 1));
            int diag2 = (X - 1) - diag1;

            for (int c = 0; c < X; c=c+1) {

        
                if ((r == 0 || r == Y - 1) && (c == 0 || c == X - 1)) {
                    System.out.print("+");

                } else if (r == 0 || r == Y - 1) {
                    System.out.print("=");

                } else if (c == 0 || c == X - 1) {
                   System.out.print("I");

                } else if (r == centreRow && c == centreCol) {
                    System.out.print("@");

                } else if (c == diag1 || c == diag2) {
                    System.out.print("X");

                } else if (r == centreRow) {
                     System.out.print("-");

                } else if (c == centreCol) {
                     System.out.print("|");

                } else if ((r / B + c / B) % 2 == 0) {
                    System.out.print(".");

                } else {
                     System.out.print("o");
                }
            }
            System.out.println(); 
        }

    }
}