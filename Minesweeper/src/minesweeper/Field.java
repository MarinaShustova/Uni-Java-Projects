package minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Field {
    public int[][] field;
    public int[][] ofield;
    public int sizex, sizey, bam;
    public boolean gameIsOn;
    public boolean debug = true;

    public Field(int xsize, int ysize, int bnum) {
        Random rnd = new Random(System.currentTimeMillis());
        field = new int[xsize+2][ysize+2];
        ofield = new int[xsize+2][ysize+2];
        sizex = xsize+2;
        sizey = ysize+2;
        bam = bnum;
        gameIsOn = true;
        int bombs = bnum, x, y, isbomb;
        while (bombs>0) {
            x = rnd.nextInt(xsize) + 1;
            y = rnd.nextInt(ysize) + 1;
            if (field[x][y] != -1) {
                field[x][y] = -1;
                --bombs;
                if (field[x-1][y] >= 0)
                    ++field[x-1][y];
                if (field[x-1][y+1] >= 0)
                    ++field[x-1][y+1];
                if (field[x][y+1] >= 0)
                    ++field[x][y+1];
                if (field[x+1][y+1] >= 0)
                    ++field[x+1][y+1];
                if (field[x+1][y] >= 0)
                    ++field[x+1][y];
                if (field[x+1][y-1] >= 0)
                    ++field[x+1][y-1];
                if (field[x][y-1] >= 0)
                    ++field[x][y-1];
                if (field[x-1][y-1] >= 0)
                    ++field[x-1][y-1];
            }
        }
        for(int i = 0; i < xsize+2; ++i)
            for(int j = 0; j<ysize+2; ++j)
                if ((i == 0)||(j == 0)||(i == sizex-1)||(j == sizey-1))
                    field[i][j] = -1;

    }

    public void updateAfterClick(int x, int y, int flag) { //coords as user sees them
        if(flag == 1){
            ofield[x+1][y+1] = 3;
            if(field[x+1][y+1] == -1){
                --bam;
            }
        }
        else if (flag == -1){
            ofield[x+1][y+1] = 0;
        }
        else if (field[x+1][y+1] == -1) {
            gameIsOn = false;
            field[x+1][y+1] = -2;
        }
        else {
            if (field[x+1][y+1] != 0)
                ofield[x+1][y+1] = 1;
            else {
                ArrayList<Integer> q = new ArrayList<>();
                int x1, y1;
                q.add((x+1)*sizey+y+1);
                while(!q.isEmpty()) {
                    Integer c = q.get(0);
                    x1 = c/sizey;
                    y1 = c - (c/sizey)*sizey;
                    if ((field[x1 - 1][y1] >= 0) && (ofield[x1 - 1][y1] == 0)) {
                        if (field[x1 - 1][y1] == 0) {
                            q.add(((x1 - 1) * sizey + y1));
                            ofield[x1 - 1][y1] = 2;
                        } else
                            ofield[x1 - 1][y1] = 1;
                    }
                    if ((field[x1 - 1][y1 + 1] >= 0) && (ofield[x1 - 1][y1 + 1] == 0)) {
                        if (field[x1 - 1][y1 + 1] == 0) {
                            q.add(((x1 - 1) * sizey + y1 + 1));
                            ofield[x1 - 1][y1 + 1] = 2;
                        } else
                            ofield[x1 - 1][y1 + 1] = 1;
                    }
                    if ((field[x1][y1 + 1] >= 0) && (ofield[x1][y1 + 1] == 0)) {
                        if (field[x1][y1 + 1] == 0) {
                            q.add((x1 * sizey + y1 + 1));
                            ofield[x1][y1 + 1] = 2;
                        } else
                            ofield[x1][y1 + 1] = 1;
                    }
                    if ((field[x1 + 1][y1 + 1] >= 0) && (ofield[x1 + 1][y1 + 1] == 0)) {
                        if (field[x1 + 1][y1 + 1] == 0) {
                            q.add(((x1 + 1) * sizey + y1 + 1));
                            ofield[x1 + 1][y1 + 1] = 2;
                        } else
                            ofield[x1 + 1][y1 + 1] = 1;
                    }
                    if ((field[x1 + 1][y1] >= 0) && (ofield[x1 + 1][y1] == 0)) {
                        if (field[x1 + 1][y1] == 0) {
                            q.add(((x1 + 1) * sizey + y1));
                            ofield[x1 + 1][y1] = 2;
                        } else
                            ofield[x1 + 1][y1] = 1;
                    }
                    if ((field[x1 + 1][y1 - 1] >= 0) && (ofield[x1 + 1][y1 - 1] == 0)) {
                        if (field[x1 + 1][y1 - 1] == 0) {
                            q.add(((x1 + 1) * sizey + y1 - 1));
                            ofield[x1 + 1][y1 - 1] = 2;
                        } else
                            ofield[x1 + 1][y1 - 1] = 1;
                    }
                    if ((field[x1][y1 - 1] >= 0) && (ofield[x1][y1 - 1] == 0)) {
                        if (field[x1][y1 - 1] == 0) {
                            q.add((x1 * sizey + y1 - 1));
                            ofield[x1][y1 - 1] = 2;
                        } else
                            ofield[x1][y1 - 1] = 1;
                    }
                    if ((field[x1 - 1][y1 - 1] >= 0) && (ofield[x1 - 1][y1 - 1] == 0)) {
                        if (field[x1 - 1][y1 - 1] == 0) {
                            q.add(((x1 - 1) * sizey + y1 - 1));
                            ofield[x1 - 1][y1 - 1] = 2;
                        } else
                            ofield[x1 - 1][y1 - 1] = 1;
                    }
                    q.remove(c);
                    ofield[x1][y1] = 1;
                }
            }
        }
    }
}