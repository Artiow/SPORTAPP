package com.sport.controller.results;

import com.sport.controller.PopupControllerImpl;
import com.sport.dao.impl.AthleteDAO;
import com.sport.dao.impl.ParticipateDAO;
import com.sport.model.Athlete;
import com.sport.model.Competition;
import com.sport.model.Participate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ResultsPopupFormController extends PopupControllerImpl {
    private final int AGELIMIT = 18;
    private LocalDate DATELIMIT = null;

    private Competition competition = null;
    private Participate participate = null;
    private boolean edit = false;

    @FXML
    private ListView<Athlete> athleteListView;
    private ObservableList<Athlete> athleteObservableList = FXCollections.observableArrayList();
    @FXML
    private TextField resultTextField;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    void submit(ActionEvent event) {
        int result = 0;
        boolean valid = false;
        try {
            result = Integer.parseInt(resultTextField.getText());
            valid = (result > 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Athlete athlete = athleteListView.getSelectionModel().getSelectedItem();

        if ((edit && (participate == null)) || (!edit && (competition == null))) alert(
                "Ошибка!",
                "Неизвестная ошибка!"
        );
        else if (!edit && (athlete == null)) alert(
                "Ошибка!",
                "Выберите спортсмена!"
        );
        else if (athlete.getBirth().toLocalDate().isAfter(DATELIMIT)) alert(
                "Ошибка!",
                "Спортсмен не может быть моложе " + AGELIMIT + " лет!"
        );
        else if (!valid) alert(
                "Ошибка!",
                "Неверный формат результата!"
        );
        else {
            ParticipateDAO participateDAO = new ParticipateDAO();
            if (edit) participateDAO.updateParticipate(participate, result);
            else participateDAO.saveParticipate(competition, athlete, result);

            getParent().updateView();
            close();
        }
    }

    void setCompetition(Competition competition) {
        edit = false;
        this.competition = competition;
        this.participate = null;

        athleteListView.setDisable(false);
    }

    void setParticipate(Participate participate) {
        edit = true;
        this.competition = null;
        this.participate = participate;

        resultTextField.setText(String.valueOf(this.participate.getResult()));
        athleteListView.getSelectionModel().select(this.participate.getAthlete());

        athleteListView.setDisable(true);
    }

    @Override
    public void loadView() {
        DATELIMIT = new java.util.Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DATELIMIT = DATELIMIT.minusYears(AGELIMIT);
    }

    @Override
    public void updateView() {
        AthleteDAO athleteDAO = new AthleteDAO();
        List<Athlete> athleteList = athleteDAO.getAllAthletes();

        athleteObservableList.clear();
        athleteObservableList.addAll(athleteList);
        athleteListView.setItems(athleteObservableList);
        athleteListView.setDisable(edit);
    }
}