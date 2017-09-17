package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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


public class PatientManagement {
    private static Stage window = new Stage();;
    private static TableView<Patient> table;
    private static Label label;
    private static Boolean bEdit = true;
    private static TextField nameInput;
    private static TextField addressInput;
    private static TextField phoneInput;
    private static TextField idInput;


    public static void management(){
        window.setTitle("Dentist Application");
        window.initModality(Modality.APPLICATION_MODAL);
        //Name column
        TableColumn<Patient, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price column
        TableColumn<Patient, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(280);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        //Quantity column
        TableColumn<Patient, String> phoneColumn = new TableColumn<>("Phone No.");
        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        //Quantity column
        TableColumn<Patient, String> idColumn = new TableColumn<>("Patient ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patientNo"));

        //Name input
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        //Price input
        addressInput = new TextField();
        addressInput.setPromptText("Address");

        //Quantity input
        phoneInput = new TextField();
        phoneInput.setPromptText("Phone No.");

        //Quantity input
        idInput = new TextField();
        idInput.setPromptText("ID");

        //Button
        Button addButton = new Button("Add");
        addButton.setMinWidth(50);
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> saveButtonClicked());
        Button exitButton = new Button("Exit Without Saving Changes");
        exitButton.setOnAction(e -> exitButtonClicked());

        HBox hBox1 = new HBox();
        hBox1.setPadding(new Insets(10,10,10,135));
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(nameInput, addressInput, addButton);

        HBox hBox3 = new HBox();
        hBox3.setPadding(new Insets(10,10,10,135));
        hBox3.setSpacing(10);
        hBox3.getChildren().addAll(phoneInput, idInput, deleteButton);

        label = new Label("Patients have been successfully loaded");
        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10,35,10,125));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(saveButton, exitButton, label);

        table = new TableView<>();
        table.setItems(getPatient());
        table.getColumns().addAll(nameColumn, addressColumn, idColumn, phoneColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox1, hBox3, hBox2);
        Scene scene = new Scene(vBox, 700, 550);
        window.setMinWidth(700);
        window.setMaxWidth(700);
        window.setMinHeight(550);
        window.setMaxHeight(550);

        window.setOnCloseRequest(e -> {
            e.consume();
            exitButtonClicked();
        });

        window.setScene(scene);
        window.showAndWait();

    }
    public static void addButtonClicked(){
        try{
        Patient patient = new Patient();
        patient.setName(nameInput.getText());
        patient.setAddress(addressInput.getText());
        patient.setPatientNo(Integer.parseInt(idInput.getText()));
        patient.setPhoneNo(Integer.parseInt(phoneInput.getText()));
        table.getItems().add(patient);
        bEdit =false;}
        catch (Exception e){
            e.printStackTrace();
            label.setText("Invalid Data");
        }
        nameInput.clear();
        addressInput.clear();
        phoneInput.clear();
        idInput.clear();
    }

    //Delete button clicked
    public static void deleteButtonClicked(){
        ObservableList<Patient> patientSelected, allPatient;
        allPatient = table.getItems();
        patientSelected = table.getSelectionModel().getSelectedItems();
        patientSelected.forEach(allPatient::remove);
        bEdit = false;
    }

    //Get all of the products
    public static ObservableList<Patient> getPatient(){
        ObservableList<Patient> patient = FXCollections.observableArrayList();
        try
        {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("hostelser.txt"));
            ArrayList<Patient> patientBuffer = (ArrayList<Patient>) is.readObject();
            patient = FXCollections.observableArrayList(patientBuffer);
            is.close();
        }
        catch (Exception ex) {
            label.setText("Could not load Patient information");
            patient = FXCollections.observableArrayList();
            ex.printStackTrace();
        }
        return patient;
    }
    private static void saveButtonClicked() {
        ObservableList<Patient> tableContent;
        tableContent =  table.getItems();
        ArrayList<Patient> patient = new ArrayList<>();
        for (int i = 0; i < tableContent.size(); i++){
            patient.add(tableContent.get(i));
        }
        try
        {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("hostelser.txt"));
            {
                os.writeObject(patient);
            }
            os.close();
            label.setText("File has been successfully saved");
            bEdit = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            label.setText("Could not save file");
        }}
    private static void exitButtonClicked() {
        if (bEdit == false){
            Confirmation.display("Warning", "Are you sure you wish to exit without saving?");
        }
        else
            window.close();
    }
}
