/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva.pkg1;

/**
 *
 * @Author ChenAo
 */
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter Original Phrase: ");
        String phrase = input.nextLine();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        int sum = 0;
        for (int i = 0; i < phrase.length(); i++) {
            char ch = phrase.charAt(i);
            if (ch >= '0' && ch <= '9') {
                sum = sum + (ch - '0');
            }
        }

        int key = sum % 26;
        if (key == 0) {
            key = 13;
        }

        String encoded = "";
        for (int i = 0; i < phrase.length(); i++) {
            char ch = phrase.charAt(i);

            if (ch >= 'a' && ch <= 'z') {
                int pos = ch - 'a';
                int newPos = (pos + key) % 26;
                encoded = encoded + alphabet.charAt(newPos);
            } else if (ch >= 'A' && ch <= 'Z') {
                int pos = ch - 'A';
                if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U') {
                    encoded = encoded + "*" + alphabet.charAt(pos);
                } else {
                    int newPos = (pos + 1) % 26;
                    encoded = encoded + alphabet.charAt(newPos);
                }
            } else if (ch >= '0' && ch <= '9') {
                int digit = ch - '0';
                encoded = encoded + ((digit * digit) % 10);
            } else if (ch == ' ') {
                encoded = encoded + '.';
            } else if (ch == '!') {
                encoded = encoded + '#';
            } else if (ch == '@') {
                encoded = encoded + '$';
            } else if (ch == '#') {
                encoded = encoded + '%';
            } else if (ch == '$') {
                encoded = encoded + '!';
            } else if (ch == '%') {
                encoded = encoded + '@';
            } else {
                encoded = encoded + ch;
            }
        }

        String password = "";
        for (int i = encoded.length() - 1; i >= 0; i--) {
            password = password + encoded.charAt(i);
        }

        System.out.println("Shift Key: " + key);
        System.out.println("Before Reversal: " + encoded);
        System.out.println("Encoded Password: " + password);

        input.close();
    }
}
