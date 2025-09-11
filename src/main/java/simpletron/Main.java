package simpletron;

import simpletron.core.Simpletron;

public class Main {
    public static void main(String[] args) {

        Simpletron simpletronV2 = new Simpletron();
        simpletronV2.memory[0] = 1;
        simpletronV2.memory[1] = 2;
        simpletronV2.memory[2] = 3;
        simpletronV2.memory[9999] = 1;


        simpletronV2.coreDump(99, 99);


    }
}

/*
Alright, so you need to make some packages and files and shit...
make a couples dirs:
- core: simpletron.java, operation codes...
- services: all the extra functions need (like i/o, intro program, file reading...)
 */