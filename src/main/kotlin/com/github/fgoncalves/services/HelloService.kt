package com.github.fgoncalves.services

import com.github.fgoncalves.CrystalReply
import com.github.fgoncalves.CrystalRequest
import com.github.fgoncalves.CrystalServiceGrpc
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
class HelloService : CrystalServiceGrpc.CrystalServiceImplBase() {
  override fun send(request: CrystalRequest?, responseObserver: StreamObserver<CrystalReply>?) {
    val greeting = if (request?.name.isNullOrBlank())
      "World"
    else
      request?.name

    responseObserver?.run {
      onNext(
        CrystalReply.newBuilder()
          .setMessage("Hello $greeting")
          .build()
      )

      onCompleted()
    }
  }
}