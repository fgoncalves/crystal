package com.github.fgoncalves.analyser

import io.micronaut.runtime.Micronaut.build
import io.grpc.protobuf.services.ProtoReflectionService

fun main(args: Array<String>) {
  build()
    .args(*args)
    .packages("com.github.fgoncalves.analyser")
    .singletons(ProtoReflectionService.newInstance())
    .start()
}

