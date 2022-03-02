package common.controllerFiles;

import common.Flight;
import databasefiles.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StatisticsController {

    @FXML
    private ListView companyList;
    @FXML
    private Label label1;
    @FXML
    private ObservableMap<String, Integer> Omap;
    public static Map<String, Integer> companiesList = new HashMap<>();

    public void initialize()throws SQLException{
//        Olist = FXCollections.observableList(dailyList);
//        flightListTable.setItems(Olist)
        getCompanyList();
        Omap = FXCollections.observableMap(companiesList);
        companiesList.put("qwee",1);
        label1.setText(companiesList.keySet().toString());
    }

    public void getCompanyList() throws SQLException {
        DataSource dt = new DataSource();
        dt.open();
        companiesList = dt.statistics_dailyFlightsByCompanies();
        dt.close();
    }

}
