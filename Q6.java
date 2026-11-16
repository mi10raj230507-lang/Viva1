/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva.pkg1;

/**
 *
 * @author Miten
 */
import java.util.Scanner;
public class Q6 {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter robot commands: ");
        String commands = input.nextLine();
        boolean valid = true;

        for (int i = 0; i < commands.length(); i++) {

            char c = commands.charAt(i);

            if (c != 'F' && c != 'B' &&
                c != 'L' && c != 'R' &&
                c != 'f' && c != 'b' &&
                c != 'l' && c != 'r') {

                valid = false;
                break;
            }
        }

        if (!valid) {
            System.out.println("Invalid Command String");
        }

        else {

            int xP = 0;
            int yP = 0;
            int dirP = 0;

            int xQ = 0;
            int yQ = 0;
            int dirQ = 0;

            int meetings = 0;
            int originReturns = 0;
            int maximumGap = 0;

            char[] command = new char[commands.length()];
            int count = 0;

            for (int i = 0; i < commands.length(); i++) {

                char c = commands.charAt(i);

                if (c == 'F' || c == 'B' ||
                    c == 'L' || c == 'R') {

                    if (c == 'R') {
                        dirP = (dirP + 1) % 4;
                    }
                    else if (c == 'L') {
                        dirP = (dirP + 3) % 4;
                    }

                    // Forward movement
                    else if (c == 'F') {

                        if (dirP == 0) {
                            yP++;
                        }
                        else if (dirP == 1) {
                            xP++;
                        }
                        else if (dirP == 2) {
                            yP--;
                        }
                        else if (dirP == 3) {
                            xP--;
                        }
                    }

                    else if (c == 'B') {

                        if (dirP == 0) {
                            yP--;
                        }
                        else if (dirP == 1) {
                            xP--;
                        }
                        else if (dirP == 2) {
                            yP++;
                        }
                        else if (dirP == 3) {
                            xP++;
                        }
                    }

                    if (c == 'F' || c == 'B') {

                        if (xP == xQ && yP == yQ) {
                            meetings++;
                        }

                        if (xP == 0 && yP == 0) {
                            originReturns++;
                        }

                        int gap = Math.abs(xP - xQ)
                                + Math.abs(yP - yQ);

                        if (gap > maximumGap) {
                            maximumGap = gap;
                        }
                    }
                }
                
                else {

                    if (c == 'r') {
                        dirQ = (dirQ + 1) % 4;
                    }
                    else if (c == 'l') {
                        dirQ = (dirQ + 3) % 4;
                    }

                    else if (c == 'f') {

                        if (dirQ == 0) {
                            yQ++;
                        }
                        else if (dirQ == 1) {
                            xQ++;
                        }
                        else if (dirQ == 2) {
                            yQ--;
                        }
                        else if (dirQ == 3) {
                            xQ--;
                        }
                    }

                    else if (c == 'b') {

                        if (dirQ == 0) {
                            yQ--;
                        }
                        else if (dirQ == 1) {
                            xQ--;
                        }
                        else if (dirQ == 2) {
                            yQ++;
                        }
                        else if (dirQ == 3) {
                            xQ++;
                        }
                    }

                    if (c == 'f' || c == 'b') {

                        if (xP == xQ && yP == yQ) {
                            meetings++;
                        }

                        if (xQ == 0 && yQ == 0) {
                            originReturns++;
                        }

                        int gap = Math.abs(xP - xQ)
                                + Math.abs(yP - yQ);

                        if (gap > maximumGap) {
                            maximumGap = gap;
                        }
                    }
                }

                if (count > 0) {

                    char last = command[count - 1];

                    if ((last == 'F' && c == 'B') ||
                        (last == 'B' && c == 'F') ||
                        (last == 'L' && c == 'R') ||
                        (last == 'R' && c == 'L') ||
                        (last == 'f' && c == 'b') ||
                        (last == 'b' && c == 'f') ||
                        (last == 'l' && c == 'r') ||
                        (last == 'r' && c == 'l')) {

                        count--;
                    }
                    else{
                          command[count] = c;
                          count++;
                        
                    }
                }

                else {

                    command[count] = c;
                    count++;

                    if (c == 'R' || c == 'L' ||
                        c == 'r' || c == 'l') {

                        if (count >= 4) {

                            char a = command[count - 1];
                            char b = command[count - 2];
                            char d = command[count - 3];
                            char e = command[count - 4];

                            if (a == b && b == d && d == e) {
                                count = count - 4;
                            }
                        }
                    }
                }
            }

            System.out.println("Meetings: " + meetings);
            System.out.println("Origin Returns: " + originReturns);
            System.out.println("Maximum Gap: " + maximumGap);

            // Convert direction numbers to letters
            char headingP = 'N';
            char headingQ = 'N';

            if (dirP == 1) {
                headingP = 'E';
            }
            else if (dirP == 2) {
                headingP = 'S';
            }
            else if (dirP == 3) {
                headingP = 'W';
            }

            if (dirQ == 1) {
                headingQ = 'E';
            }
            else if (dirQ == 2) {
                headingQ = 'S';
            }
            else if (dirQ == 3) {
                headingQ = 'W';
            }

            System.out.println("Robot P Final: (" +
                    xP + "," + yP + ") facing " + headingP);

            System.out.println("Robot Q Final: (" +
                    xQ + "," + yQ + ") facing " + headingQ);

            // Print simplified commands
            System.out.print("Simplified Commands: ");

            if (count == 0) {
                System.out.println("EMPTY");
            }
            else {
                for (int i = 0; i < count; i++) {
                    System.out.print(command[i]);
                }
                System.out.println();
            }
        }

        input.close();
    }
}

