package evolution;
import java.util.ArrayList;

public class BasicMap extends AbstractWorldMap {
    protected ArrayList<AbstractWorldElement> mapContent = new ArrayList<>();

    public BasicMap(){
        super(new Vector2d(0,0), new Vector2d(9,9));
    }
    public BasicMap(int size){
        super(new Vector2d(0,0), new Vector2d(size-1,size-1));
    }
    public BasicMap(int width, int height){
        super(new Vector2d(0,0), new Vector2d(width-1,height-1));
    }
    @Override
    public void place(AbstractWorldElement a){
        mapContent.add(a);
    }
}
