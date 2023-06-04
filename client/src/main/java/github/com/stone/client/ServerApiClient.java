package github.com.stone.client;

import feign.RequestInterceptor;
import feign.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name="serverApiClient",
        url = "${internal.server.url}" ,
        configuration = ServerApiClient.ServerApiClientConfiguration.class
        )
public interface ServerApiClient {

    @GetMapping("/long_query")
    Response longQuery();

    @GetMapping("/todo")
    List<TodoItem> listTodo();

    @PostMapping("/todo")
    TodoItem createTodo(@RequestBody TodoItem todoItem);

    class ServerApiClientConfiguration {

        @Value("${internal.server.token}")
        private String token;

        @Bean
        public RequestInterceptor requestInterceptor() {
            return requestTemplate -> {
                requestTemplate.header("token", token);
            };
        }


        /* if you need a custom HTTP client
        @Bean
        public Feign.Builder feignBuilder() {
            ApacheHttp5Client hc5 = new ApacheHttp5Client();

            return Feign.builder()
                    .retryer(Retryer.NEVER_RETRY)
                    .client(hc5);
        }
         */

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
