package simpletron.core;

import simpletron.services.ScannerService;

import java.util.List;
import java.util.Scanner;

public class Simpletron {
    private enum OperationCodes {
        UNUSED_0, UNUSED_1, UNUSED_2, UNUSED_3, UNUSED_4,
        UNUSED_5, UNUSED_6, UNUSED_7, UNUSED_8, UNUSED_9,

        READ,
        WRITE,

        UNUSED_12, UNUSED_13, UNUSED_14, UNUSED_15,
        UNUSED_16, UNUSED_17, UNUSED_18, UNUSED_19,

        LOAD,
        LOADIM,
        LOADX,
        LOADIDX,

        UNUSED_24,

        STORE,
        STOREIDX,

        UNUSED_27, UNUSED_28, UNUSED_29,

        ADD,
        ADDX,
        SUBTRACT,
        SUBTRACTX,
        DIVIDE,
        DIVIDEX,
        MULTIPLY,
        MULTIPLYX,
        INC,
        DEC,
        BRANCH,
        BRANCHNEG,
        BRANCHZERO,
        SWAP,

        UNUSED_44,

        HALT
    }

    private final int MEMORY_SIZE = 10000;
    private final int PAGE_SIZE = 100;

    private final int[] memory = new int[MEMORY_SIZE];

    private int accumulatorRegister = 0;
    private int instructionCounterRegister = 0;
    private int instructionRegister = 0;
    private int indexRegister = 0;

    public Simpletron() {}


    public void runProgram() {

        // each iteration represents one instruction cycle of the Von Neumann architecture
        while (instructionCounterRegister < MEMORY_SIZE) {

            // fetch
            int currentInstruction = fetchInstruction();
            this.instructionRegister = currentInstruction;

            // decode
            int operationCode = decodeOperationCode(currentInstruction);
            int operand = decodeOperand(currentInstruction);

            // execute
            boolean programHasAnotherInstruction = executeInstruction(operationCode, operand);

            if (!programHasAnotherInstruction) {
                break;
            }

            this.instructionCounterRegister++;
        }

        if (this.instructionCounterRegister >= MEMORY_SIZE) {
            throw new RuntimeException("Simpletron memory overflowed, or instruction counter is out of memory bounds");
        }
    }

    private boolean executeInstruction(int operationCode, int operand) {
        if (operationCode > 45) {
            System.out.println("Wrong operation code: " + operationCode + "\n");
            this.coreDump(0, 0);
            System.exit(0);
        }

        OperationCodes opCode = OperationCodes.values()[Math.abs(operationCode)];

        switch (opCode) {
            case READ:
                this.read(operand);
                break;

            case WRITE:
                this.write(operand);
                break;

            case LOAD:
                this.load(operand);
                break;

            case LOADX:
                this.loadX(operand);
                break;

            case LOADIM:
                this.loadIm(operand);
                break;

            case LOADIDX:
                this.loadIdx();
                break;

            case STORE:
                this.store(operand);
                break;

            case STOREIDX:
                this.storeIdx();
                break;

            case ADD:
                this.add(operand);
                break;

            case ADDX:
                this.addX();
                break;

            case SUBTRACT:
                this.subtract(operand);
                break;

            case SUBTRACTX:
                this.subtractX();
                break;

            case DIVIDE:
                this.divide(operand);
                break;

            case DIVIDEX:
                this.divideX();
                break;

            case MULTIPLY:
                this.multiply(operand);
                break;

            case MULTIPLYX:
                this.multiplyX();
                break;

            case INC:
                this.inc();
                break;

            case DEC:
                this.dec();
                break;

            case BRANCH:
                this.branch(operand);
                break;

            case BRANCHNEG:
                this.branchNeg(operand);
                break;

            case BRANCHZERO:
                this.branchZero(operand);
                break;

            case SWAP:
                this.swap();
                break;

            case HALT:
                System.out.println();
                this.halt(operand);
                return false;

            default:
                System.out.println("Wrong operation code: " + operationCode + "\n");
                this.coreDump(0, 0);
                System.exit(0);
        }

        validateAccumulatorRegister();

        return true;
    }

    private void validateAccumulatorRegister() {
        if (this.accumulatorRegister > 999999 || this.accumulatorRegister < -999999) {
            System.out.println("Accumulator overflow\n");
            this.coreDump(0, 0);
            System.exit(0);
        }
    }

    private int decodeOperationCode(int instruction) {
        return instruction / 10000;
    }

    private int decodeOperand(int instruction) {
        return instruction % 10000;
    }

    private int fetchInstruction() {
        return this.memory[this.instructionCounterRegister];
    }

    public void loadInstructionsIntoMemory(List<Integer> instructions) {
        if (instructions.size() > 10000) {
            throw new RuntimeException("Simpletron memory overflow error");
        }

        int locationInMemory = 0;

        for (int instruction : instructions) {
//            if (!this.validateInstruction(instruction)) {
//                throw new RuntimeException("Simpletron instructions are invalid, encountered an instruction with more or less than 6 digits");
//            }

            this.memory[locationInMemory++] = instruction;
        }
        if (locationInMemory <= 0) {
            this.memory[0] = 450000;
        } else if (this.memory[locationInMemory - 1] / 10000 != 45) {  // adds in HALT op code in case user forgets
            this.memory[locationInMemory] = 450000;
        }
    }

//    private boolean validateInstruction(int instruction) {
//        StringBuilder stringInstruction = new StringBuilder(String.valueOf(instruction));
//
//        if (instruction < 0) {
//            stringInstruction.delete(0, 1);
//        }
//
//        return stringInstruction.length() == 6 || instruction == 0;
//    }

    // READ
    private void read(int operand) {
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

        // 6 digits (word size)
        int WORD_SIZE = 6;
        if (wordString.length() > WORD_SIZE) {
            throw new RuntimeException("Read value, word size, must be less then " + WORD_SIZE +  " digits");
        }
    }

    // WRITE
    private void write(int operand) {
        this.printIntegerWithCorrectAmountOfDigits(this.memory[operand], 6);
        System.out.println();
    }

    // LOAD
    private void load(int operand) {
        this.accumulatorRegister = this.memory[operand];
    }

    // LOADIM  --> this function is weird because it's not possible to load a full
    //             word into the accumulator, only 4 digits max...
    private void loadIm(int operand) {
        validateLoadIm(operand);
        this.accumulatorRegister = operand;
    }

    private void validateLoadIm(int operand) {
        StringBuilder operandString = new StringBuilder(String.valueOf(operand));

        // removing negative sign which will turn 6 digit values into 7
        if (operand < 0) {
            operandString.delete(0, 1);
        }

        int OPERAND_SIZE = 4;
        if (operandString.length() > OPERAND_SIZE) {
            throw new RuntimeException("Read value, operand size, must be less then " + OPERAND_SIZE +  " digits");
        }
    }

    // LOADX
    private void loadX(int operand) {
        this.indexRegister = this.memory[operand];
    }

    // LOADIDX
    private void loadIdx() {
        this.accumulatorRegister = this.memory[this.indexRegister];
    }

    // STORE
    private void store(int operand) {
        this.memory[operand] = this.accumulatorRegister;
    }

    // STOREIDX
    private void storeIdx() {
        this.memory[this.indexRegister] = this.accumulatorRegister;
    }

    // ADD
    private void add(int operand) {
        this.accumulatorRegister += this.memory[operand];
    }

    // ADDX
    private void addX() {
        this.accumulatorRegister += this.memory[this.indexRegister];
    }

    // SUBTRACT
    private void subtract(int operand) {
        this.accumulatorRegister -= this.memory[operand];
    }

    // SUBTRACTX
    private void subtractX() {
        this.accumulatorRegister -= this.memory[this.indexRegister];
    }

    // DIVIDE
    private void divide(int operand) {
        if (operand == 0) {
            System.out.println("Divide by zero undefined\n");
            this.coreDump(0, 0);
            System.exit(0);
        }

        this.accumulatorRegister /= this.memory[operand];
    }

    // DIVIDEX
    private void divideX() {
        this.accumulatorRegister /= this.memory[this.indexRegister];
    }

    // MULTIPLY
    private void multiply(int operand) {
        this.accumulatorRegister *= this.memory[operand];
    }

    // MULTIPLYX
    private void multiplyX() {
        this.accumulatorRegister *= this.memory[this.indexRegister];
    }

    // INC
    private void inc() {
        this.indexRegister++;
    }

    // DEC
    private void dec() {
        this.indexRegister--;
    }

    // BRANCH                                            // they were all - 2
    private void branch(int operand) {
        this.instructionCounterRegister = operand - 1;
    }

    // BRANCHNEG
    private void branchNeg(int operand) {
        if (this.accumulatorRegister < 0) {
            this.instructionCounterRegister = operand - 1;
        }
    }

    // BRANCHZERO
    private void branchZero(int operand) {
        if (this.accumulatorRegister == 0) {
            this.instructionCounterRegister = operand - 1;
        }
    }

    // SWAP
    private void swap() {
        int tmp = this.accumulatorRegister;
        this.accumulatorRegister = indexRegister;
        this.indexRegister = tmp;
    }

    // HALT
    private void halt(int operand) {
        int lowRange = operand / 100;
        int highRange = operand % 100;
        coreDump(lowRange, highRange);
    }

    private void coreDump(int lowPageRange, int highPageRange) {
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

        System.out.print(correctNumber);
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
        System.out.println("*** PAGE ## " + pageNumber + " ***\n");
    }

    // TODO : refactor this into a function that prints each register
    private void printRegisters() {
        System.out.println("REGISTERS:");

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
