package evolution;
import java.util.ArrayList;


public class Simulation {
    private FinalMap map;
    private Parameters param;
    private Window wnd;
    private boolean isRunning = false;
    private int day_no = 1;
    private int daysByOneStep = 1;
    private int simDelay = 150;
    private boolean isTrackingOn = false;
    private Changes latestPositions = new Changes();

    public Simulation(Parameters param, Window wnd){
        this.param = param;
        this.wnd = wnd;
        this.map = new FinalMap(param, this);
        this.map.initiateChangelist(latestPositions);
        Window.updateNumberOfAnimals(wnd, map.getNumberOfAnimals());
        Window.updateNumberOfPlants(wnd, map.getNumberOfPlants());
        Window.updateAvgStamina(wnd, map.getAverageStamina(), map.getMinReproductiveStamina());
        Window.updateAverageFertility(wnd, map.getAverageFertility());
        Window.updateMostCommonGene(wnd, map.getMostCommonGene());
        Window.updateMostCommonGenome(wnd, Genome.hashedToHTML(map.getMostCommonGenome()));
    }
    public void startStopSimulation(){
        isRunning = !isRunning;
        if(isRunning){
            Thread runTheSim = new Thread(this::playSimulation);
            runTheSim.start();
        }
    }
    public void playSimulation(){
        wait(80);
        while(isRunning){
            procedeNDays();
            wait(simDelay);
            wnd.setVisible(true);
        }
    }
    public void procedeNDays(){
        for(int i=0; i<daysByOneStep; i++){
            procedeOneDay();
            day_no++;
        }
        Window.updateDayNo(wnd, day_no);
        map.restoreNumberOfPlants();
        Window.updateNumberOfAnimals(wnd, map.getNumberOfAnimals());
        Window.updateNumberOfPlants(wnd, map.getNumberOfPlants());
        Window.updateAvgStamina(wnd, map.getAverageStamina(), map.getMinReproductiveStamina());
        Window.updateAverageFertility(wnd, map.getAverageFertility());
        Window.updateMostCommonGene(wnd, map.getMostCommonGene());
        Window.updateMostCommonGenome(wnd, Genome.hashedToHTML(map.getMostCommonGenome()));
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
    public void markDominants(){
        map.markDominants();
    }
    public void markAsDominant(Vector2d vec){
        Window.markAsDominant(wnd, vec.x, vec.y);
    }
    public void setAnimationRate(int n){
        daysByOneStep = n;
    }
    public boolean isRunning() {
        return isRunning;
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
    public Animal getStrongest(Vector2d vec){
        ArrayList<Animal> animals = latestPositions.getAnimalsAt(vec);
        if(animals != null) return animals.get(0);
        else return null;
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

            Animal strongest = animals.get(0);
            if(strongest.getStamina() < map.getMinReproductiveStamina()/4){
                Window.markAsHungry(wnd, strongest.position.x, strongest.position.y);
            }
            if(isTrackingOn){
                if(strongest.getTrackingNum() == 1){
                    Window.markAsTracked(wnd, strongest.position.x, strongest.position.y);
                }
                if(strongest.getTrackingNum() == 2){
                    Window.markAsDescendant(wnd, strongest.position.x, strongest.position.y);
                }
            }
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
            futureParents.get(i).justBecameParent();
            futureParents.get(i+1).justBecameParent();
        }
    }
    private void wait(int miliseconds){
        try{
            Thread.sleep(miliseconds);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    public int getPlantEnergy(){
        return param.plantEnergy;
    }
    public void changePlantEnergy(int newPlantEnergy){
        param.plantEnergy = newPlantEnergy;
    }
    public int getSimDelay(){
        return simDelay;
    }
    public void changeSimDelay(int newSimDelay){
        simDelay = newSimDelay;
    }
    public void turnTrackingOn(){
        isTrackingOn = true;
    }
}
