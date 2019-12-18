package evolution;

public class World {
    public static void main(String args[]){
        try{
            Window.runTheWindowSimulation(new Parameters());
            /*Changes ch = new Changes();
            BasicMap map = new BasicMap();
            ch.addChange(new Animal(map, new Vector2d(0,1)));
            ch.addChange(new Animal(map, new Vector2d(0,1)));
            ch.addChange(new Animal(map, new Vector2d(1,1)));
            for(Animal a : ch.getAnimalsAt(new Vector2d(0,1))){
                System.out.println(a);
            }
            for(Vector2d ves : ch.getPositions()){
                System.out.println(ves);
            }*/
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.getStackTrace();
        }
    }
}
