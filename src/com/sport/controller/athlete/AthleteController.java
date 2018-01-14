package com.sport.controller.athlete;

import com.sport.controller.ControllerImpl;
import com.sport.dao.impl.AthleteDAO;
import com.sport.model.Athlete;
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

public class AthleteController extends ControllerImpl {
    private ObservableList<Athlete> athleteObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Athlete> athleteTableView;
    @FXML
    private TableColumn<Athlete, String> nameTableColumn;
    @FXML
    private TableColumn<Athlete, String> categoryTableColumn;
    @FXML
    private TableColumn<Athlete, Date> dateTableColumn;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        popup(
                "../../view/athletePopupForm.fxml",
                "Добавление записи", false
        );
    }

    @FXML
    private void delete(ActionEvent event) {
        Athlete item = athleteTableView.getSelectionModel().getSelectedItem();
        int next = athleteTableView.getSelectionModel().getSelectedIndex();

        if (item != null) {
            AthleteDAO athleteDAO = new AthleteDAO();
            try {
                athleteDAO.deleteAthlete(item);
                updateView(next);
            } catch (AthleteDAO.AthleteInUseException e) {
                e.printStackTrace();
                alert(
                        "Ошибка!",
                        "Невозможно удалить: спортсмен используется!"
                );
            }
        }
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
        Athlete item = athleteTableView.getSelectionModel().getSelectedItem();
        if (item != null) ((AthletePopupFormController) popup(
                "../../view/athletePopupForm.fxml",
                "Редактирование записи", false
        )).setAthlete(item);
    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
        Athlete item = athleteTableView.getSelectionModel().getSelectedItem();
        if (item != null) ((AthletePopupStatController) popup(
                "../../view/athletePopupStat.fxml",
                "Статистика спортсмена", false
        )).setAthlete(item);
    }

    @Override
    public void loadView(){
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));
        categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    @Override
    public void updateView() {
        AthleteDAO athleteDAO = new AthleteDAO();
        List<Athlete> athleteList = athleteDAO.getAllAthletes();

        athleteObservableList.clear();
        athleteObservableList.addAll(athleteList);
        athleteTableView.setItems(athleteObservableList);
    }

    private void updateView(int selectedRow) {
        updateView();
        athleteTableView.getSelectionModel().select(selectedRow);
    }

    @FXML
    private void refresh(ActionEvent event) {
        updateView();
    }
}