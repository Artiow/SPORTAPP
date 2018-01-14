package com.sport.controller.competition;

import com.sport.controller.ControllerImpl;
import com.sport.dao.impl.CompetitionDAO;
import com.sport.model.Competition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class CompetitionController extends ControllerImpl {
    private ObservableList<Competition> competitionObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Competition> competitionTableView;
    @FXML
    private TableColumn<Competition, String> nameTableColumn;
    @FXML
    private TableColumn<Competition, String> sportTableColumn;
    @FXML
    private TableColumn<Competition, String> venueTableColumn;
    @FXML
    private TableColumn<Competition, Date> dateTableColumn;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        popup(
                "../../view/competitionPopupForm.fxml",
                "Добавление записи", false
        );
    }

    @FXML
    private void delete(ActionEvent event) {
        Competition item = competitionTableView.getSelectionModel().getSelectedItem();
        int next = competitionTableView.getSelectionModel().getSelectedIndex();

        if (item != null) {
            CompetitionDAO competitionDAO = new CompetitionDAO();
            try {
                competitionDAO.deleteCompetition(item);
                updateView(next);
            } catch (CompetitionDAO.CompetitionInUseException e) {
                e.printStackTrace();
                alert(
                        "Ошибка!",
                        "Невозможно удалить: соревнование используется!"
                );
            }
        }
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
        Competition item = competitionTableView.getSelectionModel().getSelectedItem();
        if (item != null) ((CompetitionPopupFormController) popup(
                "../../view/competitionPopupForm.fxml",
                "Редактирование записи", false
        )).setData(item);
    }

    @Override
    public void loadView(){
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sportTableColumn.setCellValueFactory(new PropertyValueFactory<>("sport"));
        venueTableColumn.setCellValueFactory(new PropertyValueFactory<>("venue"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @Override
    public void updateView() {
        CompetitionDAO competitionDAO = new CompetitionDAO();
        List<Competition> competitionList = competitionDAO.getAllCompetitions();

        competitionObservableList.clear();
        competitionObservableList.addAll(competitionList);
        competitionTableView.setItems(competitionObservableList);
    }

    private void updateView(int selectedRow) {
        updateView();
        competitionTableView.getSelectionModel().select(selectedRow);
    }

    @FXML
    private void refresh(ActionEvent event) {
        updateView();
    }
}