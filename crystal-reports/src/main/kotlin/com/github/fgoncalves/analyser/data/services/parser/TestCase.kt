package com.github.fgoncalves.analyser.data.services.parser

data class TestCase(
  val name: String,
  val className: String,
  val flaky: Boolean,
  val weblink: String,
  val result: Result,
) {
  companion object {
    fun builder() = Builder()
  }

  class Builder {
    private var name: String = ""
    private var className: String = ""
    private var flaky: Boolean = false
    private var weblink: String = ""
    private var result: Result = Result.Pass

    fun name(name: String) = apply {
      this.name = name
    }

    fun className(className: String) = apply {
      this.className = className
    }

    fun flaky(flaky: Boolean) = apply {
      this.flaky = flaky
    }

    fun weblink(weblink: String) = apply {
      this.weblink = weblink
    }

    fun result(result: Result) = apply {
      this.result = result
    }

    fun build() =
      TestCase(
        name,
        className,
        flaky,
        weblink,
        result,
      )
  }
}