package lx.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class TcpServer implements InitializingBean {

    static Logger LOGGER = LoggerFactory.getLogger(TcpServer.class);

    private ServerSocket serverSocket;

    @Override
    public void afterPropertiesSet() throws Exception {
        serverSocket = new ServerSocket(26789);
        LOGGER.info("start server....");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String clientSentence = inFromClient.readLine();
                        System.out.println("Received: " + clientSentence);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
