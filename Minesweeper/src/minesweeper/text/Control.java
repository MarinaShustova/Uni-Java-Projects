package minesweeper.text;

import minesweeper.Field;

import java.util.Scanner;

public class Control {
    public void run(int xsize, int ysize, int bnum) {
        boolean br = false;
        Field f = new Field(xsize,ysize,bnum);
        Drawer d = new Drawer();
        d.showMenu();
        f.debug = false;
        String[] response;
        try(Scanner s = new Scanner(System.in)) {
            while ((f.gameIsOn)&&(!br)) {
                response = getResponse(s).split(" ");
                if ((response.length == 2)&&(isInteger(response[0]))) {
                    f.updateAfterClick(Integer.parseInt(response[0]), Integer.parseInt(response[1]), 0);
                    d.Draw(f);
                } else {
                    switch (response[0].toLowerCase()) {
                        case "new": {
                            d.Draw(f);
                            System.out.println("\n\n");
                            break;
                        }
                        case "high": {
                            System.out.println("Coming soon :)");
                            break;
                        }
                        case "about": {
                            System.out.println("3rd laboratory task made by Shustova Marina, 16203");
                            break;
                        }
                        case "exit": {
                            //f.gameIsOn = false;
                            br = true;
                            //System.out.println("Coming soon :)");
                            break;
                        }
                    }
                }
            }
            if(!f.gameIsOn){
                System.out.println("Whoops, there was a bomb! Try again.");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public String getResponse(Scanner s) {
        String message = "";
        message = s.nextLine();
        return message;
    }
    boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
