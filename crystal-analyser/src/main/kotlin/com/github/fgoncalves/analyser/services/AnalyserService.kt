package com.github.fgoncalves.analyser.services

import com.github.fgoncalves.analyser.*
import com.github.fgoncalves.analyser.commons.toUrn
import com.github.fgoncalves.analyser.domain.models.AnalyserTaskStatus
import com.github.fgoncalves.analyser.domain.usecases.CreateAnalyserTaskUseCase
import com.github.fgoncalves.analyser.domain.usecases.GetAnalyserTaskStatusUseCase
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Singleton
internal class AnalyserService(
  private val createAnalyserTaskUseCase: CreateAnalyserTaskUseCase,
  private val getAnalyserTaskStatusUseCase: GetAnalyserTaskStatusUseCase,
) : CrystalAnalyserServiceGrpc.CrystalAnalyserServiceImplBase() {
  override fun analyse(request: ReportAnalyseRequest, responseObserver: StreamObserver<ReportAnalyseReply>) {
    runBlocking {
      coroutineScope {
        createAnalyserTaskUseCase.create(request.name, request.data.toByteArray()).collect {
          responseObserver.onNext(
            ReportAnalyseReply.newBuilder()
              .setUrn(it.urn.raw)
              .setStatus(it.status.toProtoStatus())
              .setErrorMsg(
                if (it.status is AnalyserTaskStatus.Error)
                  it.status.errorMsg
                else
                  null
              )
              .build()
          )
          responseObserver.onCompleted()
        }
      }
    }
  }

  override fun status(request: StatusRequest, responseObserver: StreamObserver<ReportAnalyseReply>) {
    runBlocking {
      coroutineScope {
        getAnalyserTaskStatusUseCase.get(request.urn.toUrn())
          .catch { responseObserver.onError(it) }
          .collect {
            responseObserver.onNext(
              ReportAnalyseReply.newBuilder()
                .setUrn(request.urn)
                .setStatus(it.toProtoStatus())
                .setErrorMsg(
                  if (it is AnalyserTaskStatus.Error)
                    it.errorMsg
                  else
                    null
                )
                .build()
            )
            responseObserver.onCompleted()
          }
      }
    }
  }
}

private fun AnalyserTaskStatus.toProtoStatus() =
  when (this) {
    is AnalyserTaskStatus.Pending -> AnalyserRequestStatus.SUBMITTED

    is AnalyserTaskStatus.Running -> AnalyserRequestStatus.PROCESSING

    is AnalyserTaskStatus.Done -> AnalyserRequestStatus.DONE

    is AnalyserTaskStatus.Error -> AnalyserRequestStatus.ERROR
  }
