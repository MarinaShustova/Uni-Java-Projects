import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Forwarder {
    InetAddress addr;
    int port;
    int dest_port;
    private Map<SocketChannel, SocketChannel> connections = new HashMap<>();
    private Map<SocketChannel, SocketChannel> servers = new HashMap<>();
    Forwarder(Integer lport, String host, Integer rport) throws UnknownHostException {
        addr = InetAddress.getByName(host);
        dest_port = rport;
        port = lport;
    }
    public void forward() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);
        ssChannel.socket().bind(new InetSocketAddress("localhost", port));
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        SelectionKey key = null;
        while (true) {
            //if (selector.select() <= 0)
             //   continue;
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        SocketChannel sc = ssChannel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);

                        SocketChannel connection = SocketChannel.open();
                        connection.configureBlocking(false);
                        connection.connect(new InetSocketAddress(addr, dest_port));
                        connection.register(selector, SelectionKey.OP_READ | SelectionKey.OP_CONNECT);

                        System.out.println("Connection Accepted: "
                                + sc.getLocalAddress() + "\n" + "My Connection is: " + connection.getRemoteAddress());
                        connections.put(sc, connection);
                        servers.put(connection, sc);

                    }
                    if (key.isConnectable()) ((SocketChannel) key.channel()).finishConnect();
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        SocketChannel dst = (connections.containsKey(sc)) ? connections.get(sc) : servers.get(sc);
                        ByteBuffer bb = ByteBuffer.allocate(1024 * 8);
                        if(dst.isConnected()) {
                            int res = sc.read(bb);
                            if (res<=0) {
                                if (!connections.containsKey(sc)) {
                                    connections.remove(dst);
                                    servers.remove(sc);
                                } else {
                                    connections.remove(sc);
                                    servers.remove(dst);
                                }
                                sc.close();
                                dst.close();
                                System.out.println("Connection closed...");
                                System.out.println(
                                        "Server will keep running. " +
                                                "Try running another client to " +
                                                "re-establish connection");
                            } else {
                                dst.write(ByteBuffer.wrap(bb.array(), 0, res));
                            }
                            bb.clear();
                        }
                    }
                }
            }
        }
    }
}
