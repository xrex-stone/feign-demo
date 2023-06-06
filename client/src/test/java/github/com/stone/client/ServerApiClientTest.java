package github.com.stone.client;

import feign.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ServerApiClientTest {

    @Autowired
    private ServerApiClient serverApiClient;

    @Test
    public void testListTodo() {
        List<ServerApiClient.TodoItem> result = serverApiClient.listTodo();

        System.out.println("fetch size:" + result.size());
        Assertions.assertThat(result.size()).isGreaterThan(0);
        result.forEach(System.out::println);
    }

    @Test
    public void testCreateTodo() {
        ServerApiClient.TodoItem item = new ServerApiClient.TodoItem();
        item.setName("my schedule");
        item.setDescription("my first schedule...");

        ServerApiClient.TodoItem returnTodo = serverApiClient.createTodo(item);

        System.out.println(returnTodo);
        Assertions.assertThat(returnTodo.getId()).isNotNull();
        Assertions.assertThat(returnTodo.getCreatedTime()).isNotNull();
        Assertions.assertThat(returnTodo.getName()).isEqualTo(item.getName());
        Assertions.assertThat(returnTodo.getDescription()).isEqualTo(item.getDescription());
    }

    @Test
    public void testNotFound() {
        ServerApiClient.TodoItem returnTodo = serverApiClient.notFound();

        System.out.println(returnTodo);
    }

    @Test
    public void testLongQuery() {
        Response response = serverApiClient.longQuery();
        System.out.println(response);
        Assertions.assertThat(response.status()).isEqualTo(200);
    }

}
