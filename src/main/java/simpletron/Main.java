package simpletron;

import simpletron.core.Simpletron;
import simpletron.services.FileService;
import simpletron.services.KeyboardService;

import java.rmi.registry.LocateRegistry;
import java.util.List;

public class Main {



    public static void main(String[] args) {

        Simpletron simpletronV2 = new Simpletron();

        FileService fs = new FileService();
        List<Integer> list =  fs.executeService();

        for (int e : list) {
            System.out.println(e);
        }

    }
}
