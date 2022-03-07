import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Server extends Thread {

    private static int port;
    private ServerSocket socket_s;

    public Server(int p) {
        try {
            port = p;
            socket_s = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                Socket socket = socket_s.accept();
                new Connection(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Connection extends Thread {

        private DataInputStream input;
        private DataOutputStream output;
        private Socket socket;
        private String file_name;
        private long file_size;
        private boolean receiving = false;
        private long recieved, total;
        private int update_time = 3, kb = 1024, sec = 1;
        private byte[] recv_buf = new byte[64 * kb], curr_byte_buff = {};
        private byte curr_byte;
        private ByteBuffer long_buff = ByteBuffer.allocate(Long.BYTES);

        public Connection(Socket socket) {
            try {
                this.socket = socket;
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while (true) {
                    curr_byte = input.readByte();
                    if (curr_byte == '*')
                        break;
                    outputStream.write(curr_byte_buff);
                    outputStream.write(curr_byte);
                    curr_byte_buff = outputStream.toByteArray();
                    outputStream.reset();
                }
                file_name = new String(curr_byte_buff, 0, curr_byte_buff.length);
    //            file_size = input.readLong();
                for (int i = 0; i< Long.BYTES; ++i){
                    long_buff.put(input.readByte());
//                    outputStream.write(curr_byte_buff);
//                    outputStream.write(curr_byte);
//                    curr_byte_buff = outputStream.toByteArray();
//                    outputStream.reset();
                }
                long_buff.position(0);
                file_size = long_buff.getLong();
                System.out.format("Receiving \"%s\" (size - %.2f Mb) from %s\n", file_name, 1.0 * file_size / 1024 / 1024, socket.getInetAddress().toString());
                FileOutputStream outputFile = new FileOutputStream("upload/" + file_name);
                int count;
                total = 0;
                receiving = true;

                new Thread(() -> {
                    double speed_m, speed_a;
                    recieved = 0;
                    while (receiving) {
                        try {
                            Thread.sleep(update_time * 1000);
                            ++sec;
                            speed_m = 1.0 * (total - recieved) / update_time / kb / kb;
                            speed_a = 1.0 * total / (1.0 * sec * update_time) / kb / kb;
                            recieved = total;
                            System.out.format("From %s file \"%s\": moment speed - %.2f MB/s, " + "average speed - %.2f MB/s\n", socket.getLocalSocketAddress().toString(), file_name, speed_m, speed_a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.format("File \"%s\": Received %.2f%% " + "Time spent: %ds\n", file_name, 100.0 * total / file_size, sec * update_time);
                }).start();

                while ((count = input.read(recv_buf)) != -1) {
                    total += count;
                    outputFile.write(recv_buf, 0, count);
                    if (total == file_size) {
                        receiving = false;
                        break;
                    }
                }
                if (!receiving) {
                    outputFile.flush();
                    outputFile.close();
                    output.writeUTF("Successfully received");
                } else {
                    outputFile.close();
                    File f = new File("upload/" + file_name);
                    f.delete();
                    System.out.format("Receive error with file \"%s\" from %s\n",
                            file_name, socket.getInetAddress().toString());
                    receiving = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
