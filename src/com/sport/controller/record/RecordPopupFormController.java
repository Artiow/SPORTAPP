package com.sport.controller.record;

import com.sport.controller.PopupControllerImpl;
import com.sport.dao.impl.SportDAO;
import com.sport.dao.impl.WorldRecordDAO;
import com.sport.model.Sport;
import com.sport.model.WorldRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class RecordPopupFormController extends PopupControllerImpl {
    private Sport sport = null;
    private WorldRecord record = null;
    private boolean edit = false;

    @FXML
    private TextField valueTextField;
    @FXML
    private DatePicker datePicker;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    void submit(ActionEvent event) {
        int value = 0;
        boolean valid = false;
        try {
            value = Integer.parseInt(valueTextField.getText());
            valid = true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        LocalDate localDate = datePicker.getValue();
        if ((edit && (record == null)) || (!edit && (sport == null))) alert(
                "Ошибка!",
                "Неизвестная ошибка!"
        );
        else if (localDate == null) alert(
                "Ошибка!",
                "Выберите дату!"
        );
        else if (!valid) alert(
                "Ошибка!",
                "Неверный формат результата!"
        );
        else {
            Date date = Date.valueOf(localDate);
            WorldRecordDAO recordDAO = new WorldRecordDAO();
            if (edit) recordDAO.updateWorldRecord(record, value, date);
            else recordDAO.saveWorldRecord(sport, value, date);

            getParent().updateView();
            close();
        }
    }

    void addRecord(Sport sport) {
        edit = false;
        this.sport = sport;
        this.record = null;
    }

    void editRecord(WorldRecord record) {
        edit = true;
        this.sport = null;
        this.record = record;

        valueTextField.setText(String.valueOf(this.record.getValue()));
        datePicker.setValue(LocalDate.parse(this.record.getDate().toString()));
    }

    @Override
    public void loadView() {

    }

    @Override
    public void updateView() {

    }
}