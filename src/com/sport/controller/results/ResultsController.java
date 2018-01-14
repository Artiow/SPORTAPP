package com.sport.controller.results;

import com.sport.controller.ControllerImpl;
import com.sport.dao.impl.CompetitionDAO;
import com.sport.dao.impl.ParticipateDAO;
import com.sport.model.Competition;
import com.sport.model.Participate;

import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultsController extends ControllerImpl {

    @FXML
    private ListView<Competition> competitionListView;
    private ObservableList<Competition> competitionObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Result> resultsTableView;
    private ObservableList<Result> resultsObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Result, Integer> positionTableColumn;
    @FXML
    private TableColumn<Result, String> athleteTableColumn;
    @FXML
    private TableColumn<Result, Integer> resultTableColumn;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        saveSelected();

        Competition item = competitionListView.getSelectionModel().getSelectedItem();
        if (item != null) ((ResultsPopupFormController) popup(
                "../../view/resultsPopupForm.fxml",
                "Добавление записи", false
        )).setCompetition(item);
    }

    @FXML
    private void delete(ActionEvent event) {
        saveSelected();

        Result result = resultsTableView.getSelectionModel().getSelectedItem();
        if (result != null) {
            Participate item = result.getParticipate();
            int next = resultsTableView.getSelectionModel().getSelectedIndex();

            ParticipateDAO participateDAO = new ParticipateDAO();
            participateDAO.deleteParticipate(item);

            updateView(next);
        }
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
        saveSelected();

        Result result = resultsTableView.getSelectionModel().getSelectedItem();
        if (result != null) {
            Participate item = result.getParticipate();
            ((ResultsPopupFormController) popup(
                    "../../view/resultsPopupForm.fxml",
                    "Редактирование записи", false
            )).setParticipate(item);
        }
    }

    @Override
    public void loadView(){
        positionTableColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty().asObject());
        athleteTableColumn.setCellValueFactory(cellData -> cellData.getValue().athleteProperty());
        resultTableColumn.setCellValueFactory(cellData -> cellData.getValue().resultProperty().asObject());
    }

    @Override
    public void updateView() {
        CompetitionDAO competitionDAO = new CompetitionDAO();
        List<Competition> competitionList = competitionDAO.getAllCompetitions();

        competitionObservableList.clear();
        competitionObservableList.addAll(competitionList);
        competitionListView.setItems(competitionObservableList);

        restoreSelected();
    }

    private void updateView(int selectedRow) {
        updateView();
        resultsTableView.getSelectionModel().select(selectedRow);
    }

    @FXML
    private void refresh(ActionEvent event) {
        updateView();
    }

    @FXML
    void select(MouseEvent event) {
        showDetail(competitionListView.getSelectionModel().getSelectedItem());
    }

    private void showDetail(Competition competition) {
        ParticipateDAO participateDAO = new ParticipateDAO();

        List<Participate> participateList = participateDAO.getParticipateFor(competition);
        participateList.sort(new Participate.ComparatorByResult(true));

        int position = 1; List<Result>resultList = new ArrayList<>();
        for (Participate participate: participateList) resultList.add(new Result(position++, participate));

        resultsObservableList.clear();
        resultsObservableList.addAll(resultList);
        resultsTableView.setItems(resultsObservableList);
    }

    private Integer selected = null;

    private void saveSelected() {
        selected = competitionListView.getSelectionModel().getSelectedIndex();
    }

    private void restoreSelected() {
        if (selected != null) {
            competitionListView.getSelectionModel().select(selected);
            Competition competition = competitionListView.getSelectionModel().getSelectedItem();
            if (competition != null) showDetail(competition);
        }
    }

    private static class Result {
        private Integer position = null;
        private String athlete = null;
        private Integer result = null;

        private Participate participate = null;

        Result(Integer position, Participate participate) {
            this.position = position;
            this.participate = participate;

            this.athlete = this.participate.getAthlete().getFullName();
            this.result = this.participate.getResult();
        }

        Integer getPosition() {
            return position;
        }

        Participate getParticipate() {
            return participate;
        }

        IntegerProperty positionProperty() {
            return new SimpleIntegerProperty(position);
        }

        StringProperty athleteProperty() {
            return new SimpleStringProperty(athlete);
        }

        IntegerProperty resultProperty() {
            return new SimpleIntegerProperty(result);
        }
    }
}