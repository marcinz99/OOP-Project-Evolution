package evolution;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

public class FreeSpaces {
    private TreeSet free;
    private static Random generator = new Random();
    private int height;
    private int heightFrom;

    public FreeSpaces(int height, int heightFrom){
        this.height = height;
        this.heightFrom = heightFrom;
        this.free = new TreeSet<>(new Comparator<Vector2d>() {
            @Override
            public int compare(Vector2d v1, Vector2d v2) {
                if(v1.x < v2.x) return -1;
                else if(v1.x > v2.x) return 1;
                else{
                    if(v1.y < v2.y) return -1;
                    else if(v1.y > v2.y) return 1;
                    else return 0;
                }
            }
        });
    }
    public void add(Vector2d vec){
        this.free.add(vec);
    }
    public Vector2d getRandomPosition(){
        if(free.isEmpty()) return null;
        Vector2d first = (Vector2d) free.first();
        Vector2d last = (Vector2d) free.last();
        int randX = generator.nextInt(last.x - first.x + 1) + first.x;
        int randY = generator.nextInt(height) + heightFrom;
        Vector2d seed = new Vector2d(randX, randY);

        Vector2d getRand = (Vector2d) free.higher(seed);
        if(getRand != null) return getRand;
        else return (Vector2d) free.lower(seed);
    }
    public Vector2d getRandomPosition(Vector2d jungleBottomLeft, Vector2d jungleUpperRight){
        if(free.isEmpty()) return null;
        Vector2d first = (Vector2d) free.first();
        Vector2d last = (Vector2d) free.last();
        Vector2d seed;
        do{
            int randX = generator.nextInt(last.x - first.x + 1) + first.x;
            int randY = generator.nextInt(height) + heightFrom;
            seed = new Vector2d(randX, randY);
        }while(seed.follows(jungleBottomLeft) && seed.precedes(jungleUpperRight));

        Vector2d getRand = (Vector2d) free.higher(seed);
        if(getRand != null) return getRand;
        else return (Vector2d) free.lower(seed);
    }
    public boolean isFree(Vector2d vec){
        return !free.contains(vec);
    }
    public void delete(Vector2d vec){
        this.free.remove(vec);
    }
}
