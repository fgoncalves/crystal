package com.github.fgoncalves.analyser.commons

fun forSuite(id: Long?) =
  if (id == null)
    ""
  else
    "crystal-analyser:suite:$id"

fun forTestCase(id: Long?) =
  if (id == null)
    ""
  else
    "crystal-analyser:testcase:$id"

fun forRun(id: Long?) =
  if (id == null)
    ""
  else
    "crystal-analyser:run:$id"

fun forTask(id: String) = Urn(
  "crystal-analyser",
  "tasks",
  id,
)

internal fun String.toUrn(): Urn {
  val specs = split(':')

  if (specs.size != 3)
    throw IllegalArgumentException("Cannot find the 3 parts for urn: $this")

  return Urn(
    specs[0],
    specs[1],
    specs[2],
  )
}