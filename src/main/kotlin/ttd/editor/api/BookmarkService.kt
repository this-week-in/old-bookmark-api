package ttd.editor.api

import org.apache.commons.logging.LogFactory
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*


// TODO move to TestContainers and some seed-data to make sure this is always consistent
@Service
@Transactional
class BookmarkService(private val databaseClient: DatabaseClient) {

  private val log = LogFactory.getLog(javaClass)
  private val defaultBoolean: (Boolean?) -> Boolean = { it ?: false }

  private fun localDateTimeToDate(ldt: LocalDateTime): Date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant())

  private val mapper: (Map<String, Any>) -> Bookmark = {
    Bookmark(
        it["bookmark_id"] as Int,
        it["href"] as String,
        it["description"] as String,
        it["extended"] as String,
        it["hash"] as String,
        it["meta"] as String,
        Date((it["time"] as LocalDateTime).toEpochSecond(ZoneOffset.UTC)),
        defaultBoolean(it["shared"] as Boolean?),
        defaultBoolean(it["toread"] as Boolean?),
        it["tags"] as Array<String>,
        if (it["edited"] == null) null else localDateTimeToDate((it["edited"] as LocalDateTime))
    )
  }

  fun count(): Mono<Long> =
      this.databaseClient
          .execute("select count(*) as count_of_records from bookmark where edited is null and deleted = false ")
          .fetch()
          .one()
          .map {
            it["count_of_records"] as Long
          }

  fun search(query: String?, startDate: Date?, stopDate: Date?, errors: Boolean?): Flux<Bookmark> {

    if (log.isDebugEnabled) {
      log.debug("""
        =========
        ${query} 
        ${startDate} 
        ${stopDate} 
        ${errors} 
        """.trimIndent())
    }

    data class Predicate(
        val clause: String,
        val params: Map<String, Any> = mapOf()
    )

    val sql = """
      select * from bookmark where deleted = false 
    """

    var bindCtr = 1

    val predicates = mutableListOf<Predicate>()
    if (query != null && StringUtils.hasText(query.trim())) {
      predicates.add(Predicate(" and LOWER( description) similar to \$${bindCtr} ", mapOf("\$${bindCtr}" to "%${query.toLowerCase()}%")))
      bindCtr += 1
    }
    if (errors != null && errors) {
      predicates.add(Predicate(" and edited is ${if (errors) "" else "not"} null "))
    }
    if (startDate != null) {
      predicates.add(Predicate(" and time >= \$${bindCtr}", mapOf("\$${bindCtr}" to startDate)))
      bindCtr += 1
    }
    if (stopDate != null) {
      predicates.add(Predicate(" and time <= \$${bindCtr}", mapOf("\$${bindCtr}" to stopDate)))
      bindCtr += 1
    }

    val stringBuilder = StringBuilder()
    stringBuilder.append(sql)
    predicates.forEach {
      stringBuilder.append(it.clause)
    }
//    stringBuilder.append(" limit 100 ")

    val finishedSqlQuery = stringBuilder.toString()
    if (log.isDebugEnabled) {
      log.debug(finishedSqlQuery)
    }

    var querySpec: DatabaseClient.GenericExecuteSpec =
        databaseClient.execute(finishedSqlQuery)
    predicates.forEach {
      it.params.forEach { (k, v) ->
        querySpec = querySpec.bind(k, v)
      }
    }
    return querySpec.fetch().all().map(this.mapper)
  }


  fun delete(bookmarkId: Int): Mono<Boolean> {
    return databaseClient
        .execute("update bookmark set deleted = true where bookmark_id = $1")
        .bind("$1", bookmarkId)
        .fetch()
        .rowsUpdated()
        .map { it > 0 }
  }

  fun update(bookmarkId: Int, href: String, description: String, tags: Array<String>): Mono<Bookmark> =
      this.databaseClient
          .execute(" update bookmark set description = $1, href = $2, tags = $3, edited = NOW() where bookmark_id = $4 ")
          .bind("$1", description)
          .bind("$2", href)
          .bind("$3", tags)
          .bind("$4", bookmarkId)
          .fetch()
          .rowsUpdated()
          .doOnNext { log.debug("updated $it records.") }
          .onErrorContinue { throwable, _ -> log.error("ERROR!! $throwable") }
          .filter { count -> count > 0 }
          .flatMapMany {
            this.databaseClient
                .execute("select * from bookmark where href = $1 limit 1")
                .bind("$1", href)
                .fetch()
                .first()
                .map(mapper)
          }
          .single()

  fun next(): Mono<Bookmark> = databaseClient
      .execute(" select * from bookmark where edited is null and deleted = false order by time desc limit 1  ")
      .fetch()
      .one()
      .map(mapper)

}