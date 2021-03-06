package evolution;
import com.sun.security.jgss.GSSUtil;

import java.util.Random;

public class Genome {
    private Integer[] motorChromosome;
    private static Random generator = new Random();
    /** motor gene code:
         0 - 0.00 pi, 1 - 0.25 pi, ... 7 - 1.75 pi
     */
    public Genome(){
        this.motorChromosome = new Integer[32];
        int[] numOfEachMove = new int[8];
        for(int i=0; i<8; i++){
            numOfEachMove[i] = 1;
        }
        for(int i=0; i<24; i++){
            numOfEachMove[generator.nextInt(8)]++;
        }
        for(int i=0, j=0; i<8; i++){
            while(numOfEachMove[i]-- > 0){
                this.motorChromosome[j++] = i;
            }
        }
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Genome:");
        for(Integer gene : motorChromosome){
            str.append(" " + gene.toString() + ",");
        }
        return str.toString();
    }
    public String toStringCondensed(){
        StringBuilder str = new StringBuilder();
        str.append("Genome: ");
        for(Integer gene : motorChromosome){
            str.append(gene.toString());
        }
        return str.toString();
    }
    public Rotation getNextRotation(){
        return Rotation.intToRotate(this.motorChromosome[generator.nextInt(32)]);
    }
    public static Genome mixGenes(Genome parentA, Genome parentB){
        int split_a = generator.nextInt(31);
        int split_b = generator.nextInt(31 - split_a) + split_a + 1;

        int[] numOfEachMove = new int[8];
        for(int i=0; i<8; i++){
            numOfEachMove[i] = 0;
        }
        for(int i=0; i<split_a; i++){
            numOfEachMove[parentA.motorChromosome[i]]++;
        }
        for(int i=split_a; i<split_b; i++){
            numOfEachMove[parentB.motorChromosome[i]]++;
        }
        for(int i=split_b; i<32; i++){
            numOfEachMove[parentA.motorChromosome[i]]++;
        }
        for(int i=0; i<8; i++){
            if(numOfEachMove[i] == 0){
                numOfEachMove[i] = 1;
                int rGene = generator.nextInt(8);
                while(numOfEachMove[rGene] < 2){
                    rGene = generator.nextInt(8);
                }
                numOfEachMove[rGene]--;
            }
        }
        Genome resultGenome = new Genome();
        for(int i=0, j=0; i<8; i++){
            while(numOfEachMove[i]-- > 0){
                resultGenome.motorChromosome[j++] = i;
            }
        }
        return resultGenome;
    }
    public long hashed(){
        int[] numOfEachMove = new int[8];
        for(int i=0; i<8; i++){
            numOfEachMove[i] = 0;
        }
        for(int i=0; i<32; i++){
            numOfEachMove[motorChromosome[i]]++;
        }
        long hashed = 0;
        for(int i=0; i<8; i++){
            hashed *= 25;
            hashed += numOfEachMove[i];
        }
        return hashed;
    }
    public static String hashedToHTML(long hashed){
        if(hashed == 0) return "None";
        int[] numOfEachMove = new int[8];
        for(int i=7; i>=0; i--){
            numOfEachMove[i] = (int) (hashed % 25);
            hashed /= 25;
        }
        int[] restoredGenome = new int[32];
        for(int i=0, j=0; i<8; i++){
            while(numOfEachMove[i]-- > 0){
                restoredGenome[j++] = i;
            }
        }
        StringBuilder str = new StringBuilder();
        str.append("<html>");
        for(int i=0; i<4; i++){
            for(int j=0; j<8; j++){
                str.append(String.format("%d ", restoredGenome[i*8+j]));
            } str.append("<br/>");
        } str.append("</html>");

        return str.toString();
    }
    public int[] getGenePool(){
        int[] genePool = new int[8];
        for(int i=0; i<8; i++) genePool[i] = 0;
        for(int i=0; i<32; i++){
            genePool[motorChromosome[i]]++;
        }
        return genePool;
    }
}