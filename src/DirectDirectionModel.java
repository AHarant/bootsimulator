public class DirectDirectionModel {

     double lastX;
     double lastY;
     double nextX;
     double nextY;

    DirectDirectionModel(double lastX, double lastY, double nextX, double nextY){
        this.lastX = lastX;
        this.lastY = lastY;

        this.nextX = nextX;
        this.nextY = nextY;

    }

    DirectDirectionModel convertToPrint(DirectDirectionModel item, int factor){
        item = new DirectDirectionModel(item.lastX*factor,item.lastY*factor,item.nextX*factor,item.nextY*factor);
        return item;
    }




}

