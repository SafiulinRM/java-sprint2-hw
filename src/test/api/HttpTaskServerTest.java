package test.api;

import api.HttpTaskServer;
import api.KVServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskServerTest {
    public static final int PORT = 8080;
    public static final String TASK_NOT_REMOVE = "Задача не удалилась";
    public static final String TASK_NOT_CREATE = "Задача не создалась";
    public static final String NAME = "task";
    public static final long DURATION_MS = 100;
    public static final LocalDateTime START_TIME_1 = LocalDateTime.of(2022, 6, 1, 0, 0);
    public static final LocalDateTime START_TIME_2 = LocalDateTime.of(2022, 6, 1, 1, 1);
    public IdGenerator generator = new IdGenerator();
    String url = "http://localhost:" + PORT;
    private HttpTaskManager taskManager;
    HttpClient client = HttpClient.newHttpClient();
    HttpTaskServer httpTaskServer;
    KVServer kvServer;

    @BeforeEach
    void startServers() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        taskManager = Managers.getDefault();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.startServer();
        generator.setCounterId(0);
        URI urlTasksDelete = URI.create(url + "/tasks");
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(urlTasksDelete)
                .version(HttpClient.Version.HTTP_1_1)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
    }

    @AfterEach
    void stopServers() {
        kvServer.stop();
        httpTaskServer.stop();
    }

    @Test
    void createTask() throws IOException, InterruptedException {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlTask = URI.create(url + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlTask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertEquals(task, taskManager.getTask(1), TASK_NOT_CREATE);
    }

    @Test
    void getNewHistory() throws IOException, InterruptedException {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlTask = URI.create(url + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlTask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlTaskGet = URI.create(url + "/tasks/task?id=1");
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(urlTaskGet)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();
        client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        URI urlHistory = URI.create(url + "/tasks/history");
        HttpRequest requestHistory = HttpRequest.newBuilder()
                .uri(urlHistory)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(requestHistory, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, gson.fromJson(response.body(), List.class).size());
    }

    @Test
    void createSubtask() throws IOException, InterruptedException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        URI urlEpic = URI.create(url + "/tasks/epic");
        HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
        HttpRequest requestEpic = HttpRequest.newBuilder()
                .uri(urlEpic)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(bodyEpic)
                .build();
        client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        URI urlSubtask = URI.create(url + "/tasks/subtask");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(subtask));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlSubtask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertEquals(subtask, taskManager.getSubtask(2), TASK_NOT_CREATE);
    }

    @Test
    void createEpic() throws IOException, InterruptedException {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlEpic = URI.create(url + "/tasks/epic");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlEpic)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertEquals(epic, taskManager.getEpic(1), TASK_NOT_CREATE);
    }

    @Test
    void removeEpic() throws IOException, InterruptedException {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlEpic = URI.create(url + "/tasks/epic");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlEpic)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlEpicDelete = URI.create(url + "/tasks/epic?id=1");
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(urlEpicDelete)
                .version(HttpClient.Version.HTTP_1_1)
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
        assertEquals(202, responseDelete.statusCode());
        assertEquals(0, taskManager.getEpics().size(), TASK_NOT_REMOVE);
    }

    @Test
    void removeSubtask() throws IOException, InterruptedException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        URI urlEpic = URI.create(url + "/tasks/epic");
        HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
        HttpRequest requestEpic = HttpRequest.newBuilder()
                .uri(urlEpic)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(bodyEpic)
                .build();
        client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        URI urlSubtask = URI.create(url + "/tasks/subtask");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(subtask));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlSubtask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlSubtaskDelete = URI.create(url + "/tasks/subtask?id=2");
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(urlSubtaskDelete)
                .version(HttpClient.Version.HTTP_1_1)
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
        assertEquals(202, responseDelete.statusCode());
        assertEquals(0, taskManager.getSubtasks().size(), TASK_NOT_REMOVE);
    }

    @Test
    void removeTask() throws IOException, InterruptedException {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlTask = URI.create(url + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlTask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlTaskDelete = URI.create(url + "/tasks/task?id=1");
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(urlTaskDelete)
                .version(HttpClient.Version.HTTP_1_1)
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
        assertEquals(202, responseDelete.statusCode());
        assertEquals(0, taskManager.getTasks().size(), TASK_NOT_REMOVE);
    }

    @Test
    void getTask() throws IOException, InterruptedException {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlTask = URI.create(url + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlTask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlTaskGet = URI.create(url + "/tasks/task?id=1");
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(urlTaskGet)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(task, gson.fromJson(response.body(), Task.class));
    }

    @Test
    void getEpic() throws IOException, InterruptedException {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlEpic = URI.create(url + "/tasks/epic");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlEpic)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        URI urlSubtask = URI.create(url + "/tasks/subtask");
        HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(gson.toJson(subtask));
        HttpRequest requestSubtask = HttpRequest.newBuilder()
                .uri(urlSubtask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(bodySubtask)
                .build();
        client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());
        URI urlEpicGet = URI.create(url + "/tasks/epic?id=1");
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(urlEpicGet)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, gson.fromJson(response.body(), List.class).size());
    }

    @Test
    void getPrioritizedTasks() throws IOException, InterruptedException {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlTask = URI.create(url + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlTask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlTasksGet = URI.create(url + "/tasks");
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(urlTasksGet)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, gson.fromJson(response.body(), Set.class).size());
    }

    @Test
    void removeAll() throws IOException, InterruptedException {
        Epic epic = new Epic(generator.generate(), NAME, Status.NEW);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlEpic = URI.create(url + "/tasks/epic");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(epic));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlEpic)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = new Subtask(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1, epic.getId());
        URI urlSubtask = URI.create(url + "/tasks/subtask");
        HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(gson.toJson(subtask));
        HttpRequest requestSubtask = HttpRequest.newBuilder()
                .uri(urlSubtask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(bodySubtask)
                .build();
        client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_2);
        URI urlTask = URI.create(url + "/tasks/task");
        HttpRequest.BodyPublisher bodyTask = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
        HttpRequest requestTask = HttpRequest.newBuilder()
                .uri(urlTask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(bodyTask)
                .build();
        client.send(requestTask, HttpResponse.BodyHandlers.ofString());
        URI urlTasksDelete = URI.create(url + "/tasks");
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(urlTasksDelete)
                .version(HttpClient.Version.HTTP_1_1)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
        assertEquals(202, response.statusCode());
        assertEquals(0, gson.fromJson(response.body(), Set.class).size());
    }

    @Test
    void getTasks() throws IOException, InterruptedException {
        Task task = new Task(generator.generate(), NAME, Status.NEW, DURATION_MS, START_TIME_1);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlTask = URI.create(url + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlTask)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(body)
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        URI urlTasksGet = URI.create(url + "/tasks/task");
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(urlTasksGet)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, gson.fromJson(response.body(), Map.class).size());
    }
}