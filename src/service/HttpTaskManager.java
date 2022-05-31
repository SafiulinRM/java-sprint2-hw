package service;

import java.net.URI;

public class HttpTaskManager extends FileBackedTasksManager {
    public KVTaskClient client;
    private static final String KEY_GREEN = "зеленый";
    private URI uri;
    private String url;

    public HttpTaskManager(String url) {
        super(url);
        this.url = url;
        this.uri = URI.create(url);
        client = new KVTaskClient(this.uri);
    }

    @Override
    public void save() {
        client.put(KEY_GREEN, this);
    }

    @Override
    public String toString() {
        return "HttpTaskManager{" +
                "client=" + client +
                ", uri=" + uri +
                ", url='" + url + '\'' +
                ", prioritizedTasks=" + prioritizedTasks +
                '}';
    }
}
