package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import static java.time.temporal.ChronoUnit.DAYS;


public class PaymentManagement {
    private static Stage window = new Stage();
    private static ObservableList<Patient> patient;
    private static ChoiceBox<String> patientChoiceBox;
    private static ChoiceBox<String> invoiceChoiceBox;
    private static Label invoiceBalance, label6;
    private static DatePicker datePicker;

    private static LocalDate date;
    private static int patientListID;
    private static double amountOwed;
    private static TextField paymentInput;


    public static void management(){
        window.setTitle("Dentist Application");
        window.initModality(Modality.APPLICATION_MODAL);
        amountOwed = 0;
        patientChoiceBox = new ChoiceBox<>();
        patientChoiceBox.setMinWidth(160);
        invoiceChoiceBox = new ChoiceBox<>();
        invoiceChoiceBox.setMinWidth(160);
        patient = loadPatients();
        Button button = new Button("Submit");
        button.setMinWidth(160);
        Button saveButton = new Button("Save Payment");
        saveButton.setMinWidth(160);
        paymentInput = new TextField();
        invoiceBalance = new Label();
        invoiceBalance.setText("No Invoice Currently Selected");


        //Name input
        paymentInput = new TextField();
        paymentInput.setPromptText("Money");
        paymentInput.setMinWidth(150);

        Label label1 = new Label("Payment Amount: ");
        label1.setMinWidth(150);
        Label label2 = new Label("Patient Name: ");
        label2.setMinWidth(150);
        Label label3 = new Label("Invoice Number: ");
        label3.setMinWidth(150);
        Label label4 = new Label("Amount Due: ");
        label4.setMinWidth(150);
        Label label5 = new Label("Payment Date: ");
        label5.setMinWidth(150);
        label6 = new Label("Patients Successfully Loaded");

        datePicker = new DatePicker();

        datePicker.setOnAction(e -> {
            date = datePicker.getValue();
        });

        patientChoiceBox.setOnAction(e -> {
            addInvoiceToList();
        });
        button.setOnAction(e -> {
            newPayment();
        });

        HBox patientDetails = new HBox();
        patientDetails.getChildren().addAll(label2, patientChoiceBox);
        patientDetails.setPadding(new Insets(25,10,10,10));
        patientDetails.setSpacing(30);
        HBox invoiceDetails = new HBox();
        invoiceDetails.getChildren().addAll(label3, invoiceChoiceBox);
        invoiceDetails.setPadding(new Insets(10,10,10,10));
        invoiceDetails.setSpacing(30);
        HBox paymentDetails = new HBox();
        paymentDetails.getChildren().addAll(label4, invoiceBalance);
        paymentDetails.setPadding(new Insets(10,10,10,10));
        paymentDetails.setSpacing(30);
        HBox paymentEntry = new HBox();
        paymentEntry.getChildren().addAll(label1, paymentInput);
        paymentEntry.setSpacing(30);
        paymentEntry.setPadding(new Insets(10,10,10,10));
        HBox dateEntry = new HBox();
        dateEntry.getChildren().addAll(label5, datePicker);
        dateEntry.setSpacing(30);
        dateEntry.setPadding(new Insets(10,10,10,10));
        HBox buttonPlace = new HBox();
        buttonPlace.getChildren().addAll(button, saveButton);
        buttonPlace.setSpacing(30);
        buttonPlace.setPadding(new Insets(10,10,10,10));
        HBox labelSection = new HBox();
        labelSection.getChildren().add(label6);
        labelSection.setSpacing(30);
        labelSection.setPadding(new Insets(10, 10, 10, 10));

        VBox paymentSection = new VBox();
        paymentSection.getChildren().addAll(patientDetails, invoiceDetails, paymentDetails, paymentEntry, dateEntry, buttonPlace, labelSection );
        saveButton.setOnAction(e -> {
            savePatients();
        });
        invoiceChoiceBox.setOnAction(e -> {
            if ((invoiceChoiceBox.getValue().equals("No Invoice Available")) == false )
                getInvoiceAmountDue();
        });

        Scene scene = new Scene(paymentSection, 400, 365);
        window.setScene(scene);
        window.setMinHeight(365);
        window.setMaxHeight(365);
        window.setMinWidth(400);
        window.setMaxWidth(400);
        window.showAndWait();

    }

    private static ObservableList<Patient> loadPatients() {
        patient = FXCollections.observableArrayList();
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("hostelser.txt"));
            ArrayList<Patient> patientBuffer = (ArrayList<Patient>) is.readObject();
            patient = FXCollections.observableArrayList(patientBuffer);
            is.close();
            addPatientsToList();

        } catch (Exception ex) {
            label6.setText("Could not load Patient information");
            patient = FXCollections.observableArrayList();
            ex.printStackTrace();
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
        }
        for (int i = 0; i < patient.get(patientListID).getInvoiceList().size(); i++) {

            String s = "" + (patient.get(patientListID).getInvoiceList().get(i).getInvoiceNo());
            invoiceChoiceBox.getItems().add(s);
        }
        if (invoiceChoiceBox.getItems().size() == 0){
            invoiceChoiceBox.getItems().add("No Invoice Available");
            invoiceChoiceBox.setValue("No Invoice Available");
        }
    }
    private static void getInvoiceAmountDue(){
        double due = patient.get(patientListID).getInvoiceList().get(Integer.parseInt(invoiceChoiceBox.getValue()) - 1).calculateAmountDue();
        amountOwed = due;
        String s = "€" + due +"0" ;
        System.out.print(s);
        invoiceBalance.setText(s);
    }
    private static void  newPayment(){
        try
        {
        if (amountOwed == 0)
            label6.setText("Invoice is already paid");
        else {
        double cost = Double.parseDouble(paymentInput.getText());
        if (cost > 0){
        int paymentNo = patient.get(patientListID).getInvoiceList().get(Integer.parseInt(invoiceChoiceBox.getValue()) - 1).getPayList().size() + 1;
        Payment newPayment = new Payment(paymentNo, cost, date);
        double d = patient.get(patientListID).getInvoiceList().get(Integer.parseInt(invoiceChoiceBox.getValue()) - 1).calculateAmountDue() - cost;
        label6.setText("Payment Successfully Added");
        if (d < 0){
            newPayment.setPaymentAmt(cost - d);
            label6.setText("Payment was above amount due\nPayment was accepted as the amount needed to pay off balance");
        }
        patient.get(patientListID).getInvoiceList().get(Integer.parseInt(invoiceChoiceBox.getValue()) - 1).getPayList().add(newPayment);
        getInvoiceAmountDue();}
        else
            label6.setText("Please enter a valid number");}}
        catch (Exception e){
            e.printStackTrace();
            label6.setText("Please enter a valid number");
        }
    }
    private static void savePatients(){
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
            label6.setText("File has been successfully saved");
            //bEdit = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            label6.setText("Could not save file");
        }
    }


}