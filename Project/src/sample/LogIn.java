package sample;
import javafx.collections.ObservableList;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.util.ArrayList;

public class LogIn {
    private Boolean returnValue = true;
    static Stage window = new Stage();
    static Label label3;
    public static void login() {



        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Dentist Login");
        window.setMinWidth(250);

        Label label1 = new Label();
        label1.setMinWidth(160);
        TextField password = new PasswordField();
        label1.setText("Please enter your password: ");
        Label label2 = new Label();
        label2.setMinWidth(160);
        TextField name = new TextField();
        name.setPromptText("Name");
        label2.setText("Please enter your name: ");
        Button loginButton = new Button("Submit");
        label3 = new Label("Please Enter Your Details");
        loginButton.setOnAction(e -> loginCheck(name.getText(), password.getText()));


        HBox loginStuff1 = new HBox(10);
        loginStuff1.getChildren().addAll(label2, name);
        HBox loginStuff2 = new HBox(10);
        loginStuff2.getChildren().addAll(label1, password);
        HBox buttonLayout = new HBox();
        VBox layout = new VBox(10);
        layout.getChildren().addAll(loginStuff1, loginStuff2, label3, loginButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        window.setOnCloseRequest(e -> {
            e.consume();
            closeWindow();
        });
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void closeWindow() {
        Main.window.close();
        window.close();

    }
    private static void loginCheck(String s, String c){
        String n = s;
        String p = c;
        ObservableList<Dentist> checker = Main.getDentists();
        for (int i = 0; i < checker.size();i++){
            if ((n.equalsIgnoreCase(checker.get(i).getName())) && (p.equals(checker.get(i).getPassword())))
                window.close();
        }
        label3.setText("Incorrect Name/Password combination");
    }

}