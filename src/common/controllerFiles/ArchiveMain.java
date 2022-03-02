package common.controllerFiles;

import common.Aerodrome;
import common.Flight;
import databasefiles.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ArchiveMain extends Aerodrome {


    @FXML
    private Label labelResult;
    @FXML
    private TextField textSearchCallSign;
    @FXML
    private TextField textSearchType;
    @FXML
    private RadioButton radCallSign;
    @FXML
    private RadioButton radType;
    @FXML
    private RadioButton radReg;
    @FXML
    private RadioButton radTer;
    @FXML
    private RadioButton radHND;
    @FXML
    private RadioButton radPark;
    @FXML
    private RadioButton radFrom;
    @FXML
    private RadioButton radTo;
    @FXML
    private TableView<Flight> ArchiveTable;
    @FXML
    private ObservableList<Flight> oList;

    public void initialize() {
        ButtonGroup bg = new ButtonGroup();
        Integer x = ArchiveList.size();
        labelResult.setText(x.toString()+" results in archive");
        loadArchiveTable();
        printList(ArchiveList);
    }


    @FXML
    public void loadArchiveTable() {
        DataSource dt = new DataSource();
        dt.open();
        ArchiveTable.getItems().removeAll();
        try {
            ArchiveList = dt.loadArchiveFlights();

        } catch (SQLException e) {
            System.out.println("cant load Archive Table. " + e.getMessage());
        }
        oList = FXCollections.observableList(ArchiveList);
        //FilteredList<Flight> filteredList = new FilteredList<>(oList);
        ArchiveTable.setItems(oList);
        dt.close();
    }

    @FXML
    public void filterByCallSign() {
        ArchiveTable.getItems().removeAll();
        Integer y = ArchiveList.size();
        String s = textSearchCallSign.getText();
        List<Flight> l = new ArrayList<>();
        if (s.toUpperCase().equals(null) || s.toUpperCase().equals("")) {
            oList = FXCollections.observableList(ArchiveList);
            labelResult.setText(y.toString()+" results in archive");
        } else {
            for (int i = 0; i < ArchiveList.size(); i++) {
                if (radCallSign.isSelected()) {
                    if (ArchiveList.get(i).getName().contains(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
                if (radType.isSelected()) {
                    if (ArchiveList.get(i).getBodyType().contains(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
                if (radReg.isSelected()) {
                    if (ArchiveList.get(i).getRegistration().contains(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
                if (radTer.isSelected()) {
                    Integer apr = ArchiveList.get(i).getTerminal();
                    String p = apr.toString();
                    if (p.contains(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
                if (radHND.isSelected()) {
                    if (ArchiveList.get(i).getHnd().contains(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
                if (radPark.isSelected()) {
                    if (ArchiveList.get(i).getPlot().getName().equals(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
                if (radFrom.isSelected()) {
                    if (ArchiveList.get(i).getFrom().contains(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
                if (radTo.isSelected()) {
                    if (ArchiveList.get(i).getTo().contains(s.toUpperCase())) l.add(ArchiveList.get(i));
                }
            }
            oList = FXCollections.observableList(l);
            Integer x = oList.size();

            labelResult.setText(x.toString()+" results found out of "+ y+" records");
        }
        ArchiveTable.setItems(oList);

    }


}



