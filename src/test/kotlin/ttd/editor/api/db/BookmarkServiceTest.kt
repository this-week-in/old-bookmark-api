package ttd.editor.api.db

import org.apache.commons.logging.LogFactory
import org.junit.jupiter.api.Test
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import ttd.editor.api.BookmarkService
import java.io.InputStreamReader

@DataR2dbcTest
@ActiveProfiles("test")
@Import(BookmarkService::class)
class BookmarkServiceTest(
    @Autowired private val dbc: DatabaseClient,
    @Autowired private val bookmarkService: BookmarkService) {

  private val log = LogFactory.getLog(javaClass)

  @Test
  fun search() {

    InputStreamReader(
        ClassPathResource("/bookmarks.sql").inputStream)
        .use { inputStreamReader ->

          val updated: Publisher<Int> = Flux
              .fromArray(inputStreamReader.readLines().toTypedArray())
              .flatMap { sql ->
                log.info(sql)
                this.dbc
                    .execute(sql)
                    .fetch()
                    .rowsUpdated()
              }
          StepVerifier
              .create(updated)
              .expectNextCount(28)
              .verifyComplete()

          val text = "Official: Trump to make Dems an offer to end shutdown"
          val search = this.bookmarkService.search(text, null, null, false)
          StepVerifier
              .create(search)
              .expectNextMatches {
                it.description.contains(text)
              }
              .verifyComplete()
        }
  }
}

