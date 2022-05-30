package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.AbstractTask;
import model.Epic;
import model.Subtask;
import model.Task;
import service.AbstractElementAdapter;
import service.HttpTaskManager;
import service.Managers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    private  final int NEW_PORT = 8080;

     HttpServer httpServer;
    public  HttpTaskManager fileManager;
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    Gson gson;

    public HttpTaskServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(NEW_PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        fileManager = Managers.getDefault();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(AbstractTask.class, new AbstractElementAdapter());
        gson = gsonBuilder.create();
    }

     class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            String response = "";
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String[] splitStrings = path.split("/");
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            inputStream.close();
            if (httpExchange.getRequestURI().getRawQuery() != null) {
                int id = Integer.parseInt(httpExchange.getRequestURI().getRawQuery().split("=")[1]);
                switch (splitStrings[2]) {
                    case "epic":
                        if (method.equals("GET")) {
                            response = gson.toJson(fileManager.getSubtasksOfEpic(id));
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (method.equals("DELETE")) {
                            fileManager.removeEpic(id);
                            response = "Задача удалена!";
                            httpExchange.sendResponseHeaders(202, 0);
                        }
                        break;
                    case "subtask":
                        if (method.equals("GET")) {
                            response = gson.toJson(fileManager.getSubtask(id));
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (method.equals("DELETE")) {
                            fileManager.removeSubtask(id);
                            response = "Задача удалена!";
                            httpExchange.sendResponseHeaders(202, 0);
                        }
                        break;
                    case "task":
                        if (method.equals("GET")) {
                            response = gson.toJson(fileManager.getTask(id));
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (method.equals("DELETE")) {
                            fileManager.removeTask(id);
                            response = "Задача удалена!";
                            httpExchange.sendResponseHeaders(202, 0);
                        }
                        break;
                    default:
                        response = "Задача с таким id отсутствует!";
                        break;
                }
            } else if (splitStrings.length == 2) {
                response = actionWithAllTasks(method);
                if (method.equals("GET")) {
                    httpExchange.sendResponseHeaders(200, 0);
                } else if (method.equals("DELETE")) {
                    httpExchange.sendResponseHeaders(202, 0);
                }
            } else if (splitStrings.length == 3) {
                switch (splitStrings[2]) {
                    case "epic":
                        if (method.equals("GET")) {
                            response = actionWithEpics();
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (method.equals("POST")) {
                            postEpic(body);
                            response = "Задача создалась";
                            httpExchange.sendResponseHeaders(201, 0);
                        }
                        break;
                    case "subtask":
                        if (method.equals("GET")) {
                            response = actionWithSubtasks();
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (method.equals("POST")) {
                            postSubtask(body);
                            response = "Задача создалась";
                            httpExchange.sendResponseHeaders(201, 0);
                        }
                        break;
                    case "task":
                        if (method.equals("GET")) {
                            response = actionWithTasks();
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (method.equals("POST")) {
                            postTask(body);
                            response = "Задача создалась";
                            httpExchange.sendResponseHeaders(201, 0);
                        }
                        break;
                    case "history":
                        if (method.equals("GET")) {
                            response = actionWithHistory();
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                        break;
                    default:
                        System.out.println("неверный URL");
                        break;
                }
            }
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public void startServer() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }

    protected  String actionWithAllTasks(String method) {
        if (method.equals("GET")) {
            return gson.toJson(fileManager.getPrioritizedTasks());
        } else if (method.equals("DELETE")) {
            fileManager.removeAll();
            return gson.toJson(fileManager.getPrioritizedTasks());
        } else {
            return "Другой метод!";
        }
    }

    protected  String actionWithTasks() {
        return gson.toJson(fileManager.getTasks());
    }

    protected String actionWithEpics() {
        return gson.toJson(fileManager.getEpics());
    }

    protected String actionWithSubtasks() {
        return gson.toJson(fileManager.getSubtasks());
    }

    protected String actionWithHistory() {
        return gson.toJson(fileManager.getNewHistory());
    }

    protected void postTask(String body) {
        fileManager.createTask(gson.fromJson(body, Task.class));
    }

    protected void postEpic(String body) {
        fileManager.createEpic(gson.fromJson(body, Epic.class));
    }

    protected void postSubtask(String body) {
        fileManager.createSubtask(gson.fromJson(body, Subtask.class));
    }
}
