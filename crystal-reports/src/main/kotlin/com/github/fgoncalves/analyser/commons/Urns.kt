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
