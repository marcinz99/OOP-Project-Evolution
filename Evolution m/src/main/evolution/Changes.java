package evolution;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Changes {
    private HashMap<Vector2d, ArrayList<Animal>> content = new HashMap<>();
    public Changes(){}
    public void addChange(Animal animal){
        if(content.containsKey(animal.position)){
            ArrayList<Animal> animalsHere = content.get(animal.position);
            int i = 0;
            while(i < animalsHere.size() && animalsHere.get(i).getStamina() > animal.getStamina()) i++;
            animalsHere.add(i, animal);
        }
        else{
            ArrayList<Animal> animalsHere = new ArrayList<>();
            animalsHere.add(animal);
            content.put(animal.position, animalsHere);
        }
    }
    public Set<Vector2d> getPositions(){
        return content.keySet();
    }
    public ArrayList<Animal> getAnimalsAt(Vector2d pos){
        return content.get(pos);
    }
    public void clear(){
        content.clear();
    }
}
