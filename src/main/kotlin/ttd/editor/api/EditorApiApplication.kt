package ttd.editor.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class EditorApiApplication {

    @Bean
    fun runner(bs :BookmarkService) = ApplicationListener<ApplicationReadyEvent> {
        bs.count().subscribe {
            println( "count $it ")
        }
    }
}

fun main(args: Array<String>) {
    System.getenv().forEach { (k, _) -> println(k) }
    runApplication<EditorApiApplication>(*args)
}