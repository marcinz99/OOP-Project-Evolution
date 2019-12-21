package evolution;

public class Main {
    public static void main(String args[]){
        try{
            MainWindow.showMainWindow();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.getStackTrace();
        }
    }
}
