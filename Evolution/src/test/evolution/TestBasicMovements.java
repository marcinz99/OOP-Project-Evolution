package evolution;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestBasicMovements {
    private Rotation pi_0in4 = Rotation.PI_0IN4;
    private Rotation pi_1in4 = Rotation.PI_1IN4;
    private Rotation pi_2in4 = Rotation.PI_2IN4;
    private Rotation pi_3in4 = Rotation.PI_3IN4;
    private Rotation pi_4in4 = Rotation.PI_4IN4;
    private Rotation pi_5in4 = Rotation.PI_5IN4;
    private Rotation pi_6in4 = Rotation.PI_6IN4;
    private Rotation pi_7in4 = Rotation.PI_7IN4;

    private MapDirection N = MapDirection.NORTH;
    private MapDirection NE = MapDirection.NORTHEAST;
    private MapDirection E = MapDirection.EAST;
    private MapDirection SE = MapDirection.SOUTHEAST;
    private MapDirection S = MapDirection.SOUTH;
    private MapDirection SW = MapDirection.SOUTHWEST;
    private MapDirection W = MapDirection.WEST;
    private MapDirection NW = MapDirection.NORTHWEST;

    @Test
    public void testRotations(){
        MapDirection dir = N;
        dir = dir.rotate(pi_0in4);
        assertTrue(dir == N);
        dir = dir.rotate(pi_1in4);
        assertTrue(dir == NE);
        dir = dir.rotate(pi_2in4);
        assertTrue(dir == SE);
        dir = dir.rotate(pi_3in4);
        assertTrue(dir == W);
        dir = dir.rotate(pi_4in4);
        assertTrue(dir == E);
        dir = dir.rotate(pi_5in4);
        assertTrue(dir == NW);
        dir = dir.rotate(pi_6in4);
        assertTrue(dir == SW);
        dir = dir.rotate(pi_7in4);
        assertTrue(dir == S);
    }

    @Test
    public void testSomeMoves(){
        BasicMap map = new BasicMap(10);
        Animal narwal = new Animal(map, new Vector2d(4,2), MapDirection.EAST);
        assertTrue(narwal.toString().equals("[(4, 2) - East]"));
        narwal.move(pi_0in4);
        assertTrue(narwal.toString().equals("[(5, 2) - East]"));
        narwal.move(pi_6in4);
        assertTrue(narwal.toString().equals("[(5, 3) - North]"));
        narwal.move(pi_5in4);
        assertTrue(narwal.toString().equals("[(4, 2) - South-west]"));
        narwal.move(pi_7in4);
        assertTrue(narwal.toString().equals("[(4, 1) - South]"));
        narwal.move(pi_2in4);
        assertTrue(narwal.toString().equals("[(3, 1) - West]"));
    }

    @Test
    public void testPassThroughEdge(){
        BasicMap map = new BasicMap(3);
        Animal narwal = new Animal(map, new Vector2d(1,1), MapDirection.EAST);
        assertTrue(narwal.toString().equals("[(1, 1) - East]"));
        narwal.move(pi_0in4);
        assertTrue(narwal.toString().equals("[(2, 1) - East]"));
        narwal.move(pi_0in4);
        assertTrue(narwal.toString().equals("[(0, 1) - East]"));
        narwal.move(pi_0in4);
        assertTrue(narwal.toString().equals("[(1, 1) - East]"));
        narwal.move(pi_6in4);
        assertTrue(narwal.toString().equals("[(1, 2) - North]"));
        narwal.move(pi_0in4);
        assertTrue(narwal.toString().equals("[(1, 0) - North]"));
        narwal.move(pi_5in4);
        assertTrue(narwal.toString().equals("[(0, 2) - South-west]"));
    }

    @Test
    public void testPassThroughCorners(){
        BasicMap map = new BasicMap(3);
        Animal narwal = new Animal(map, new Vector2d(1,1), MapDirection.EAST);
        assertTrue(narwal.toString().equals("[(1, 1) - East]"));
        narwal.move(pi_7in4);
        assertTrue(narwal.toString().equals("[(2, 2) - North-east]"));
        narwal.move(pi_0in4);
        assertTrue(narwal.toString().equals("[(0, 0) - North-east]"));
        narwal.move(pi_6in4);
        assertTrue(narwal.toString().equals("[(2, 1) - North-west]"));
        narwal.move(pi_7in4);
        assertTrue(narwal.toString().equals("[(1, 1) - West]"));
        narwal.move(pi_1in4);
        assertTrue(narwal.toString().equals("[(0, 2) - North-west]"));
        narwal.move(pi_0in4);
        assertTrue(narwal.toString().equals("[(2, 0) - North-west]"));
    }
}
