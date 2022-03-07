public class Main {
    public static void main(String[] args) {
        if (args[0].equals("Server")) {
            Server s = new Server(Integer.parseInt(args[1]));
            s.start();
        }
        else if (args[0].equals("Client")){
            Client c = new Client(args[1]);
        }
    }


}
