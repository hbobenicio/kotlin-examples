package br.com.hbobenicio.kotlinexamples.hellonetty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

/**
 * https://github.com/netty/netty/blob/4.1/example/src/main/java/io/netty/example/discard/DiscardServer.java
 */
fun main(args: Array<String>) {
    val mainLoopGroup: EventLoopGroup = NioEventLoopGroup(1)
    val workerLoopGroup: EventLoopGroup = NioEventLoopGroup(4)
    try {
        val serverBootstrap = ServerBootstrap().apply {
            group(mainLoopGroup, workerLoopGroup)
            channel(NioServerSocketChannel::class.java)
            handler(LoggingHandler(LogLevel.INFO))
            childHandler(DummyChannelInitializer())
        }

        val channelFuture: ChannelFuture = serverBootstrap.bind("localhost", 8080)
            .sync()

        channelFuture.channel().closeFuture().sync()
    } finally {
        workerLoopGroup.shutdownGracefully()
        mainLoopGroup.shutdownGracefully()
    }
}

class DummyChannelInitializer: ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel?) {
        ch!!.pipeline().addLast(DummyHandler())
    }
}

@ChannelHandler.Sharable
class DummyHandler: ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        ctx!!.write(msg)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        ctx!!.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause!!.printStackTrace()
        ctx?.close()
    }
}
