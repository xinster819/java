package lx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 8999));
        while(! sc.finishConnect() ){
            System.out.println("没谁了");   
        }
        System.out.println("关了");
        sc.close();
    }
}
