import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


public class JsonSerializer extends ASerializer {

    @Override
    protected String getString(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();
        jsonElementsMap.put("@type", object.getClass().getName());
        while (clazz != Object.class) {

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                if (field.isAnnotationPresent(JsonElement.class)) {
                    Object o = field.get(object);

                    if (o != null && (AbstractComponent.class.isAssignableFrom(o.getClass())
                            || o.getClass() == GameObject.class
                            || o.getClass() == Vector2.class))
                        jsonElementsMap.put(getKey(field), getString(o));
                    else
                        jsonElementsMap.put(getKey(field), Objects.toString(o));
                }
            }

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(JsonElement.class)) {
                    try {
                        Object o = method.invoke(object);

                        if (o != null && (AbstractComponent.class.isAssignableFrom(o.getClass())
                        || o.getClass() == GameObject.class
                        || o.getClass() == Vector2.class))
                            jsonElementsMap.put(getKey(method), getString(o));
                        else
                            jsonElementsMap.put(getKey(method), Objects.toString(o));
                    }
                    catch(InvocationTargetException e) {
                        //Ignore invocation error, because we only support methods with no argument.
                    }
                }
            }

            if (object.getClass() != TransformComponent.class)
                clazz = clazz.getSuperclass();
            else
                break;
        }

        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(","));

        jsonString = jsonString.replace("\"{", "{").replace("}\"", "}");

        return "{" + jsonString + "}";
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class)
                .key();
        return value.isEmpty() ? field.getName() : value;
    }

    private String getKey(Method method) {
        String value = method.getAnnotation(JsonElement.class)
                .key();
        return value.isEmpty() ? method.getName() : value;
    }
}
