package simpletron.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KeyboardService {
    final private List<Integer> instructions = new ArrayList<>();

    public KeyboardService() {}

    public List<Integer> executeService() {
        Scanner scanner = ScannerService.getScanner();
        printServiceMessage();
        int lineCount = 1;
        System.out.println("Line");

        while (lineCount < 10001) {
            printLineNumber(lineCount);
            System.out.print("  ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("go")) {
                break;
            }

            try {
                int instruction = Integer.parseInt(input);
                this.instructions.add(instruction);
                lineCount++;
            } catch (NumberFormatException e) {
                System.out.println("You can only enter integers, or the word 'GO'");
            }
        }

        return this.instructions;
    }

    private void printLineNumber(int lineNumber) {
        StringBuilder formatedLineNumber = new StringBuilder(String.valueOf(lineNumber));
        while (formatedLineNumber.length() < 4) {
            formatedLineNumber.insert(0, "0");
        }
        System.out.print(formatedLineNumber);
    }

    private void printServiceMessage() {
        String serviceMessage = """
                *** Please enter your program one instruction( or data word ) at a time        ***
                *** I will type the location number and a question mark (?). You then          ***
                *** type the word for that location. Type the word GO to execute  your program ***
                """;
        System.out.println(serviceMessage);
    }
}
