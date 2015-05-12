package lx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketServerChannel {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8999));
        while (true) {
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                Thread.sleep(5000);
                System.out.println("没谁了");
            } else {
                System.out.println("来了S");
            }
        }

    }

}
