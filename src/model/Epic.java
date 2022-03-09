package model;

import service.Status;

public class Epic extends AbstractTask {

    public Epic(String name, Status status) {
        super(name, status);
    }
}
