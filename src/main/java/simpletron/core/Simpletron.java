package simpletron.core;

// TODO: setters and getters and make class fields private


public class Simpletron {
    final int MEMORY_SIZE = 10000;
    final int PAGE_SIZE = 100;

    public int[] memory = new int[MEMORY_SIZE];

    int accumulatorRegister = 0;
    int instructionCounterRegister = 0;
    int instructionRegister = 0;
    int indexRegister = 0;

    public Simpletron() {

    }

    public void coreDump(int lowPageRange, int highPageRange) {

        this.validatePageRanges(lowPageRange, highPageRange);

        for (int currentPage = lowPageRange; currentPage <= highPageRange; currentPage++) {
            this.printPageNumber(currentPage);
            this.printRegisters();
            // get op code and print
            // get operand and print
            this.printPageMemory(currentPage);
        }
    }

    private void printPageMemory(int pageNumber) {
        System.out.println("MEMORY\n");
        System.out.println("       0      1      2      3      4      5      6      7      8      9");

        int currentPageLocation = pageNumber * PAGE_SIZE;

        for (int row = 0; row < 10; row++) {
            System.out.print(row + " ");

            for (int memoryIndex = currentPageLocation; memoryIndex < currentPageLocation + 10; memoryIndex++) {
                printIntegerWithCorrectAmountOfDigits(this.memory[memoryIndex]);
                System.out.print(" ");
            }

            currentPageLocation += 10;
            System.out.println();
        }

    }

    private void printIntegerWithCorrectAmountOfDigits(int number) {
        String stringNumber = String.valueOf(number);
        StringBuilder correctNumber = new StringBuilder(stringNumber);

        while (correctNumber.length() < 6) {
            correctNumber.insert(0, 0);
        }

        System.out.print(correctNumber.toString());
    }

    private void validatePageRanges(int lowPageRange, int highPageRange) throws IllegalArgumentException {
        if (lowPageRange < 0 || highPageRange < 0) {
            throw new IllegalArgumentException("Pages range is 0 to " + this.MEMORY_SIZE / this.PAGE_SIZE);
        }

        if (lowPageRange > this.MEMORY_SIZE / this.PAGE_SIZE  || highPageRange > this.MEMORY_SIZE / this.PAGE_SIZE) {
            throw new IllegalArgumentException("Pages range is 0 to " + this.MEMORY_SIZE / this.PAGE_SIZE);
        }

        if (lowPageRange > highPageRange) {
            throw new IllegalArgumentException("Low page must be less then high page");
        }
    }

    private void printPageNumber(int pageNumber) {
        System.out.println("PAGE ## " + pageNumber + "\n"); // add page number
    }

    private void printRegisters() {
        System.out.println("REGISTERS:\n");
        System.out.println("accumulator           " + this.accumulatorRegister);
        System.out.println("InstructionCounter    " + this.instructionCounterRegister);
        System.out.println("IndexRegister         " + this.indexRegister);
    }
}
