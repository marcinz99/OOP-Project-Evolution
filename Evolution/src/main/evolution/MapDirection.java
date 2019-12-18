package evolution;
import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    static Random generator = new Random();

    public String toString(){
        switch(this){
            case NORTH:     return "North";
            case NORTHEAST: return "North-east";
            case EAST:      return "East";
            case SOUTHEAST: return "South-east";
            case SOUTH:     return "South";
            case SOUTHWEST: return "South-west";
            case WEST:      return "West";
            case NORTHWEST: return "North-west";
            default:        return null;
        }
    }

    public MapDirection rotate(Rotation angle){
        int result = (this.ordinal() + angle.ordinal()) % 8;
        switch(result){
            case 0: return NORTH;
            case 1: return NORTHEAST;
            case 2: return EAST;
            case 3: return SOUTHEAST;
            case 4: return SOUTH;
            case 5: return SOUTHWEST;
            case 6: return WEST;
            case 7: return NORTHWEST;
            default: return null;
        }
    }

    public Vector2d toUnitVector(){
        switch(this){
            case NORTH:     return new Vector2d(0,1);
            case NORTHEAST: return new Vector2d(1,1);
            case EAST:      return new Vector2d(1,0);
            case SOUTHEAST: return new Vector2d(1,-1);
            case SOUTH:     return new Vector2d(0,-1);
            case SOUTHWEST: return new Vector2d(-1,-1);
            case WEST:      return new Vector2d(-1,0);
            case NORTHWEST: return new Vector2d(-1,1);
            default:        return null;
        }
    }

    public static MapDirection getRandomMapDirection(){
        int result = generator.nextInt(8);
        switch(result){
            case 0: return NORTH;
            case 1: return NORTHEAST;
            case 2: return EAST;
            case 3: return SOUTHEAST;
            case 4: return SOUTH;
            case 5: return SOUTHWEST;
            case 6: return WEST;
            case 7: return NORTHWEST;
            default: return null;
        }
    }
}
