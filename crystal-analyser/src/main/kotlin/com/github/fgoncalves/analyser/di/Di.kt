package com.github.fgoncalves.analyser.di

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

@Factory
internal class XmlParserFactory {
  @Bean
  @Singleton
  fun xmlParser(): SAXParser = SAXParserFactory.newInstance().newSAXParser()
}

internal interface DispatchersContainer {
  fun io(): CoroutineDispatcher
}

@Singleton
internal class DefaultDispatchersContainer : DispatchersContainer {
  override fun io() = Dispatchers.IO
}