package com.github.fgoncalves.analyser.data.services

import com.github.fgoncalves.analyser.data.services.DefaultAnalyserParserService
import com.github.fgoncalves.analyser.data.services.parser.Result
import com.github.fgoncalves.analyser.data.services.parser.Suite
import com.github.fgoncalves.analyser.data.services.parser.TestCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import javax.xml.parsers.SAXParserFactory

internal class DefaultAnalyserParserServiceTest {
  @ExperimentalCoroutinesApi
  private val testDispatcher = TestCoroutineDispatcher()

  @ExperimentalCoroutinesApi
  private val service = DefaultAnalyserParserService(
    SAXParserFactory.newInstance().newSAXParser(),
    testDispatcher,
  )

  @ExperimentalCoroutinesApi
  @Test
  fun `should collect all test suites`() = testDispatcher.runBlockingTest {
    val uri = javaClass.classLoader.getResource("example.xml")!!.toURI()

    service.analyse(uri).collect {
      assertThat(it.size).isEqualTo(3)

      assertThat(it[0]).isEqualTo(
        Suite(
          name = "Nexus5X-23-en-portrait",
          testCases = listOf(
            TestCase(
              name = "testSkipIsNotAllowedOnAd",
              className = "com.soundcloud.android.tests.player.ads.AudioAdTest",
              weblink = "https://console.firebase.google.com/project/soundcloud.com:soundcloud/testlab/histories/bh.5f498d27577cf454/matrices/5441846332547453586/executions/bs.1bccd30f01e9d4dc/testcases/1",
              flaky = false,
              result = Result.Pass,
            ),
            TestCase(
              name = "testClickOnHtmlCompanionsOpenClickThroughUrlInBrowser",
              className = "com.soundcloud.android.tests.player.ads.pods.AdPodWithHtmlCompanionsTest",
              weblink = "https://console.firebase.google.com/project/soundcloud.com:soundcloud/testlab/histories/bh.5f498d27577cf454/matrices/5441846332547453586/executions/bs.1bccd30f01e9d4dc/testcases/2",
              flaky = false,
              result = Result.Pass,
            ),
          ),
        ),
      )

      assertThat(it[1]).isEqualTo(
        Suite(
          name = "Nexus5X-23-en-portrait",
          testCases = listOf(
            TestCase(
              name = "showsHtmlLeaveBehindOnNextTrackAsPriority",
              className = "com.soundcloud.android.tests.player.ads.HtmlLeaveBehindForVideoAdTest",
              weblink = "https://console.firebase.google.com/project/soundcloud.com:soundcloud/testlab/histories/bh.5f498d27577cf454/matrices/5441846332547453586/executions/bs.2c0cb85547c21dd/testcases/1",
              flaky = false,
              result = Result.Pass,
            ),
          ),
        ),
      )

      assertThat(it[2]).isEqualTo(
        Suite(
          name = "Nexus5X-23-en-portrait",
          testCases = listOf(
            TestCase(
              name = "testQuartileEvents",
              className = "com.soundcloud.android.tests.player.ads.VideoAdsTest",
              weblink = "https://console.firebase.google.com/project/soundcloud.com:soundcloud/testlab/histories/bh.5f498d27577cf454/matrices/5441846332547453586/executions/bs.3aa1f398a6444150/testcases/1",
              flaky = false,
              result = Result.Failed("""FatalException:io.reactivex.rxjava3.exceptions.d:Invaliddefinitionofthetargettypedetected.Targettype:co.dCausedbybp.c${'$'}b:Invaliddefinitionofthetargettypedetected.Targettype:co.datrxdogtag2.-${'$'}${'$'}Lambda${'$'}RxDogTag${'$'}YP26HIb1nhHqbV4QlRgnyBuwHqU.apply(-.java:6)at[[↑↑Inferredsubscribepoint↑↑]].([[↑↑Inferredsubscribepoint↑↑]].java)at[[↓↓Originaltrace↓↓]].([[↓↓Originaltrace↓↓]].java)atcom.soundcloud.android.json.JacksonJsonTransformer.handleInvalidDefinitionException(JacksonJsonTransformer.java:63)atcom.soundcloud.android.json.JacksonJsonTransformer.fromJson(JacksonJsonTransformer.java:43)atcom.soundcloud.android.api.DefaultApiClient.parseJsonResponse(DefaultApiClient.java:276)atcom.soundcloud.android.api.DefaultApiClient.mapResponse(DefaultApiClient.java:262)atcom.soundcloud.android.api.DefaultApiClientRx.lambda${'$'}mappedResponse${'$'}3(DefaultApiClientRx.java:68)atcom.soundcloud.android.api.DefaultApiClientRx.lambda${'$'}mappedResponse${'$'}3${'$'}DefaultApiClientRx(DefaultApiClientRx.java)atcom.soundcloud.android.api.-${'$'}${'$'}Lambda${'$'}DefaultApiClientRx${'$'}be0OQ1VqYEdZj9Gu6xhmCc5Nm2I.subscribe(-.java:6)atio.reactivex.rxjava3.internal.operators.single.SingleCreate.subscribeActual(SingleCreate.java:40)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleDoOnError.subscribeActual(SingleDoOnError.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleFlatMap${'$'}SingleFlatMapCallback.onSuccess(SingleFlatMap.java:85)atio.reactivex.rxjava3.internal.operators.single.SingleZipArray${'$'}ZipCoordinator.innerSuccess(SingleZipArray.java:119)atio.reactivex.rxjava3.internal.operators.single.SingleZipArray${'$'}ZipSingleObserver.onSuccess(SingleZipArray.java:170)atio.reactivex.rxjava3.internal.operators.single.SingleMap${'$'}MapSingleObserver.onSuccess(SingleMap.java:65)atio.reactivex.rxjava3.internal.operators.single.SingleFromCallable.subscribeActual(SingleFromCallable.java:55)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleZipArray.subscribeActual(SingleZipArray.java:63)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleFlatMap.subscribeActual(SingleFlatMap.java:37)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleDoOnSuccess.subscribeActual(SingleDoOnSuccess.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleResumeNext.subscribeActual(SingleResumeNext.java:39)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn${'$'}SubscribeOnObserver.run(SingleSubscribeOn.java:89)atio.reactivex.rxjava3.core.Scheduler${'$'}DisposeTask.run(Scheduler.java:614)atio.reactivex.rxjava3.internal.schedulers.ScheduledRunnable.run(ScheduledRunnable.java:65)atio.reactivex.rxjava3.internal.schedulers.ScheduledRunnable.call(ScheduledRunnable.java:56)atjava.util.concurrent.FutureTask.run(FutureTask.java:266)atjava.util.concurrent.ScheduledThreadPoolExecutor${'$'}ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:301)atjava.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)atjava.util.concurrent.ThreadPoolExecutor${'$'}Worker.run(ThreadPoolExecutor.java:641)atjava.lang.Thread.run(Thread.java:919)Causedbycom.fasterxml.jackson.databind.exc.InvalidDefinitionException:Multiplefieldsrepresentingproperty"a":ko.d#Avsko.d#aat[Source:(byte[])"{"playlist":{"urn":"soundcloud:playlists:201464500","user_urn":"soundcloud:users:29664945","title":"ReadyEP","created_at":"2016/03/0116:18:57+0000","duration":1290720,"permalink_url":"https://soundcloud.com/sexontoast/sets/ready-ep-1","artwork_url":"https://i1.sndcdn.com/artworks-000149235335-1rzq7u-large.jpg","track_count":0,"sharing":"public","user_tags":[],"_embedded":{"stats":{"likes_count":85,"reposts_count":17},"user":{"urn":"soundcloud:users:29664945","permalink":"sexontoast","username"[truncated2110bytes];line:1,column:1]atcom.fasterxml.jackson.databind.deser.BeanDeserializerFactory.buildBeanDeserializer(BeanDeserializerFactory.java:227)atcom.fasterxml.jackson.databind.deser.BeanDeserializerFactory.createBeanDeserializer(BeanDeserializerFactory.java:143)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createDeserializer2(DeserializerCache.java:414)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createDeserializer(DeserializerCache.java:349)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createAndCache2(DeserializerCache.java:264)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createAndCacheValueDeserializer(DeserializerCache.java:244)atcom.fasterxml.jackson.databind.deser.DeserializerCache.findValueDeserializer(DeserializerCache.java:142)atcom.fasterxml.jackson.databind.DeserializationContext.findContextualValueDeserializer(DeserializationContext.java:458)atcom.fasterxml.jackson.databind.deser.std.CollectionDeserializer.createContextual(CollectionDeserializer.java:181)atcom.fasterxml.jackson.databind.deser.std.CollectionDeserializer.createContextual(CollectionDeserializer.java:26)atcom.fasterxml.jackson.databind.DeserializationContext.handlePrimaryContextualization(DeserializationContext.java:665)atcom.fasterxml.jackson.databind.deser.BeanDeserializerBase.resolve(BeanDeserializerBase.java:508)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createAndCache2(DeserializerCache.java:293)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createAndCacheValueDeserializer(DeserializerCache.java:244)atcom.fasterxml.jackson.databind.deser.DeserializerCache.findValueDeserializer(DeserializerCache.java:142)atcom.fasterxml.jackson.databind.DeserializationContext.findNonContextualValueDeserializer(DeserializationContext.java:481)atcom.fasterxml.jackson.databind.deser.BeanDeserializerBase.resolve(BeanDeserializerBase.java:497)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createAndCache2(DeserializerCache.java:293)atcom.fasterxml.jackson.databind.deser.DeserializerCache._createAndCacheValueDeserializer(DeserializerCache.java:244)atcom.fasterxml.jackson.databind.deser.DeserializerCache.findValueDeserializer(DeserializerCache.java:142)atcom.fasterxml.jackson.databind.DeserializationContext.findRootValueDeserializer(DeserializationContext.java:491)atcom.fasterxml.jackson.databind.ObjectMapper._findRootDeserializer(ObjectMapper.java:4713)atcom.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4522)atcom.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3563)atcom.soundcloud.android.json.JacksonJsonTransformer.fromJson(JacksonJsonTransformer.java:41)atcom.soundcloud.android.api.DefaultApiClient.parseJsonResponse(DefaultApiClient.java:276)atcom.soundcloud.android.api.DefaultApiClient.mapResponse(DefaultApiClient.java:262)atcom.soundcloud.android.api.DefaultApiClientRx.lambda${'$'}mappedResponse${'$'}3(DefaultApiClientRx.java:68)atcom.soundcloud.android.api.DefaultApiClientRx.lambda${'$'}mappedResponse${'$'}3${'$'}DefaultApiClientRx(DefaultApiClientRx.java)atcom.soundcloud.android.api.-${'$'}${'$'}Lambda${'$'}DefaultApiClientRx${'$'}be0OQ1VqYEdZj9Gu6xhmCc5Nm2I.subscribe(-.java:6)atio.reactivex.rxjava3.internal.operators.single.SingleCreate.subscribeActual(SingleCreate.java:40)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleDoOnError.subscribeActual(SingleDoOnError.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleFlatMap${'$'}SingleFlatMapCallback.onSuccess(SingleFlatMap.java:85)atio.reactivex.rxjava3.internal.operators.single.SingleZipArray${'$'}ZipCoordinator.innerSuccess(SingleZipArray.java:119)atio.reactivex.rxjava3.internal.operators.single.SingleZipArray${'$'}ZipSingleObserver.onSuccess(SingleZipArray.java:170)atio.reactivex.rxjava3.internal.operators.single.SingleMap${'$'}MapSingleObserver.onSuccess(SingleMap.java:65)atio.reactivex.rxjava3.internal.operators.single.SingleFromCallable.subscribeActual(SingleFromCallable.java:55)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleZipArray.subscribeActual(SingleZipArray.java:63)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleFlatMap.subscribeActual(SingleFlatMap.java:37)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleDoOnSuccess.subscribeActual(SingleDoOnSuccess.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleMap.subscribeActual(SingleMap.java:35)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleResumeNext.subscribeActual(SingleResumeNext.java:39)atio.reactivex.rxjava3.core.Single.subscribe(Single.java:4813)atio.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn${'$'}SubscribeOnObserver.run(SingleSubscribeOn.java:89)atio.reactivex.rxjava3.core.Scheduler${'$'}DisposeTask.run(Scheduler.java:614)atio.reactivex.rxjava3.internal.schedulers.ScheduledRunnable.run(ScheduledRunnable.java:65)atio.reactivex.rxjava3.internal.schedulers.ScheduledRunnable.call(ScheduledRunnable.java:56)atjava.util.concurrent.FutureTask.run(FutureTask.java:266)atjava.util.concurrent.ScheduledThreadPoolExecutor${'$'}ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:301)atjava.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)atjava.util.concurrent.ThreadPoolExecutor${'$'}Worker.run(ThreadPoolExecutor.java:641)atjava.lang.Thread.run(Thread.java:919)""".trimIndent()),
            ),
          ),
        )
      )
    }

  }
}