public class Vector2 {
    public int x;

    public int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public static Vector2 Zero = new Vector2(0, 0);

    public static Vector2 One = new Vector2(1, 1);

    public static Vector2 Add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 Mul(Vector2 a, double b) {
        return new Vector2((int)(a.x * b), (int)(a.y * b));
    }
}