package controller;

import java.util.Scanner;

public class InputScanner {
    private static Scanner scanner = new Scanner(System.in);

    public static String nextLine() {
        return scanner.nextLine().trim();
    }
}
