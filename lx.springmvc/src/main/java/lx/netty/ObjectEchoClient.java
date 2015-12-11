/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package lx.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Modification of {@link EchoClient} which utilizes Java object serialization.
 */
public final class ObjectEchoClient {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    static List<ChannelFuture> actives = new ArrayList<ChannelFuture>();
    static Map<Integer, ChannelFuture> brokens = new ConcurrentHashMap<Integer, ChannelFuture>();

    static MessageVo msg = new MessageVo();
    static {
        msg.setName("我是好人");
    }

    public static void main(String[] args) throws InterruptedException {
        List<SocketAddress> list = new ArrayList<SocketAddress>();
        list.add(new InetSocketAddress(HOST, PORT));
        for (int i = 0; i < list.size(); i++) {
            SocketAddress one = list.get(i);
            Bootstrap b = new Bootstrap();
            EventLoopGroup group = new NioEventLoopGroup();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new ObjectEncoder(), new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEchoClientHandler());
                        }
                    });
            AttributeKey<SocketAddress> key = AttributeKey.newInstance("socket");
            b.attr(key, one);
            ChannelFuture future = b.connect(one).channel().closeFuture().addListener(new CloseFutureListener(b));
            Thread.sleep(3000);
            if (future.channel().isRegistered() && future.channel().isActive()) {
                actives.add(future);
            } else {
                brokens.put(i, future);
            }
        }
        write();
    }

    public static void write() {
        while (true) {
            ChannelFuture write = actives.get(0).channel().writeAndFlush(msg);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    static class CloseFutureListener implements ChannelFutureListener {

        private Bootstrap b;

        public CloseFutureListener(Bootstrap b) {
            this.b = b;
        }

        @Override
        public void operationComplete(ChannelFuture future) {
            if (future.isDone()) {
                if (future.isSuccess()) {
                    System.out.println("我进来了 ");
                    try {
                        b.connect(HOST, PORT).channel().closeFuture().addListener(new CloseFutureListener(b)).sync();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (future.cause() != null) {
                    // System.out.println(future.cause().getMessage());
                }
            }
        }
    }

    public static void reconnect(Bootstrap b, ChannelFuture connect) {
        while (true) {
            // group.shutdownGracefully();
            // ChannelFuture connect = b.connect(HOST, PORT);
            if (connect.isSuccess()) {
                System.out.println("连上了??");
                break;
            } else {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
