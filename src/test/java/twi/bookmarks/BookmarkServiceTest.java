package twi.bookmarks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BookmarkServiceTest {

    private final BookmarkService service;

    BookmarkServiceTest(@Autowired BookmarkService service) {
        this.service = service;
    }

    @Test
    void search() {
        var now = Instant.now();
        var old = now.minus(21, TimeUnit.DAYS.toChronoUnit());
        System.out.println("the old date is " + old);
        var results = this.service.search("Spring Boot", new Date(old.toEpochMilli()), new Date(now.toEpochMilli()), false);
        System.out.println("there are " + results.size() + " results.");
        for (var b : results) {
            System.out.println("b: " + b.toString());
        }
    }

}