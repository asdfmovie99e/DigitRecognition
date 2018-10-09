package helper;

 /*
 @author Jens Krueger
 @version 1.0
 */


import java.util.HashMap;

public class MathHelper {
    private static HashMap<Integer, Double> sigmoidMap;


    public static void start(){
        // wird ausgeführt um die HashMaps zu erstellen, welche die Berechnung verschnellern
        sigmoidMap = new HashMap<Integer, Double>();
        for(int i = -600; i <= 600; i += 1){
            sigmoidMap.put(i,  (double) (1 / (1 + Math.pow(Math.E, -(i/100)))));
        }
        int a = 1+1;
    }



    public static double sigmoidApprox(double input){
        //gibt einen ungefähren wert der Sigmoid funktion zurück
        if(input < -5.8) return 0f;
        if(input > 5.8) return 1f;
        return sigmoidMap.get( (int)input * 100);
    }

    public static float identity(float input){
        return input;
    }

}
