import api.HttpTaskServer;
import api.KVServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import service.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        int PORT = 8080;
        final String NAME = "task";
        final long DURATION_MS = 100;
        final LocalDateTime START_TIME_1 = LocalDateTime.of(2022, 6, 1, 0, 0);
        final LocalDateTime START_TIME_2 = LocalDateTime.of(2022, 6, 1, 1, 1);
        HttpClient client = HttpClient.newHttpClient();
        String url = "http://localhost:" + PORT;
        IdGenerator generator = new IdGenerator();
        HttpTaskServer httpTaskServer;
        KVServer kvServer;
        try {
            kvServer = new KVServer();
            kvServer.start();
            httpTaskServer = new HttpTaskServer();
            httpTaskServer.startServer();
        } catch (IOException e) {
        }


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
        try {
            HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
            System.out.println(responseEpic);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}




