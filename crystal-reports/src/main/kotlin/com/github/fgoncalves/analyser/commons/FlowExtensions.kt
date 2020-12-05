package com.github.fgoncalves.analyser.commons

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

typealias CompletableFlow = Flow<Unit>

suspend fun FlowCollector<Unit>.complete() = emit(Unit)