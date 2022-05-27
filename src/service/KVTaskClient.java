package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    URI url;
    HttpClient client;
    HttpRequest request;
    String API_TOKEN;

    public KVTaskClient(URI url) {
        this.url = url;
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder().uri(url).GET().build();
        register();
    }

    private void register() {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            API_TOKEN = response.body();
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
    }

    public void put(String key, String json) {
        URI urlSave = URI.create("http://localhost:8078/save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(urlSave).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
    }

    public String load(String key) {
        URI urlLoad = URI.create("http://localhost:8078/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder().uri(urlLoad).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode();
            }
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
        return null;
    }
}
