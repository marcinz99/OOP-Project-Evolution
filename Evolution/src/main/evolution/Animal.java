package evolution;

public class Animal extends AbstractWorldElement {
    private int id = -1;
    private MapDirection orientation;
    private AbstractWorldMap map;
    private int stamina;
    private Genome genes;

    public Animal(FinalMap map, Vector2d position, int stamina, Genome genes){
        this.map = map;
        this.position = position;
        this.orientation = MapDirection.getRandomMapDirection();
        this.stamina = stamina;
        this.genes = genes;
        this.id = map.getNextId();
    }
    public Animal(FinalMap map, Vector2d position, int stamina) {
        this.map = map;
        this.position = position;
        this.orientation = MapDirection.getRandomMapDirection();
        this.stamina = stamina;
        this.genes = new Genome();
        this.id = map.getNextId();
    }
    public Animal(AbstractWorldMap map, Vector2d position, MapDirection dir){
        this.map = map;
        this.position = position;
        this.orientation = dir;
        this.stamina = 1;
        this.genes = new Genome();
    }
    public Animal(AbstractWorldMap map, Vector2d position){
        this.map = map;
        this.position = position;
        this.orientation = MapDirection.getRandomMapDirection();
        this.stamina = 1;
        this.genes = new Genome();
    }
    public void move(Rotation angle){
        this.orientation = orientation.rotate(angle);
        Vector2d unitVectorOfMove = this.orientation.toUnitVector();
        Vector2d targetMove = this.position.add(unitVectorOfMove);
        if(!(targetMove.follows(this.map.v_bottom_left) && targetMove.precedes(this.map.v_upper_right))){
            targetMove = this.map.normalizePosition(targetMove);
        }
        this.position = targetMove;
    }
    public void move(){
        this.orientation = orientation.rotate(genes.getNextRotation());
        Vector2d unitVectorOfMove = this.orientation.toUnitVector();
        Vector2d targetMove = this.position.add(unitVectorOfMove);
        if(!(targetMove.follows(this.map.v_bottom_left) && targetMove.precedes(this.map.v_upper_right))){
            targetMove = this.map.normalizePosition(targetMove);
        }
        if(map instanceof FinalMap){
            ((FinalMap) map).positionChanged(this.position, targetMove);
            obtainDamage(((FinalMap) map).getMoveCost());
        }
        this.position = targetMove;
    }
    public String toStringVerbose(){
        return String.format("ID(%d), HP(%d/%d), ORI(%s)",
                id, stamina, ((FinalMap) map).getMinReproductiveStamina(),  orientation.toString());
    }
    public String toString(){
        return String.format("[(%d, %d) - %s]", position.x, position.y, orientation.toString());
    }
    public int getStamina() {
        return stamina;
    }
    public Genome getGenes(){
        return genes;
    }
    public void obtainDamage(int dmg){
        stamina -= dmg;
    }
    public void eatPlant(int stm){
        stamina += stm;
    }
}
