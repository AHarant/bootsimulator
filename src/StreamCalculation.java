
import java.util.ArrayList;
import java.util.List;

public class StreamCalculation {

    double waterVelocity; // Fahrt durchs Wasser



    double direction; // Kurs über Grund
    double distance;




    Vector target;


    StreamCalculation(double direction, double distance, double waterVelocity){

        this.direction = direction;
        this.distance = distance;
        this.waterVelocity = waterVelocity;


         target = Vector.scalarMultiplication(Vector.degToVec(direction), distance);

    }

    // Vector, der die Fahrt über Grund (direkter Weg zum Ziel) angiebt
    static Vector getFuG(double waterVelocity, Vector stream, Vector target){
        return Vector.calcIntersectionCircleToLine(waterVelocity, stream, target);
    }


    //Routenstrategie: Optimaler Kurs
    //Erstellt eine Liste mit Vektoren, welche den zurückgelegten Weg für jeweils 15 Minuten darstellt
    static ArrayList<DirectDirectionModel> calculateFastestWay(Vector target,double waterVelocity, List<List<StreamItem>> streamList, int timeInMinutes, int factor){
        ArrayList<DirectDirectionModel> list = new ArrayList<>();

        double startX = 0;
        double startY = 0;


        Vector fug = getFuG(waterVelocity, StreamDataManager.getStreamItemOfList(streamList,(int)startX, (int)startY,0, factor).stream,target);
        Vector fractionFug = Vector.scalarMultiplication(fug, (double)timeInMinutes/(double)60);
        list.add(new DirectDirectionModel(startX,startY, fractionFug.x,- fractionFug.y));

        boolean reachedTarget = false;
        for(int i = 15; i<360*3;i=i+15) {
            if (reachedTarget) break;
            startX = startX + fractionFug.x;
            startY = startY - fractionFug.y;

            fug = getFuG(waterVelocity, StreamDataManager.getStreamItemOfList(streamList,(int)startX, (int)startY,i, factor).stream,target);
            fractionFug = Vector.scalarMultiplication(fug, (double)timeInMinutes/(double)60);


            for(int j = 1; j<=15;j++){

                double dividedX = fractionFug.x/15;
                double dividedY = fractionFug.y/15;
                Vector vecDistanceToTarget = new Vector(startX + (dividedX * j), startY - (dividedY * j));

                double distance = Vector.distanceBetweenTwoPoints(vecDistanceToTarget,new Vector(target.x,-target.y));
                double puffer = 0.3; //Puffer als Schwelle wie nahe das Boot am Ziel vorbeifahren muss bis Abbruch
                //Puffer muss erhöht werden, wenn die Geschwindigkeit des Bootes sehr hoch ist, da das Ziel sonst nicht erreicht wird
                if(Vector.lengthOfVector(fug)>6)puffer=0.5;
                if(distance<puffer){
                    System.out.println("Routenstrategie Optimaler Kurs: Ziel sogut wie erreicht (Zeit: "+((list.size())*15+j)+" Minuten, Restdistanz: "+distance+" sm)");
                    reachedTarget = true;
                    list.add(new DirectDirectionModel(startX,startY,startX + dividedX*j,startY - dividedY*j));

                    break;
                }
            }

            if(!reachedTarget)list.add(new DirectDirectionModel(startX, startY, startX + fractionFug.x,startY - fractionFug.y));
        }


        return list;

    }

    //Routenstrategie: Regelmäßig angepasster Kurs
    //Erstellt eine Liste mit Vektoren, welche den zurückgelegten Weg für jeweils 15 Minuten darstellt
    static ArrayList<DirectDirectionModel> calculateWayToTargetWithStream(double waterVelocity, Vector target, List<List<StreamItem>> streamList, int factor){
        ArrayList<DirectDirectionModel> list = new ArrayList<>();


            double startX = 0;
            double startY = 0;

            Vector pointerVec;
            //Erster Punkt
            Vector direkterWeg = Vector.vectorOfTwoPoints(new Vector(0,0),target);

            Vector direkterWegMitGeschwindigkeit = Vector.resizeVec(direkterWeg, waterVelocity);

            Vector wegMitAbdrift = Vector.calculateNewLocationWithStreamAfterMinutes(direkterWegMitGeschwindigkeit,StreamDataManager.getStreamItemOfList(streamList,(int)startX, (int)startY,0, factor).stream,15);

            pointerVec = wegMitAbdrift;

            list.add(new DirectDirectionModel(startX,startY,wegMitAbdrift.x,-wegMitAbdrift.y));

            boolean reachedTarget = false;
            for(int i = 15; i<360*3;i=i+15){
                if(reachedTarget)break;
                startX = startX + wegMitAbdrift.x;
                startY = startY - wegMitAbdrift.y;


                direkterWeg = Vector.vectorOfTwoPoints(pointerVec,target);

                direkterWegMitGeschwindigkeit = Vector.resizeVec(direkterWeg,waterVelocity);

                wegMitAbdrift = Vector.calculateNewLocationWithStreamAfterMinutes(direkterWegMitGeschwindigkeit, StreamDataManager.getStreamItemOfList(streamList,(int)startX, (int)startY,i, factor).stream,15);

                pointerVec = Vector.addition(pointerVec,wegMitAbdrift);


                //Check ob ein Vektor sehr nahe am Ziel vorbei läuft -> Abbruch und anteilges Anrechnen der Zeit
                for(int j = 1; j<=15;j++){

                    double dividedX = wegMitAbdrift.x/15;
                    double dividedY = wegMitAbdrift.y/15;
                    Vector vecDistanceToTarget = new Vector(startX + (dividedX * j), startY - (dividedY * j));

                    double distance = Vector.distanceBetweenTwoPoints(vecDistanceToTarget,new Vector(target.x,-target.y));
                    double puffer = 0.3; //Puffer als Schwelle wie nahe das Boot am Ziel vorbeifahren muss bis Abbruch
                    //Puffer muss erhöht werden, wenn die Geschwindigkeit des Bootes sehr hoch ist, da das Ziel sonst nicht erreicht wird
                    if(Vector.lengthOfVector(Vector.scalarMultiplication(wegMitAbdrift,waterVelocity))>6)puffer=0.5;
                    if(distance<puffer){
                        System.out.println("Routenstrategie Regelmaeszig angepasster Kurs: Ziel sogut wie erreicht (Zeit: "+((list.size())*15+j)+" Minuten, Restdistanz: "+distance+" sm)");
                        reachedTarget = true;
                        list.add(new DirectDirectionModel(startX,startY,startX + dividedX*j,startY - dividedY*j));

                        break;
                    }
                }

                // Nicht mehr ausführen, wenn Ziel bereits erreicht ist
                if(!reachedTarget)list.add(new DirectDirectionModel(startX,startY,startX + wegMitAbdrift.x,startY - wegMitAbdrift.y));


            }



        return list;
    }






}
