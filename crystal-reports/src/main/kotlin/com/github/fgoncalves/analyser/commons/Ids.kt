package com.github.fgoncalves.analyser.commons

fun fromUrn(urn: String) =
  urn.split(":")
    .takeIf { it.size == 3 }
    ?.let { it[2].toLong() }