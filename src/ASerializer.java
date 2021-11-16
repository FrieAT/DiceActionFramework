import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public abstract class ASerializer {
    public String serialize (Object object) throws SerializationException {
        try {
            checkIfSerializable(object);
            initializeObject(object);
            return getString(object);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage());
        }
    }

    private void checkIfSerializable(Object object) {
        if (Objects.isNull(object)) {
            throw new SerializationException("Can't serialize a null object");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(Serializable.class)) {
            throw new SerializationException("The class " + clazz.getSimpleName() + " is not annotated with Serializable");
        }
    }

    private void initializeObject(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    protected String getString (Object object) throws IllegalAccessException {
        throw new IllegalAccessException("Use custom serializer method.");
    }
}
