package model;

import java.util.Locale;

public class Epic extends AbstractTask {

    public Epic(int id, String name, Status status) {
        super(id, name, status);
        this.setType(Type.EPIC);
        setDescription(getType().toString().toLowerCase(Locale.ROOT) + id);
    }
}
