package simpletron.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {
    List<Integer> instructions = new ArrayList<>();
    List<String> lines = new ArrayList<>();

    public FileService() {}

    public List<Integer> executeService() {
        Scanner scanner = ScannerService.getScanner();
        System.out.print("Filename: ");
        String fileName = scanner.next();

        this.readFile(fileName);
        this.transferInstructions(fileName);

        return this.instructions;
    }

    private void transferInstructions(String fileName) {
        int lineCount = 1;

        for (String line : this.lines) {
            if (line == null || line.isEmpty()) {
                continue;
            }

            try {
                int instruction = Integer.parseInt(line);
                this.instructions.add(instruction);
                lineCount++;
            } catch (NumberFormatException e) {
                System.out.println("Error in " + fileName + " on line " + lineCount);
                System.exit(0);
            }
        }
    }

    private void readFile(String fileName) {
        try {
            this.lines = Files.readAllLines(Paths.get("src/main/resources/" + fileName));
        } catch (IOException e) {
            System.out.println("Error reading " + fileName + " : " + e.getMessage());
            throw new RuntimeException("FILE NOT FOUND");
        }
    }
}
