/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Amir Ingher
 */
public class DbConnector {

    int columnSize;
    String url;
    Connection con;
    Statement stmt;
    //Konstruktori: Suoritetaan aina kun luokasta luodaan olio English: Constructor: creates connection to database
    //Konstruktori: Luo yhteyden tietokantaan

    public DbConnector() {
        try {
            //Ajuri pitää asentaa: English: You musta install mysql-connector-java-5.1.45-bin.jar
            //Ajuri löytyy Omasta:
            //NetBeans: Project Properties -> Libraries -> Add Jar
            Class.forName("com.mysql.jdbc.Driver");
            //Koulun ympäristössä English: School enviroment
            //url ="jdbc:mysql://mysql.metropolia.fi/puush";

            //Kotoa ssh-tunnelin kautta English: From home to school server
            //Putty: Connection ->SSH-> Source:3306, Destination:mysql.metropolia.fi:3306
            // Oma palvelin English: Own server at laptop
            url = "jdbc:mysql://35.198.123.61:3306/testDB?autoReconnect=true&useSSL=false";

            con = DriverManager.getConnection(url, "amir", "amir");
            stmt = con.createStatement();

        } catch (Exception e) {
            System.out.println("Error");
            System.err.println(e.getMessage());
        }
    }
    //Destruktori: Suoritetaan automaattisesti kun muisti vapautetaan (Muistin vapauttaminen automaattista)
    //Destruktori: Sulkee tietokantayhteyden Englis: Close database connection

    protected void finalize() throws Throwable {
        try {
            con.close();
        } finally {
            super.finalize();
        }
    }

    public void alkuInfo() {

        System.out.println("testi: " + getListWithCommand("select * from testapp"));

        // System.out.println("test "+haeKomennolla("select tiletype.name from tiletype,tile,characterp where tiletype.id = tile.tileTypeID and tile.xcoord = characterp.xcoord and tile.ycoord = characterp.ycoord"));
    }

    public void inserttest(AppTableEntity ent) {

        String command;
        command = "insert into testapp(somedata,lat,lon,darange) values('"
                + ent.getSomeData() + "'," + ent.getLat() + "," + ent.getLon() + "," + ent.getRang() + ");";

        InsertToTable(command);
    }
    
    public void inserttest_key(AppTableEntity ent) {

        String command;
        command = "insert into testapp(somedata,lat,lon,darange,keyelement) values('"
                + ent.getSomeData() + "'," + ent.getLat() + "," + ent.getLon() + "," + ent.getRang() + ",'" +ent.getKeyElement()+"');";

        InsertToTable(command);
    }

    public void InsertToTable(String command) {

        System.out.println(command);
        try {
            stmt.executeUpdate(command);

        } catch (Exception e) {

            System.out.println("err");
        }

    }

    public void closeAllConnections() throws SQLException {
        stmt.close();
        con.close();
    }

    public void inserttiles(int s, int a) {
        String finalcom = "";
        String command = "insert into tile(xcoord,ycoord,tiletype) values";
        int random;

        for (int i = s; i < a; i++) {

            command = "insert into tile(xcoord,ycoord,tileTypeID) values";
            random = new Random().nextInt(3);
            random++;
            command = command + "(" + i + "," + i + "," + random + ")" + ";";

            try {
                stmt.executeUpdate(command);

            } catch (Exception e) {

                System.out.println("err");
            }

            System.out.println(command);
        }

    }

    public void inserttilesForReal(int x, int y) {
        String finalcom = "";
        String command = "insert into tile(xcoord,ycoord,tiletype) values";
        int random;

        for (int i = 1; i <= x; i++) {
            for (int j = 0; j <= y; j++) {

                command = "insert into tile(xcoord,ycoord,tileTypeID) values";
                random = new Random().nextInt(3);
                random++;
                command = command + "(" + i + "," + j + "," + random + ")" + ";";

                try {
                    stmt.executeUpdate(command);

                } catch (Exception e) {

                    System.out.println("err");
                }

                System.out.println(command);
            }
        }

    }

    //Metodi joka hakee taulusta kaikki kentät English: Method that return as strig all values from table
    public String haeKaikki() {
        // Merkkijono johon haun tulos kerätään English: String for query result
        String queryResult = "";
        // Merkkijono joka sisältää suoritettavan sql-lauseen English: String for sql query sentence
        String sqlsentence = "SELECT * FROM enemy;";
        try {
            //Kerätään ResultSet English: Resultset
            ResultSet rs = stmt.executeQuery(sqlsentence);
            //Niin kauan kun ResultSetissä on rivejä English: As long as there is rows in resultset
            while (rs.next()) {
                //Käydään kaikki sarakkeet läpi English: Go over all the colums
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Poimitaan kentän teksti English: pick up text
                    String data = rs.getString(i);
                    if (data == null) {
                        data = "null";
                    }
                    //Lisätään kentän teksti tulosmerkkijonoon English: Add text to query result string
                    queryResult = queryResult + data.trim() + "\t";
                }
                //LisÃ¤ttÃ¤n rivinvaihto kun yhden rivin kaikki sarakkeet on kÃ¤yty lÃ¤pi English: Add new line
                queryResult = queryResult + "\n";
            }
        } catch (Exception e) {
            System.out.println("Error");
            System.err.println(e.getMessage());
        }

        return queryResult;
    }

    public String haeKomennolla(String com) {
        // Merkkijono johon haun tulos kerätään English: String for query result
        String queryResult = "";
        // Merkkijono joka sisältää suoritettavan sql-lauseen English: String for sql query sentence
        String sqlsentence = com;
        try {
            //Kerätään ResultSet English: Resultset
            ResultSet rs = stmt.executeQuery(sqlsentence);

            //Niin kauan kun ResultSetissä on rivejä English: As long as there is rows in resultset
            while (rs.next()) {
                //Käydään kaikki sarakkeet läpi English: Go over all the colums

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Poimitaan kentän teksti English: pick up text
                    String data = rs.getString(i);
                    System.out.println(data);
                    if (data == null) {
                        data = "null";
                    }
                    //Lisätään kentän teksti tulosmerkkijonoon English: Add text to query result string
                    queryResult = queryResult + data.trim() + "\t";
                }
                //LisÃ¤ttÃ¤n rivinvaihto kun yhden rivin kaikki sarakkeet on kÃ¤yty lÃ¤pi English: Add new line
                queryResult = queryResult + "\n";
            }
        } catch (Exception e) {
            System.out.println("Error");
            System.err.println(e.getMessage());
        }

        return queryResult;
    }

    public String getAppTableWithCommand(String com) {
        // Merkkijono johon haun tulos kerätään English: String for query result
        String queryResult = "";
        AppTableEntity ate;
        // Merkkijono joka sisältää suoritettavan sql-lauseen English: String for sql query sentence
        String sqlsentence = com;
        try {
            //Kerätään ResultSet English: Resultset
            ResultSet rs = stmt.executeQuery(sqlsentence);

            //Niin kauan kun ResultSetissä on rivejä English: As long as there is rows in resultset
            while (rs.next()) {
                //Käydään kaikki sarakkeet läpi English: Go over all the colums

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Poimitaan kentän teksti English: pick up text
                    String data = rs.getString(i);
                    // System.out.println(data);
                    if (data == null) {
                        data = "null";
                    }

                    //Lisätään kentän teksti tulosmerkkijonoon English: Add text to query result string
                    queryResult = queryResult + data.trim() + "\t";
                }

                //LisÃ¤ttÃ¤n rivinvaihto kun yhden rivin kaikki sarakkeet on kÃ¤yty lÃ¤pi English: Add new line
                queryResult = queryResult + "\n";
            }

            int c_size = rs.getMetaData().getColumnCount();
            System.out.println(c_size);

            String[] parts = queryResult.split(" ");

            for (int i = 0; i < parts.length; i++) {
                System.out.println(parts[i]);

            }

        } catch (Exception e) {
            System.out.println("Error");
            System.err.println(e.getMessage());
        }

        return queryResult;
    }

    public ArrayList<AppTableEntity> getListWithCommand(String com) {
        // Merkkijono johon haun tulos kerätään English: String for query result
        String queryResult = "";
        AppTableEntity ate;
        //AppTableEntity[] ate_list;
        ArrayList<AppTableEntity> ate_list = new ArrayList<>();
        // Merkkijono joka sisältää suoritettavan sql-lauseen English: String for sql query sentence
        String sqlsentence = com;
        try {
            //Kerätään ResultSet English: Resultset
            ResultSet rs = stmt.executeQuery(sqlsentence);

            //Niin kauan kun ResultSetissä on rivejä English: As long as there is rows in resultset
            while (rs.next()) {
                //Käydään kaikki sarakkeet läpi English: Go over all the colums
                ate = new AppTableEntity(rs.getInt("id"), rs.getString("somedata"), rs.getDouble("lat"),
                        rs.getDouble("lon"), rs.getString("keyelement"),
                        rs.getTimestamp("reg_data"), rs.getDouble("darange"));

                ate_list.add(ate);

            }
            
            rs.close();

        } catch (Exception e) {
            System.out.println("Error");
            System.err.println(e.getMessage());
        }
        
        //System.out.println(ate_list);
        return ate_list;
    }

    public AppTableEntity getValueWithCommand(String com) {
        // Merkkijono johon haun tulos kerätään English: String for query result
        String queryResult = "";
        AppTableEntity ate;
        //AppTableEntity[] ate_list;

        // Merkkijono joka sisältää suoritettavan sql-lauseen English: String for sql query sentence
        String sqlsentence = com;
        try {
            //Kerätään ResultSet English: Resultset
            ResultSet rs = stmt.executeQuery(sqlsentence);

            //Niin kauan kun ResultSetissä on rivejä English: As long as there is rows in resultset
            //Käydään kaikki sarakkeet läpi English: Go over all the colums
            ate = new AppTableEntity(rs.getInt("id"), rs.getString("somedata"), rs.getDouble("lat"),
                    rs.getDouble("lon"), rs.getString("keyelement"),
                    rs.getTimestamp("reg_data"), rs.getDouble("darange"));
            rs.close();
            return ate;

        } catch (Exception e) {
            System.out.println("Error");
            System.err.println(e.getMessage());
        }
        
        //System.out.println(ate_list);
        return null;
    }

    public void kaskeKomennolla(String com) {
        // Merkkijono johon haun tulos kerätään English: String for query result

        // Merkkijono joka sisältää suoritettavan sql-lauseen English: String for sql query sentence
        String sqlsentence = com;
        try {
            //Kerätään ResultSet English: Resultset
            stmt.executeUpdate(sqlsentence);
            //Niin kauan kun ResultSetissä on rivejä English: As long as there is rows in resultset

        } catch (Exception e) {
            System.out.println("Error");

        }

    }

    public boolean haeKoordinaatisto(int xx, int yy) {
        // Merkkijono johon haun tulos kerätään English: String for query result

        // Merkkijono joka sisältää suoritettavan sql-lauseen English: String for sql query sentence
        String sqlsentence = "select xcoord, ycoord from tile where xcoord = " + xx + " and ycoord = " + yy + ";";

        try {
            //Kerätään ResultSet English: Resultset
            ResultSet rs = stmt.executeQuery(sqlsentence);
            // System.out.println(rs.next());

            if (rs.next()) {

                return true;

            } else {
                return false;
            }

            //Niin kauan kun ResultSetissä on rivejä English: As long as there is rows in resultset
        } catch (Exception e) {
            System.out.println("Error");
            System.err.println(e.getMessage());
            return false;
        }

    }

}
