package com.sport.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ControllerImpl implements Controller {
    protected PopupControllerImpl popup(String path, String title, boolean resizable) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(resizable);
        stage.setTitle(title);
        stage.show();

        PopupControllerImpl child = loader.getController();
        child.setStage(stage);
        child.setParent(this);

        return child;
    }

    public abstract void loadView();

    public abstract void updateView();

    @Override
    public void alert(String title, String description) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText(title);
        error.setContentText(description);
        error.showAndWait();
    }
}
