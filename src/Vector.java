

public class Vector {
    double x;
    double y;

    Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    static double realDirectionToGeometrieDirection(double richtung){
        // Reale Richtung entspricht y - Achse 0 Grad, geomtrie Richtung entspricht x - Achse 0 Grad
        return (360-richtung+90)%360;
    }

    static Vector degToVec(double degree){
        degree = realDirectionToGeometrieDirection(degree);
        return new Vector(Math.cos(Math.toRadians(degree)), Math.sin(Math.toRadians(degree)));
    }

    static Vector addition(Vector a, Vector b){
        return new Vector(a.x + b.x, a.y + b.y);
    }

    static Vector scalarMultiplication(Vector a, double factor){
        return new Vector(factor*a.x, factor*a.y);
    }

    static double scalarProduct(Vector a, Vector b){
        return a.x*b.x+a.y*b.y;
    }

    static double lengthOfVector(Vector a){
        return Math.sqrt(Math.abs(scalarProduct(a,a)));
    }

    static double distanceBetweenTwoVectors(Vector a, Vector b){
        Vector distanceVec = vectorOfTwoPoints(a,b);
        return lengthOfVector(distanceVec);
    }

    static double distanceBetweenTwoPoints(Vector a, Vector b){
        double distance = Math.sqrt(Math.pow((b.x - a.x),2) + Math.pow((b.y - a.y),2));
        return distance;
    }

    static double angleOfTwoVectors(Vector a, Vector b){
        double tmpSP = scalarProduct(a,b);
        double productOfLengthAandB = lengthOfVector(a) * lengthOfVector(b);
        double tmp = Math.acos(tmpSP/productOfLengthAandB);
        return Math.toDegrees(tmp);
    }


    static Vector calcIntersectionCircleToLine(double radiusCircle, Vector midpointCircle, Vector gradientLine){

        //Steigung
        double gradient = gradientLine.y/gradientLine.x;
        double a = 1 + gradient * gradient;
        double b = 2 * (gradient * (0 - midpointCircle.y) - midpointCircle.x);

        double c = midpointCircle.x * midpointCircle.x + (0 - midpointCircle.y) * (0 - midpointCircle.y) - radiusCircle * radiusCircle;

        double diskriminante = b * b - 4 * a * c;




        if (diskriminante < 0) {
            // Keine Schnittpunkte
            return null;
        } else if (diskriminante == 0) {
            // Ein Schnittpunkt (Tangente)
            double x = (-b) / (2 * a);
            double y = gradient * x + 0;
            return new Vector(x,y);
        } else {
            // Zwei Schnittpunkte
            double x1 = (-b + Math.sqrt(diskriminante)) / (2 * a);
            double y1 = gradient * x1 + 0;
            double x2 = (-b - Math.sqrt(diskriminante)) / (2 * a);
            double y2 = gradient * x2 + 0;


            //Nur der Schnittpunkt der am nähesten am Ziel ist soll zurückgegeben werden
            Vector firstIntersection = new Vector(x1,y1);
            Vector secondIntersection = new Vector(x2,y2);

            double distanceFirstToFinish = distanceBetweenTwoVectors(firstIntersection, gradientLine);
            double distanceSecondToFinish = distanceBetweenTwoVectors(secondIntersection, gradientLine);

            if(distanceFirstToFinish>distanceSecondToFinish){
                return secondIntersection;
            } else {
                return firstIntersection;
            }

        }
    }

    static Vector convertToMainVector(Vector vec){
        double lengthOfVec = lengthOfVector(vec);
        return new Vector(vec.x/lengthOfVec, vec.y/lengthOfVec);
    }

    static Vector resizeVec(Vector vec, double newSize){
        Vector tmp = convertToMainVector(vec);
        tmp = scalarMultiplication(tmp, newSize);
        return tmp;
    }

    static Vector vectorOfTwoPoints(Vector a, Vector b){
        Vector vec = new Vector(b.x-a.x,b.y-a.y);
        return vec;
    }

    static double vecToDeg(Vector v){
        double angle = Math.atan2(v.x,v.y);
        angle = Math.toDegrees(angle);
        if(angle<0)angle+=360;
        return angle;
    }

    static Vector calculateNewLocationWithStreamAfterMinutes(Vector kdW, Vector stream, int timeInMinutes){

        //bekannt Kurs durchs Wasser, Fahrt durchs Wasser
        // Strömung addieren
        //Ergebnis Kurs über Grund/Fahrt über Grund
        Vector fractionKdW = scalarMultiplication(kdW, (double)timeInMinutes/(double)60);
        Vector fractionStream = scalarMultiplication(stream, (double)timeInMinutes/(double)60);
        Vector location = addition(fractionKdW,fractionStream);
        return location;
    }

    static Vector degAndLengthToVec(double deg, double length){
        Vector tempVec = degToVec(deg);
        tempVec = resizeVec(tempVec, length);
        return tempVec;
    }
}
