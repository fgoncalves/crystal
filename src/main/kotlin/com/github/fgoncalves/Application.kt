package com.github.fgoncalves

import io.grpc.protobuf.services.ProtoReflectionService
import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
  build()
    .args(*args)
    .packages("com.github.fgoncalves")
    .singletons(ProtoReflectionService.newInstance())
    .start()
}

