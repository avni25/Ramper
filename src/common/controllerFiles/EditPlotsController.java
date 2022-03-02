package common.controllerFiles;

import common.Aerodrome;
import common.ModifiedPlot;
import common.Plot;
import databasefiles.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.text.TabableView;
import javax.xml.crypto.Data;
import java.sql.SQLException;


public class EditPlotsController  extends Aerodrome {


    @FXML
    private TextField textGroup;
    @FXML
    private TextField textName;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private ComboBox<Integer> comboApron;
    @FXML
    private TableView<ModifiedPlot> PlotTable;
    @FXML
    private ObservableList<ModifiedPlot> Olist;

    public void initialize(){
        comboType.getItems().addAll("","A319","A320","A321","A330","A333",
                "B737","B738","B739","B747","B752","B753","B762","B763","B772","B773","MD80","MD82","F900");

        comboApron.getItems().addAll(0,1,2,3);
//        loadPlotsTable();

        loadModifiedPlotTable();


    }
    @FXML
    public void TableOnClick(){
        ModifiedPlot p = PlotTable.getSelectionModel().getSelectedItem();
        textGroup.setText(p.getPlotGroup());
        textName.setText(p.getName());
        comboType.setValue(p.getBodyType());
        comboApron.setValue(p.getTerminal());
    }
    public void loadModifiedPlotsTable(){
        DataSource dt = new DataSource();
        dt.open();
        try{
            modifiedPlots = dt.loadModifiedPlots();
        }catch(SQLException e){
            System.out.println("cant reach database.."+e.getMessage());
        }
        Aerodrome.printModifiedPlotList(modifiedPlots);
        System.out.println();
        System.out.println("-------------All Plots loaded----------------");
        dt.close();
    }
    @FXML
    public void loadModifiedPlotTable(){
        DataSource dt = new DataSource();
        dt.open();
        PlotTable.getItems().removeAll();
        try{
            modifiedPlots = dt.loadModifiedPlots();
            if(modifiedPlots==null){
                System.out.println("Flight List is Empty");
                return;
            }
            Olist = FXCollections.observableList(modifiedPlots);
            PlotTable.setItems(Olist);
        }catch(SQLException e){
            System.out.println("cant load Modified plots. "+e.getMessage());
        }

        dt.close();
    }
    @FXML
    public void updatePlot(){

    }
    @FXML
    public void addPlot(){

    }
    @FXML
    public void deletePlot(){

    }

}
