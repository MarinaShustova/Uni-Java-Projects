package minesweeper.text;

import minesweeper.Field;

public class Drawer {
   public void Draw(Field f) {
       System.out.print("   ");
       for (int k = 0; k < f.sizey-2; ++k){
           System.out.print(k);
       }
       System.out.println();
        for(int i = 1; i<f.sizex-1; ++i) {
                for (int j = 0; j < f.sizey - 1; ++j) {
                    if (j == 0)
                        System.out.print(i-1 + "  ");
                    else if ((f.ofield[i][j] == 1) || (f.debug))
                        System.out.print(f.field[i][j]);
                    else
                        System.out.print("*");
                }
                System.out.println("\n");
        }
    }
    public void showMenu() {
        System.out.println("Menu:\nNew game\nHigh Scores\nAbout\nExit\n");
    }
}
