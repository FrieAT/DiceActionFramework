package DAF.Math;

import DAF.Serializer.JsonElement;
import DAF.Serializer.Serializable;

@Serializable
public class Vector2 {
    @JsonElement
    public Double x;
    @JsonElement
    public Double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public static Vector2 Zero = new Vector2(0.0, 0.0);

    public static Vector2 One = new Vector2(1.0, 1.0);

    public static Vector2 Add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 Mul(Vector2 a, double b) {
        return new Vector2((a.x * b), (a.y * b));
    }
}