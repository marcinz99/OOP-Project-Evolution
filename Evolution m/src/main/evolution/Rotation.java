package evolution;

public enum Rotation {
    PI_0IN4,
    PI_1IN4,
    PI_2IN4,
    PI_3IN4,
    PI_4IN4,
    PI_5IN4,
    PI_6IN4,
    PI_7IN4;

    public String toString(){
        switch(this){
            case PI_0IN4: return "0.00 pi";
            case PI_1IN4: return "0.25 pi";
            case PI_2IN4: return "0.50 pi";
            case PI_3IN4: return "0.75 pi";
            case PI_4IN4: return "1.00 pi";
            case PI_5IN4: return "1.25 pi";
            case PI_6IN4: return "1.50 pi";
            case PI_7IN4: return "1.75 pi";
            default:  return "¯\\_(ツ)_/¯";
        }
    }
    public static Rotation intToRotate(int a){
        switch(a){
            case 0: return PI_0IN4;
            case 1: return PI_1IN4;
            case 2: return PI_2IN4;
            case 3: return PI_3IN4;
            case 4: return PI_4IN4;
            case 5: return PI_5IN4;
            case 6: return PI_6IN4;
            case 7: return PI_7IN4;
            default: throw new IllegalArgumentException("Expected integer from 0 to 7.");
        }
    }
}
