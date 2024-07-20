package com.groupd.dao;

import com.groupd.beans.Patient;
import com.groupd.utils.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public void addPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO Patients (patient_id, first_name, last_name, date_of_birth, gender, phone_number, email, address, medical_history_id, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patient.getPatientId());
            statement.setString(2, patient.getFirstName());
            statement.setString(3, patient.getLastName());
            statement.setDate(4, patient.getDateOfBirth());
            statement.setString(5, patient.getGender());
            statement.setString(6, patient.getPhoneNumber());
            statement.setString(7, patient.getEmail());
            statement.setString(8, patient.getAddress());
            statement.setString(9, patient.getMedicalHistoryId());
            statement.setString(10, patient.getPassword());
            statement.executeUpdate();
        }
    }

    public Patient getPatient(String patientId) throws SQLException {
        String query = "SELECT * FROM Patients WHERE patient_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Patient patient = new Patient();
                    patient.setPatientId(resultSet.getString("patient_id"));
                    patient.setFirstName(resultSet.getString("first_name"));
                    patient.setLastName(resultSet.getString("last_name"));
                    patient.setDateOfBirth(resultSet.getDate("date_of_birth"));
                    patient.setGender(resultSet.getString("gender"));
                    patient.setPhoneNumber(resultSet.getString("phone_number"));
                    patient.setEmail(resultSet.getString("email"));
                    patient.setAddress(resultSet.getString("address"));
                    patient.setMedicalHistoryId(resultSet.getString("medical_history_id"));
                    patient.setPassword(resultSet.getString("password"));
                    return patient;
                }
            }
        }
        return null;
    }

    public List<Patient> getAllPatients() throws SQLException {
        String query = "SELECT * FROM Patients";
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getString("patient_id"));
                patient.setFirstName(resultSet.getString("first_name"));
                patient.setLastName(resultSet.getString("last_name"));
                patient.setDateOfBirth(resultSet.getDate("date_of_birth"));
                patient.setGender(resultSet.getString("gender"));
                patient.setPhoneNumber(resultSet.getString("phone_number"));
                patient.setEmail(resultSet.getString("email"));
                patient.setAddress(resultSet.getString("address"));
                patient.setMedicalHistoryId(resultSet.getString("medical_history_id"));
                patient.setPassword(resultSet.getString("password"));
                patients.add(patient);
            }
        }
        return patients;
    }

    public void updatePatient(Patient patient) throws SQLException {
        String query = "UPDATE Patients SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, phone_number = ?, email = ?, address = ?, medical_history_id = ?, password = ? WHERE patient_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setDate(3, patient.getDateOfBirth());
            statement.setString(4, patient.getGender());
            statement.setString(5, patient.getPhoneNumber());
            statement.setString(6, patient.getEmail());
            statement.setString(7, patient.getAddress());
            statement.setString(8, patient.getMedicalHistoryId());
            statement.setString(9, patient.getPassword());
            statement.setString(10, patient.getPatientId());
            statement.executeUpdate();
        }
    }

    public void deletePatient(String patientId) throws SQLException {
        String query = "DELETE FROM Patients WHERE patient_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientId);
            statement.executeUpdate();
        }
    }
}
