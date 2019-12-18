package evolution;

import java.io.*;
import org.json.JSONException;
import org.json.JSONObject;

public class Parameters {
    public int width;
    public int height;
    public int startEnergy;
    public int moveEnergy;
    public int plantEnergy;
    public double jungleRatio;
    public int animals;

    public Parameters(){
        try(FileReader reader = new FileReader("src\\main\\parameters.json")) {
            BufferedReader buffer = new BufferedReader(reader);
            String line = null;
            StringBuilder str = new StringBuilder();
            String ls = System.getProperty("line.separator");

            while((line = buffer.readLine()) != null) {
                str.append(line);
                str.append(ls);
            }
            String jsonStr = str.toString();
            JSONObject jsonObject = new JSONObject(jsonStr);

            this.width = (int) jsonObject.get("width");
            this.height = (int) jsonObject.get("height");
            this.startEnergy = (int) jsonObject.get("startEnergy");
            this.moveEnergy = (int) jsonObject.get("moveEnergy");
            this.plantEnergy = (int) jsonObject.get("plantEnergy");
            this.jungleRatio = (double) jsonObject.get("jungleRatio");
            this.animals = (int) jsonObject.get("animals");

        } catch (FileNotFoundException e) {
            System.out.println("File \"parameters.json\" not found. What a pity! It's an important file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cannot open \"parameters.json\". What a pity! It's an important file.");
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Cannot read \"parameters.json\" properly.");
            e.printStackTrace();
        }
    }
}
