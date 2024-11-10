package com.project.qlbh_kh.controllers;
import com.project.qlbh_kh.entity.receiver;
import com.project.qlbh_kh.utils.JDBCUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class danhSachNguoiNhanController implements  Initializable{
    @FXML
    protected TableView<receiver> receiverList;
    protected ObservableList<receiver> receivers = FXCollections.observableArrayList();
    @FXML
    protected TableColumn<receiver, String> addressColumn;

    @FXML
    protected TableColumn<receiver, String> receiverNameColumn;

    @FXML
    protected TextField receiverNameField;

    @FXML
    protected TableColumn<receiver, String> phoneNumberColumn;

    protected basicController mainController;
    public void setMainController(basicController basicController) {
        this.mainController = basicController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //cai dat thuoc tinh du lieu cho cac cot
        receiverNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        //load du lieu
        loadReceiverList();
        //cai dat bo loc tim kiem theo ten KH
        receiverNameField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            receiverList.setItems(receivers.filtered(receiver -> {
                String name = receiver.getName();
                System.out.println(name);
                return name.toLowerCase().contains(newValue.toLowerCase());
            }));
        }));
        //chon nguoi nhan
        receiverList.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null && mainController != null)
            {
                mainController.setSelectedReceiver(newValue.getReceiver_id(),newValue.getName());
                ((Stage) receiverList.getScene().getWindow()).close();
            }
        }));
    }
    public void loadReceiverList()
    {
        String sql = "exec receivers_list";
        try
        {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String phone_number = resultSet.getString(4);
                receivers.add(new receiver(id,name,address,phone_number));
            }
            receiverList.setItems(receivers);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}