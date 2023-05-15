package Chronicler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import Spot.Spot;

public class Chronicler {

    public Chronicler (String fileName) {
        try {
            out = new FileOutputStream(new File(fileName));

        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
       
    }
 
    public void writeChronics(List<Spot> spots) { 
        try {
            out.write("==========================================\n".getBytes());
            for (var spot: spots) {
                out.write(spot.getInfo().getBytes());
            }
        } catch(IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    public void writeChronics(List<Spot> spots, int iteration) { 
        try {
            String header = "\n==================iteration: " + iteration + " ========================\n";
            out.write(header.getBytes());
            for (var spot: spots) {
                out.write(spot.getInfo().getBytes());
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void demote() {
        try {
            out.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private OutputStream out = null;  
}
