package simpletron;

import simpletron.core.Simpletron;

public class Main {
    public static void main(String[] args) {

        Simpletron simpletronV2 = new Simpletron();
        simpletronV2.memory[0] = 1;
        simpletronV2.memory[1] = 2;
        simpletronV2.memory[2] = -3876;
        simpletronV2.memory[99] = 9;
        simpletronV2.memory[100] = 8;

        simpletronV2.memory[9999] = 5;

        simpletronV2.accumulatorRegister = 0;
        simpletronV2.indexRegister = 223;
        simpletronV2.instructionCounterRegister = 123456;
        simpletronV2.instructionRegister = 123456;

        simpletronV2.coreDump(0, 0);
    }
}

/*
Alright, so you need to make some packages and files and shit...
make a couples dirs:
- core: simpletron.java, operation codes...
- services: all the extra functions need (like i/o, intro program, file reading...)
 */