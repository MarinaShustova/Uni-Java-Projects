import java.io.*;
import java.net.*;

public class Client {

    private String address = "localhost";
    private Socket socket;
    private String file_name = "";
    private int kb = 1024;
    private byte[] name, send_buf = new byte[64*kb];

    public Client(String path) {
        try {
            socket = new Socket(address, 4446);
            file_name = path;
            new InputFromServer();
            new OutputToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket(){
        return socket;
    }
    private class InputFromServer extends Thread {

        private DataInputStream input;

        public InputFromServer() {
            try {
                input = new DataInputStream(socket.getInputStream());
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    String data = input.readUTF();
                    if (data != null) {
                        System.out.format("%s:%d: Message from server: %s\n",
                                socket.getLocalAddress().getHostAddress(), socket.getLocalPort(), data);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class OutputToServer extends Thread {

        private DataOutputStream output;

        public OutputToServer() {
            try {
                output = new DataOutputStream(socket.getOutputStream());
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                File file = new File(file_name);
                if (!file.exists()) {
                    output.writeUTF("Error");
                    System.out.println("File " + file_name + " does not exist");
                    return;
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                outputStream.write( file.getName().getBytes() );
                outputStream.write( '*' );
                name = outputStream.toByteArray();
                output.write(name);
                output.writeLong(file.length());
                FileInputStream inputFile = new FileInputStream(file);
                int count;
                while ((count = inputFile.read(send_buf)) != -1) {
                    output.write(send_buf, 0, count);
                }
                output.flush();
                inputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}