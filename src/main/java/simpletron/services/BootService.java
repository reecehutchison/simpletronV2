package simpletron.services;

import java.util.List;
import java.util.Scanner;

public class BootService {
    private enum BootType {
        FileBoot,
        TerminalBoot
    }

    public List<Integer> instructions;

    public BootService() {}

    public List<Integer> boot() {
        this.printBootMessage();
        BootType bootType = BootType.values()[readBootType()];

        switch (bootType) {
            case FileBoot:
                FileService fs = new FileService();
                this.instructions = fs.executeService();
                break;

            case TerminalBoot:
                KeyboardService kbs = new KeyboardService();
                this.instructions = kbs.executeService();
                break;

            default:
                throw new RuntimeException("Boot type now supported");
        }

        System.out.println(); // add spacing so it's pretty
        return this.instructions;
    }

    private void printBootMessage() {
        String bootMessage = """
                *** Welcome to Simpletron V2! ***
                ***
                Do you have a file that contains your SML program (Y/N) ?\s""";  // Note: \s is a space (escape char)

        System.out.print(bootMessage);
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
