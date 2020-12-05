package com.github.fgoncalves.analyser.data.services.parser

data class Suite(
  val name: String,
  val testCases: List<TestCase> = emptyList(),
) {
  companion object {
    fun builder() = Builder()
  }

  class Builder {
    private var name: String = ""
    private val testCases: MutableList<TestCase> = mutableListOf()

    fun name(name: String) = apply {
      this.name = name
    }

    fun withTestCase(testCase: TestCase) = apply {
      testCases.add(testCase)
    }

    fun build() =
      Suite(
        name,
        testCases,
      )
  }
}