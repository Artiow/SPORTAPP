package com.sport.controller.record;

import com.sport.controller.ControllerImpl;
import com.sport.dao.impl.AthleteDAO;
import com.sport.dao.impl.SportDAO;
import com.sport.dao.impl.WorldRecordDAO;
import com.sport.model.Athlete;
import com.sport.model.Sport;
import com.sport.model.WorldRecord;
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
import java.sql.Date;
import java.util.List;

public class RecordController extends ControllerImpl {

    @FXML
    private ListView<Sport> sportListView;
    private ObservableList<Sport> sportObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<WorldRecord> recordTableView;
    private ObservableList<WorldRecord> recordObservableList = FXCollections.observableArrayList();
    @FXML
    private ListView<Athlete> athleteListView;
    private ObservableList<Athlete> athleteObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<WorldRecord, Integer> valueTableColumn;
    @FXML
    private TableColumn<WorldRecord, Date> dateTableColumn;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        saveSelected();

        Sport item = sportListView.getSelectionModel().getSelectedItem();
        if (item != null) ((RecordPopupFormController) popup(
                "../../view/recordPopupForm.fxml",
                "Добавление записи", false
        )).addRecord(item);
    }

    @FXML
    private void delete(ActionEvent event) {
        saveSelected();

        WorldRecord item = recordTableView.getSelectionModel().getSelectedItem();
        int next = recordTableView.getSelectionModel().getSelectedIndex();

        if (item != null) {
            WorldRecordDAO worldRecordDAO = new WorldRecordDAO();
            worldRecordDAO.deleteWorldRecord(item);

            updateView(next);
        }
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
       saveSelected();

        WorldRecord item = recordTableView.getSelectionModel().getSelectedItem();
        if (item != null) ((RecordPopupFormController) popup(
                "../../view/recordPopupForm.fxml",
                "Редактирование записи", false
        )).editRecord(item);
    }

    @Override
    public void loadView(){
        valueTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @Override
    public void updateView() {
        SportDAO sportDAO = new SportDAO();
        List<Sport> competitionList = sportDAO.getAllSports();

        sportObservableList.clear();
        sportObservableList.addAll(competitionList);
        sportListView.setItems(sportObservableList);

        restoreSelected();
    }

    private void updateView(int selectedRow) {
        updateView();
        recordTableView.getSelectionModel().select(selectedRow);
    }

    @FXML
    private void refresh(ActionEvent event) {
        updateView();
    }

    @FXML
    void select(MouseEvent event) {
        showDetail(sportListView.getSelectionModel().getSelectedItem());
    }

    private void showDetail(Sport sport) {
        WorldRecordDAO recordDAO = new WorldRecordDAO();
        List<WorldRecord> recordList = recordDAO.getRecordFor(sport);

        recordObservableList.clear();
        recordObservableList.addAll(recordList);
        recordTableView.setItems(recordObservableList);

        AthleteDAO athleteDAO = new AthleteDAO();
        List<Athlete> athleteList = athleteDAO.getSuperAthletes(sport);

        athleteObservableList.clear();
        athleteObservableList.addAll(athleteList);
        athleteListView.setItems(athleteObservableList);
    }

    private Integer selected = null;

    private void saveSelected() {
        selected = sportListView.getSelectionModel().getSelectedIndex();
    }

    private void restoreSelected() {
        if (selected != null) {
            sportListView.getSelectionModel().select(selected);
            Sport sport = sportListView.getSelectionModel().getSelectedItem();
            if (sport != null) showDetail(sport);
        }
    }
}