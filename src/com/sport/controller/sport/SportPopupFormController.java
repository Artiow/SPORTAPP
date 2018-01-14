package com.sport.controller.sport;

import com.sport.controller.PopupControllerImpl;
import com.sport.dao.impl.SportDAO;
import com.sport.dao.impl.UnitDAO;
import com.sport.model.Sport;
import com.sport.model.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class SportPopupFormController extends PopupControllerImpl {
    private Sport sport = null;
    private boolean edit = false;

    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<Unit> unitComboBox;
    private ObservableList<Unit> unitObservableList = FXCollections.observableArrayList();
    @FXML
    private CheckBox reverseCheckBox;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    void submit(ActionEvent event) {
        String name = nameTextField.getText();
        Unit unit = unitComboBox.getSelectionModel().getSelectedItem();
        Boolean reverse = reverseCheckBox.isSelected();

        if (edit && (sport == null)) alert(
                "Ошибка!",
                "Неизвестная ошибка!"
        );
        else if (name.equals("")) alert(
                "Ошибка!",
                "Введите наименование вида спорта!"
        );
        else if (unit == null) alert(
                "Ошибка!",
                "Выберите еденицу измерения!"
        );
        else {
            SportDAO sportDAO = new SportDAO();
            if (edit) sportDAO.updateSport(sport, name, unit, reverse);
            else sportDAO.saveSport(name, unit, reverse);

            getParent().updateView();
            close();
        }
    }

    void setData(Sport sport) {
        edit = true;
        this.sport = sport;

        String name = sport.getName();
        if (name != null) nameTextField.setText(name);
        else nameTextField.setText("");

        unitComboBox.getSelectionModel().select(sport.getUnit());
        reverseCheckBox.setSelected(sport.getReverse());
    }

    @Override
    public void loadView() {

    }

    @Override
    public void updateView() {
        UnitDAO unitDAO = new UnitDAO();
        List<Unit> unitList = unitDAO.getAllUnits();

        unitObservableList.clear();
        unitObservableList.addAll(unitList);
        unitComboBox.setItems(unitObservableList);
    }
}