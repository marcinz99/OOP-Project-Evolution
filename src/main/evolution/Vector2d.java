package evolution;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
    public int hashCode(){
        return 17909*this.x + 17911*this.y;
    }
    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }
    public Vector2d upperRight(Vector2d other){
        int a = Math.max(this.x, other.x);
        int b = Math.max(this.y, other.y);
        return new Vector2d(a, b);
    }
    public Vector2d lowerLeft(Vector2d other){
        int a = Math.min(this.x, other.x);
        int b = Math.min(this.y, other.y);
        return new Vector2d(a, b);
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }
}