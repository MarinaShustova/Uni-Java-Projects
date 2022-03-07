package minesweeper;

import minesweeper.gui.GDrawer;
import minesweeper.text.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Integer x = 10, y = 10, b = 9, mode = 0;
        if(args.length > 1) {
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
            b = Integer.parseInt(args[3]);
        }
            if(args[0].equals("text"))
                mode = 0;
            else if(args[0].equals("gui"))
                mode = 1;
        if (mode == 0){
            Control c = new Control();
            c.run(x, y, b);
        }
        else if (mode == 1){
            GDrawer gd = new GDrawer(x, y, b);
        }
        else
            System.out.println("Incorrect call\n");
    }
}
