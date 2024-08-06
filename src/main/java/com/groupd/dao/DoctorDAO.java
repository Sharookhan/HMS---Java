package com.groupd.dao;
import com.groupd.beans.Doctor;
import com.groupd.utils.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public void addDoctor(Doctor doctor) throws SQLException {
        String query = "INSERT INTO Doctors (doctor_id, first_name, last_name, specialization, phone_number, email, profile, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doctor.getDoctorId());
            statement.setString(2, doctor.getFirstName());
            statement.setString(3, doctor.getLastName());
            statement.setString(4, doctor.getSpecialization());
            statement.setString(5, doctor.getPhoneNumber());
            statement.setString(6, doctor.getEmail());
            statement.setString(7, doctor.getProfile());
            statement.setString(8, doctor.getPassword());
            statement.executeUpdate();
        }
    }

    public String getNextDoctorId() throws SQLException {
        String query = "SELECT MAX(doctor_id) AS last_id FROM Doctors";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                String lastId = resultSet.getString("last_id");
                if (lastId == null) {
                    return "D1"; // Start from D1 if no records exist
                } else {
                    int nextId = Integer.parseInt(lastId.substring(1)) + 1; // Increment the numeric part
                    return String.format("D%d", nextId); // Format to maintain D<number> format
                }
            }
        }
        return "D1"; // Default in case of error
    }

    public Doctor getDoctor(String doctorId) throws SQLException {
        String query = "SELECT * FROM Doctors WHERE doctor_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doctorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setDoctorId(resultSet.getString("doctor_id"));
                    doctor.setFirstName(resultSet.getString("first_name"));
                    doctor.setLastName(resultSet.getString("last_name"));
                    doctor.setSpecialization(resultSet.getString("specialization"));
                    doctor.setPhoneNumber(resultSet.getString("phone_number"));
                    doctor.setEmail(resultSet.getString("email"));
                    doctor.setProfile(resultSet.getString("profile"));
                    doctor.setPassword(resultSet.getString("password"));
                    return doctor;
                }
            }
        }
        return null;
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        String query = "SELECT * FROM Doctors";
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(resultSet.getString("doctor_id"));
                doctor.setFirstName(resultSet.getString("first_name"));
                doctor.setLastName(resultSet.getString("last_name"));
                doctor.setSpecialization(resultSet.getString("specialization"));
                doctor.setPhoneNumber(resultSet.getString("phone_number"));
                doctor.setEmail(resultSet.getString("email"));
                doctor.setProfile(resultSet.getString("profile"));
                doctor.setPassword(resultSet.getString("password"));
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    public void updateDoctor(Doctor doctor) throws SQLException {
        String query = "UPDATE Doctors SET first_name = ?, last_name = ?, specialization = ?, phone_number = ?, email = ?, profile = ?, password = ? WHERE doctor_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setString(3, doctor.getSpecialization());
            statement.setString(4, doctor.getPhoneNumber());
            statement.setString(5, doctor.getEmail());
            statement.setString(6, doctor.getProfile());
            statement.setString(7, doctor.getPassword());
            statement.setString(8, doctor.getDoctorId());
            statement.executeUpdate();
        }
    }

    public void deleteDoctor(String doctorId) throws SQLException {
        String query = "DELETE FROM Doctors WHERE doctor_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doctorId);
            statement.executeUpdate();
        }
    }
}
