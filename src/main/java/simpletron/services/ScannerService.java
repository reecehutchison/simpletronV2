package simpletron.services;

import java.util.Scanner;

// Singleton design pattern.

public class ScannerService {
    private static Scanner scanner = null;

    private ScannerService() {}

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        return scanner;
    }
}
