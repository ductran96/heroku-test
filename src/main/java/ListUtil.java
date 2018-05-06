
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Amir Ingher
 */
public class ListUtil{

    private static List<AppTableEntity> list;

    public ListUtil() {
        list = new ArrayList<AppTableEntity>();
        loadList();
        list.add(new AppTableEntity(1, "first", 23.3, 24.4, "key", 0.5));

    }
    
    private void loadList(){
        try{
            List<AppTableEntity> temp;
            temp = list;
            list =(ArrayList)deserialize("test1.ser");
                    for (AppTableEntity val : temp) {
                        list.add(val);
                    }
        //list = (ArrayList)deserialize("test.ser");
        
        }catch(Exception e){}
    }
    public static Object deserialize(String fileName) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        
        ois.close();
        return obj;
    }

    /**
     * serialize the given object and save it to given file
     */
    public static void serialize(Object obj, String fileName)
            throws IOException {
        
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    public boolean addValue(String value) throws IOException {

        String[] slist = value.split(";");

        if (slist.length < 5 || slist == null) {
            return false;
        }

        String data = slist[0];
        Double lat = Double.parseDouble(slist[1]);
        Double lon = Double.parseDouble(slist[2]);
        String key = slist[3];
        Double rang = Double.parseDouble(slist[4]);

        if (key.equals("0")) {
            key = null;
        }
        if(rang <= 0){
            rang = 100.0;
        }
            
        list.add(new AppTableEntity(list.size() + 1,
                data,
                lat,
                lon,
                key,
                rang));
        try{
        
        serialize(list,"test1.ser");
        
        }catch(Exception e){}
        return true;
    }

    public String getvalue() {

        return "" + list;
    }

    public String getValuebasedOnLocation(String data) {

        String[] slist = data.split(";");

        if (slist.length < 2 || slist == null) {
            return "no thing";
        }

        double lat = Double.parseDouble(slist[0]);
        double lon = Double.parseDouble(slist[1]);
        AppTableEntity at = new AppTableEntity(1, "", lat, lon);
        List<AppTableEntity> tmp = new ArrayList<>();

        for (AppTableEntity ate : list) {
            if (checkRange2(at, ate)) {
                if (at.getKeyElement() != null) {
                    if (at.getKeyElement().equals(ate.getKeyElement())) {
                        tmp.add(ate);
                    }
                } else {
                    tmp.add(ate);
                }

            }

        }

        return "" + tmp;
    }

    private boolean checkRange(AppTableEntity at, AppTableEntity ate) {

        double val = Math.sqrt(Math.pow(at.getLat() - ate.getLat(), 2) + Math.pow(at.getLon() - ate.getLon(), 2));
        // System.out.println("1: " + val + " 2: " + ate.getRang());
        return val < ate.getRang();
    }
    
    private boolean checkRange2(AppTableEntity at, AppTableEntity ate){
        
        double R = 6378137;
        double lat = ate.getLat();
        double lon = ate.getLon();
            
            
        double dlat = ate.getRang()/R;
        double dlon = ate.getRang()/(R*Math.cos((Math.PI*at.getLat()/180)));

        double lat0 = lat + dlat * 180/Math.PI;
        double lon0 = lon + dlon * 180/Math.PI;

        double lat1 = lat - dlat * 180/Math.PI;
        double lon1 = lon - dlon * 180/Math.PI;

        if(at.getLat() >= lat1 && at.getLat() <= lat0){
            
            if(at.getLon() >= lon1 && at.getLon() <= lon0){
                return true;
            }
            return false;
        }
        else{
            return false;
        }
        
        /*
        System.out.println("NE point latitude is: " + lat0);
        System.out.println("NE point longitude is: "+ lon0);

        System.out.println("SW point latitude is: " + lat1);
        System.out.println("SW point longitude is: "+ lon1);
        */
        
        
    }

}
