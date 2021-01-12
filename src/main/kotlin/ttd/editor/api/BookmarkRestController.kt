package ttd.editor.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.fasterxml.jackson.databind.node.TextNode
import org.reactivestreams.Publisher
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/bookmarks")
class BookmarkRestController(
    private val objectMapper: ObjectMapper,
    private val bookmarkService: BookmarkService) {


  @DeleteMapping(path = ["/{bookmarkId}"])
  fun delete(@PathVariable bookmarkId: Int): Publisher<ResponseEntity<Any>> {
    return Mono
        .just(bookmarkId)
        .flatMap { bookmarkService.delete(bookmarkId) }
        .map {
          val replyResponseEntity: ResponseEntity<Any> =
              if (it) ResponseEntity.ok().build() else ResponseEntity.notFound().build()
          replyResponseEntity
        }
  }

  @PutMapping(
      path = ["/{bookmarkId}"],
      consumes = [MediaType.APPLICATION_JSON_VALUE],
      produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun update(@PathVariable bookmarkId: Int, @RequestBody body: String): Publisher<Map<String, *>> {
    return Mono
        .just(body)
        .flatMap {
          val jsonNode = objectMapper.readTree(body)
          val tags = mutableListOf<String>()
          val tagsNode = jsonNode["tags"]
          val href = jsonNode["href"].asText()
          if (tagsNode.nodeType == JsonNodeType.ARRAY) {
            (tagsNode as ArrayNode).forEach {
              tags.add(it.textValue())
            }
          } else if (tagsNode.nodeType == JsonNodeType.STRING) {
            tags.add((tagsNode as TextNode).textValue())
          }
          val description = jsonNode["description"].asText()
          this.bookmarkService
              .update(bookmarkId, href, description, tags.toTypedArray())
              .map { bookmarkToMap(it) }
        }
  }

  fun doSearch(query: String, start: Long, stop: Long, errors: Boolean): Flux<Bookmark> =
      this.bookmarkService
          .search(
              query = query,
              startDate = if (start == 0L) null else Date(start),
              stopDate = if (start == 0L) null else Date(stop),
              errors = errors
          )


  @ExperimentalStdlibApi
  @GetMapping("/export", produces = [MediaType.TEXT_MARKDOWN_VALUE])
  fun export(@RequestParam(required = false, defaultValue = "") query: String,
             @RequestParam(required = false, defaultValue = "0") start: Long,
             @RequestParam(required = false, defaultValue = "0") stop: Long,
             @RequestParam(required = false, defaultValue = "false") errors: Boolean)
      : ResponseEntity<Publisher<Resource>> {

    val search: Publisher<Resource> =
        this.doSearch(query, start, stop, errors)
            .map {
              "* [${it.description}](${it.href})"
            }
            .collectSortedList()
            .map { it.joinToString(System.lineSeparator()) }
            .map { ByteArrayResource(it.encodeToByteArray()) }

    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=markdown-export-${UUID.randomUUID()}.md")
        .contentType(MediaType.TEXT_MARKDOWN)
        .body(search)
  }

  @GetMapping("/search", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun search(@RequestParam(required = false, defaultValue = "") query: String,
             @RequestParam(required = false, defaultValue = "0") start: Long,
             @RequestParam(required = false, defaultValue = "0") stop: Long,
             @RequestParam(required = false, defaultValue = "false") errors: Boolean): Flux<Map<String, *>> =
      this.doSearch(query, start, stop, errors)
          .map { bookmarkToMap(it) }

  @GetMapping("/next", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun next() = this.bookmarkService.next().map { bookmarkToMap(it) }

  private fun bookmarkToMap(it: Bookmark): Map<String, *> {
    return mapOf(
        "bookmarkId" to it.bookmarkId,
        "href" to it.href,
        "description" to it.description,
        "extended" to it.extended,
        "hash" to it.hash,
        "tags" to it.tags,
        "time" to it.time.time,
        "edited" to it.edited
    )
  }
}
