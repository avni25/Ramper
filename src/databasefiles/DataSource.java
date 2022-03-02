package databasefiles;

import common.Flight;
import common.ModifiedPlot;
import common.Plot;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSource {

    public static final String DB_NAME="Ramp.db";
    public static final String fileURL="jdbc:sqlite:/JavaProjects/Ramper/"+DB_NAME;
    public static final String TABLE_NAME = "Flights";

    public static final String COLUMN_CALLSIGN = "name";
    public static final String COLUMN_BODYTYPE = "bodyType";
    public static final String COLUMN_REG = "registration";
    public static final String COLUMN_STA ="STA";
    public static final String COLUMN_STD ="STD";
    public static final String COLUMN_DEPNUMBER ="depCallSign";
    public static final String COLUMN_FROM="Ffrom";
    public static final String COLUMN_TO="Fto";
    public static final String COLUMN_HND="hnd";
    public static final String COLUMN_TER="terminal";
    public static final String COLUMN_PARK = "plot";
    public static final String COLUMN_COMPANY_NAME = "companyName";

    public static final String DELETE_FLIGHT = "DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_CALLSIGN+" = ";
    public static final String DELETE_DAILY_LIST = "DELETE FROM "+ TABLE_NAME;
    public static final String TABLE_PLOTS_NAME= "Plots";
    public static final String PLOT_COLUMN_GROUP="plotGroup";
    public static final String PLOT_COLUMN_NAME="plotName";
    public static final String PLOT_COLUMN_TYPE="bodyType";
    public static final String PLOT_COLUMN_APRON="apron";
    public static final String PLOT_COLUMN_FLIGHT_NAME="FlightNAME";
    public static final String PLOT_COLUMN_FLIGHT_DEPNAME="FlightDEPNAME";
    public static final String PLOT_COLUMN_FLIGHT_TYPE="FlightTYPE";
    public static final String PLOT_COLUMN_FLIGHT_REG="FlightREG";
    public static final String PLOT_COLUMN_FLIGHT_STA="FlightSTA";
    public static final String PLOT_COLUMN_FLIGHT_STD="FlightSTD";
    public static final String PLOT_COLUMN_FLIGHT_FROM="FlightFROM";
    public static final String PLOT_COLUMN_FLIGHT_TO="FlightTO";
    public static final String PLOT_COLUMN_FLIGHT_HND="FlightHND";

    public static final String TABLE_ARCIVE_NAME = "Archive";
    public static final String ARCHIVE_COLUM_FLIGHT_ID ="ID";
    public static final String ARCHIVE_COLUM_FLIGHT_NAME ="Name";
    public static final String ARCHIVE_COLUM_FLIGHT_TYPE ="Type";
    public static final String ARCHIVE_COLUM_FLIGHT_REG ="Reg";
    public static final String ARCHIVE_COLUM_FLIGHT_STA ="STA";
    public static final String ARCHIVE_COLUM_FLIGHT_STD ="STD";
    public static final String ARCHIVE_COLUM_FLIGHT_DEPNUMBER ="DepNumber";
    public static final String ARCHIVE_COLUM_FLIGHT_FROM ="From";
    public static final String ARCHIVE_COLUM_FLIGHT_TO ="TTo";
    public static final String ARCHIVE_COLUM_FLIGHT_HND ="Hnd";
    public static final String ARCHIVE_COLUM_FLIGHT_TER ="Ter";
    public static final String ARCHIVE_COLUM_FLIGHT_PARK ="Park";
    public static final String ARCHIVE_COLUM_FLIGHT_COMPANYNAME ="Company";
    public static final String DELETE_ARCHIVE_LIST = "DELETE FROM "+TABLE_ARCIVE_NAME;

    public static final String CREATE_TABLE_PLOTS = "CREATE TABLE IF NOT EXISTS "+TABLE_PLOTS_NAME+"('"+PLOT_COLUMN_GROUP+
            "' TEXT, '"+PLOT_COLUMN_NAME+"' TEXT, '"+PLOT_COLUMN_TYPE+"' TEXT, '"+PLOT_COLUMN_APRON+"' INTEGER, '"+PLOT_COLUMN_FLIGHT_NAME+
            "' TEXT, '"+PLOT_COLUMN_FLIGHT_TYPE+"' TEXT, '"+PLOT_COLUMN_FLIGHT_REG+"' TEXT, '"+PLOT_COLUMN_FLIGHT_STA+
            "' TEXT, '"+PLOT_COLUMN_FLIGHT_STD+"' TEXT, '"+PLOT_COLUMN_FLIGHT_FROM+"' TEXT, '"+PLOT_COLUMN_FLIGHT_TO+
            "' TEXT, '"+PLOT_COLUMN_FLIGHT_HND+"' TEXT)";
    //CREATE TABLE IF NOT EXISTS Archive ('ID' INTEGER, 'Name' TEXT, 'Type' TEXT, 'Name' TEXT, ....)
    public static final String CREATE_TABLE_ARCHIVE = "CREATE TABLE IF NOT EXISTS "+TABLE_ARCIVE_NAME+
                            "('"+ARCHIVE_COLUM_FLIGHT_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +ARCHIVE_COLUM_FLIGHT_NAME+"' TEXT, '"+ARCHIVE_COLUM_FLIGHT_TYPE+
                            "' TEXT, '"+ARCHIVE_COLUM_FLIGHT_REG+"' TEXT, '"+ARCHIVE_COLUM_FLIGHT_STA+"' TEXT, '"+ARCHIVE_COLUM_FLIGHT_STD+
                            "' TEXT, '"+ARCHIVE_COLUM_FLIGHT_DEPNUMBER+"' TEXT, '"+ARCHIVE_COLUM_FLIGHT_FROM+"' TEXT, '"+ARCHIVE_COLUM_FLIGHT_TO+
                            "' TEXT, '"+ARCHIVE_COLUM_FLIGHT_HND+"' TEXT, '"+ARCHIVE_COLUM_FLIGHT_TER+"' INTEGER, '"+ARCHIVE_COLUM_FLIGHT_PARK+
                            "' TEXT, '"+ARCHIVE_COLUM_FLIGHT_COMPANYNAME+"' TEXT)";
    public static final String SEARCH = "select companyName, COUNT(*) AS total from Flights GROUP BY companyName ORDER BY total DESC";
    private Connection conn;

    public boolean open(){
        try{
            conn =  DriverManager.getConnection(fileURL);
            return true;
        }catch(SQLException e){
            System.out.println("couldn't connect "+ e.getMessage());
            return false;
        }
    }

    public void close(){
        try{
            if(conn!=null) conn.close();
        }catch(SQLException e){
            System.out.println("cant close.. "+e.getMessage());
        }
    }

    public void addPLotsToDataBase(Plot p, String group, int apron)throws SQLException{
        Statement st = conn.createStatement();

        st.execute("CREATE TABLE IF NOT EXISTS "+TABLE_PLOTS_NAME+
                "('"+PLOT_COLUMN_GROUP + "' TEXT, '" +PLOT_COLUMN_NAME+"' TEXT, '"+PLOT_COLUMN_TYPE+
                "' TEXT, '"+PLOT_COLUMN_APRON+"' INTEGER, '"+PLOT_COLUMN_FLIGHT_NAME+"' TEXT, '"+PLOT_COLUMN_FLIGHT_DEPNAME+
                "' TEXT, '"+PLOT_COLUMN_FLIGHT_TYPE+"' TEXT, '"+PLOT_COLUMN_FLIGHT_REG+"' TEXT, '"+PLOT_COLUMN_FLIGHT_STA+
                "' TEXT, '"+PLOT_COLUMN_FLIGHT_STD+"' TEXT, '"+PLOT_COLUMN_FLIGHT_FROM+"' TEXT, '"+PLOT_COLUMN_FLIGHT_TO+
                "' TEXT, '"+PLOT_COLUMN_FLIGHT_HND+"' TEXT)");

        st.execute("INSERT INTO "+TABLE_PLOTS_NAME+" VALUES ('"+group+"','"+p.getName()+"','"+
                    p.getBodyType()+"','"+apron+"','"+p.getFlight().getName()+"','"+p.getFlight().getDepCallSign()+
                "','"+p.getFlight().getBodyType()+"','"+p.getFlight().getRegistration()+"','"+p.getFlight().getSTA()+
                "','"+p.getFlight().getSTD()+
                "','"+p.getFlight().getFrom()+"','"+p.getFlight().getTo()+"','"+p.getFlight().getHnd()+ "',)");
        st.close();
    }

    public void addToArchive(Flight f) throws SQLException{
        Statement st = conn.createStatement();
        st.execute(CREATE_TABLE_ARCHIVE);
        st.execute("INSERT INTO "+TABLE_ARCIVE_NAME+" values(null, '"+f.getName()+"', '"+f.getBodyType()+"', '"+f.getRegistration()+"','"+f.getSTA()+
                "','"+f.getSTD()+"','"+f.getDepCallSign()+"','"+f.getFrom()+"','"+f.getTo()+"','"+f.getHnd()+"','"+f.getTerminal()+
                "','"+f.getPlot().getName()+"','"+f.getCompanyName()+"')");
        st.close();
    }

    public void addToDataBase(Flight p)throws SQLException{
        Statement st = conn.createStatement();

        st.execute("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                " ('"+COLUMN_CALLSIGN+"' TEXT, '"+COLUMN_BODYTYPE+"' TEXT, '"+COLUMN_REG+"' TEXT, '"+COLUMN_STA+"' TEXT, '"+COLUMN_STD+"' TEXT, '"+
                COLUMN_DEPNUMBER+"' TEXT, '"+COLUMN_FROM+"' TEXT, '"+COLUMN_TO+"' TEXT, '"+COLUMN_HND+"' TEXT, '"+COLUMN_TER+"' INTEGER, '"+
                COLUMN_PARK+"' TEXT, '"+COLUMN_COMPANY_NAME+"' TEXT)");

        st.execute("INSERT INTO "+TABLE_NAME+" values('"+p.getName()+"', '"+p.getBodyType()+"', '"+p.getRegistration()+"','"+p.getSTA()+
                "','"+p.getSTD()+"','"+p.getDepCallSign()+"','"+p.getFrom()+"','"+p.getTo()+"','"+p.getHnd()+"','"+p.getTerminal()+
                "','"+p.getPlot().getName()+"','"+p.getCompanyName()+"')");

        st.close();
    }
    public Map<String, Integer> statistics_dailyFlightsByCompanies() throws SQLException{
        try (Statement st = conn.createStatement();
        ResultSet r = st.executeQuery(SEARCH)){
            Map<String, Integer> temp =new HashMap<>();
            while(r.next()){
                temp.put(r.getString(COLUMN_COMPANY_NAME), r.getInt("total"));
            }
            return temp;
        }catch(SQLException e){
            System.out.println("cant load company list.." +e.getMessage());
            return null;
        }

    }
    public List<Flight> loadFlights()throws SQLException{

        try(Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT * FROM "+TABLE_NAME)){


            List<Flight> fList = new ArrayList<>();
            while(r.next()){
                Flight p = new Flight();
                p.setName(r.getString(COLUMN_CALLSIGN));
                p.setBodyType(r.getString(COLUMN_BODYTYPE));
                p.setRegistration(r.getString(COLUMN_REG));
                p.setSTA(r.getString(COLUMN_STA));
                p.setSTD(r.getString(COLUMN_STD));
                p.setDepCallSign(r.getString(COLUMN_DEPNUMBER));
                p.setFrom(r.getString(COLUMN_FROM));
                p.setTo(r.getString(COLUMN_TO));
                p.setHnd(r.getString(COLUMN_HND));
                p.setTerminal(r.getInt(COLUMN_TER));
                p.setPlot(r.getString(COLUMN_PARK));
                p.setCompanyName(r.getString(COLUMN_COMPANY_NAME));
                fList.add(p);
            }
            return fList;
        }catch(SQLException e){
            System.out.println("cant load list.." +e.getMessage());
            return null;
        }
    }

    public List<Flight> loadArchiveFlights()throws SQLException{

        try(Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT * FROM "+TABLE_ARCIVE_NAME)){


            List<Flight> fList = new ArrayList<>();
            while(r.next()){
                Flight p = new Flight();

                p.setName(r.getString(ARCHIVE_COLUM_FLIGHT_NAME));
                p.setBodyType(r.getString(ARCHIVE_COLUM_FLIGHT_TYPE));
                p.setRegistration(r.getString(ARCHIVE_COLUM_FLIGHT_REG));
                p.setSTA(r.getString(ARCHIVE_COLUM_FLIGHT_STA));
                p.setSTD(r.getString(ARCHIVE_COLUM_FLIGHT_STD));
                p.setDepCallSign(r.getString(ARCHIVE_COLUM_FLIGHT_DEPNUMBER));
                p.setFrom(r.getString(ARCHIVE_COLUM_FLIGHT_FROM));
                p.setTo(r.getString(ARCHIVE_COLUM_FLIGHT_TO));
                p.setHnd(r.getString(ARCHIVE_COLUM_FLIGHT_HND));
                p.setTerminal(r.getInt(ARCHIVE_COLUM_FLIGHT_TER));
                p.setPlot(r.getString(ARCHIVE_COLUM_FLIGHT_PARK));
                p.setCompanyName(r.getString(ARCHIVE_COLUM_FLIGHT_COMPANYNAME));
                fList.add(p);
            }
            return fList;
        }catch(SQLException e){
            System.out.println("cant load list.." +e.getMessage());
            return null;
        }
    }

    public List<Plot> loadPlots()throws SQLException{

        try(Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT * FROM "+TABLE_PLOTS_NAME)){

            List<Plot> plotList = new ArrayList<>();
            while(r.next()){
                Plot p = new Plot();
                p.setName(r.getString(PLOT_COLUMN_NAME));
                p.setBodyType(r.getString(PLOT_COLUMN_TYPE));
                p.setTerminal(r.getInt(PLOT_COLUMN_APRON));

                    Flight f = new Flight();
                    f.setName(r.getString(PLOT_COLUMN_FLIGHT_NAME));
                    f.setBodyType(r.getString(PLOT_COLUMN_FLIGHT_TYPE));
                    f.setRegistration(r.getString(PLOT_COLUMN_FLIGHT_REG));
                    f.setSTA(r.getString(PLOT_COLUMN_FLIGHT_STA));
                    f.setSTD(r.getString(PLOT_COLUMN_FLIGHT_STD));
                    f.setDepCallSign(r.getString(PLOT_COLUMN_FLIGHT_DEPNAME));
                    f.setFrom(r.getString(PLOT_COLUMN_FLIGHT_FROM));
                    f.setTo(r.getString(PLOT_COLUMN_FLIGHT_TO));
                    f.setHnd(r.getString(PLOT_COLUMN_FLIGHT_HND));
                    f.setTerminal(r.getInt(PLOT_COLUMN_APRON));
                    f.setPlot(r.getString(PLOT_COLUMN_NAME));
                    f.setCompanyName(r.getString(PLOT_COLUMN_GROUP)); //---------------Group infos----------------
                p.setFlight(f);
                plotList.add(p);
            }
            st.close();
            return plotList;
        }catch(SQLException e){
            System.out.println("cant load plot list.." +e.getMessage());
            return null;
        }

    }
    public List<ModifiedPlot> loadModifiedPlots()throws SQLException{

        try(Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT * FROM "+TABLE_PLOTS_NAME)){

            List<ModifiedPlot> plotList = new ArrayList<>();
            while(r.next()){
                ModifiedPlot p = new ModifiedPlot();
                p.setPlotGroup(r.getString(PLOT_COLUMN_GROUP));
                p.setName(r.getString(PLOT_COLUMN_NAME));
                p.setBodyType(r.getString(PLOT_COLUMN_TYPE));
                p.setTerminal(r.getInt(PLOT_COLUMN_APRON));

                Flight f = new Flight();
                f.setName(r.getString(PLOT_COLUMN_FLIGHT_NAME));
                f.setBodyType(r.getString(PLOT_COLUMN_FLIGHT_TYPE));
                f.setRegistration(r.getString(PLOT_COLUMN_FLIGHT_REG));
                f.setSTA(r.getString(PLOT_COLUMN_FLIGHT_STA));
                f.setSTD(r.getString(PLOT_COLUMN_FLIGHT_STD));
                f.setDepCallSign(r.getString(PLOT_COLUMN_FLIGHT_DEPNAME));
                f.setFrom(r.getString(PLOT_COLUMN_FLIGHT_FROM));
                f.setTo(r.getString(PLOT_COLUMN_FLIGHT_TO));
                f.setHnd(r.getString(PLOT_COLUMN_FLIGHT_HND));
                f.setTerminal(r.getInt(PLOT_COLUMN_APRON));
                f.setPlot(r.getString(PLOT_COLUMN_NAME));
                p.setFlight(f);
                plotList.add(p);
            }
            st.close();
            return plotList;
        }catch(SQLException e){
            System.out.println("cant load Modified plot list.." +e.getMessage());
            return null;
        }

    }
    public void updatePlots(Plot p, String group)throws SQLException{
        Statement st = conn.createStatement();
        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_NAME+" = '"+p.getName()+"', "
                                                        +PLOT_COLUMN_TYPE+" = '"+p.getBodyType()+"', "
                                                        +PLOT_COLUMN_APRON+" = '"+p.getTerminal()+"', "
                                                        +PLOT_COLUMN_FLIGHT_NAME+" = '"+p.getFlight().getName()+"', "
                                                        +PLOT_COLUMN_FLIGHT_DEPNAME+" = '"+p.getFlight().getDepCallSign()+"', "
                                                        +PLOT_COLUMN_FLIGHT_TYPE+" = '"+p.getFlight().getBodyType()+"', "
                                                        +PLOT_COLUMN_FLIGHT_REG+" = '"+p.getFlight().getRegistration()+"', "
                                                        +PLOT_COLUMN_FLIGHT_STA+" = '"+p.getFlight().getSTA()+"', "
                                                        +PLOT_COLUMN_FLIGHT_STD+" = '"+p.getFlight().getSTD()+"', "
                                                        +PLOT_COLUMN_FLIGHT_FROM+" = '"+p.getFlight().getFrom()+"', "
                                                        +PLOT_COLUMN_FLIGHT_TO+" = '"+p.getFlight().getTo()+"', "
                                                        +PLOT_COLUMN_FLIGHT_HND+" = '"+p.getFlight().getHnd()+"', "
                                                        +PLOT_COLUMN_GROUP+" = '"+group+
                        "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");

//        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_GROUP+" = '"+group+
//                "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");
        st.close();
    }
    public void updatePlotsFeatures(Plot p, String group)throws SQLException{
        Statement st = conn.createStatement();
        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_NAME+" = '"+p.getName()+"', "
                                                        +PLOT_COLUMN_TYPE+" = '"+p.getBodyType()+"', "
                                                        +PLOT_COLUMN_APRON+" = '"+p.getTerminal()+"', "
                                                        +PLOT_COLUMN_GROUP+" = '"+group+
                "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");

//        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_GROUP+" = '"+group+
//                "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");
        st.close();
    }

    public void updatePlotsFlight(Plot p)throws SQLException{
        Statement st = conn.createStatement();
        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_FLIGHT_NAME+" = '"+p.getFlight().getName()+"', "
                                                        +PLOT_COLUMN_FLIGHT_DEPNAME+" = '"+p.getFlight().getDepCallSign()+"', "
                                                        +PLOT_COLUMN_FLIGHT_TYPE+" = '"+p.getFlight().getBodyType()+"', "
                                                        +PLOT_COLUMN_FLIGHT_REG+" = '"+p.getFlight().getRegistration()+"', "
                                                        +PLOT_COLUMN_FLIGHT_STA+" = '"+p.getFlight().getSTA()+"', "
                                                        +PLOT_COLUMN_FLIGHT_STD+" = '"+p.getFlight().getSTD()+"', "
                                                        +PLOT_COLUMN_FLIGHT_FROM+" = '"+p.getFlight().getFrom()+"', "
                                                        +PLOT_COLUMN_FLIGHT_TO+" = '"+p.getFlight().getTo()+"', "
                                                        +PLOT_COLUMN_FLIGHT_HND+" = '"+p.getFlight().getHnd()+

                "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");

//        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_GROUP+" = '"+group+
//                "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");
        st.close();
    }
    public void cleanPlotsFlight(Plot p)throws SQLException{
        Statement st = conn.createStatement();
        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_FLIGHT_NAME+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_DEPNAME+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_TYPE+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_REG+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_STA+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_STD+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_FROM+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_TO+" = '"+"', "
                                                        +PLOT_COLUMN_FLIGHT_HND+" = '"+

                "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");

//        st.execute("UPDATE "+ TABLE_PLOTS_NAME+" SET "  +PLOT_COLUMN_GROUP+" = '"+group+
//                "' WHERE "+PLOT_COLUMN_NAME+" = '"+p.getName()+"'");
        st.close();
    }
    public void updateFlight(Flight p)throws SQLException{

            Statement st = conn.createStatement();
            st.execute("UPDATE "+ TABLE_NAME+" SET "    +COLUMN_CALLSIGN+" = '"+p.getName()+"', "
                                                        +COLUMN_BODYTYPE+" = '"+p.getBodyType()+"', "
                                                        +COLUMN_REG+" = '"+p.getRegistration()+"', "
                                                        +COLUMN_STA+" = '"+p.getSTA()+"', "
                                                        +COLUMN_STD+" = '"+p.getSTD()+"', "
                                                        +COLUMN_DEPNUMBER+" = '"+p.getDepCallSign()+"', "
                                                        +COLUMN_FROM+" = '"+p.getFrom()+"', "
                                                        +COLUMN_TO+" = '"+p.getTo()+"', "
                                                        +COLUMN_HND+" = '"+p.getHnd()+"', "
                                                        +COLUMN_TER+" = '"+p.getTerminal()+"', "
                                                        +COLUMN_PARK+" = '"+p.getPlot()+"', "
                                                        +COLUMN_COMPANY_NAME+" = '"+p.getCompanyName()+
                            "' WHERE "+COLUMN_CALLSIGN+" = '"+p.getName()+"'");
            st.close();
    }

    public void removeFlight(String flightName)throws SQLException{
        Statement st = conn.createStatement();
        st.execute(DELETE_FLIGHT+"'"+flightName+"'");
        st.close();
    }
    public void removeDailyList()throws SQLException{
        Statement st = conn.createStatement();
        st.execute(DELETE_DAILY_LIST);
        st.close();
    }

    public void removeArchiveList()throws SQLException{
        Statement st = conn.createStatement();
        st.execute(DELETE_ARCHIVE_LIST);
        st.close();
    }

    public void add_plot(ModifiedPlot p)throws SQLException{
        Statement st = conn.createStatement();

        st.close();
    }
    public void update_plot(ModifiedPlot p)throws SQLException{
        Statement st = conn.createStatement();

        st.close();
    }
    public void delete_plot(ModifiedPlot p)throws SQLException{
        Statement st = conn.createStatement();

        st.close();
    }
}
