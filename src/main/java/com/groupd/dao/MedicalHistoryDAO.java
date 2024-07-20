package com.groupd.dao;

import com.groupd.beans.MedicalHistory;
import com.groupd.utils.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryDAO {

    public void addMedicalHistory(MedicalHistory medicalHistory) throws SQLException {
        String query = "INSERT INTO MedicalHistory (medical_history_id, patient_id, diagnosis, treatment, notes) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicalHistory.getMedicalHistoryId());
            statement.setString(2, medicalHistory.getPatientId());
            statement.setString(3, medicalHistory.getDiagnosis());
            statement.setString(4, medicalHistory.getTreatment());
            statement.setString(5, medicalHistory.getNotes());
            statement.executeUpdate();
        }
    }

    public MedicalHistory getMedicalHistory(String medicalHistoryId) throws SQLException {
        String query = "SELECT * FROM MedicalHistory WHERE medical_history_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicalHistoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MedicalHistory medicalHistory = new MedicalHistory();
                    medicalHistory.setMedicalHistoryId(resultSet.getString("medical_history_id"));
                    medicalHistory.setPatientId(resultSet.getString("patient_id"));
                    medicalHistory.setDiagnosis(resultSet.getString("diagnosis"));
                    medicalHistory.setTreatment(resultSet.getString("treatment"));
                    medicalHistory.setNotes(resultSet.getString("notes"));
                    return medicalHistory;
                }
            }
        }
        return null;
    }

    public List<MedicalHistory> getAllMedicalHistories() throws SQLException {
        String query = "SELECT * FROM MedicalHistory";
        List<MedicalHistory> medicalHistoryList = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                MedicalHistory medicalHistory = new MedicalHistory();
                medicalHistory.setMedicalHistoryId(resultSet.getString("medical_history_id"));
                medicalHistory.setPatientId(resultSet.getString("patient_id"));
                medicalHistory.setDiagnosis(resultSet.getString("diagnosis"));
                medicalHistory.setTreatment(resultSet.getString("treatment"));
                medicalHistory.setNotes(resultSet.getString("notes"));
                medicalHistoryList.add(medicalHistory);
            }
        }
        return medicalHistoryList;
    }

    public void updateMedicalHistory(MedicalHistory medicalHistory) throws SQLException {
        String query = "UPDATE MedicalHistory SET patient_id = ?, diagnosis = ?, treatment = ?, notes = ? WHERE medical_history_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicalHistory.getPatientId());
            statement.setString(2, medicalHistory.getDiagnosis());
            statement.setString(3, medicalHistory.getTreatment());
            statement.setString(4, medicalHistory.getNotes());
            statement.setString(5, medicalHistory.getMedicalHistoryId());
            statement.executeUpdate();
        }
    }

    public void deleteMedicalHistory(String medicalHistoryId) throws SQLException {
        String query = "DELETE FROM MedicalHistory WHERE medical_history_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicalHistoryId);
            statement.executeUpdate();
        }
    }
}
