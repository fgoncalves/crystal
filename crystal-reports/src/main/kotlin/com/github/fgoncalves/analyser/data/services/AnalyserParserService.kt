package com.github.fgoncalves.analyser.data.services

import com.github.fgoncalves.analyser.data.services.parser.Result
import com.github.fgoncalves.analyser.data.services.parser.Suite
import com.github.fgoncalves.analyser.data.services.parser.TestCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.net.URI
import javax.inject.Singleton
import javax.xml.parsers.SAXParser

/**
 * Analyser service
 */
interface AnalyserParserService {
  /**
   * Analyse the given xml
   *
   * @param uri Uri to a valid xml
   */
  fun analyse(uri: URI): Flow<List<Suite>>
}

@Singleton
class DefaultAnalyserParserService(
  private val saxParser: SAXParser,
  private val dispatcher: CoroutineDispatcher,
) : AnalyserParserService {
  @ExperimentalCoroutinesApi
  override fun analyse(uri: URI): Flow<List<Suite>> = flow {
    val handler = Handler()
    saxParser.parse(uri.toString(), handler)

    emit(handler.suites)
  }.flowOn(dispatcher)


  private inner class Handler : DefaultHandler() {
    private var failureBuilder: Result.Failed.Builder? = null
    private var testCaseBuilder: TestCase.Builder? = null
    private var suiteBuilder: Suite.Builder? = null
    private val textBuffer = StringBuffer()

    val suites = mutableListOf<Suite>()

    override fun endElement(uri: String?, localName: String?, qName: String?) {
      when (qName) {
        "failure" -> handleFailureEnd()

        "webLink" -> handleWebLinkEnd()

        "testcase" -> handleTestCaseEnd()

        "testsuite" -> handleSuiteEnd()
      }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
      textBuffer.append(
        ch?.slice(start until (start + length))?.joinToString(separator = "") ?: ""
      )
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
      when (qName) {
        "failure" -> {
          textBuffer.clear()
          failureBuilder = Result.Failed.builder()
        }

        "testcase" -> testCaseBuilder = TestCase.builder()
          .name(attributes?.getValue("name") ?: "")
          .className(attributes?.getValue("classname") ?: "")
          .flaky(attributes?.getValue("flaky").toBoolean())

        "testsuite" -> suiteBuilder = Suite.builder()
          .name(attributes?.getValue("name") ?: "")

        "webLink" -> textBuffer.clear()
      }
    }

    private fun handleSuiteEnd() {
      suites.add(suiteBuilder!!.build())
      suiteBuilder = null
    }

    private fun handleTestCaseEnd() {
      suiteBuilder = suiteBuilder!!.withTestCase(
        testCaseBuilder!!.build()
      )
      testCaseBuilder = null
    }

    private fun handleWebLinkEnd() {
      testCaseBuilder = testCaseBuilder!!.weblink(textBuffer.toString())
      textBuffer.clear()
    }

    private fun handleFailureEnd() {
      testCaseBuilder = testCaseBuilder!!.result(
        failureBuilder!!.message(textBuffer.toString()).build()
      )

      failureBuilder = null
      textBuffer.clear()
    }

    private fun StringBuffer.clear() =
      delete(0, length)
  }
}