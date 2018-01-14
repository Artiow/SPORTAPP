package com.sport.controller.competition;

import com.sport.controller.PopupControllerImpl;
import com.sport.dao.impl.CompetitionDAO;
import com.sport.dao.impl.SportDAO;
import com.sport.model.Competition;
import com.sport.model.Sport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

public class CompetitionPopupFormController extends PopupControllerImpl {
    private LocalDate todayDate = null;
    private LocalDate currentDate = null;

    private Competition competition = null;
    private boolean edit = false;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField venueTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView<Sport> sportListView;
    private ObservableList<Sport> sportObservableList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    void submit(ActionEvent event) {
        String name = nameTextField.getText();
        String venue = venueTextField.getText();
        LocalDate localDate = datePicker.getValue();
        Sport sport = sportListView.getSelectionModel().getSelectedItem();

        if (edit && (competition == null)) alert(
                "Ошибка!",
                "Неизвестная ошибка!"
        );
        else if (edit && !localDate.isEqual(currentDate) && localDate.isBefore(todayDate)) alert(
                "Ошибка!",
                "Нельзя изменить дату прошедшего соревнования!"
        );
        else if (edit && !localDate.isEqual(currentDate) && localDate.isBefore(currentDate)) alert(
                "Ошибка!",
                "Нельзя изменить дату на более раннюю!"
        );
        else if (name.equals("")) alert(
                "Ошибка!",
                "Введите наименование!"
        );
        else if (venue.equals("")) alert(
                "Ошибка!",
                "Введите место проведения!"
        );
        else if (localDate == null) alert(
                "Ошибка!",
                "Выберите дату проведения!"
        );
        else if (sport == null) alert(
                "Ошибка!",
                "Выберите вид спорта!"
        );
        else {
            Date date = Date.valueOf(localDate);

            CompetitionDAO competitionDAO = new CompetitionDAO();
            if (edit) competitionDAO.updateCompetition(competition, sport, name, venue, date);
            else competitionDAO.saveCompetition(sport, name, venue, date);

            getParent().updateView();
            close();
        }
    }

    void setData(Competition competition) {
        edit = true;
        this.competition = competition;

        String name = competition.getName();
        if (name != null) nameTextField.setText(name);
        else nameTextField.setText("");
        String venue = competition.getVenue();
        if (venue != null) venueTextField.setText(venue);
        else venueTextField.setText("");

        currentDate = LocalDate.parse(competition.getDate().toString());
        datePicker.setValue(currentDate);

        sportListView.getSelectionModel().select(competition.getSport());
    }

    @Override
    public void loadView() {
        todayDate = new java.util.Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public void updateView() {
        SportDAO sportDAO = new SportDAO();
        List<Sport> sportList = sportDAO.getAllSports();

        sportObservableList.clear();
        sportObservableList.addAll(sportList);
        sportListView.setItems(sportObservableList);
    }
}