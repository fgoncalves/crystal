package com.github.fgoncalves.analyser.domain.models

data class Run(
  val urn: String,
  val suites: List<Suite> = emptyList(),
)