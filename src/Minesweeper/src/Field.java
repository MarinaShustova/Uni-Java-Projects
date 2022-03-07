import java.util.Random;

public class Field
{
    public int[][] field;
    Field()
    {
        Random rnd = new Random(System.currentTimeMillis());
        field = new int[12][12];
        int bombs = 10, x, y, isbomb;
        while (bombs>0)
        {
            x = rnd.nextInt(10) + 1;
            y = rnd.nextInt(10) + 1;
            if (field[x][y] != -1)
            {
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
    }
}