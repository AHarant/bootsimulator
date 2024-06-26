import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CoordinateSystem extends JPanel {

    private StreamTime streamTime;

    private Vector target;

    int factor = 10;

    ArrayList<DirectDirectionModel> directDirectionList;
    ArrayList<DirectDirectionModel> fastedWayList;
    List<List<StreamItem>> streamList;

    CoordinateSystem(Vector target, double waterVelocity, StreamTime streamTime){


        this.target = target;

        this.streamTime = streamTime;


        streamList = StreamDataManager.load();
        directDirectionList = StreamCalculation.calculateWayToTargetWithStream(waterVelocity,target,streamList, factor);
        fastedWayList = StreamCalculation.calculateFastestWay(target,waterVelocity,streamList,15, factor);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;


        int x0 = 300;
        int y0 = 300;

        //Ursprung (0|0) auf nahezu Mitte setzen
        g.translate(300,300);

        //Faktor für Vergrößerung der Darstellung = 10;
        int factorPoints = 10;

        // Achsen zeichnen
        g2.setStroke(new BasicStroke(3));
        g.drawLine(-x0,0,x0,0);
        g.drawLine(0,-y0,0,y0);
        g2.setStroke(new BasicStroke(1));

        //Gitter zeichnen
        g.setColor(Color.lightGray);
        for(int m = 0; m < x0*2;m+=20){
            if(m == 300)continue;
            g.drawLine(-x0,-y0+m,x0,-y0+m);
            g.drawLine(-x0+m,-y0,-x0+m,y0);

        }
        g.setColor(Color.black);

        //Punkte zeichnen + Strömungen für jedes Feld
        g.setColor(Color.lightGray);
        int ksX = -16;
        int ksY = -16;
        for(int z = 10; z < x0*2; z+=20){
            ksX += 1;
            ksY = -16;
            for(int s = 10; s < y0*2; s+=20){
                ksY += 1;
                g.drawOval(-x0+z, -y0+s,5,5);
                drawStream(g,-x0+z,-y0+s,ksX, ksY,streamList,streamTime);
            }
        }

        // Beschriftungen hinzufügen
        g.drawString("X", x0-10, 15);
        g.drawString("Y", 10, -y0+10);


        //Fahrt über Grund Linie zum Ziel
        drawListToLine(fastedWayList,g2,factor,false);

        //Zielpunkt
        g.setColor(Color.black);
        g.fillOval(((int)(target.x * factorPoints))-5,- ((int)(target.y*factorPoints))-5, 10,10);

        // Weg zum Ziel, wenn alle 15 Minuten die Richtung nachjustiert wird
        drawListToLine(directDirectionList,g2,factor,true);


    }


    // Zeichnet die Strömung für ein Feld
    void drawStream(Graphics g,int x, int y, int ksX, int ksY, List<List<StreamItem>> streamList, StreamTime streamTime){
        StreamItem item = StreamDataManager.getStreamItemOfList(streamList,ksX, ksY,streamTime.getStreamTime()*15, factor);
        g.setColor(Color.lightGray);

        g.drawLine(x,y,x+((int)(item.stream.x * factor)),y-((int)(item.stream.y*factor)));
    }

    //Zeichnet die Listenelemente in Form von Linien in das Koordinatensystem ein
    void drawListToLine(ArrayList<DirectDirectionModel> list, Graphics2D g, int factor, boolean color){
        g.setStroke(new BasicStroke(3));
        Color[] colorList = new Color[]{Color.black};
        if(color) colorList = new Color[]{Color.red, Color.green, Color.yellow, Color.black, Color.pink};
        for(int i = 0; i < streamTime.getStreamTime()+1; i++){
            if(streamTime.getStreamTime()+1>list.size()){
                continue;
            }
            g.setColor(colorList[i%colorList.length]);
            DirectDirectionModel item = list.get(i);
            item = item.convertToPrint(item,factor);
            g.drawLine((int)item.lastX, (int)item.lastY,(int)item.nextX, (int)item.nextY);
        }
    }




}
