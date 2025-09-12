package simpletron;

import simpletron.core.Simpletron;
import simpletron.services.KeyboardService;

import java.util.List;

public class Main {



    public static void main(String[] args) {

        Simpletron simpletronV2 = new Simpletron();

        KeyboardService kb = new KeyboardService();

        List<Integer> instructions = kb.executeService();

        System.out.println("------");
        for (int e : instructions) {
            System.out.println(e);
        }


    }
}
