package evolution;
import java.util.ArrayList;

abstract public class AbstractWorldMap {
    protected Vector2d v_bottom_left;
    protected Vector2d v_upper_right;
    protected int width;
    protected int height;

    public AbstractWorldMap(Vector2d bottom_left, Vector2d upper_right){
        this.v_bottom_left = bottom_left;
        this.v_upper_right = upper_right;
        this.height = upper_right.y - bottom_left.y + 1;
        this.width = upper_right.x - bottom_left.x + 1;
    }

    public void place(AbstractWorldElement a){}

    public Vector2d normalizePosition(Vector2d vec){
        if(v_bottom_left.equals(new Vector2d(0,0))){
            Vector2d res = vec.subtract(v_bottom_left);
            int x = res.x % this.width;
            if(x < 0) x = this.width + x;
            int y = res.y % this.height;
            if(y < 0) y = this.height + y;
            return (new Vector2d(x, y)).add(v_bottom_left);
        }
        else{
            int x = vec.x % this.width;
            if(x < 0) x = this.width + x;
            int y = vec.y % this.height;
            if(y < 0) y = this.height + y;
            return new Vector2d(x, y);
        }
    }
}
