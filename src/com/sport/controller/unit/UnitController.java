package com.sport.controller.unit;

import com.sport.controller.ControllerImpl;
import com.sport.dao.impl.UnitDAO;
import com.sport.model.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class UnitController extends ControllerImpl {
    private ObservableList<Unit> unitObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Unit> unitTableView;
    @FXML
    private TableColumn<Unit, String> nameTableColumn;

    @FXML
    private void initialize() {
        loadView();
        updateView();
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        popup(
                "../../view/unitPopupForm.fxml",
                "Добавление записи", false
        );
    }

    @FXML
    private void delete(ActionEvent event) {
        Unit item = unitTableView.getSelectionModel().getSelectedItem();
        int next = unitTableView.getSelectionModel().getSelectedIndex();

        if (item != null) {
            UnitDAO unitDAO = new UnitDAO();
            try {
                unitDAO.deleteUnit(item);
                updateView(next);
            } catch (UnitDAO.UnitInUseException e) {
                e.printStackTrace();
                alert(
                        "Ошибка!",
                        "Невозможно удалить: единица измерения используется!"
                );
            }
        }
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
        Unit item = unitTableView.getSelectionModel().getSelectedItem();
        if (item != null) ((UnitPopupFormController) popup(
                "../../view/unitPopupForm.fxml",
                "Редактирование записи", false
        )).setData(item);
    }

    @Override
    public void loadView(){
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @Override
    public void updateView() {
        UnitDAO unitDAO = new UnitDAO();
        List<Unit> unitList = unitDAO.getAllUnits();

        unitObservableList.clear();
        unitObservableList.addAll(unitList);
        unitTableView.setItems(unitObservableList);
    }

    private void updateView(int selectedRow) {
        updateView();
        unitTableView.getSelectionModel().select(selectedRow);
    }

    @FXML
    private void refresh(ActionEvent event) {
        updateView();
    }
}