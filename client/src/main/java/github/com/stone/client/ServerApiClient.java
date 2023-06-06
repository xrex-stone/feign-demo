package github.com.stone.client;

import feign.RequestInterceptor;
import feign.Response;
import github.com.stone.client.ServerApiClient.Fallback;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name="serverApiClient",
        url = "${internal.server.url}" ,
        configuration = ServerApiClient.ServerApiClientConfiguration.class,
        fallback = Fallback.class
        )
@Primary
public interface ServerApiClient {

    @GetMapping("/long_query")
    Response longQuery();

    @GetMapping("/todo")
    List<TodoItem> listTodo();

    @PostMapping("/todo")
    TodoItem createTodo(@RequestBody TodoItem todoItem);

    @PostMapping("/not_found")
    TodoItem notFound();

    class ServerApiClientConfiguration {

        @Value("${internal.server.token}")
        private String token;

        @Bean
        public RequestInterceptor requestInterceptor() {
            return requestTemplate -> {
                requestTemplate.header("token", token);
            };
        }

    }

    @Component
    class Fallback implements ServerApiClient {

        @Override
        public Response longQuery() {
            return null;
        }

        @Override
        public List<TodoItem> listTodo() {
            return null;
        }

        @Override
        public TodoItem createTodo(TodoItem todoItem) {
            return null;
        }

        @Override
        public TodoItem notFound() {
            return new TodoItem(UUID.randomUUID().toString(), "mock schedule", "this is return from fallback", LocalDateTime.now());
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class TodoItem {
        private String id;
        private String name;
        private String description;
        private LocalDateTime createdTime;
    }
}
