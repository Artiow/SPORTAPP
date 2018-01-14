package com.sport.controller.athlete;

import com.sport.controller.PopupControllerImpl;
import com.sport.dao.impl.ParticipateDAO;
import com.sport.dao.impl.SportDAO;
import com.sport.model.Athlete;
import com.sport.model.Participate;
import com.sport.model.Sport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class AthletePopupStatController extends PopupControllerImpl {
    private Athlete athlete = null;

    @FXML
    private ListView<Sport> sportListView;
    private ObservableList<Sport> sportObservableList = FXCollections.observableArrayList();
    @FXML
    private ListView<Participate> resultListView;
    private ObservableList<Participate> resultObservableList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    void select(MouseEvent event) {
        showDetail(sportListView.getSelectionModel().getSelectedItem());
    }

    private void showDetail(Sport sport) {
        ParticipateDAO participateDAO = new ParticipateDAO();
        List<Participate> participateList = participateDAO.getAllParticipatesFor(this.athlete, sport);
        participateList.sort(new Participate.ComparatorByResult(true));

        resultObservableList.clear();
        resultObservableList.addAll(participateList);
        resultListView.setItems(resultObservableList);
    }

    void setAthlete(Athlete athlete) {
        this.athlete = athlete;

        SportDAO sportDAO = new SportDAO();
        List<Sport> sportList = sportDAO.getAllSportsFor(this.athlete);

        sportObservableList.clear();
        sportObservableList.addAll(sportList);
        sportListView.setItems(sportObservableList);
    }

    @Override
    public void loadView() {

    }

    @Override
    public void updateView() {

    }
}
