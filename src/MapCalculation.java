public class MapCalculation {

    static double getHaversineDistance(Coordinate coordA, Coordinate coordB){

        double lonDif = coordB.lon - coordA.lon;
        double lonDifRad = toRad(lonDif);

        double latARad = toRad(coordA.lat);
        double latBRad = toRad(coordB.lat);

        // Breitengrad modifiziert, um auch auf der Südhalbkugel positive Werte zu erhalten
        double latBRadmodified = (Math.PI / 2) -latBRad;
        double latARadmodified = (Math.PI / 2) - latARad;

        double earthRadius = 6371.221;

        // Haversine Formel
        double tmp = Math.acos(
                Math.cos(latBRadmodified) * Math.cos(latARadmodified) + Math.sin(latBRadmodified) * Math.sin(latARadmodified) * Math.cos(lonDifRad)
        );

        // Umrechnung Kilometer in Seemeilen
        double distance = (earthRadius * tmp) * 0.54;
        return distance;
    }


    static double getVincentyDirection(Coordinate coordA, Coordinate coordB){

        double lonDif = coordB.lon - coordA.lon;
        double lonDifRad = toRad(lonDif);

        double latARad = toRad(coordA.lat);
        double latBRad = toRad(coordB.lat);

        double angle = Math.atan2(Math.sin(lonDifRad) * Math.cos(latBRad), Math.cos(latARad) * Math.sin(latBRad) -
                Math.sin(latARad) * Math.cos(latBRad) * Math.cos(lonDifRad));

        angle = toDeg(angle);

        return (angle + 360) % 360;
    }

    static double toRad(double x){
        return (x * Math.PI) / 180;
    }

    static double toDeg(double x){
        return (x * 180) / Math.PI;
    }
}
