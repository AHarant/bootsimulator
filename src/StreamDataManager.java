import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StreamDataManager {


    public static List<List<StreamItem>> load(){

        List<List<StreamItem>> streamItemList = new ArrayList<List<StreamItem>>(13);

        try {


            for (int i = 0; i < 49; i++) {
                BufferedReader br = new BufferedReader(new FileReader(String.format("src/streamData/daten_%s.txt", i+1)));

                StreamTokenizer st = new StreamTokenizer(br);
                st.whitespaceChars(',', ',');

                streamItemList.add(new ArrayList<StreamItem>());


                while (st.nextToken() != StreamTokenizer.TT_EOF) {
                    double x = st.nval;
                    st.nextToken();
                    double y = st.nval;
                    st.nextToken();
                    int time = (int) st.nval;
                    st.nextToken();
                    double streamDirection = st.nval;
                    st.nextToken();
                    double streamVelocity = st.nval;

                    StreamItem item = new StreamItem(x, y, time, Vector.degAndLengthToVec(streamDirection, streamVelocity));
                    streamItemList.get(i).add(item);
                }
                br.close();


            }
        } catch (IOException e){
            System.out.println("Fehler beim einlesen der Stream Daten"+e);
        }
        return streamItemList;
    }

    static StreamItem getStreamItemOfList(List<List<StreamItem>> streamList, double x, double y, int time, int factor){
        int fieldSize = 20;
        int roundX = (int)Math.floor(x*factor/fieldSize);
        int roundY = (int)Math.floor(y*factor/fieldSize);


        if(roundX >= 0){
            roundX +=1;
        } else {
            roundX-=1;
        }

        if(roundY >= 0){
            roundY +=1;
        } else {
            roundY-=1;
        }

        List<StreamItem> tempList;
        StreamItem tempItem = null;

        tempList = streamList.get(time/15);

        for(StreamItem item : tempList){

            if(item.x==roundX && item.y == roundY){
                tempItem = item;
            }
        }
        
        return tempItem;
    }
}
