package ttd.editor.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class EditorApiApplication

fun main(args: Array<String>) {
    System.getenv().forEach { (k, v) -> println("$k") }
    runApplication<EditorApiApplication>(*args)
}