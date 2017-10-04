package com.mygdx.game.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesktopLauncher extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		primaryStage.setTitle("SKRRRT");
		primaryStage.setScene(new Scene(root, 1200, 800));
		primaryStage.show();
	}

	public static void main (	String[] arg) {}
}
