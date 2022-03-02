package common;

import databasefiles.DataSource;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Aerodrome {

    public static final List<String> catA = new ArrayList<>(){{add("C100");}};
    public static final List<String> catB = new ArrayList<>(){{add("F900");}};
    public static final List<String> catC = new ArrayList<>(){{add("B737");add("B738");add("B739");add("A310");add("A319");
                                                                add("A320");add("A321");}};
    public static final List<String> catD = new ArrayList<>(){{add("B762");add("B752");add("MD82");}};
    public static final List<String> catE = new ArrayList<>(){{add("B763");add("B753");}};
    public static final List<String> catF = new ArrayList<>(){{add("B747");add("A380");add("B773");add("B772");add("A330");add("A333");}};

    public static String[] domesticAerodromeCodes = {"ESB","AYT","IST","TRX","TRZ","ADB","SAW","ADA","ADF","ANK","AJI","BZI","BDM",
                                                    "BAL","BJV","CKZ","DLM","DNZ","DIY","EDO","EZS","ERC","ERZ","ESK","GZT","ISE",
                                                    "IZM","KCM","KSY","KFS","ASR","KCO","KYA","XKU","MLX","MQM","QRQ","QIN","MZH","MSR",
                                                    "NAV","QOR","QRI","SZF","SFQ","SXZ","SIC","VAS","VAN","TEQ","TJK","USQ","ONQ"};
    public static List<String> domestics = new ArrayList<>();
    public static String[] plotGroups ={"A1","A1DomesticGates","A1Domestic","A1DomesticLarge","A1Gates","A2","A2Gates","A3"};
    public static String[] GatesWithLetter ={"10","11","12","13","62","63","64","65"};
    public static List<Plot> A1 = new ArrayList<>();
    public static List<Plot> A1DomesticGates = new ArrayList<>();
    public static List<Plot> A1Domestic = new ArrayList<>();
    public static List<Plot> A1DomesticLarge = new ArrayList<>();
    public static List<Plot> A1Gates = new ArrayList<>();
    public static List<Plot> A2 = new ArrayList<>();
    public static List<Plot> A2Large = new ArrayList<>();
    public static List<Plot> A2Gates = new ArrayList<>();
    public static List<Plot> A3 = new ArrayList<>();

    public static List<Plot> AllPlots = new ArrayList<>();
    public static List<ModifiedPlot> modifiedPlots = new ArrayList<>();
    public static List<String> AllPlotsInString = new ArrayList<>();

    public static List<Flight> dailyList = new ArrayList<>();
    public static List<String> dailyListOnlyFlightName = new ArrayList<>();
    public static List<Flight> ArchiveList = new ArrayList<>();
    public static long timeDiff;
    public static final long groundTimeLimit=120;
    public static final Plot emptyPlot = new Plot("","",0,null);
    public static final Flight emptyFlight = new Flight("","","","","","","","","",0,null,"");

    public Flight getFlightFromDaily(Flight f){
        for(int i=0;i<dailyList.size();i++){
            if(dailyList.get(i).getName().equals(f.getName())){
                return dailyList.get(i);
            }
        }
        return f;
    }

    public int category(Plot p){

        if(catA.contains(p.getBodyType())) return 1;
        if(catB.contains(p.getBodyType())) return 2;
        if(catC.contains(p.getBodyType())) return 3;
        if(catD.contains(p.getBodyType())) return 4;
        if(catE.contains(p.getBodyType())) return 5;
        if(catF.contains(p.getBodyType())) return 6;

        return 10;
    }
    public static int category(Flight p){
        if(catA.contains(p.getBodyType())) return 1;
        if(catB.contains(p.getBodyType())) return 2;
        if(catC.contains(p.getBodyType())) return 3;
        if(catD.contains(p.getBodyType())) return 4;
        if(catE.contains(p.getBodyType())) return 5;
        if(catF.contains(p.getBodyType())) return 6;

        return 10;
    }

    public List<Plot> suitablePlotsFortheFlight(Flight f){
        List<Plot> possiblePlots = new ArrayList<>();
        List<Flight> flightsOnThepark = new ArrayList<>();
        for(int i=0;i<dailyList.size();i++){
            if(dailyList.get(i).getPlot().equals(f.getPlot())){
                flightsOnThepark.add(dailyList.get(i));
            }
        }

        for(int i=0;i<flightsOnThepark.size();i++){

        }

        return possiblePlots;
    }

    public static List<Plot> combineLists(List<Plot>... lists){
        List<Plot> p = new ArrayList<Plot>();
        for(List<Plot> l: lists){
            p.addAll(l);
        }
        return p;
    }
    public static void printPlotList(List<Plot> Arr){
        for(Plot b: Arr){
            System.out.println(b.printPlot());
        }
    }

    public static void printModifiedPlotList(List<ModifiedPlot> pl){
        for(ModifiedPlot f: pl){
            System.out.println(f.toString());
        }
    }
    public static void printList(List<Flight> pl){
        for(Flight f: pl){
            System.out.println(f.toString());
        }
    }
    public long dateDiff(Date d1, Date d2){
        long dif = d2.getTime()-d1.getTime();
        long diffMinutes = (dif / (60 * 1000));
        return diffMinutes;
    }

    public Date getDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date d1 = null;
        Date ronDate = new Date();
        if(date.contains("RON")){
            long l = ronDate.getTime() + 2592000000L; //1 aya denk gelen saniye
            return new Date(l);
        }
        if(date.equals("") || date.equals(null)){
            long l = ronDate.getTime() + (2592000000L *120); //10 seneye denk gelen saniye
            return new Date(l);
        }
        try {
            d1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d1;
    }

   public LocalDate getLocalDate(String date){
       DateTimeFormatter format;
       format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
       LocalDate d1 = LocalDate.parse(date,format);

       return d1;
   }

    public Plot toPlot(String plotName){

        for(int i=0; i<AllPlots.size();i++){
            if(plotName.equals(AllPlots.get(i).getName())){
                return AllPlots.get(i);
            }
        }
        //System.out.println("There is no such park name.."+plotName);
        return emptyPlot;
    }
    public Plot getGateLetter(String plot,String letter){
        List<String> l = Arrays.asList(GatesWithLetter);
        Plot p = toPlot(plot);
        if(l.contains(plot.toUpperCase())){
            StringBuilder s = new StringBuilder();
            s.append(plot);
            s.append(letter);
            p = toPlot(s.toString());
        }
            return p;
    }
    public Plot getGateWithoutLetter(Plot p){
        StringBuilder s=new StringBuilder();
        String plotName = p.getName().toUpperCase();
        s.append(plotName);
        if(plotName.contains("A") || plotName.contains("B")){
            s.deleteCharAt(s.length()-1);
            return toPlot(s.toString());
        }else{
            return p;
        }
    }

    public Flight getFlightWithlatestSTD(Plot p){
        List<String> l = Arrays.asList(GatesWithLetter);
        if(l.contains(p)){
            Plot pA = getGateLetter(p.getName(),"A");
            Plot pB = getGateLetter(p.getName(),"B");
            Date dA = getDate(pA.getFlight().getSTD());
            Date dB = getDate(pB.getFlight().getSTD());
             if( dateDiff(dA,dB)<=0) return pA.getFlight();
             else return pB.getFlight();
        }else{
            return p.getFlight();
        }
    }

    public void allocPlot(Flight f){
        long groundTime = dateDiff(getDate(f.getSTA()), getDate(f.getSTD()));
        System.out.println(f.getName()+" "+"ground time: "+ groundTime);
        if(f.getTerminal()==1){
            if(f.isDosmesticFlight()){
                if(groundTime<=groundTimeLimit){
                    findPlace(f,A1DomesticGates);
                }
                findPlace(f,A1DomesticLarge);
                findPlace(f,A1Domestic);
                findPlace(f,A1);
            }else{
                if(groundTime<=groundTimeLimit){
                    findPlace(f,A1Gates);
                }
                findPlace(f,A1);
                findPlace(f,A1Domestic);
                findPlace(f,A1DomesticLarge);
                //findPlace(f,A1Gates);
            }
        }else if(f.getTerminal()==2){
            if(groundTime<=120){
                findPlace(f,A2Gates);
            }
            findPlace(f,A2);
            findPlace(f,A3);
            findPlace(f,A2Large);
            //findPlace(f,A2Gates);
        }else{
            System.out.println("unknown terminal for "+ f.getName());
        }
        if(f.getPlot().getName()=="0"){
            System.out.println("couldnt find a proper place for "+f.getName());
        }
    }



    public void findPlace(Flight f, List<Plot> pl){

        List<String> l = Arrays.asList(GatesWithLetter);
        try{
            if(f.getPlot()==emptyPlot){
                for(int i=0;i< pl.size();i++){

                    long dif=0;//
                    if(!pl.get(i).getFlight().getName().equals("")){
                        String plotHND="";
                        try{
                            plotHND = pl.get(i).getFlight().getHnd();
                        }catch(NullPointerException e){
                            System.out.println("cant get plot handling...");
                        }
                        //System.out.println("step1");
                        try{
                            dif = dateDiff(getDate(pl.get(i).getFlight().getSTD()), getDate(f.getSTA()));
//                            System.out.println("diff calculated");
                        }catch(Exception e){
                            System.out.println("cant get time difference between plot and flight "+e.getMessage());
                        }
                            if(category(f)==category(pl.get(i))) {
                                if (dif > timeDiff && (f.getHnd().equals(plotHND))) {
                                    pl.get(i).setFlight(f);
                                    Plot p =  getGateWithoutLetter(pl.get(i));
                                    p.setFlight(getFlightWithlatestSTD(pl.get(i)));
                                    f.setPlot(pl.get(i));
                                    if(l.contains(pl.get(i).getName())){
                                        getGateLetter(pl.get(i).getName(),"A").setFlight(f);
                                        getGateLetter(pl.get(i).getName(),"B").setFlight(f);
                                    }

                                    System.out.println(f.getName() + " : " + pl.get(i).getName());

                                    return;
                                }
                            }
                    }else{
                        System.out.println("plot empty");
                        if(category(f)==category(pl.get(i))){
                            pl.get(i).setFlight(f);
                            Plot p =  getGateWithoutLetter(pl.get(i));
                            p.setFlight(getFlightWithlatestSTD(pl.get(i)));
                            f.setPlot(pl.get(i));
                            if(l.contains(pl.get(i).getName())){
                                getGateLetter(pl.get(i).getName(),"A").setFlight(f);
                                getGateLetter(pl.get(i).getName(),"B").setFlight(f);
                            }

                            return;
                        }
                    }

                }//for loop

                for(int i=0;i<pl.size();i++){

                    long dif=0;
                    //System.out.println("isnull?: "+pl.get(i).getFlight());
                    //String plotHND="";
                    if(!pl.get(i).getFlight().getName().equals("")){
                        String plotHND="";
                        try{
                            plotHND = pl.get(i).getFlight().getHnd();
                        }catch(NullPointerException e){
                            System.out.println("cant get plot handling...");
                        }
                        //System.out.println("step1");
                        try{
                            dif = dateDiff(getDate(pl.get(i).getFlight().getSTD()), getDate(f.getSTA()));
                            //System.out.println("diff calculated");
                        }catch(Exception e){
                            System.out.println("cant get time difference between plot and flight... "+e.getMessage());
                        }
                        if(category(f)<=category(pl.get(i)) && dif > timeDiff) {
                                pl.get(i).setFlight(f);
                                Plot p =  getGateWithoutLetter(pl.get(i));
                                p.setFlight(getFlightWithlatestSTD(pl.get(i)));
                                f.setPlot(pl.get(i));
                                if(l.contains(pl.get(i).getName())){
                                    getGateLetter(pl.get(i).getName(),"A").setFlight(f);
                                    getGateLetter(pl.get(i).getName(),"B").setFlight(f);
                                }
                                 System.out.println(f.getName() + " : " + pl.get(i).getName());

                                return;
                        }
                    }else{
                        System.out.println("plot empty");
                        if(category(f)<=category(pl.get(i))){
                            pl.get(i).setFlight(f);
                            Plot p =  getGateWithoutLetter(pl.get(i));
                            p.setFlight(getFlightWithlatestSTD(pl.get(i)));
                            f.setPlot(pl.get(i));
                            if(l.contains(pl.get(i).getName())){
                                getGateLetter(pl.get(i).getName(),"A").setFlight(f);
                                getGateLetter(pl.get(i).getName(),"B").setFlight(f);
                            }

                            return;
                        }
                    }
                }
            }else{
                System.out.println(f.getName()+" has already a park spot. plot: "+f.getPlot().getName());
            }
        }catch(NullPointerException e){
            System.out.println("FindPlace function doesnt work.."+e.getMessage());
        }

    }


    public Plot getFlightPlot(String plotName){
        for(int i=0;i<AllPlots.size();i++){
            if(plotName.equals(AllPlots.get(i).getName())) return AllPlots.get(i);
        }
        return emptyPlot;
    }
    public Plot getPlotByName(String pName, List<Plot> pl){
        for(int i=0;i<pl.size();i++){
            if(pl.get(i).getName().equals(pName)) return pl.get(i);
        }
        return emptyPlot;
    }

    public boolean isExist(Plot p){
        if(AllPlots.contains(p)) return true;
        else return false;
    }

    public void assignToGroups(){
        List<String> l = Arrays.asList(plotGroups);
        for(int i = 0; i< AllPlots.size(); i++){
            String plotGroup = AllPlots.get(i).getFlight().getCompanyName(); //company name'e grupadları atandı
            if(plotGroup.equals("A1")) {
                A1.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A1Gates")) {
                A1Gates.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A1DomesticGates")) {
                A1DomesticGates.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A1Domestic")) {
                A1Domestic.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A1DomesticLarge")) {
                A1DomesticLarge.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A2")) {
                A2.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A2Large")) {
                A2Large.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A2Gates")) {
                A2Gates.add(AllPlots.get(i));
            }
            if(plotGroup.equals("A3")) {
                A3.add(AllPlots.get(i));
            }
        }
        System.out.println();
        Aerodrome.printPlotList(A1);
        System.out.println();
        Aerodrome.printPlotList(A1DomesticGates);
        System.out.println();
        Aerodrome.printPlotList(A1DomesticLarge);
        System.out.println();
        Aerodrome.printPlotList(A1Domestic);
        System.out.println();
        Aerodrome.printPlotList(A2Gates);
        System.out.println();
        Aerodrome.printPlotList(A2);
        System.out.println();
        Aerodrome.printPlotList(A2Large);
        System.out.println();
        Aerodrome.printPlotList(A3);
        System.out.println();
        System.out.println("All Plots have been assigned to groups...");
        System.out.println(A1.size()+A1Gates.size()+A1DomesticLarge.size()+A1Domestic.size()+A1DomesticGates.size()+
                            A2.size()+A2Large.size()+A2Gates.size()+A3.size()+" plots loaded");
    }

    public void loadParkingPlots(){
        loadPlotsTable();
        refreshPlots();
        assignToGroups();
        getAllPlotsInString(AllPlots);
    }
    public void refreshPlots(){
        for(int i=0;i<dailyList.size();i++){
            dailyList.get(i).getPlot().setFlight(dailyList.get(i));
        }
    }
    public void loadPlotsTable(){
        DataSource dt = new DataSource();
        dt.open();
        try{
            AllPlots = dt.loadPlots();
        }catch(SQLException e){
            System.out.println("cant reach database.."+e.getMessage());
        }
        Aerodrome.printPlotList(AllPlots);
        System.out.println();
        System.out.println("-------------All Plots loaded----------------");
        dt.close();
    }

    public void getAllPlotsInString(List<Plot> p){
        for(int i=0; i<p.size(); i++){
            AllPlotsInString.add(p.get(i).getName());
        }
    }
    public void getFlightNamesforDailyList(List<Flight> f){
        for(int i=0; i<f.size(); i++){
            dailyListOnlyFlightName.add(f.get(i).getName());
        }
    }
}
