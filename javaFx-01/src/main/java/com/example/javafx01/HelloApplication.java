package com.example.javafx01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Button button = new Button("点这里");
        button.setLayoutX(100);
        button.setLayoutY(200);
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(button);
//        button.setOnAction(e->{
//            getHostServices().showDocument("wwww");
//        });

        Scene scene = new Scene(pane, 1000, 1000);
        stage.getIcons().add(new Image("1665318672-136533f27574475.png"));
        stage.setResizable(false); //不改变大小
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}