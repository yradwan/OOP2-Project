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
import java.time.LocalDate;
import java.util.ArrayList;

public class InvoiceManagement {
    private static Stage window = new Stage();
    private static TableView<Procedure> table1;
    private static DatePicker datePicker;
    private static LocalDate date;
    private static int patientListID;
    private static Button invoiceAndSaveButton;
    private static ChoiceBox<String> procedureChoiceBox;
    private static ChoiceBox<String> patientChoiceBox;
    private static ChoiceBox<String> invoiceChoiceBox;
    private static ObservableList<Patient> patient;
    private static ObservableList<Procedure> procedures;
    private static Label label;

    public static void management(){
        patientListID = -1;
        Label label1 = new Label("Patient: ");
        Label label2 = new Label("Invoice: ");
        Label label3 = new Label("Date: ");
        label = new Label("Patients have been successfully loaded");
        patientChoiceBox = new ChoiceBox<>();
        procedureChoiceBox = new ChoiceBox<>();
        invoiceChoiceBox = new ChoiceBox<>();
        window.setTitle("Dentist Application");
        window.initModality(Modality.APPLICATION_MODAL);
        patient = loadPatients();
        addPatientsToList();
        //patientChoiceBox.setOnAction(E -> loadInvoice(patientChoiceBox.getValue()));
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
        invoiceAndSaveButton = new Button("Save Invoice");
        invoiceAndSaveButton.setOnAction(e -> saveButtonClicked());


        //Quantity input
        datePicker = new DatePicker();
        datePicker.setOnAction(e -> {
            date = datePicker.getValue();
        });

        table1 = new TableView<>();
        getProcedure();
        table1.getColumns().addAll(procNameColumn, procCostColumn, procNumberColumn);

        HBox bottomLayout4 = new HBox(10);
        bottomLayout4.getChildren().addAll(label3, datePicker);
        HBox bottomLayout2 = new HBox(10);
        bottomLayout2.getChildren().addAll(label1, patientChoiceBox);
        HBox bottomLayout3 = new HBox(10);
        bottomLayout3.getChildren().addAll(label2, invoiceChoiceBox);
        HBox bottomLayout = new HBox(10);
        bottomLayout.getChildren().addAll(addButton, deleteButton, invoiceAndSaveButton);
        bottomLayout.setPadding(new Insets(10, 10, 10 , 80));
        HBox bottomLayout5 = new HBox(10);
        bottomLayout5.getChildren().addAll(label);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(table1, bottomLayout2, bottomLayout3, bottomLayout4, bottomLayout, bottomLayout5);
        bottomLayout2.setPadding(new Insets(10, 10, 10 , 145));
        bottomLayout3.setPadding(new Insets(10, 10, 10 , 145));
        bottomLayout4.setPadding(new Insets(10, 10, 10 , 145));
        bottomLayout5.setPadding(new Insets(10, 10, 10 , 145));


        patientChoiceBox.setOnAction(e -> {
            addInvoiceToList();
        });
        //bPlane.getChildren().add(leftLayout);

        invoiceChoiceBox.setOnAction(e -> {
            String test = "New Invoice";
            if (invoiceChoiceBox.getValue().equals(test))
                createNewInvoice();
            else
                updateInvoice();
        });


        Scene scene = new Scene(layout, 505, 550);
        window.setMinHeight(550);
        window.setMaxWidth(505);
        window.setMinWidth(505);
        window.setMaxHeight(550);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void updateInvoice() {
        table1.getItems().clear();
        for (int i = 0; i < patient.get(patientListID).getInvoiceList().get(Integer.parseInt(invoiceChoiceBox.getValue()) - 1).getProcList().size();i++){
            table1.getItems().add(patient.get(patientListID).getInvoiceList().get(Integer.parseInt(invoiceChoiceBox.getValue()) - 1).getProcList().get(i));
        }
        System.out.println(patientListID);

    }

    private static void createNewInvoice() {
        table1.getItems().clear();
        System.out.println(patientListID);

    }

    private void loadInvoice(Patient value) {
        invoiceChoiceBox = new ChoiceBox<>();
        for (int i = 0; i < value.getInvoiceList().size(); i++){
            invoiceChoiceBox.getItems().add(String.valueOf(value.getInvoiceList().get(i).getInvoiceNo()));
        }
    }

    private static void deleteButtonClicked() {
        ObservableList<Procedure> proceduresSelected, allProcedure;
        allProcedure = table1.getItems();
        proceduresSelected = table1.getSelectionModel().getSelectedItems();
        proceduresSelected.forEach(allProcedure::remove);
    }

    public static void getProcedure(){
        try
        {
            ObservableList<Procedure> procedure = FXCollections.observableArrayList();
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("hostelser1.txt"));
            ArrayList<Procedure> procedureBuffer = (ArrayList<Procedure>) is.readObject();
            procedure = FXCollections.observableArrayList(procedureBuffer);
            procedures = FXCollections.observableArrayList(procedureBuffer);
            is.close();
            for (int i = 0; i < procedure.size(); i++){
                procedureChoiceBox.getItems().add(procedure.get(i).getProcName());
            }
            label.setText("Procedures Loaded");
        }
        catch (Exception ex) {
            label.setText("Could not load Procedures");


        }


    }
    private static void saveButtonClicked() {

            ObservableList<Procedure> tableContent;
            tableContent =  table1.getItems();
            System.out.println(patientListID);
            ArrayList<Procedure> procedure = new ArrayList<>();
            for (int i = 0; i < tableContent.size(); i++){
                procedure.add(tableContent.get(i));
            }
            System.out.println(procedure);
            Invoice invoice = new Invoice(date);
            invoice.setProcList(procedure);
            invoice.setInvoiceNo(patient.get(patientListID).getInvoiceList().size() + 1);
            System.out.print(procedure);
            if (invoiceChoiceBox.getValue().equals("New Invoice")){
                invoice.setInvoiceNo(patient.get(patientListID).getInvoiceList().size() + 1);
                patient.get(patientListID).getInvoiceList().add(invoice);
            }
            else {
                for (int i = 0; i < patient.get(patientListID).getInvoiceList().size(); i++) {
                    if ((patient.get(patientListID).getInvoiceList().get(i).getInvoiceNo()) == Integer.parseInt(invoiceChoiceBox.getValue())) {
                        invoice.setInvoiceNo(Integer.parseInt(invoiceChoiceBox.getValue()));
                        patient.get(patientListID).getInvoiceList().remove(i);
                        patient.get(patientListID).getInvoiceList().add(i, invoice);
                    }

                }
            }
            ArrayList<Patient> patient2 = new ArrayList<>();
            for (int i = 0; i < patient.size(); i++)
                patient2.add(patient.get(i));

            try
            {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("hostelser.txt"));
                {
                    os.writeObject(patient2);
                }
                os.close();
                label.setText("File has been successfully saved");

            } catch (Exception ex) {
                ex.printStackTrace();
                label.setText("Could not save file");
            }
        }
    public static void addButtonClicked(){


            for (int i = 0; i < procedures.size(); i++) {
                if (procedureChoiceBox.getValue().equals(procedures.get(i).getProcName())){
                    Procedure newProcedure = new Procedure(procedures.get(i).getProcName(), procedures.get(i).getProcCost(), (table1.getItems().size() + 1) );
                    table1.getItems().add(newProcedure);
                }
                System.out.print(procedures.get(i).getProcName());
            }
        }


    private static ObservableList<Patient> loadPatients(){
        ObservableList<Patient> patient = FXCollections.observableArrayList();
        try
        {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("hostelser.txt"));
            ArrayList<Patient> patientBuffer = (ArrayList<Patient>) is.readObject();
            patient = FXCollections.observableArrayList(patientBuffer);
            is.close();

        }
        catch (Exception ex) {
            //label.setText("Could not load Patient information");
            patient = FXCollections.observableArrayList();
            ex.printStackTrace();
            label.setText("Could Not Load Patients");
        }
        return patient;
    }

    private static void addPatientsToList(){
        patientChoiceBox = new ChoiceBox<>();
        for (int i = 0; i < patient.size(); i++){
            String s = patient.get(i).getName();
            patientChoiceBox.getItems().add(s);
        }}
    private static void addInvoiceToList(){
        invoiceChoiceBox.getItems().clear();
        patientListID = -1;
        for (int i = 0; i < patient.size(); i++){
            if (patient.get(i).getName().equals(patientChoiceBox.getValue()))
                patientListID = i;
                System.out.println(patientListID);
        }
        for (int i = 0; i < patient.get(patientListID).getInvoiceList().size(); i++) {

            String s = "" + (patient.get(patientListID).getInvoiceList().get(i).getInvoiceNo());
            invoiceChoiceBox.getItems().add(s);
            System.out.println(patientListID);

        }
        String s = "New Invoice";
        invoiceChoiceBox.getItems().add(s);
    }


    }