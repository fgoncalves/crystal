package com.github.fgoncalves.analyser.domain.controllers

import com.github.fgoncalves.analyser.CrystalAnalyserReply
import com.github.fgoncalves.analyser.CrystalAnalyserRequest
import com.github.fgoncalves.analyser.CrystalAnalyserServiceGrpc
import com.github.fgoncalves.analyser.domain.models.Result
import com.github.fgoncalves.analyser.domain.models.Suite
import com.github.fgoncalves.analyser.domain.models.TestCase
import com.github.fgoncalves.analyser.domain.repositories.RunsRepository
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

class AnalyserController(
  private val runsRepository: RunsRepository,
) : CrystalAnalyserServiceGrpc.CrystalAnalyserServiceImplBase() {
  override fun send(request: CrystalAnalyserRequest?, responseObserver: StreamObserver<CrystalAnalyserReply>?) {
    runBlocking {
      runsRepository.createOrUpdate(
        Suite(
          "",
          "testSuite#1",
          listOf(
            TestCase(
              "",
              "testCase1",
              "className1",
              false,
              "https://www.google.com",
              Result.Pass,
            ),
            TestCase(
              "",
              "testCase2",
              "className2",
              true,
              "https://www.google.com",
              Result.Failed("This is the error message"),
            ),
          )
        ),
        Suite(
          "",
          "testSuite#2",
          listOf(
            TestCase(
              "",
              "testCase3",
              "className3",
              false,
              "https://www.google.com",
              Result.Pass,
            ),
            TestCase(
              "",
              "testCase3",
              "className3",
              true,
              "https://www.google.com",
              Result.Failed("This is the error message"),
            ),
          )
        ),
      ).collect()

      responseObserver?.run {
        onNext(
          CrystalAnalyserReply.newBuilder()
            .setMessage("Worked")
            .build()
        )

        onCompleted()
      }
    }
  }

}