package com.github.dach83.server

import com.github.dach83.hello_grpc.GreeterGrpc
import com.github.dach83.hello_grpc.HelloReply
import com.github.dach83.hello_grpc.HelloRequest
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver

class HelloWorldServer {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val server = HelloWorldServer()
            server.start()
            server.blockUntilShutdown()
        }
    }

    private var server: Server? = null

    private fun start() {
        println("Server started")

        val port = 50051
        server = ServerBuilder
            .forPort(port)
            .addService(GreeterImpl())
            .build()
            .start()

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                this@HelloWorldServer.stop()
                println("Server shut down")
            }
        })
    }

    private fun stop() {
        server?.shutdown()
    }

    private fun blockUntilShutdown() {
        server?.awaitTermination()
    }

    private class GreeterImpl : GreeterGrpc.GreeterImplBase() {

        override fun sayHello(
            request: HelloRequest,
            responseObserver: StreamObserver<HelloReply>
        ) {
            val reply = HelloReply.newBuilder()
                .setMessage("Hello, ${request.name}")
                .build()
            responseObserver.onNext(reply)
            responseObserver.onCompleted()
        }
    }

}