package com.sport.controller.unit;

import com.sport.controller.PopupControllerImpl;
import com.sport.dao.impl.UnitDAO;
import com.sport.model.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UnitPopupFormController extends PopupControllerImpl {
    private Unit unit = null;
    private boolean edit = false;

    @FXML
    private TextField nameTextField;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    void submit(ActionEvent event) {
        String name = nameTextField.getText();

        if (edit && (unit == null)) alert(
                "Ошибка!",
                "Неизвестная ошибка!"
        );
        else if (name.equals("")) alert(
                "Ошибка!",
                "Введите имя спортсмена!"
        );
        else {
            UnitDAO unitDAO = new UnitDAO();
            if (edit) unitDAO.updateUnit(unit, name);
            else unitDAO.saveUnit(name);

            getParent().updateView();
            close();
        }
    }

    void setData(Unit unit) {
        edit = true;
        this.unit = unit;

        String name = unit.getName();
        if (name != null) nameTextField.setText(name);
        else nameTextField.setText("");
    }

    @Override
    public void loadView() {

    }

    @Override
    public void updateView() {

    }
}