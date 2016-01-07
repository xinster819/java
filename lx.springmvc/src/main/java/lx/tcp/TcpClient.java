package lx.tcp;

import java.io.DataOutputStream;
import java.net.Socket;

public class TcpClient {
    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost", 26789);
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeBytes("hellow");
        out.flush();
        clientSocket.close();
    }
}
