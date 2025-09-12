package simpletron;

import simpletron.core.Simpletron;
import simpletron.services.BootService;
import simpletron.services.FileService;
import simpletron.services.KeyboardService;

import java.rmi.registry.LocateRegistry;
import java.util.List;

public class Main {



    public static void main(String[] args) {
        Simpletron simpletronV2 = new Simpletron();
        BootService bs = new BootService();

        List<Integer> instructions = bs.boot();

        simpletronV2.loadInstructionsIntoMemory(instructions);

        simpletronV2.coreDump(0,0);

        // simpletron run program (will execute instruction cycles, fetch -> decode -> execute)
        // terminate simulation gracefully
        // write program in simpletron machine language

    }
}
