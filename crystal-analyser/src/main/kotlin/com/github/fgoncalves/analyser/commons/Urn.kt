package com.github.fgoncalves.analyser.commons

data class Urn(
  val service: String,
  val type: String,
  val id: String,
) {
  val raw = "$service:$type:$id"
}