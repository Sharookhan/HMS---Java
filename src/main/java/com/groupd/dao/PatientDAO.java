package com.groupd.dao;

import com.groupd.beans.Patient;
import com.groupd.utils.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public boolean addPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO Patients (patient_id, first_name, last_name, date_of_birth, gender, phone_number, email, address,  password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters
            statement.setString(1, patient.getPatientId());
            statement.setString(2, patient.getFirstName());
            statement.setString(3, patient.getLastName());
            statement.setDate(4, patient.getDateOfBirth());
            statement.setString(5, patient.getGender());
            statement.setString(6, patient.getPhoneNumber());
            statement.setString(7, patient.getEmail());
            statement.setString(8, patient.getAddress());
            statement.setString(9, patient.getPassword());

            // Execute update and check if rows were affected
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error adding patient: " + e.getMessage(), e);
        }
    }

    public String getNextPatientId() throws SQLException {
        String query = "SELECT MAX(patient_id) AS last_id FROM Patients";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                String lastId = resultSet.getString("last_id");
                if (lastId == null) {
                    return "P1"; // Start from P1 if no records exist
                } else {
                    int nextId = Integer.parseInt(lastId.substring(1)) + 1; // Increment the numeric part
                    return String.format("P%d", nextId); // Format to maintain P<number> format
                }
            }
        }
        return "P1"; // Default in case of error
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
                patient.setPassword(resultSet.getString("password"));
                patients.add(patient);
            }
        }
        return patients;
    }

    public void updatePatient(Patient patient) throws SQLException {
        if (patient.getPatientId() == null || patient.getPatientId().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be null or empty.");
        }

        StringBuilder queryBuilder = new StringBuilder("UPDATE Patients SET ");
        List<Object> parameters = new ArrayList<>();

        // Append the fields to be updated
        queryBuilder.append("first_name = ?, ");
        parameters.add(patient.getFirstName());
        queryBuilder.append("last_name = ?, ");
        parameters.add(patient.getLastName());
        queryBuilder.append("date_of_birth = ?, ");
        parameters.add(patient.getDateOfBirth());
        queryBuilder.append("gender = ?, ");
        parameters.add(patient.getGender());
        queryBuilder.append("phone_number = ?, ");
        parameters.add(patient.getPhoneNumber());
        queryBuilder.append("email = ?, ");
        parameters.add(patient.getEmail());
        queryBuilder.append("address = ?");
        parameters.add(patient.getAddress());

        // Conditionally append password if it's not null or empty
        if (patient.getPassword() != null && !patient.getPassword().trim().isEmpty()) {
            queryBuilder.append(", password = ?");
            parameters.add(patient.getPassword());
        }

        // Append the WHERE clause
        queryBuilder.append(" WHERE patient_id = ?");
        parameters.add(patient.getPatientId());

        String query = queryBuilder.toString();
        System.out.println("Generated Query: " + query); // Debug info
        System.out.println("Parameters: " + parameters); // Debug info

        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            // Execute update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient updated successfully.");
            } else {
                System.out.println("No rows affected. Patient may not have been updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating patient: " + e.getMessage(), e);
        }
    }




    public void deletePatient(String patientId) throws SQLException {
        String query = "DELETE FROM Patients WHERE patient_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, patientId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient deleted successfully.");
            } else {
                System.out.println("No rows affected. Patient may not have been deleted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Optional: Rollback transaction if using manual commit
            throw new SQLException("Error deleting patient: " + e.getMessage(), e);
        }
    }
}
