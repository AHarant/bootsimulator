public class Main {

    public static void main(String[] args){

        double waterVelocity = 4.0;

        Coordinate cordA = new Coordinate(54.0,8.0); //10sm süd süd ost von Helgoland
        Coordinate cordB = new Coordinate(54.1,7.55); //Hafeneinfahrt Helgoland (54.1,7.55)

        double distance = MapCalculation.getHaversineDistance(cordA,cordB);
        double direction = MapCalculation.getVincentyDirection(cordA,cordB);

        StreamCalculation neueBerechnung = new StreamCalculation(direction,distance,waterVelocity);


        System.out.println("Distanz zum Ziel:"+distance+" sm Richtung: "+direction+" Grad");

        new UserInterface(neueBerechnung.target,waterVelocity);
    }
}
