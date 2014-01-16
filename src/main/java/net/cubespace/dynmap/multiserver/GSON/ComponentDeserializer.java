package net.cubespace.dynmap.multiserver.GSON;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ComponentDeserializer implements JsonDeserializer<Component> {
    private static ArrayList<Component> components = new ArrayList<>();

    public static void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public Component deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        for(Component component : components) {
            if(component.isComponent(jsonObject)) {
                Component component1 = new Gson().fromJson(jsonElement, component.getClass());
                return component1;
            }
        }

        return null;
    }
}
