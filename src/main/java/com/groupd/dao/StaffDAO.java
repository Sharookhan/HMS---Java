package com.groupd.dao;

import com.groupd.beans.Staff;
import com.groupd.utils.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public void addStaff(Staff staff) throws SQLException {
        String query = "INSERT INTO Staff (staff_id, first_name, last_name, position, phone_number, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getStaffId());
            statement.setString(2, staff.getFirstName());
            statement.setString(3, staff.getLastName());
            statement.setString(4, staff.getPosition());
            statement.setString(5, staff.getPhoneNumber());
            statement.setString(6, staff.getEmail());
            statement.setString(7, staff.getPassword());
            statement.executeUpdate();
        }
    }

    public Staff getStaff(String staffId) throws SQLException {
        String query = "SELECT * FROM Staff WHERE staff_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staffId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Staff staff = new Staff();
                    staff.setStaffId(resultSet.getString("staff_id"));
                    staff.setFirstName(resultSet.getString("first_name"));
                    staff.setLastName(resultSet.getString("last_name"));
                    staff.setPosition(resultSet.getString("position"));
                    staff.setPhoneNumber(resultSet.getString("phone_number"));
                    staff.setEmail(resultSet.getString("email"));
                    staff.setPassword(resultSet.getString("password"));
                    return staff;
                }
            }
        }
        return null;
    }

    public List<Staff> getAllStaff() throws SQLException {
        String query = "SELECT * FROM Staff";
        List<Staff> staffList = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffId(resultSet.getString("staff_id"));
                staff.setFirstName(resultSet.getString("first_name"));
                staff.setLastName(resultSet.getString("last_name"));
                staff.setPosition(resultSet.getString("position"));
                staff.setPhoneNumber(resultSet.getString("phone_number"));
                staff.setEmail(resultSet.getString("email"));
                staff.setPassword(resultSet.getString("password"));
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public void updateStaff(Staff staff) throws SQLException {
        String query = "UPDATE Staff SET first_name = ?, last_name = ?, position = ?, phone_number = ?, email = ?, password = ? WHERE staff_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setString(3, staff.getPosition());
            statement.setString(4, staff.getPhoneNumber());
            statement.setString(5, staff.getEmail());
            statement.setString(6, staff.getPassword());
            statement.setString(7, staff.getStaffId());
            statement.executeUpdate();
        }
    }

    public void deleteStaff(String staffId) throws SQLException {
        String query = "DELETE FROM Staff WHERE staff_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staffId);
            statement.executeUpdate();
        }
    }
}
