package simpletron.core;

// TODO: setters and getters and make class fields private
// TODO: MAKE EVERYTHING PRIVATE THAT SHOULD BE PRIVATE
// TODO: make sure to enumerate the switch statement
// TODO: remeber to follow the von nueman architecture, fetch -> decode -> execute (cycle)
// TODO: program run: will loop of instruction cycles (doing the fetch, decode, execute)
// todo: go through each class and click . and make sure nothing is public that should not be!

import simpletron.services.ScannerService;

import java.util.Scanner;

public class Simpletron {
    final int MEMORY_SIZE = 10000;
    final int PAGE_SIZE = 100;
    final int WORD_SIZE = 6; // 6 digits (word size)
    final int OPERAND_SIZE = 4;

    public int[] memory = new int[MEMORY_SIZE];

    public int accumulatorRegister = 0;
    public int instructionCounterRegister = 0;
    public int instructionRegister = 0;
    public int indexRegister = 0;

    public Simpletron() {}

    // TODO: loadInstructions()
    // TODO: executeInstructions()

    // READ
    public void read(int operand) {
        Scanner scanner = ScannerService.getScanner();
        System.out.print("? ");
        int word = scanner.nextInt();
        this.validateRead(word);
        this.memory[operand] = word;
    }

    private void validateRead(int word) throws RuntimeException {
        StringBuilder wordString = new StringBuilder(String.valueOf(word));

        // removing negative sign which will turn 6 digit values into 7
        if (word < 0) {
            wordString.delete(0, 1);
        }

        if (wordString.length() > WORD_SIZE) {
            throw new RuntimeException("Read value, word size, must be less then " + this.WORD_SIZE +  " digits");
        }
    }

    // WRITE
    public void write(int operand) {
        this.printIntegerWithCorrectAmountOfDigits(this.memory[operand], 6);
        System.out.println();
    }

    // LOAD
    public void load(int operand) {
        this.accumulatorRegister = this.memory[operand];
    }

    // LOADIM  --> this function is weird because it's not possible to load a full
    //             word into the accumulator, only 4 digits max...
    public void loadIm(int operand) {
        validateLoadIm(operand);
        this.accumulatorRegister = operand;
    }

    private void validateLoadIm(int operand) {
        StringBuilder operandString = new StringBuilder(String.valueOf(operand));

        // removing negative sign which will turn 6 digit values into 7
        if (operand < 0) {
            operandString.delete(0, 1);
        }

        if (operandString.length() > OPERAND_SIZE) {
            throw new RuntimeException("Read value, operand size, must be less then " + this.OPERAND_SIZE +  " digits");
        }
    }

    // LOADX
    public void loadX(int operand) {
        this.indexRegister = this.memory[operand];
    }

    // LOADIDX
    public void loadIdx() {
        this.accumulatorRegister = this.memory[this.indexRegister];
    }

    // STORE
    public void store(int operand) {
        this.memory[operand] = this.accumulatorRegister;
    }

    // STOREIDX
    public void storeIdx() {
        this.memory[this.indexRegister] = this.accumulatorRegister;
    }

    // ADD
    public void add(int operand) {
        this.accumulatorRegister += this.memory[operand];
    }

    // ADDX
    public void addX() {
        this.accumulatorRegister += this.memory[this.indexRegister];
    }

    // SUBTRACT
    public void subtract(int operand) {
        this.accumulatorRegister -= this.memory[operand];
    }

    // SUBTRACTX
    public void subtractX() {
        this.accumulatorRegister -= this.memory[this.indexRegister];
    }

    // DIVIDE
    public void divide(int operand) {
        this.accumulatorRegister /= this.memory[operand];
    }

    // DIVIDEX
    public void divideX() {
        this.accumulatorRegister /= this.memory[this.indexRegister];
    }

    // MULTIPLY
    public void multiply(int operand) {
        this.accumulatorRegister *= this.memory[operand];
    }

    // MULTIPLYX
    public void multiplyX(int operand) {
        this.accumulatorRegister *= this.memory[this.indexRegister];
    }

    // INC
    public void inc() {
        this.indexRegister++;
    }

    // DEC
    public void dec() {
        this.indexRegister--;
    }

    // BRANCH
    public void branch(int operand) {
        this.instructionCounterRegister = operand;
    }

    // BRANCHNEG
    public void branchNeg(int operand) {
        if (this.accumulatorRegister < 0) {
            this.instructionCounterRegister = operand;
        }
    }

    // BRANCHZERO
    public void branchZero(int operand) {
        if (this.accumulatorRegister == 0) {
            this.instructionCounterRegister = operand;
        }
    }

    // SWAP
    public void swap(int operand) {
        int tmp = this.accumulatorRegister;
        this.accumulatorRegister = indexRegister;
        this.indexRegister = tmp;
    }

    // HALT
    public void halt(int operand) {
        int lowRange = operand / 100;
        int highRange = operand % 100;
        coreDump(lowRange, highRange);
    }


    // TODO : make this private
    public void coreDump(int lowPageRange, int highPageRange) {
        this.validatePageRanges(lowPageRange, highPageRange);

        for (int currentPage = lowPageRange; currentPage <= highPageRange; currentPage++) {
            this.printPageNumber(currentPage);
            this.printRegisters();
            System.out.println();
            this.printPageMemory(currentPage);
            System.out.println();
        }
    }

    private void printPageMemory(int pageNumber) {
        printMemoryHeader();
        int currentPageLocation = pageNumber * PAGE_SIZE;
        final int ROW_SIZE = 10;

        for (int row = 0; row < 10; row++) {
            System.out.print(row + "  ");

            printMemoryRow(currentPageLocation);

            currentPageLocation += ROW_SIZE;
            System.out.println();
        }
    }

    private void printMemoryRow(int currentPageLocation) {
        final int ROW_SIZE = 10;

        for (int memoryIndex = currentPageLocation; memoryIndex < currentPageLocation + ROW_SIZE; memoryIndex++) {
            printIntegerWithCorrectAmountOfDigits(this.memory[memoryIndex], 6);
            System.out.print(" ");
        }
    }

    private void printMemoryHeader() {
        System.out.println("MEMORY\n");
        System.out.println("         0       1       2       3       4       5       6       7       8       9");
    }

    private void printIntegerWithCorrectAmountOfDigits(int number, int amountOfDigits) {
        String stringNumber = String.valueOf(number);
        StringBuilder correctNumber = new StringBuilder(stringNumber);

        if (number < 0) {
            correctNumber.delete(0, 1);
        }

        while (correctNumber.length() < amountOfDigits) {
            correctNumber.insert(0, 0);
        }

        if (number >= 0) {
            correctNumber.insert(0, "+");
        } else {
            correctNumber.insert(0, "-");
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
        System.out.println("PAGE ## " + pageNumber + "\n");
    }

    // TODO : refactor this into a function that prints each register
    private void printRegisters() {
        System.out.println("REGISTERS:\n");

        System.out.print("accumulator           ");
        this.printIntegerWithCorrectAmountOfDigits(this.accumulatorRegister, 6);
        System.out.println();

        System.out.print("InstructionCounter    ");
        this.printIntegerWithCorrectAmountOfDigits(this.instructionCounterRegister, 6);
        System.out.println();

        System.out.print("IndexRegister         ");
        this.printIntegerWithCorrectAmountOfDigits(this.indexRegister, 6);
        System.out.println();

        System.out.print("operationCode             ");
        this.printIntegerWithCorrectAmountOfDigits(this.instructionRegister / 10000, 2);
        System.out.println();

        System.out.print("operand                 ");
        this.printIntegerWithCorrectAmountOfDigits(this.instructionRegister % 10000, 4);
        System.out.println();
    }
}
