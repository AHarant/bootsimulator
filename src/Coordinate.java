public class Coordinate {
    double lat;
    double lon;

    Coordinate(double lat, double lon){
        setLat(lat);
        setLon(lon);
    }

    void setLat(double lat){
        this.lat = coordinateToDecimal(lat);
    }

    double getLat(){
        return this.lat;
    }

    void setLon(double lon){
        this.lon = coordinateToDecimal(lon);
    }

    static double coordinateToDecimal(double coord){
        double degree = (int)coord;
        double minute = (Math.round((coord - degree)*100))/60.0;

        return degree + minute;
    }
}
