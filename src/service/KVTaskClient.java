package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.AbstractTask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private URI url;
    private HttpClient client;
    private String apiTokenClient;

    public KVTaskClient(URI url) {
        this.url = url;
        client = HttpClient.newHttpClient();
        register();
    }

    private void register() {
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            apiTokenClient = response.body();
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
    }

    public void put(String key, HttpTaskManager manager) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        URI urlSave = URI.create("http://localhost:8078/save/" + key + "?apiTokenClient=" + apiTokenClient);
        System.out.println(manager);
        String a = gson.toJson(manager);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(a);
        HttpRequest request = HttpRequest.newBuilder().uri(urlSave).POST(body).build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
    }

    public HttpTaskManager load(String key) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        URI urlLoad = URI.create("http://localhost:8078/load/" + key + "?apiTokenClient=" + apiTokenClient);
        HttpRequest request = HttpRequest.newBuilder().uri(urlLoad).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), HttpTaskManager.class);
            }
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
        return null;
    }
}
