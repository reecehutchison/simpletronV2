package simpletron.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BootService {
    private enum BootType {
        FileBoot,
        TerminalBoot
    }

    public List<Integer> instructions = new ArrayList<>();

    public BootService() {}

    public void boot() {
        this.printBootMessage();
        BootType bootType = BootType.values()[readBootType()];

        switch (bootType) {
            case FileBoot:
                // file service
                // update instruction list
                break;
            case TerminalBoot:
                // terminal service
                // update instruction list
                break;
            default:
                throw new RuntimeException("Boot type now supported");
        }
    }

//    public int[] getInstructions() {
//
//    }


    private void printBootMessage() {
        String bootMessage = """
                *** Welcome to Simpletron V2! ***
                ***
                Do you have a file that contains your SML program (Y/N) ? 
                """;

        System.out.println(bootMessage);
    }

    private int readBootType() throws RuntimeException {
        Scanner scanner = ScannerService.getScanner();
        String bootType = scanner.next().toLowerCase();

        if (bootType.equals("y")) {
            return 0;
        } else if (bootType.equals("n")) {
            return 1;
        } else {
            throw new RuntimeException("Must chose either 'Y' or 'N', please restart the program");
        }
    }
}
