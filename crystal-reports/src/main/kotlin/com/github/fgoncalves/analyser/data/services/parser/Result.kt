package com.github.fgoncalves.analyser.data.services.parser

sealed class Result {
  object Pass : Result()

  data class Failed(
    val message: String,
  ) : Result() {
    companion object {
      fun builder() = Builder()
    }

    class Builder {
      private var message: String = ""

      fun message(msg: String) = apply {
        this.message = msg
      }

      fun build() = Failed(message)
    }
  }
}