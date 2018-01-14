package com.sport.controller.athlete;

import com.sport.controller.PopupControllerImpl;
import com.sport.dao.impl.AthleteDAO;
import com.sport.model.Athlete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class AthletePopupFormController extends PopupControllerImpl {
    private LocalDate todayDate = null;

    private Athlete athlete = null;
    private boolean edit = false;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    void submit(ActionEvent event) {
        String name = nameTextField.getText();
        String category = categoryTextField.getText();
        LocalDate localDate = birthDatePicker.getValue();

        if (edit && (athlete == null)) alert(
                "Ошибка!",
                "Неизвестная ошибка!"
        );
        else if (name.equals("")) alert(
                "Ошибка!",
                "Введите имя спортсмена!"
        );
        else if (localDate == null) alert(
                "Ошибка!",
                "Выберите дату!"
        );
        else {
            if (category.equals("")) category = null;
            Date date = Date.valueOf(localDate);

            AthleteDAO athleteDAO = new AthleteDAO();
            if (edit) athleteDAO.updateAthlete(athlete, name, date, category);
            else athleteDAO.saveAthlete(name, date, category);

            getParent().updateView();
            close();

            if (localDate.isAfter(todayDate)) alert(
                    "Внимание!",
                    "Спортсмен моложе 18 лет!"
            );
        }
    }

    void setAthlete(Athlete athlete) {
        edit = true;
        this.athlete = athlete;

        String name = athlete.getFullName();
        if (name != null) nameTextField.setText(name);
        else nameTextField.setText("");
        String category = athlete.getCategory();
        if (category != null) categoryTextField.setText(category);
        else categoryTextField.setText("");

        birthDatePicker.setValue(LocalDate.parse(athlete.getBirth().toString()));
    }

    @Override
    public void loadView() {
        todayDate = new java.util.Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        todayDate = todayDate.minusYears(18);
    }

    @Override
    public void updateView() {

    }
}