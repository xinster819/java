package lx.netty;

import java.net.InetAddress;
import java.net.UnknownHostException;

import redis.clients.jedis.HostAndPort;

// lx 2015年8月3日: 从jedis copy过来的
public class Host {
    public static final String LOCALHOST_STR = "localhost";

    private String host;
    private int port;

    public Host(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HostAndPort) {
            HostAndPort hp = (HostAndPort) obj;

            String thisHost = convertHost(host);
            String hpHost = convertHost(hp.getHost());
            return port == hp.getPort() && thisHost.equals(hpHost);

        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31 * convertHost(host).hashCode() + port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }

    private String convertHost(String host) {
        if (host.equals("127.0.0.1"))
            return LOCALHOST_STR;
        else if (host.equals("::1"))
            return LOCALHOST_STR;

        return host;
    }

    public static void main(String [] args) {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            System.out.println(addr.getHostAddress());//获得本机IP
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
