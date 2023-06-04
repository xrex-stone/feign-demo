package github.com.stone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController("/internal")
public class InternalController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("data","success");
    }

    @GetMapping("/check_auth")
    public ResponseEntity health(@RequestHeader("token") String token) {

        if (!"123456".equals(token)) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Map>(Map.of("data","success"), HttpStatus.OK) ;
    }

    @GetMapping("/long_query")
    public ResponseEntity longQuery(@RequestHeader("token") String token) throws InterruptedException {

        if (!"123456".equals(token)) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Thread.sleep(70*1000);

        return new ResponseEntity<Map>(Map.of("data","success"), HttpStatus.OK) ;
    }

    @GetMapping("/todo")
    public ResponseEntity listTodo(@RequestHeader("token") String token) {

        if (!"123456".equals(token)) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        List<TodoItem> result = List.of(new TodoItem(UUID.randomUUID().toString(), "schedule01", "see a doctor", LocalDateTime.now()),
                new TodoItem(UUID.randomUUID().toString(), "schedule02", "go to gym", LocalDateTime.now()),
                new TodoItem(UUID.randomUUID().toString(), "schedule03", "study", LocalDateTime.now()),
                new TodoItem(UUID.randomUUID().toString(), "schedule04", "do exercise", LocalDateTime.now()),
                new TodoItem(UUID.randomUUID().toString(), "schedule05", "study", LocalDateTime.now()),
                new TodoItem(UUID.randomUUID().toString(), "schedule06", "coding", LocalDateTime.now())
        );

        return new ResponseEntity<List>(result, HttpStatus.OK) ;
    }

    @PostMapping("/todo")
    public ResponseEntity createTodo(@RequestHeader("token") String token, @RequestBody TodoItem todoItem) {

        if (!"123456".equals(token)) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        todoItem.setId(UUID.randomUUID().toString());
        todoItem.setCreatedTime(LocalDateTime.now());

        return new ResponseEntity<TodoItem>(todoItem, HttpStatus.OK) ;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoItem {
        private String id;
        private String name;
        private String description;
        private LocalDateTime createdTime;


    }

}
