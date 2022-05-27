package service;

import com.google.gson.*;
import model.AbstractTask;

import java.lang.reflect.Type;

public class AbstractElementAdapter implements JsonSerializer<AbstractTask>, JsonDeserializer<AbstractTask> {
    @Override
    public JsonElement serialize(AbstractTask src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }

    @Override
    public AbstractTask deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName("model." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}