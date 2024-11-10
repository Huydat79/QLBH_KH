package com.project.qlbh_kh.controllers;

import com.project.qlbh_kh.entity.receiver;
import com.project.qlbh_kh.utils.JDBCUtil;
import javafx.fxml.Initializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class danhSachNguoiNhanNhapController extends danhSachNguoiNhanController implements Initializable {
    @Override
    public void loadReceiverList()
    {
        String sql = "exec receivers_in_list";
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
