package com.sport.controller;

import javafx.fxml.FXML;

public interface Controller {
    void loadView();
    void updateView();
    void alert(String title, String description);
}