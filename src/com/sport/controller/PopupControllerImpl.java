package com.sport.controller;

import javafx.stage.Stage;

public abstract class PopupControllerImpl extends ControllerImpl {
    private Controller parent = null;
    private Stage stage = null;

    void setParent(Controller c) {
        this.parent = c;
    }

    protected Controller getParent() {
        return parent;
    }

    void setStage(Stage s) {
        this.stage = s;
    }

    protected Stage getStage() {
        return stage;
    }

    public abstract void loadView();

    public abstract void updateView();

    protected void show() {
        if (stage != null) stage.show();
    }

    protected void close() {
        if (stage != null) stage.close();
    }

    @Override
    public void alert(String title, String description) {
        super.alert(title, description);
    }
}
