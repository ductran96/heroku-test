
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Amir Ingher
 */
public class ListUtil {

    private static List<AppTableEntity> list;

    public ListUtil() {
        list = new ArrayList<AppTableEntity>();
        list.add(new AppTableEntity(1, "first", 23.3, 24.4, "key", 0.5));

    }

    public boolean addValue(String value) {

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
        if (rang <= 0) {
            rang = 0.05;
        } else {

        }

        list.add(new AppTableEntity(list.size() + 1,
                data,
                lat,
                lon,
                key,
                rang));

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
            if (checkRange(at, ate)) {
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

}
