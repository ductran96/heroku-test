/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Amir Ingher
 */
public class CheckManager {

    DbConnector tkyhteys;
    ArrayList<AppTableEntity> ateList;
    double basicRange;

    public CheckManager() {
        tkyhteys = new DbConnector();
        this.basicRange = 3.3;
    }

    public ArrayList<AppTableEntity> CanIGetAllData(AppTableEntity at) throws SQLException {
        System.out.println("Tietokantayhteys");
        ArrayList<AppTableEntity> retList = new ArrayList<>();
        String com = "select * from testapp where lat > " + (at.getLat() - this.basicRange) + " AND lat <" + (at.getLat() + this.basicRange)
                + " AND lon > " + (at.getLon() - this.basicRange) + " AND lon < " + (at.getLon() + this.basicRange) + ";";

        //tkyhteys.alkuInfo();
        ateList = tkyhteys.getListWithCommand(com);

        for (int i = 0; i < ateList.size(); i++) {

            if (checkRange(at, ateList.get(i))) {

                if (ateList.get(i).getKeyElement() != null) {
                    
                    if (at.getKeyElement().equals(ateList.get(i).getKeyElement())) {
                        retList.add(ateList.get(i));
                    }
                }else{
                    retList.add(ateList.get(i));
                }
            }

        }

        tkyhteys.closeAllConnections();

        return retList;
    }

    private boolean checkRange(AppTableEntity at, AppTableEntity ate) {

        double val = Math.sqrt(Math.pow(at.getLat() - ate.getLat(), 2) + Math.pow(at.getLon() - ate.getLon(), 2));
        System.out.println("1: " + val + " 2: " + ate.getRang());
        return val < ate.getRang();
    }

    public void InsertAllNecessary(AppTableEntity at) {

        tkyhteys.inserttest(at);
    }

    public void InsertWithKeyElement(AppTableEntity at) {

        tkyhteys.inserttest_key(at);
    }

}
