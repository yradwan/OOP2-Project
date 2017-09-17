
//set stuff as editable false by default and maybe do a pop up
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ProcedureManagement {
    private static Stage window = new Stage();
    private static TableView<Procedure> table1;
    private static TextField nameInput;
    private static TextField costInput;
    private static Button saveButton;
    private static ObservableList<Procedure> procedures;
    private static Label label;

    public static void management(){


        window.setTitle("Dentist Application");
        window.initModality(Modality.APPLICATION_MODAL);
        label = new Label("Procedures Successfully Loaded");
        TableColumn<Procedure, String> procNameColumn = new TableColumn<>("Procedure Name");
        procNameColumn.setMinWidth(200);
        procNameColumn.setCellValueFactory(new PropertyValueFactory<>("procName"));

        //Price column
        TableColumn<Procedure, Double> procCostColumn = new TableColumn<>("Procedure Cost");
        procCostColumn.setMinWidth(100);
        procCostColumn.setCellValueFactory(new PropertyValueFactory<>("procCost"));

        //Quantity column
        TableColumn<Procedure, Integer> procNumberColumn = new TableColumn<>("Procedure Number");
        procNumberColumn.setMinWidth(200);
        procNumberColumn.setCellValueFactory(new PropertyValueFactory<>("procNo"));


        Button addButton = new Button("Add Procedure");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete Procedure");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        saveButton = new Button("Save Procedures");
        saveButton.setOnAction(e -> saveButtonClicked());

        //Name input
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        //Price input
        costInput = new TextField();
        costInput.setPromptText("Cost");


        table1 = new TableView<>();
        table1.setItems(getProcedure());
        table1.getColumns().addAll(procNameColumn, procCostColumn, procNumberColumn);





        //bPlane.getChildren().add(rightLayout);

        HBox bottomLayout2 = new HBox(10);
        bottomLayout2.getChildren().addAll(nameInput, costInput);
        HBox bottomLayout3 = new HBox(10);
        bottomLayout3.getChildren().add(label);
        HBox bottomLayout = new HBox(10);
        bottomLayout.getChildren().addAll(addButton, deleteButton, saveButton);
        bottomLayout.setPadding(new Insets(10, 10, 10 , 80));
        VBox layout = new VBox(10);
        layout.getChildren().addAll(table1, bottomLayout2, bottomLayout, bottomLayout3);
        bottomLayout2.setPadding(new Insets(10, 10, 10 , 85));
        bottomLayout3.setPadding(new Insets(10, 10, 10 , 145));

        Scene scene = new Scene(layout, 505, 550);
        window.setMinHeight(550);
        window.setMaxWidth(505);
        window.setMinWidth(505);
        window.setMaxHeight(550);
        window.setScene(scene);
        window.showAndWait();
    }
    private static void deleteButtonClicked() {
        ObservableList<Procedure> proceduresSelected, allProcedure;
        allProcedure = table1.getItems();
        proceduresSelected = table1.getSelectionModel().getSelectedItems();
        proceduresSelected.forEach(allProcedure::remove);
    }

    public static ObservableList<Procedure> getProcedure(){
        ObservableList<Procedure> procedure = FXCollections.observableArrayList();
        try
        {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("hostelser1.txt"));
            ArrayList<Procedure> procedureBuffer = (ArrayList<Procedure>) is.readObject();
            procedure = FXCollections.observableArrayList(procedureBuffer);
            procedures = FXCollections.observableArrayList(procedureBuffer);
            is.close();


        }
        catch (Exception ex) {
            //label.setText("Could not load Patient information");
            procedure = FXCollections.observableArrayList();
            ex.printStackTrace();
            label.setText("Could not load procedures ");
        }

        return procedure;
    }
    private static void saveButtonClicked() {

            ObservableList<Procedure> tableContent;
            tableContent =  table1.getItems();
            ArrayList<Procedure> patient = new ArrayList<>();
            for (int i = 0; i < tableContent.size(); i++){
                patient.add(tableContent.get(i));
            }
            try
            {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("hostelser1.txt"));
                {
                    os.writeObject(patient);
                }
                os.close();
                label.setText("File has been successfully saved");
            } catch (Exception ex) {
                ex.printStackTrace();
                label.setText("Could not save file");
            }
    }
    public static void addButtonClicked(){
            try{
            String s = nameInput.getText();
            Double c = Double.parseDouble(costInput.getText());
            Integer n = (table1.getItems().size() + 1);

            Procedure procedure = new Procedure(s, c, n);
            table1.getItems().add(procedure);
            label.setText("Procedure Successfully Added");}
            catch (Exception e){
                e.printStackTrace();
                label.setText("Could not add new procedure ");
            }
            nameInput.clear();
            costInput.clear();

        }

        }





