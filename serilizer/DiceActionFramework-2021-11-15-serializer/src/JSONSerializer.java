
import java.beans.Introspector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class JSONSerializer implements ISerializer {
	 
    private String JSON;

    @Override
    public String serialize(AGraphic g) {
        StringBuilder sb = new StringBuilder("{\n");
        if (g instanceof PictureGraphic) {
            // @type
            sb.append("\t\"@type\": ").append(getStringIfNull(g.getClass().getName())).append(",\n");
            // parent
            sb.append("\t\"parent\": ").append(getStringIfNull(g.owner.getName())).append(",\n");
            // gameObject
            sb.append("\t\"gameObject\": {\n");
            sb.append("\t\t\"name\": ").append(getStringIfNull(g.getGameObject().getName())).append(",\n");
            sb.append("\t\t\"id\": ").append(getStringIfNull(g.getGameObject().getId())).append("\n");
            sb.append("\t},\n");
            // transform
            sb.append("\t\"transform\": {\n");
            sb.append("\t\t\"@type\": ").append(getStringIfNull(g.getTransform().getClass().getName())).append(",\n");
            sb.append("\t\t\"position\": {\n");
            sb.append("\t\t\t\"@type\": ").append(getStringIfNull(g.getTransform().getPosition().getClass().getName())).append(",\n");
            sb.append("\t\t\t\"x\": ").append(g.getTransform().getPosition().x).append(",\n");
            sb.append("\t\t\t\"y\": ").append(g.getTransform().getPosition().y).append("\n");
            sb.append("\t\t},\n");
            sb.append("\t\t\"scale\": {\n");
            sb.append("\t\t\t\"@type\": ").append(getStringIfNull(g.getTransform().getScale().getClass().getName())).append(",\n");
            sb.append("\t\t\t\"x\": ").append(g.getTransform().getScale().x).append(",\n");
            sb.append("\t\t\t\"y\": ").append(g.getTransform().getScale().y).append("\n");
            sb.append("\t\t},\n");
            // TODO: check how to get zRotation
            sb.append("\t\t\"zRotation\": ").append(0).append("\n");
            sb.append("\t},\n");
            // picturePath
            sb.append("\t\"picturePath\": ").append(getStringIfNull(((PictureGraphic) g).getPicturePath())).append(",\n");
            // width
            sb.append("\t\"width\": ").append(((PictureGraphic) g).getWidth()).append(",\n");
            // height
            sb.append("\t\"height\": ").append(((PictureGraphic) g).getHeight()).append(",\n");
            sb.append("\t\"renderingLayer\": ").append(((PictureGraphic) g).getRenderingLayer()).append("\n");
            sb.append("}");
        }


        JSON = sb.toString();
        return JSON;
    }

    public String serialize2 (AGraphic g) {
        StringBuilder sb = new StringBuilder();
        for (Method m : g.getClass().getMethods()) {
            if (m.getName().startsWith("get")) {

                //System.out.println(c.getName().toLowerCase().substring(3));
                try {
                    if (m.invoke(g) instanceof Integer || m.invoke(g) instanceof Double) {
                        sb.append("\"").append(m.getName().toLowerCase().substring(3)).append("\": ");
                        sb.append(m.invoke(g)).append("\n");
                    }
                    else if (m.invoke(g) instanceof Class) {
                        sb.append("\"@type\": ").append(((Class<?>) m.invoke(g)).getName());
                    } else if (m.invoke(g) instanceof GameObject) {
                        sb.append("\"gameObject\"\n");
                        sb.append("\t\"name\": ").append(getStringIfNull(((GameObject) m.invoke(g)).getName())).append("\n");
                        sb.append("\t\"id\": ").append(getStringIfNull(((GameObject) m.invoke(g)).getId())).append("\n");
                    }

                    // TODO: find a way to handle other Object types
                    else if (m.invoke(g) instanceof TransformComponent && m.invoke(g) != null) {
                        sb.append("\"transform\"\n");
                        sb.append("\t\"@type\": ").append(getStringIfNull(m.invoke(g).getClass().getName())).append("\n");
                        sb.append("\t\"position\"\n");
                        sb.append("\t\t\"@type\": ");
                    }
                    /*
                    else if (m.invoke(g) instanceof AbstractComponent) {
                        sb.append("ABSTRACT COMPONENT: ").append(m.invoke(g)).append("\n");
                        sb.append(serializeAbstractComponent((AbstractComponent) m.invoke(g)));
                        //sb.append("ABSTRACT COMPONENT: ").append(m.invoke(g)).append("\n");
                        //sb.append("\t").append(m.invoke(g).getClass()).append("\n");
                    }*/
                    else {
                        sb.append("\"").append(m.getName().toLowerCase().substring(3)).append("\": ");
                        sb.append(getStringIfNull(m.invoke(g))).append("\n");
                        //System.out.println(c.invoke(g));
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }

        return sb.toString();
    }

    private String serializeAbstractComponent(AbstractComponent c) {
        StringBuilder sb = new StringBuilder();

        for (Method m : c.getClass().getMethods()) {

            try {
                if (m.getName().startsWith("get")
                        && !m.getName().startsWith("getClass")
                        && !m.getName().startsWith("getGameObject")) {
                    sb.append("\t\"").append(m.getName()).append("\": ").append(m.invoke(c)).append("\n");
                }

            } catch (IllegalAccessException e) {
                    e.printStackTrace();
            } catch (InvocationTargetException e) {
                    e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private String getStringIfNull (Object o) {
        if (o == null)
            return "null";
        return "\"" + o + "\"";
    }
}
