package evolution;
import java.util.LinkedList;
import java.util.Random;

public class FinalMap extends AbstractWorldMap {
    private Simulation sim;
    private LinkedList<Animal> animals = new LinkedList<>();
    private Boolean[][] plants;
    private Vector2d jungleBottomLeft;
    private Vector2d jungleUpperRight;
    private FreeSpaces freeSpacesOutOfJungle;
    private FreeSpaces freeSpacesInJungle;
    private double jungleRatioSquared;
    private int startingStamina;
    private int moveCost;
    private int numberOfPlants = 0;
    private int nextId = -1;
    private static Random generator = new Random();

    public FinalMap(Parameters param, Simulation sim){
        super(new Vector2d(0,0), new Vector2d(param.width-1,param.height-1));
        this.sim = sim;
        plants = new Boolean[this.width][this.height];
        for(int i=0; i<this.width; i++){
            for(int j=0; j<this.height; j++){
                plants[i][j] = false;
            }
        }
        int halfJungleWidth = (int) (param.width * param.jungleRatio) / 2;
        int halfJungleHeight = (int) (param.height * param.jungleRatio) / 2;
        this.jungleBottomLeft = new Vector2d(this.width/2 - halfJungleWidth, this.height/2 - halfJungleHeight);
        this.jungleUpperRight = new Vector2d(this.width/2 + halfJungleWidth, this.height/2 + halfJungleHeight);

        freeSpacesOutOfJungle = new FreeSpaces(this.height, 0);
        freeSpacesInJungle = new FreeSpaces(jungleUpperRight.y - jungleBottomLeft.y + 1, jungleBottomLeft.y);
        this.jungleRatioSquared = param.jungleRatio * param.jungleRatio;

        for(int i=0; i<this.width; i++){
            for(int j=0; j<this.height; j++){
                Vector2d here = new Vector2d(i, j);
                if(here.follows(this.jungleBottomLeft) && here.precedes(this.jungleUpperRight)){
                    freeSpacesInJungle.add(here);
                } else freeSpacesOutOfJungle.add(here);
            }
        }

        this.startingStamina = param.startEnergy;
        this.moveCost = param.moveEnergy;
        for(int i=0; i<param.animals; i++){
            spawnAnimalWherever();
        }
        spawnPlants();
    }
    private void spawnAnimalWherever(){
        if(generator.nextDouble() < jungleRatioSquared){
            Vector2d addAt = freeSpacesInJungle.getRandomPosition();
            if(addAt != null){
                freeSpacesInJungle.delete(addAt);
                animals.add(new Animal(this, addAt, startingStamina));
                sim.animalAppearedOn(addAt);
            }
        }
        else{
            Vector2d addAt = freeSpacesOutOfJungle.getRandomPosition();
            if(addAt != null){
                freeSpacesOutOfJungle.delete(addAt);
                animals.add(new Animal(this, addAt, startingStamina));
                sim.animalAppearedOn(addAt);
            }
        }
    }
    public void spawnPlants(){
        Vector2d addAt = freeSpacesInJungle.getRandomPosition();
        if(addAt != null){
            freeSpacesInJungle.delete(addAt);
            plants[addAt.x][addAt.y] = true;
            sim.plantAppearedOn(addAt);
            numberOfPlants++;
        }
        addAt = freeSpacesOutOfJungle.getRandomPosition(jungleBottomLeft, jungleUpperRight);
        if(addAt != null){
            freeSpacesOutOfJungle.delete(addAt);
            plants[addAt.x][addAt.y] = true;
            sim.plantAppearedOn(addAt);
            numberOfPlants++;
        }
    }
    public void moveEveryAnimal(Changes changes){
        for(Animal animal : animals){
            animal.move();
            changes.addChange(animal);
        }
    }
    public void initiateChangelist(Changes changes){
        for(Animal animal : animals){
            changes.addChange(animal);
        }
    }
    public void positionChanged(Vector2d from, Vector2d to){
        if(plants[to.x][to.y] == true){
            if(to.follows(jungleBottomLeft) && to.precedes(jungleUpperRight)){
                freeSpacesInJungle.add(to);
            } else freeSpacesOutOfJungle.add(to);
            //plants[to.x][to.y] = false;
            numberOfPlants--;
        }
        if(from.follows(jungleBottomLeft) && from.precedes(jungleUpperRight)){
            freeSpacesInJungle.add(from);
        } else freeSpacesOutOfJungle.add(from);
        sim.somethingDisappeared(from);

        if(to.follows(jungleBottomLeft) && to.precedes(jungleUpperRight)){
            freeSpacesInJungle.delete(to);
        } else freeSpacesOutOfJungle.delete(to);
        sim.animalAppearedOn(to);
    }
    public int getNumberOfAnimals(){
        return animals.size();
    }
    public int getNumberOfPlants(){
        return numberOfPlants;
    }
    public void restoreNumberOfPlants(){
        numberOfPlants = 0;
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                if(plants[i][j]) numberOfPlants++;
            }
        }
    }
    public int getNextId(){
        nextId++;
        return nextId;
    }
    public boolean isPlantHere(int x, int y){
        return plants[x][y];
    }
    public void getRidOfPlant(int x, int y){
        plants[x][y] = false;
    }
    public int getMinReproductiveStamina(){
        return startingStamina / 2;
    }
    public int getMoveCost(){
        return moveCost;
    }
    private Vector2d getPositionOfBirth(int x, int y){
        int[][] moves = {{1,1},{1,0},{1,-1},{0,1},{0,-1},{-1,1},{-1,0},{-1,-1}};
        Vector2d vec;
        for(int i=0; i<8; i++){
            vec = normalizePosition(new Vector2d(x + moves[i][0], y + moves[i][1]));
            if(freeSpacesInJungle.isFree(vec)) return vec;
            if(freeSpacesOutOfJungle.isFree(vec)) return vec;
        }
        int i = generator.nextInt(8);
        return normalizePosition(new Vector2d(x + moves[i][0], y + moves[i][1]));
    }
    public void giveBirthToNewAnimal(Changes changes, Animal parent1, Animal parent2){
        Vector2d birthPos = getPositionOfBirth(parent1.position.x, parent1.position.y);
        int p1 = parent1.getStamina()/4;
        int p2 = parent2.getStamina()/4;
        parent1.obtainDamage(p1);
        parent2.obtainDamage(p2);
        int newbornStamina = p1 + p2;
        Genome newbornGenes = Genome.mixGenes(parent1.getGenes(), parent2.getGenes());
        Animal animal = new Animal(this, birthPos, newbornStamina, newbornGenes);
        animals.add(animal);
        if(birthPos.follows(jungleBottomLeft) && birthPos.precedes(jungleUpperRight)){
            freeSpacesInJungle.delete(birthPos);
        } freeSpacesOutOfJungle.delete(birthPos);
        changes.addChange(animal);
        sim.animalAppearedOn(birthPos);
    }
    public void getRidOfCorpse(Animal animal){
        animals.remove(animal);
    }
    public void freeLocation(Vector2d pos){
        if(pos.follows(jungleBottomLeft) && pos.precedes(jungleUpperRight)){
            freeSpacesInJungle.add(pos);
        } else freeSpacesOutOfJungle.add(pos);
    }
    public void occupyLocation(Vector2d pos) {
        if (pos.follows(jungleBottomLeft) && pos.precedes(jungleUpperRight)) {
            freeSpacesInJungle.delete(pos);
        } else freeSpacesOutOfJungle.delete(pos);
    }
}
