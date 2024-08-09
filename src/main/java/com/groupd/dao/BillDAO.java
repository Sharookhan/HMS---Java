package com.groupd.dao;

import com.groupd.beans.Bill;
import com.groupd.utils.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public void addBill(Bill bill) throws SQLException {
        String query = "INSERT INTO Bills (patient_id, staff_id, amount, bill_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bill.getPatientId());
            statement.setString(2, bill.getStaffId());
            statement.setBigDecimal(3, bill.getAmount());
            statement.setDate(4, bill.getBillDate());
            statement.setString(5, bill.getStatus());
            statement.executeUpdate();
        }
    }

    public Bill getBill(int billId) throws SQLException {
        String query = "SELECT * FROM Bills WHERE bill_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, billId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(resultSet.getInt("bill_id"));
                    bill.setPatientId(resultSet.getString("patient_id"));
                    bill.setStaffId(resultSet.getString("staff_id"));
                    bill.setAmount(resultSet.getBigDecimal("amount"));
                    bill.setBillDate(resultSet.getDate("bill_date"));
                    bill.setStatus(resultSet.getString("status"));
                    return bill;
                }
            }
        }
        return null;
    }

    public List<Bill> getAllBills() throws SQLException {
        String query = "SELECT * FROM Bills";
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setBillId(resultSet.getInt("bill_id"));
                bill.setPatientId(resultSet.getString("patient_id"));
                bill.setStaffId(resultSet.getString("staff_id"));
                bill.setAmount(resultSet.getBigDecimal("amount"));
                bill.setBillDate(resultSet.getDate("bill_date"));
                bill.setStatus(resultSet.getString("status"));
                bills.add(bill);
            }
        }
        return bills;
    }

    public void updateBill(Bill bill) throws SQLException {
        String query = "UPDATE Bills SET patient_id = ?, staff_id = ?, amount = ?, bill_date = ?, status = ? WHERE bill_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bill.getPatientId());
            statement.setString(2, bill.getStaffId());
            statement.setBigDecimal(3, bill.getAmount());
            statement.setDate(4, bill.getBillDate());
            statement.setString(5, bill.getStatus());
            statement.setInt(6, bill.getBillId());
            statement.executeUpdate();
        }
    }

    public void deleteBill(int billId) throws SQLException {
        String query = "DELETE FROM Bills WHERE bill_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, billId);
            statement.executeUpdate();
        }
    }

    public List<Bill> getBillsByPatientId(String patientId) throws SQLException {
        String query = "SELECT * FROM Bills WHERE patient_id = ?";
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(resultSet.getInt("bill_id"));
                    bill.setPatientId(resultSet.getString("patient_id"));
                    bill.setStaffId(resultSet.getString("staff_id"));
                    bill.setAmount(resultSet.getBigDecimal("amount"));
                    bill.setBillDate(resultSet.getDate("bill_date"));
                    bill.setStatus(resultSet.getString("status"));
                    bills.add(bill);
                }
            }
        }
        return bills;
    }

    public List<Bill> getPendingBillsByPatientId(String patientId) throws SQLException {
        String query = "SELECT * FROM Bills WHERE patient_id = ? AND status = 'Unpaid'";
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(resultSet.getInt("bill_id"));
                    bill.setPatientId(resultSet.getString("patient_id"));
                    bill.setStaffId(resultSet.getString("staff_id"));
                    bill.setAmount(resultSet.getBigDecimal("amount"));
                    bill.setBillDate(resultSet.getDate("bill_date"));
                    bill.setStatus(resultSet.getString("status"));
                    bills.add(bill);
                }
            }
        }
        return bills;
    }
}
