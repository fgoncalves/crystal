package com.github.fgoncalves.analyser.domain.models

data class TestCase(
  val urn: String,
  val name: String,
  val className: String,
  val flaky: Boolean,
  val weblink: String,
  val result: Result,
)