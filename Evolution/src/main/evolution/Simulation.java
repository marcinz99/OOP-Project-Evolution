package evolution;
import java.util.ArrayList;


public class Simulation {
    private FinalMap map;
    private Parameters param;
    private Window wnd;
    private boolean isRunning = false;
    private int day_no = 1;
    private Changes latestPositions = new Changes();

    public Simulation(Parameters param, Window wnd){
        this.param = param;
        this.wnd = wnd;
        this.map = new FinalMap(param, this);
        this.map.initiateChangelist(latestPositions);
        Window.updateNumberOfAnimals(wnd, map.getNumberOfAnimals());
        Window.updateNumberOfPlants(wnd, map.getNumberOfPlants());
    }
    public void procedeNDays(int n){
        if(isRunning) return;
        for(int i=0; i<n; i++){
            procedeOneDay();
            day_no++;
        }
        Window.updateDayNo(wnd, day_no);
        map.restoreNumberOfPlants();
        Window.updateNumberOfAnimals(wnd, map.getNumberOfAnimals());
        Window.updateNumberOfPlants(wnd, map.getNumberOfPlants());
        Window.defaultStatView(wnd);
    }
    public void procedeOneDay(){
        latestPositions.clear();
        map.moveEveryAnimal(latestPositions);
        handleEvents();
        map.spawnPlants();
    }
    public void animalAppearedOn(Vector2d vec){
        Window.animalAppearedOn(wnd, vec.x, vec.y);
    }
    public void plantAppearedOn(Vector2d vec){
        Window.plantAppearedOn(wnd, vec.x, vec.y);
    }
    public void somethingDisappeared(Vector2d vec){
        Window.somethingDisappeared(wnd, vec.x, vec.y);
    }
    public String getContentHere(int x, int y){
        StringBuilder str = new StringBuilder();
        str.append("<html>");
        str.append(String.format("Position: [%d, %d]", x, y));
        ArrayList<Animal> animals = latestPositions.getAnimalsAt(new Vector2d(x, y));
        if(animals != null){
            for(Animal animal : animals){
                str.append("<br/>");
                str.append(animal.toStringVerbose());
            }
        }
        else{
            if(map.isPlantHere(x, y)){
                str.append("<br/>Plant grows here");
            }
        }
        str.append("</html>");
        return str.toString();
    }
    private void handleEvents(){
        for(Vector2d pos : latestPositions.getPositions()){
            ArrayList<Animal> animals = latestPositions.getAnimalsAt(pos);
            int numOfAnims = animals.size();
            for(Animal animal : animals){
                if(animal.getStamina() <= 0){
                    map.getRidOfCorpse(animal);
                    numOfAnims--;
                }
            }
            if(numOfAnims == 0){
                wnd.somethingDisappeared(wnd, pos.x, pos.y);
                map.freeLocation(pos);
            }
        }
        latestPositions.clear();
        map.initiateChangelist(latestPositions);
        ArrayList<Animal> futureParents = new ArrayList<>();
        for(Vector2d pos : latestPositions.getPositions()){
            map.occupyLocation(pos);
            animalAppearedOn(pos);
            ArrayList<Animal> animals = latestPositions.getAnimalsAt(pos);
            if(map.isPlantHere(pos.x, pos.y)){
                map.getRidOfPlant(pos.x, pos.y);
                ArrayList<Animal> theStrongest = new ArrayList<>();
                int maxStamina = animals.get(0).getStamina();
                for(Animal animal : animals){
                    if(animal.getStamina() == maxStamina){
                        theStrongest.add(animal);
                    } else break;
                }
                int energyBonusPerAnimal = param.plantEnergy / theStrongest.size();
                for(Animal animal : theStrongest){
                    animal.eatPlant(energyBonusPerAnimal);
                }
            }
            else{
                if(animals.size() >= 2){
                    Animal parent1 = animals.get(0);
                    Animal parent2 = animals.get(1);
                    if(parent1.getStamina() > map.getMinReproductiveStamina() &&
                       parent2.getStamina() > map.getMinReproductiveStamina()){
                        futureParents.add(parent1);
                        futureParents.add(parent2);
                    }
                }
            }
        }
        for(int i=0; i<futureParents.size(); i+=2){
            map.giveBirthToNewAnimal(latestPositions, futureParents.get(i), futureParents.get(i+1));
        }
    }
}
