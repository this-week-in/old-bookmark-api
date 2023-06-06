package twi.bookmarks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

@EnableMethodSecurity
@SpringBootApplication
public class BookmarkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkApiApplication.class, args);
    }

}

@ResponseBody
@Controller
class MeController {

    @GetMapping("/me")
    Map<String, String> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("name", jwt.getSubject());
    }
}


record Bookmark(
        int bookmarkId, String href, String description, String extended, String hash, String meta, Date time,
        String[] tags, Date edited) {
}


@Service
@Transactional
class BookmarkService {

    private static final Logger log = LoggerFactory.getLogger(BookmarkService.class);

    private final JdbcTemplate template;

    private final RowMapper<Bookmark> bookmarkRowMapper = (rs, rowNum) ->
            new Bookmark(
                    rs.getInt("bookmark_id"),
                    rs.getString("href"),
                    rs.getString("description"),
                    rs.getString("extended"),
                    rs.getString("hash"),
                    rs.getString("meta"),
                    (rs.getDate("time")),
                    tagsFromArray(rs.getArray("tags")),
                    rs.getDate("edited"));

    private static String[] tagsFromArray(Array a) {
        try {
            var o = a.getArray();
            if (o instanceof String[] sa) {
                return sa;
            }
        } catch (Exception e) {
            log.error("couldn't convert the array of tags into an array of Strings");
        }
        return new String[0];
    }

    BookmarkService(JdbcTemplate template) {
        this.template = template;
    }

    private static String paramName(int p) {
        return " ? ";
    }

    public Collection<Bookmark> search(String query, Date start, Date stop, Boolean errors) {
        var sql = """
                select * from bookmark where deleted = false 
                """;

        record Predicate(String clause, List<Object> params) {
        }

        var bindCtr = 1;
        var predicates = new ArrayList<Predicate>();
        if (query != null && StringUtils.hasText(query.trim())) {
            predicates.add(new Predicate(" and LOWER( description) similar to  " + paramName(bindCtr) + " ", List.of("%" + query.toLowerCase() + "%")));
            bindCtr += 1;
        }
        if (errors != null && errors) {
            predicates.add(new Predicate(" and edited is " + (errors ? "" : " not "), List.of()));
        }
        if (start != null) {
            predicates.add(new Predicate(" and time >= " + paramName(bindCtr), List.of(start)));
            bindCtr += 1;
        }
        if (stop != null) {
            predicates.add(new Predicate(" and time <= " + paramName(bindCtr), List.of(stop)));
            bindCtr += 1;
        }
        var stringBuilder = new StringBuilder();
        stringBuilder.append(sql);
        predicates.forEach(p -> stringBuilder.append(p.clause()));
        var finishedSqlQuery = stringBuilder.toString();
        var listOfParams = new ArrayList<>();
        for (var predicate : predicates) {
            if (predicate.params() != null)
                listOfParams.addAll(predicate.params());
        }
        if (log.isDebugEnabled()) {
            log.debug(finishedSqlQuery);
            listOfParams.forEach((p) -> log.debug(p.toString()));
        }
        return this.template.query(finishedSqlQuery, ps -> {
            var indx = 0;
            for (var p : listOfParams) {
                indx += 1;
                if (p instanceof java.util.Date d) {
                    ps.setDate(indx, new java.sql.Date(d.toInstant().toEpochMilli()));
                }//
                else if (p instanceof String s) {
                    ps.setString(indx, s);
                }
            }
        }, this.bookmarkRowMapper);
    }

    public long count() {
        return this.template.queryForObject("select count(*) as count_of_records from bookmark where edited is null and deleted = false", Long.class);
    }

    public Bookmark next() {
        var results = this.template.query(" select * from bookmark where edited is null and deleted = false order by time desc limit 1  ",
                this.bookmarkRowMapper);
        if (results.size() > 0)
            return results.iterator().next();
        return null;

    }

    public boolean delete(long bookmarkId) {
        return this.template.update("update bookmark set deleted = true where bookmark_id = ?", bookmarkId) > 0;
    }

    public Bookmark update(long bookmarkId, String href, String description, String[] tags) {
        this.template.update(" update bookmark set description = ?, href =?, tags = ?, edited = NOW() where bookmark_id = ?",
                description, href, tags, bookmarkId);
        return this.template.queryForObject("select * from bookmark where bookmark_id = ? ", this.bookmarkRowMapper, bookmarkId);
    }

}


@RestController
@RequestMapping("/bookmarks")
class BookmarkRestController {

    private final ObjectMapper objectMapper;
    private final BookmarkService bookmarkService;

    BookmarkRestController(ObjectMapper objectMapper, BookmarkService bookmarkService) {
        this.objectMapper = objectMapper;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/test")
    Map<String, String> test() {
        return Map.of("message", "hello, world!");
    }

    @DeleteMapping("/{bookmarkId}")
    ResponseEntity<?> delete(@PathVariable int bookmarkId) {
        if (this.bookmarkService.delete(bookmarkId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{bookmarkId}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> update(@PathVariable int bookmarkId, @RequestBody String body) throws Exception {
        var json = objectMapper.readTree(body);
        var tags = new ArrayList<String>();
        var tagsNode = json.get("tags");
        var href = json.get("href").asText();
        if (tagsNode.getNodeType() == JsonNodeType.ARRAY) {
            var a = ((ArrayNode) tagsNode);
            a.elements().forEachRemaining(i -> tags.add(i.textValue()));
        } else if (tagsNode.getNodeType() == JsonNodeType.STRING) {
            tags.add(tagsNode.textValue());
        }
        var description = json.get("description").asText();
        var updated = this.bookmarkService.update(bookmarkId, href, description, tags.toArray(new String[0]));
        return bookmarkToMap(updated);
    }

    private static Map<String, Object> bookmarkToMap(Bookmark it) {
        return Map.of(
                "bookmarkId", it.bookmarkId(),
                "href", it.href(),
                "description", it.description(),
                "extended", it.extended(),
                "hash", it.hash(),
                "tags", it.tags(),
                "time", it.time().getTime(),
                "edited", it.edited() == null ? false : it.edited()
        );
    }

    private Collection<Bookmark> doSearch(String query, Long start, Long stop, boolean errors) {
        return this.bookmarkService.search(query,
                start == null ? null : new Date(start),
                stop == null ? null : new Date(stop),
                errors);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<Map<String, Object>> search(@RequestParam(required = false, defaultValue = "") String query,
                                           @RequestParam(required = false, defaultValue = "0") Long start,
                                           @RequestParam(required = false, defaultValue = "0") Long stop,
                                           @RequestParam(required = false, defaultValue = "false") Boolean errors) {
        return this.doSearch(query, start, stop, errors).stream()
                .map(BookmarkRestController::bookmarkToMap)
                .toList();
    }

    @GetMapping(path = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> next() {
        return bookmarkToMap(this.bookmarkService.next());
    }

    @GetMapping(path = "/export", produces = MediaType.TEXT_MARKDOWN_VALUE)
    ResponseEntity<Resource> export(@RequestParam(required = false, defaultValue = "") String query,
                                    @RequestParam(required = false, defaultValue = "0") Long start,
                                    @RequestParam(required = false, defaultValue = "0") Long stop,
                                    @RequestParam(required = false, defaultValue = "false") Boolean errors) {
        var results = this.doSearch(query, start, stop, errors)
                .stream()
                .map(bookmark -> String.format("* [%s](%s)", bookmark.description(), bookmark.href()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_MARKDOWN)
                .headers(hc -> hc.setContentDisposition(ContentDisposition.attachment().filename("twi-" + UUID.randomUUID() + ".md").build()))
                .body(new ByteArrayResource(results.getBytes()));
    }

}
