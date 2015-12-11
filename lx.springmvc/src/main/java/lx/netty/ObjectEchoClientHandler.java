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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * Creates a client-side handler.
     */
    public ObjectEchoClientHandler() {
        MessageVo s = new MessageVo();
        s.setName("client");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        MessageVo s = new MessageVo();
//        s.setName("client");
//        ChannelFuture future = ctx.writeAndFlush(s);
//        future.addListener(new ChannelFutureListener(){
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                if(future.isDone()) {
//                    System.out.println("Done");
//                }else {
//                    System.out.println("not Done");
//                }
//            }
//        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof MessageVo) {
            MessageVo o = (MessageVo) msg;
            System.out.println("from : " + o.getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
        ctx.close();
    }
}
