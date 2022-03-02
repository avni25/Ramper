package common.controllerFiles;

import common.Aerodrome;
import common.Flight;
import common.Plot;
import databasefiles.DataSource;
import databasefiles.ExcelData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class MainController extends Aerodrome {

    @FXML
    private Pane mainPane;
    @FXML
    private Label labelTime;
    @FXML
    private TextField textSearch;
    @FXML
    private TextField textCallSign;
    @FXML
    private TextField textReg;
    @FXML
    private TextField textSTAHour;
    @FXML
    private TextField textSTDHour;
    @FXML
    private TextField textDepSign;
    @FXML
    private TextField textFrom;
    @FXML
    private TextField textTo;
    @FXML
    private TextField textCompany;
    @FXML
    private DatePicker DateSTA;
    @FXML
    private DatePicker DateSTD;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private ComboBox<String> comboHND;
    @FXML
    private ComboBox<Integer> comboTerminal;
    @FXML
    private ComboBox<String> comboPark;
    @FXML
    private ComboBox<Integer> comboTimeDif;
    @FXML
    private ComboBox<String> comboAllocFrom;
    @FXML
    private ComboBox<String> comboAllocTo;
    @FXML
    private TableView<Flight> flightListTable;
    @FXML
    private TableColumn<Flight, String> col_FlightName;
    @FXML
    private TableColumn<Flight, String> terminal;
    @FXML
    private TableRow<Flight> row;
    @FXML
    private ObservableList<Flight> Olist;
//    private FilteredList<Flight> filteredList = new FilteredList<>(Olist, e->true);

    //------------------------GLOBAl VARIABLES----------------------------------------
    Flight p = new Flight();
    final KeyCombination keyShift = new KeyCodeCombination(KeyCode.DELETE, KeyCombination.SHIFT_DOWN);
    //-------------------------------------------------------------------------------------
    public void initialize(){
        loadParkingPlots();
        loadFlightTable();
        printList(dailyList);
        System.out.println("--------------------------------------------------");

        comboType.getItems().addAll("","A319","A320","A321","A330","A333",
                                    "B737","B738","B739","B747","B752","B753","B762","B763","B772","B773","MD80","MD82");
        comboType.setValue("");
//        comboType.setEditable(true);
        comboHND.getItems().addAll("","HVŞ","TGS","ÇHS");
        comboTerminal.getItems().addAll(0,1,2);
        comboTerminal.setValue(0);
        comboTimeDif.getItems().addAll(20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,110,120,130,140,150,160,170,180);
        comboTimeDif.setValue(30);
        comboAllocFrom.getItems().addAll("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00"
                                        ,"05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30"
                ,"10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30"
                ,"17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30");
        comboAllocFrom.setValue("00:00");
        comboAllocTo.getItems().addAll("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00"
                ,"05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30"
                ,"10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30"
                ,"17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30");
        comboAllocTo.setValue("23:59");
        comboPark.getItems().addAll(AllPlotsInString);
        comboPark.setEditable(true);
        DateSTA.setValue(LocalDate.now());
        DateSTA.setEditable(true);
        DateSTD.setValue(LocalDate.now());

        //        col_FlightName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        col_FlightREG.setCellValueFactory(new PropertyValueFactory<>("registration"));
//        flightListTable.setItems(Olist);
        flightListTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);



    }

    @FXML
    public void handleOnKeyReleased(KeyEvent e) throws SQLException{
//        if(keyShift.match(e)) deleteFlight();
      if(e.getCode().equals(KeyCode.DELETE)) cleanPlotsFromSelectedFlight();
      else if(e.getCode().equals(KeyCode.ENTER)) updateSelectedFlight();


    }
    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            labelTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void onClickButton_SaveDailyToDatabase()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        //List<Flight> DataBaseFlightsList  = dt.loadFlights();

            for(int i=0;i<dailyList.size();i++){
                dt.addToDataBase(dailyList.get(i));
            }

        dt.close();
    }

    @FXML
    public void onClickButton_CleanDailyList()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        dt.removeDailyList();
        dt.close();
        infoBox("Daily list deleted","Inforrmation");
        loadFlightTable();
        flightListTable.refresh();
    }

    @FXML
    public void onClickButton_CleanArchiveList()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        dt.removeArchiveList();
        dt.close();
        infoBox("Archive list deleted","Inforrmation");
        loadFlightTable();
        flightListTable.refresh();
    }
    @FXML
    public void saveFlightsButton()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        for(int i=0;i<AllPlots.size();i++){
            try{
                dt.updatePlotsFlight(AllPlots.get(i));
            }catch(NullPointerException e){
                System.out.println(AllPlots.get(i).getName()+"has no park plot");
            }

        }
        loadPlotsTable();
        System.out.println("Parks saved to database");
        dt.close();
    }
    @FXML
    public void AllocSelectedFlight()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        Flight f = getFlightFromDaily(getNewFlight());
        allocPlot(f);
        dt.updateFlight(f);
        dt.updatePlotsFlight(f.getPlot());
        dt.close();
        loadFlightListToTable(dailyList);
        flightListTable.refresh();
    }

    @FXML
    public void AllocButton()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        timeDiff = (long)comboTimeDif.getValue();
        Aerodrome ae = new Aerodrome();
        ExcelData ex = new ExcelData();
//        if(comboAllocTo.getValue().equals(null) || comboAllocTo.getValue().equals("")){
//            comboAllocTo.setValue("23:59");
//        }
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        String hourFromComboTo = dateFormat.format(date)+" "+comboAllocTo.getValue();
        for(int i=0; i<dailyList.size();i++){

            if(dateDiff(getDate(dailyList.get(i).getSTA()), getDate(hourFromComboTo))>=0){
                ae.allocPlot(dailyList.get(i));
                dt.updateFlight(dailyList.get(i));
            }
        }
        dt.close();
        loadFlightListToTable(dailyList);
        flightListTable.refresh();
    }
    public void cleanTable(){
        for(int i=0;i<flightListTable.getItems().size();i++){
            flightListTable.getItems().clear();
        }
    }
    @FXML
    public void printButton(){
//        ExcelData ex = new ExcelData();
//        System.out.println(dailyList.get(0).getName()+","+dailyList.get(0).getSTA()+","+dailyList.get(0).getSTD());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
//        long l = dateDiff(getDate(dailyList.get(0).getSTA()),getDate(dailyList.get(0).getSTD()));
//        System.out.println(dailyList.get(0).getSTD());
//        System.out.println(dailyList.get(0).getSTA());
//        String s = ex.getFileDate(ex.excelFileName);
//        System.out.println(ex.excelFileName);
//
//        System.out.println(getDate(s+" "+dailyList.get(0).getSTA()));
//        System.out.println(l);
//        findPlace(dailyList.get(0),AllPlots);
//        System.out.println(dailyList.get(1).getBodyType()+" flight category: "+category(dailyList.get(1)));
//        System.out.println(dailyList.get(1).getTo()+" ==="+dailyList.get(1).isDosmesticFlight());
//        Plot p = getGateLetter(comboPark.getValue(),"A");
//        System.out.println(p.getName());

//        Plot p = toPlot(comboPark.getValue());
//        String s = getGateWithoutLetter(p).getName();
//        System.out.println(s);
//        getGateWithoutLetter(p).setFlight(emptyFlight);
//        System.out.println("name is: "+p.getName());
//        for(int i=0;i<AllPlots.size();i++){
//            System.out.println(AllPlots.get(i).getName()+": "+AllPlots.get(i).getFlight().getName());
//        }

        for(String s :dailyListOnlyFlightName){
            System.out.print(s+",");
        }
        System.out.println();
        System.out.println("----------------");
        for(Flight f :dailyList){
            System.out.print(f.getName()+",");
        }
        System.out.println();
        System.out.println("----------------");

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        String hourFromComboTo = dateFormat.format(date)+" "+comboAllocTo.getValue();
        System.out.println(hourFromComboTo);


    }
    @FXML
    private void setExcelFileURL()throws IOException {
        ExcelData ex  =new ExcelData();
        ex.excelFileURL =getFileDirectory(); // sonrasında secilen dosyanın excel olup olmadığını kontrol et
        //ex.readExcelFile(ex.excelFileURL);
        dailyList.addAll(ex.readExcelFile2(ex.excelFileURL));
        loadFlightListToTable(dailyList);
        try {
            onClickButton_SaveDailyToDatabase();
        }catch(SQLException e){
            System.out.println("Flight list cant be saved to database. "+e.getMessage());
        }

    }

    private String getFileDirectory(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select File");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(true);

        try{
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                if(chooser.getCurrentDirectory().toString()=="") return "";
                ExcelData.excelFileName = chooser.getSelectedFile().getName();
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                System.out.println("getSelectedFilename() : " + chooser.getSelectedFile().getName());
            }else {
                System.out.println("canceled");
                return "";
            }
            return chooser.getSelectedFile().toString();
        }catch(Exception e){
            System.out.println("No Selection ");
            return "";
        }


    }
    private Flight getNewFlight(){
        String name = this.textCallSign.getText();
        String bodyType = this.comboType.getSelectionModel().getSelectedItem();
        String registration = this.textReg.getText();
                 String dateSTA = this.DateSTA.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String STA =dateSTA +" "+this.textSTAHour.getText();
                 String dateSTD = this.DateSTD.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String STD =dateSTD +" "+ this.textSTDHour.getText();
        String DepSign = this.textDepSign.getText();
        String from = this.textFrom.getText();
        String to = this.textTo.getText();
        String hnd = this.comboHND.getSelectionModel().getSelectedItem();
        int terminal = this.comboTerminal.getSelectionModel().getSelectedItem();
        Plot pl;


        String s;
        try{
            s = this.comboPark.getSelectionModel().getSelectedItem().toString();
        }catch(Exception e){
            s="";
        }

        pl= toPlot(s);
        String company = this.textCompany.getText();

        return new Flight(name,bodyType,registration,STA,STD,DepSign,from,to,hnd,terminal,pl,company);
    }
    @FXML
    public void onClickMenuItemAdd(){
        DataSource dt = new DataSource();
        Flight p = getNewFlight();
        dt.open();

        try{
            dt.addToDataBase(p);
            System.out.println(p.toString());

        }catch(SQLException e){
            System.out.println(p.getName()+" can not be added into database." + e.getMessage());

            return;
        }
        dt.close();
        loadFlightTable();

        this.textCallSign.setText("");
        this.textReg.setText("");
        this.textSTAHour.setText("");
        this.textDepSign.setText("");
        this.textFrom.setText("");
        this.textTo.setText("");
        this.textCompany.setText("");
        this.comboPark.setValue("");

    }

    @FXML
    public void TableOnClick(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Flight f = flightListTable.getSelectionModel().getSelectedItem();
        textCallSign.setText(f.getName());
        comboType.setValue(f.getBodyType());
        comboPark.setValue(f.getPlot().getName());
        comboTerminal.setValue(f.getTerminal());
        comboHND.setValue(f.getHnd());
        textReg.setText(f.getRegistration());
        textCompany.setText(f.getCompanyName());
        textFrom.setText(f.getFrom());
        textTo.setText(f.getTo());
        textDepSign.setText(f.getDepCallSign());
         String[] DateAndTimeSTA = f.getSTA().split(" ");
        textSTAHour.setText(DateAndTimeSTA[1]);
                            String dateSTA = DateAndTimeSTA[0];
                            LocalDate localDateSTA = LocalDate.parse(dateSTA, formatter);
        DateSTA.setValue(localDateSTA);
        String[] DateAndTimeSTD = f.getSTD().split(" ");
        textSTDHour.setText(DateAndTimeSTD[1]);
                        String dateSTD = DateAndTimeSTD[0];
                        LocalDate localDateSTD = LocalDate.parse(dateSTD, formatter);
        DateSTD.setValue(localDateSTD);

    }

    private void loadFlightListToTable(List<Flight> f){

        if(f==null){
            System.out.println("Flight List is Empty");
            return;
        }
        Olist = FXCollections.observableList(f);
        flightListTable.setItems(Olist);
    }

    @FXML
    public void loadFlightTable(){
        DataSource dt = new DataSource();
        dt.open();
        flightListTable.getItems().removeAll();
        try{
            //List<Flight> c  = dt.loadFlights();
            dailyList=dt.loadFlights();

            getFlightNamesforDailyList(dailyList);
            if(dailyList==null){
                System.out.println("Flight List is Empty");
                return;
            }
            Olist = FXCollections.observableList(dailyList);
             flightListTable.setItems(Olist);
//            for(int i=0; i<Olist.size();i++){
//                flightListTable.getItems().add(new Flight(c.get(i).getName(),c.get(i).getBodyType(),c.get(i).getRegistration(),
//                                                          c.get(i).getSTA(),c.get(i).getSTD(),c.get(i).getDepCallSign(),
//                                                          c.get(i).getFrom(),c.get(i).getTo(),c.get(i).getHnd(),c.get(i).getTerminal(),
//                                                        c.get(i).getPlot(),c.get(i).getCompanyName()));
//            }
            System.out.println("Flight table loaded...");
        }catch(SQLException e){
            System.out.println("cant load Flight List. "+e.getMessage());

        }

        dt.close();
    }

    @FXML
    public void updateSelectedFlight(){
        DataSource dt = new DataSource();
        dt.open();
        Flight f = getNewFlight();

        try{
            dt.updateFlight(f);
            System.out.println(f.getName()+ " updated..");
        }catch(SQLException e){
            System.out.println("cant update Flight.."+ e.getMessage());
        }

        dt.close();
        loadFlightTable();
        flightListTable.refresh();
    }
    @FXML
    public void deleteParkOfSelectedFlight(){
        DataSource dt = new DataSource();
        dt.open();
        Flight f = flightListTable.getSelectionModel().getSelectedItem();
        f.setPlot(emptyPlot);
        try{
            dt.updateFlight(f);
            System.out.println(f.getName()+ " updated..");
        }catch(SQLException e){
            System.out.println("cant update Flight.."+ e.getMessage());
        }

        dt.close();
        loadFlightTable();
        flightListTable.refresh();
    }
    @FXML
    public void updatePlots(){
        DataSource dt = new DataSource();
        dt.open();
       Flight f = getNewFlight();
       Plot p = f.getPlot();
//        p.setName(this.textName.getText());
//        p.setAmount(Integer.parseInt(this.textAmount.getText()));
//        p.setCost(Double.parseDouble(this.textCost.getText()));
//        p.setPrice(Double.parseDouble(this.textPrice.getText()));
        try{
            dt.updatePlots(p, "A1DomesticGates");
            System.out.println("plot number: "+p.getName()+" updated...");
        }catch(SQLException e){
            System.out.println("error.."+e.getMessage());
            return;
        }
        dt.close();
    }
    @FXML
    public void cleanPlotsFlight(){
        DataSource dt = new DataSource();
        dt.open();
        for(int i=0;i<AllPlots.size();i++){
            try{
                dt.cleanPlotsFlight(AllPlots.get(i));
                AllPlots.get(i).setFlight(emptyFlight);
                System.out.println("plot number: "+p.getName()+" updated...");
            }catch(SQLException e){
                System.out.println("error.."+e.getMessage());
                return;
            }
        }
        dt.close();
        loadPlotsTable();
        flightListTable.refresh();
    }
    @FXML
    public void cleanPlotsFromFlight()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        for(int i=0;i<dailyList.size();i++){
            Plot p = dailyList.get(i).getPlot();
            p.setFlight(emptyFlight);
            dailyList.get(i).setPlot(emptyPlot);
            dt.updateFlight(dailyList.get(i));
        }
        updatePlots();
        loadFlightTable();
        flightListTable.refresh();
    dt.close();
    }
    public void cleanPlotsFromSelectedFlight()throws SQLException{
        DataSource dt = new DataSource();
        dt.open();
        Flight f = flightListTable.getSelectionModel().getSelectedItem();
            Plot p = f.getPlot();
            p.setFlight(emptyFlight);
            f.setPlot(emptyPlot);
            dt.updateFlight(f);
        updatePlots();
        loadFlightTable();
        flightListTable.refresh();
        dt.close();
    }

    @FXML
    public void deleteFlight(){
        DataSource dt  =new DataSource();
        dt.open();
        p = flightListTable.getSelectionModel().getSelectedItem();
        int i = dailyList.indexOf(p);
        System.out.println("index: "+i);
        if(p == null) {
            System.out.println("No Flight Selected");
            return;
        }
        try{
            dt.removeFlight(p.getName());
        }catch(SQLException e){
            System.out.println("couldnt delete.. "+e.getMessage());
            return;
        }
        dt.close();
        loadFlightTable();

        flightListTable.getSelectionModel().select(i);
        flightListTable.refresh();
    }

    private void cleanTexts(){
        textCallSign.setText("");
        comboType.setValue("");
        comboPark.setValue("");
        comboTerminal.setValue(0);
        comboHND.setValue("");
        textCompany.setText("");
        textFrom.setText("");
        textTo.setText("");
        textDepSign.setText("");
        textSTDHour.setText("");
        textSTAHour.setText("");
        textReg.setText("");
    }
//    @FXML
//    public void searchFlight(javafx.scene.input.KeyEvent keyEvent) {
//        textSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
//            filteredList.setPredicate((Predicate<? super Flight>) (Flight f) -> {
//                if(newValue.isEmpty() || newValue==null){
//                    return true;
//                }else if(f.getName().contains(newValue)){
//                    return true;
//                }
//                return false;
//            });
//        }));
//
//        SortedList sort = new SortedList(filteredList);
//        sort.comparatorProperty().bind(flightListTable.comparatorProperty());
//        flightListTable.setItems(sort);
//    }

    @FXML
    public void onClick_EditPlots(){
        try{
            FXMLLoader fxmlLoader  =new FXMLLoader(getClass().getResource("../fxmlFiles/EditPlots.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage s = new Stage();
            s.setTitle("Airport Plots");
            s.setResizable(true);
            s.setScene(new Scene(root1, 600,500));
            s.show();
        }catch(Exception e){
            System.out.println("cant open Plots. "+e.getMessage());
        }
    }

    @FXML
    public void onClick_GoToChart(){
        try{
            FXMLLoader fxmlLoader  =new FXMLLoader(getClass().getResource("../fxmlFiles/ChartMain.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            Stage s = new Stage();
            s.setTitle("Airport Chart");
            s.initModality(Modality.APPLICATION_MODAL);  // cant interact with other windows unless this window is closed.
            s.setResizable(false);
            s.setScene(new Scene(root1, 1300,600));
            s.show();
        }catch(Exception e){
            System.out.println("cant open chart. "+e.getMessage());
        }

    }
    @FXML
    public void onClick_GoToArchive(){
        try{
            FXMLLoader fxmlLoader  =new FXMLLoader(getClass().getResource("../fxmlFiles/ArchiveMain.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            Stage s = new Stage();
            s.setTitle("Archive");
            s.initModality(Modality.APPLICATION_MODAL);  // cant interact with other windows unless this window is closed.
            s.setResizable(false);
            s.setScene(new Scene(root1));
            s.show();
        }catch(Exception e){
            System.out.println("cant open archive. "+e.getMessage());
        }

    }
    @FXML
    public void onClick_Statistics(){
        try{
            FXMLLoader fxmlLoader  =new FXMLLoader(getClass().getResource("../fxmlFiles/statistics.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            Stage s = new Stage();
            s.setTitle("Statistics");
            s.initModality(Modality.APPLICATION_MODAL);  // cant interact with other windows unless this window is closed.
            s.setResizable(false);
            s.setScene(new Scene(root1));
            s.show();
        }catch(Exception e){
            System.out.println("cant open statistics. "+e.getMessage());
        }

    }
    @FXML
    public void onClick_AddToArchive(){
        DataSource dt = new DataSource();
        dt.open();
        Flight f = flightListTable.getSelectionModel().getSelectedItem();
        try{
            dt.addToArchive(f);
            System.out.println("Flight have been added to archive.");
        }catch(SQLException e){
            System.out.println("cant add to database. "+e.getMessage());
        }

        dt.close();
    }

    @FXML
    public void onClick_AddAllToArchive(){
        DataSource dt = new DataSource();
        dt.open();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        Date date = new Date();
        String currentTime = dateFormat.format(date);
        List<Flight> unplottedFlights = new ArrayList<>();
        try{
            for(int i=0; i<dailyList.size();i++){
                String plotName = dailyList.get(i).getPlot().getName();
                String STA = dailyList.get(i).getSTA();
                if(plotName.equals(null) || plotName.equals("")){
                    unplottedFlights.add(dailyList.get(i));

                }else{

                    if(dateDiff(getDate(currentTime),getDate(STA))<0){
                        dt.addToArchive(dailyList.get(i));
                        dt.removeFlight(dailyList.get(i).getName());

                    }
                }
            }
            System.out.println("Flight have been added to archive.");
        }catch(SQLException e){
            System.out.println("cant add to database. "+e.getMessage());
        }
//        Olist = FXCollections.observableList(unplottedFlights);
//        flightListTable.setItems(Olist);
        loadFlightTable();
        dt.close();
    }

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}
