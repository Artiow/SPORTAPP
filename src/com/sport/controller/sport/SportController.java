package com.sport.controller.sport;

import com.sport.controller.ControllerImpl;
import com.sport.dao.impl.SportDAO;
import com.sport.dao.impl.UnitDAO;
import com.sport.model.Sport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class SportController extends ControllerImpl {
    private ObservableList<Sport> sportObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Sport> sportTableView;
    @FXML
    private TableColumn<Sport, String> nameTableColumn;
    @FXML
    private TableColumn<Sport, String> unitTableColumn;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        popup(
                "../../view/sportPopupForm.fxml",
                "Добавление записи", false
        );
    }

    @FXML
    private void delete(ActionEvent event) {
        Sport item = sportTableView.getSelectionModel().getSelectedItem();
        int next = sportTableView.getSelectionModel().getSelectedIndex();

        if (item != null) {
            SportDAO sportDAO = new SportDAO();
            try {
                sportDAO.deleteSport(item);
                updateView(next);
            } catch (SportDAO.SportInUseException e) {
                e.printStackTrace();
                alert(
                        "Ошибка!",
                        "Невозможно удалить: вид спорта используется!"
                );
            }
        }
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
        Sport item = sportTableView.getSelectionModel().getSelectedItem();
        if (item != null) ((SportPopupFormController) popup(
                "../../view/sportPopupForm.fxml",
                "Редактирование записи", false
        )).setData(item);
    }

    @Override
    public void loadView(){
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<Sport, String>("name"));
        unitTableColumn.setCellValueFactory(new PropertyValueFactory<Sport, String>("unit"));
    }

    @Override
    public void updateView() {
        SportDAO sportDAO = new SportDAO();
        List<Sport> sportList = sportDAO.getAllSports();

        sportObservableList.clear();
        sportObservableList.addAll(sportList);
        sportTableView.setItems(sportObservableList);
    }

    private void updateView(int selectedRow) {
        updateView();
        sportTableView.getSelectionModel().select(selectedRow);
    }

    @FXML
    private void refresh(ActionEvent event) {
        updateView();
    }
}