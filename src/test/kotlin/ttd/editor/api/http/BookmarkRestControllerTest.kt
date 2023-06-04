package ttd.editor.api.http

import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryBuilder
import org.springframework.boot.autoconfigure.r2dbc.EmbeddedDatabaseConnection
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ttd.editor.api.Bookmark
import ttd.editor.api.BookmarkService
import java.util.*
import kotlin.math.floor

@WebFluxTest
@ExtendWith(SpringExtension::class)
@AutoConfigureWebTestClient
@Import(TestConfig::class)
class BookmarkRestControllerTest(@Autowired private val webTestClient: WebTestClient) {

  private val root = "/bookmarks"

  @MockBean
  lateinit var bookmarkService: BookmarkService

  private val href = "http://FoxNews.gov"
  private val description = "a description"
  private val extended = "extended"
  private val hash = "hash"
  private val meta = "meta"
  private val time = Date()
  private val tags = arrayOf("trump", "coronavirus", "corruption")
  private val bookmarkId = 1323

  private fun generateNRandomBookmarks(number: Int): Map<String, Bookmark> {
    fun generateSingleRandomBookmark(id: Int): Bookmark {
      val tokens = arrayOf(
          "Garfield", "Adobe", "Yahoo", "Google", "CNN",
          "HuffPo", "Twitter", "BBC"
      )
      val tags = arrayOf("trump", "russia", "china", "blm")
      val uris = tokens.map { x -> "http://${x}.com" }
      val descriptions = uris.map { x -> "this website ${x} is easily one of the nicest websites ever made by anyone in the history of websites." };
      val rind: () -> Int = {
        floor(Math.random() * (tokens.size - 1)).toInt()
      }
      val bookmarkTags = tags.filter { tag ->
        tag === tags[0] || Math.random() > .5
      }
      return Bookmark(id, uris[rind()], descriptions[rind()], "", "${rind()}",
          "meta", Date(), false, false, bookmarkTags.toTypedArray(), if (Math.random() > .5) Date() else null)
    }

    val bookmarks = mutableMapOf<String, Bookmark>()
    (0..number).forEach {
      bookmarks[it.toString()] = generateSingleRandomBookmark(it)
    }
    return bookmarks
  }

  @Test
  fun search() {
    val results = generateNRandomBookmarks(10)
    val array = results.values.toTypedArray()
    Mockito.`when`(this.bookmarkService.search(query = "trump", errors = true, stopDate = null, startDate = null))
        .thenReturn(Flux.fromArray(array))
    val json = this.webTestClient
        .get()
        .uri("$root/search?query={q}&errors={e}", "trump", true)
        .headers { headers: HttpHeaders ->
          headers.setBasicAuth(TestConfig.USER, TestConfig.PW)
        }
        .exchange()
        .expectStatus().isOk
        .expectBody()  //
        .jsonPath("@.[0].bookmarkId").isEqualTo(array[0].bookmarkId)
        .jsonPath("@.[0].href").isEqualTo(array[0].href)
        .jsonPath("@.[2].bookmarkId").isEqualTo(array[2].bookmarkId)
        .jsonPath("@.[2].href").isEqualTo(array[2].href)
        .returnResult()
        .toString()
    println(json)
  }

  @Test
  fun delete() {

    Mockito.`when`(this.bookmarkService.delete(this.bookmarkId)).thenReturn(Mono.just(true))
    this.webTestClient
        .delete()
        .uri("$root/{id}", this.bookmarkId)
        .headers { headers: HttpHeaders ->
          headers.setBasicAuth(TestConfig.USER, TestConfig.PW)
        }
        .exchange()
        .expectStatus().isOk()

    Mockito.`when`(this.bookmarkService.delete(988)).thenReturn(Mono.just(false))
    this.webTestClient
        .delete()
        .uri("$root/{id}", 988)
        .headers { headers: HttpHeaders ->
          headers.setBasicAuth(TestConfig.USER, TestConfig.PW)
        }
        .exchange()
        .expectStatus().isNotFound()
  }

  @Test
  fun update() {
    Mockito.`when`(this.bookmarkService.update(this.bookmarkId, this.href, this.description, this.tags))
        .thenReturn(Mono.just(Bookmark(this.bookmarkId, this.href, this.description, extended, hash,
            this.meta, this.time, false, true, this.tags, Date())))
    val json =
        """  
           {  
                      "href" : "${this.href}",
                      "tags" : [ ${tags.map { "\"${it}\"" }.joinToString(",")} ],
               "description" : "${this.description}"
           }
        """
    this.webTestClient
        .put()
        .uri("$root/{id}", this.bookmarkId)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromProducer(Mono.just(json), String::class.java))
        .headers { headers: HttpHeaders ->
          headers.setBasicAuth(TestConfig.USER, TestConfig.PW)
        }
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        .expectBody() //
        .jsonPath(".href").isEqualTo(this.href)
        .jsonPath(".description").isEqualTo(this.description)
        .jsonPath(".extended").isEqualTo(this.extended)
        .jsonPath(".hash").isEqualTo(this.hash)
        .jsonPath(".tags.[0]").isEqualTo(this.tags[0])
  }

  @Test
  fun next() {
    Mockito.`when`(this.bookmarkService.next())
        .thenReturn(Mono.just(Bookmark(bookmarkId, href, description, extended, hash,
            meta, time, false, true, tags,  Date())))

    this.webTestClient
        .get()
        .uri("$root/next")
        .headers { headers: HttpHeaders ->
          headers.setBasicAuth(TestConfig.USER, TestConfig.PW)
        }
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        .expectBody() //
        .jsonPath(".href").isEqualTo(this.href)
        .jsonPath(".description").isEqualTo(this.description)
        .jsonPath(".extended").isEqualTo(this.extended)
        .jsonPath(".hash").isEqualTo(this.hash)
        .jsonPath(".tags.[0]").isEqualTo(this.tags[0])
  }
}

@Configuration
@PropertySource("classpath:/application-default.properties")
class TestConfig {

  companion object {
    const val USER = "user"
    const val PW = "pw"
  }

  @Bean
  fun bookmarkService(dbc: DatabaseClient) = BookmarkService(dbc)

  @Bean
  fun databaseClient(cf: ConnectionFactory): DatabaseClient = DatabaseClient.create(cf)

  @Bean
  fun r2dbcProperties(env: Environment) = R2dbcProperties()
      .apply {
        password = "orders"
        username = "orders"
        url = " r2dbc:postgres://localhost/ttd ".trim()
      }

  @Bean
  fun connectionFactory(r2dbcProperties: R2dbcProperties) = ConnectionFactoryBuilder
      .of(r2dbcProperties) { EmbeddedDatabaseConnection.get(BookmarkService::class.java.classLoader) }.build()

  @Bean
  fun authorization(http: ServerHttpSecurity) =
      http
          .csrf { it.disable() }
          .httpBasic(Customizer.withDefaults())
          .build()

  @Bean
  fun authentication() =
      MapReactiveUserDetailsService(
          User.withDefaultPasswordEncoder()
              .username(USER)
              .password(PW)
              .roles("USER")
              .build())

}