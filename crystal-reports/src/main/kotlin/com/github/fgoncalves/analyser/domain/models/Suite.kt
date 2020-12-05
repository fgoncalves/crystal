package com.github.fgoncalves.analyser.domain.models

data class Suite(
  val urn: String,
  val name: String,
  val testCases: List<TestCase> = emptyList()
)