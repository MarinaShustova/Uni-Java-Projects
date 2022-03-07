public class Drawer
{
   public void Draw(Field f)
    {
        for(int i = 1; i<11; ++i) {
            for (int j = 1; j < 11; ++j) {
                System.out.print(f.field[i][j]);
            }
            System.out.println("\n");
        }
    }
}
