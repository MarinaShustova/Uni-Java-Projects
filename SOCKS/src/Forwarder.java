import org.xbill.DNS.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Forwarder {
    int size = 1024;
    int port;
    ByteBuffer buf;
    private int ACCEPTED = 0;
    private int CONNECTED = 1;
    private int CONNECTION = 2;
    private DatagramChannel udpsocket;
    private Map<SocketChannel, SocketChannel> connections = new HashMap<>();
    private Map<Integer, MyConnection> dnslist = new HashMap<>();
    private Map<SelectionKey, Meta> information = new HashMap<>();

    Forwarder(Integer lport) throws SocketException {
        port = lport;
        buf = ByteBuffer.allocate(size);
    }

    public void forward() throws IOException {
        MessageParser messageParser = new MessageParser();

        Selector selector = Selector.open();

        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);
        ssChannel.socket().bind(new InetSocketAddress("localhost", port));
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        String dnsServers[] = ResolverConfig.getCurrentConfig().servers();
        udpsocket = DatagramChannel.open();
        udpsocket.configureBlocking(false);
        udpsocket.connect(new InetSocketAddress(dnsServers[0],53));
        udpsocket.register(selector, SelectionKey.OP_READ);

        SelectionKey key = null;
        ByteBuffer messagebuffer = ByteBuffer.allocate(size);
        while (true) {
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
                        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                    if (key.isConnectable()) {
                        SocketChannel channel = ((SocketChannel) key.channel());
                        channel.finishConnect();
                    }
                    if (key.isReadable()) {
                        Meta info;
                        if (information.containsKey(key))
                            info = information.get(key);
                        else {
                            info = new Meta(size);
                            info.state = ACCEPTED;
                            information.put(key, info);
                        }
                        boolean is_dns = !(key.channel() instanceof SocketChannel);
                        if (!is_dns) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            int res = -1;
                                boolean correct;
                                if ((info.state == ACCEPTED)||(info.state == CONNECTED)) {
                                    res = sc.read(messagebuffer);
                                    if (res < 1) {
                                        information.remove(key);
                                        System.out.println("res < 1");
                                        close(key);
                                    }
                                    correct = messageParser.checkIfCorrect(messagebuffer, info.state);

                                    messagebuffer.rewind();
                                    if (correct) {
                                        if (info.state == ACCEPTED) {
                                            ByteBuffer outBuffer = messageParser.makeAcceptionAnswer();
                                            sc.write(ByteBuffer.wrap(outBuffer.array(), 0, 2));
                                            info.state = CONNECTED;
                                        } else if (info.state == CONNECTED) {
                                            SocketChannel connection = SocketChannel.open();
                                            connection.configureBlocking(false);
                                            connections.put(sc, connection);
                                            connections.put(connection, sc);
                                            ByteBuffer bb = messageParser.makeConnectionAnswer(port);
                                            sc.write(ByteBuffer.wrap(bb.array(), 0, 10));
                                            try {
                                                InetAddress address = messageParser.getAddress(messagebuffer);
                                                messagebuffer.rewind();
                                                int connection_port = messageParser.getPort(messagebuffer, res);
                                                connection.connect(new InetSocketAddress(address, connection_port));
                                                connection.register(selector, SelectionKey.OP_READ | SelectionKey.OP_CONNECT);
                                                info.state = CONNECTION;
                                            } catch (DomainException e) {
                                                Name name = org.xbill.DNS.Name.fromString(messageParser.getDomain(messagebuffer), Name.root);
                                                Record rec = Record.newRecord(name, Type.A, DClass.IN);
                                                Message dns_message = Message.newQuery(rec);
                                                udpsocket.write(ByteBuffer.wrap(dns_message.toWire()));
                                                int port = messageParser.getPort(messagebuffer, res);
                                                dnslist.put(dns_message.getHeader().getID(), new MyConnection(port, sc));
                                            }
                                        }
                                        messagebuffer.clear();
                                    }
                                }
                                else {
                                    System.out.println("Connection!");
                                    SocketChannel connection = connections.get(sc);

                                    System.out.println("CONNECTION (SOURCE) : " +sc.toString() + sc.isConnected());
                                    System.out.println("CONNECTION (DEST) : " +connection.toString() + connection.isConnected());
                                    if (connection.isConnected()) {
                                        sc.read(messagebuffer);
                                        connection.write(ByteBuffer.wrap(messagebuffer.array(), 0, messagebuffer.array().length));
                                    }
                                    messagebuffer.clear();
                                }
                            }
                        else {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int len = udpsocket.read(buffer);
                            if (len <= 0) continue;
                            Message msg = new Message(buffer.array());
                            Record[] recs = msg.getSectionArray(1);
                            for (Record rec : recs) {
                                if (rec instanceof ARecord) {
                                    ARecord arec = (ARecord)rec;
                                    InetAddress adr = arec.getAddress();
                                    int id = msg.getHeader().getID();
                                    MyConnection myConnection = dnslist.get(id);
                                    int port = myConnection.port;
                                    SocketChannel connection = connections.get(dnslist.get(id).socketChannel);
                                    connection.connect(new InetSocketAddress(adr, port));
                                    info.state = CONNECTION;
                                    dnslist.remove(id);
                                }
                            }
                        }
                    }
                    if (key.isWritable()){

                    }
                }
            }
        }
    }

    private void close(SelectionKey key) throws IOException {
        System.out.println("Something was closed");
        connections.remove(connections.get(key.channel()));
        connections.remove(key.channel());
        key.channel().close();
    }
}
