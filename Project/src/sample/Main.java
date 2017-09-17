package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


public class Main extends Application {


    private static Label label;
    private static ObservableList<Dentist> dentists;
    private static ObservableList<Patient> patient;
    public static Stage window ;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        label = new Label();
        window.setTitle("Dentist Application");


        dentists = createDentist();
        Menu invoiceM = new Menu("Invoices");
        MenuItem invoiceManagement = new Menu("Invoice Management");
        invoiceM.getItems().add(invoiceManagement);
        invoiceManagement.setOnAction(event -> InvoiceManagement.management());
        Menu procedureM = new Menu("Procedures");
        MenuItem procedureManagement = new Menu("Procedure Management");
        procedureM.getItems().add(procedureManagement);
        procedureManagement.setOnAction(event -> ProcedureManagement.management());
        Menu patientM = new Menu("Patients");
        MenuItem patientManagement = new Menu("Patient Management");
        patientM.getItems().add(patientManagement);
        patientManagement.setOnAction(event -> PatientManagement.management());
        Menu paymentM = new Menu("Payments");
        MenuItem paymentManagement = new Menu("Payment Management");
        paymentM.getItems().add(paymentManagement);
        paymentManagement.setOnAction(event -> PaymentManagement.management());
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(invoiceM, procedureM, patientM, paymentM);


        Button generateDateReport = new Button("Generate Report For Patients Who Have Not Payed in 6 months");
        Button generateNormalReport = new Button("Generate Report For All Patients");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(generateDateReport,generateNormalReport);
        generateDateReport.setOnAction( e -> {
            bubbleSortCash();
        });
        generateNormalReport.setOnAction(e -> {
            bubbleSortName();
        });

        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        HBox report = new HBox(10);
        report.getChildren().addAll(generateDateReport, generateNormalReport);
        HBox label1 = new HBox(10);
        Label introLabel = new Label("Welcome to this dentist app. Use menus above or report generation below.");
        label1.getChildren().add(introLabel);
        HBox label2 = new HBox(10);
        label2.setPadding(new Insets(10, 10, 10 , 145));
        label2.getChildren().add(label);
        VBox vBox1 = new VBox(10);
        vBox1.getChildren().addAll(label1, report, label2);
        layout.setCenter(vBox1);

        Scene scene = new Scene(layout, 700, 550);

        window.setScene(scene);
        window.show();
        LogIn.login();

        //InvoiceManagement.main();
    }

    public ObservableList<Dentist> createDentist(){
        ObservableList<Dentist> dentist = FXCollections.observableArrayList();
        dentist.add(new Dentist("Mark", "34 St", "1"));
        dentist.add(new Dentist("Dasee Lackr", "342 Octist", "JING2"));
        dentist.add(new Dentist("Karl Kamer", "Basu Lance", "JING3"));
        dentist.add(new Dentist("James Drack", "12  Den", "JING4"));
        dentist.add(new Dentist("Jax Dark", "12 Luctist", "JING5"));
        return dentist;
    }
    public static ObservableList<Dentist> getDentists(){
        return dentists;
    }
    public static void bubbleSortCash(){
        ArrayList<Patient> patient2 = new ArrayList<>();
        Patient y1, y2;
        for (int i = 0; i<patient.size(); i++) {
            if (patient.get(i).sixMonthsNoPay() == false)
                patient2.add(patient.get(i));
        }
        for (int i = 0; i<patient2.size(); i++){
            for (int x = 1; x<(patient2.size() - i); x++){
                if (sortPatientsByCash(patient2.get(x-1), patient2.get(x)) == true ) {
                    y1 = patient2.get(x);
                    y2 = patient2.get(x - 1);
                    patient2.remove(x - 1);
                    patient2.remove(x - 1);
                    patient2.add(x - 1, y1);
                    patient2.add(x, y2);
                }}
        }
        writeToFile(patient2);
    }
    public static void bubbleSortName(){
        ArrayList<Patient> patient2 = new ArrayList<>();
        Patient y1, y2;
        for (int i = 0; i<patient.size(); i++) {
            patient2.add(patient.get(i));
        }
        for (int i = 0; i<patient2.size(); i++){
            for (int x = 1; x<(patient2.size() - i); x++){
                if (sortPatientsByName(patient2.get(x-1), patient2.get(x)) == true ){
                    y1 = patient2.get(x);
                    y2 = patient2.get(x - 1);
                    patient2.remove(x-1);
                    patient2.remove(x-1);
                    patient2.add(x-1, y1);
                    patient2.add(x, y2);
                }
            }
        }
        writeToFile(patient2);
    }
    public static Boolean sortPatientsByCash(Patient a, Patient b){
        Boolean patientComparison= true;
        if (a.calculateTotalCashOwed() == b.calculateTotalCashOwed()){
            if ((a.getName()).compareTo(b.getName()) < 0)
                patientComparison = false;
        }
        else{
            if (a.calculateTotalCashOwed() < b.calculateTotalCashOwed())
                patientComparison = false;
        }
        return patientComparison;
    }
    public static Boolean sortPatientsByName(Patient a, Patient b){
        Boolean patientComparison= true;
        if ((a.getName()).compareTo(b.getName()) < 0)
            patientComparison = false;
        return patientComparison;
    }


    private static ObservableList<Patient> loadPatients() {
        patient = FXCollections.observableArrayList();
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("hostelser.txt"));
            ArrayList<Patient> patientBuffer = (ArrayList<Patient>) is.readObject();
            patient = FXCollections.observableArrayList(patientBuffer);
            is.close();

        } catch (Exception ex) {
            label.setText("Could not load Patient information");
            patient = FXCollections.observableArrayList();
            ex.printStackTrace();
        }
        return patient;
    }
    private static void writeToFile(ArrayList<Patient> patients){
        String s= "";
        for (int i = 0; i < patients.size(); i++)
            s = s + "\n\nPatient " + (i+1) + " Details\n" + patients.get(i).toString();
        try {
            FileWriter fw = new FileWriter("patientDetails.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Patient Details Report \n");
            bw.write(s);
            bw.close();
            label.setText("Report has been successfully printed to file.");
        }
        catch (FileNotFoundException fnf){
            label.setText("Could not save report");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            label.setText("Could not save report");
        }
    }
}


