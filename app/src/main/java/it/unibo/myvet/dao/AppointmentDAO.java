package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.*;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class AppointmentDAO {

    private AnimalDAO animalDAO = new AnimalDAO();
    private VetDAO vetDAO = new VetDAO();
    private AppointmentStateDAO appointmentStateDAO = new AppointmentStateDAO();

    public Appointment findById(int appointmentId) {
        Appointment appointment = null;
        String sql = "SELECT * FROM Appointments WHERE IDAppuntamento = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    appointment = mapToAppointment(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

    public List<Appointment> findByAnimalId(int animalId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE IDAnimale = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, animalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(mapToAppointment(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public List<Appointment> findByVetId(int vetId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(mapToAppointment(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public void save(Appointment appointment) {
        String sql = "INSERT INTO Appointments (IDAnimale, IDVeterinario, DataOraAppuntamento, Referto, IDStato) VALUES (?, ?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, appointment.getAnimal().getAnimalId());
            statement.setInt(2, appointment.getVet().getVetId());
            statement.setTimestamp(3, Timestamp.valueOf(appointment.getDateTime()));
            statement.setBytes(4, appointment.getReport()); // Gestione del file come array di byte
            statement.setInt(5, appointment.getStatus().getStateId()); // Inserisce l'ID dello stato
            statement.executeUpdate();

            // Recupera l'ID generato automaticamente e impostalo sull'oggetto Appointment
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setAppointmentId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Appointment appointment) {
        String sql = "UPDATE Appointments SET IDAnimale = ?, IDVeterinario = ?, DataOraAppuntamento = ?, Referto = ?, IDStato = ? WHERE IDAppuntamento = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, appointment.getAnimal().getAnimalId());
            statement.setInt(2, appointment.getVet().getVetId());
            statement.setTimestamp(3, Timestamp.valueOf(appointment.getDateTime()));
            statement.setBytes(4, appointment.getReport()); // Gestione del file come array di byte
            statement.setInt(5, appointment.getStatus().getStateId()); // Aggiorna l'ID dello stato
            statement.setInt(6, appointment.getAppointmentId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int appointmentId) {
        String sql = "DELETE FROM Appointments WHERE IDAppuntamento = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Appointment mapToAppointment(ResultSet resultSet) throws SQLException {
        int appointmentId = resultSet.getInt("IDAppuntamento");
        int animalId = resultSet.getInt("IDAnimale");
        int vetId = resultSet.getInt("IDVeterinario");
        LocalDateTime dateTime = resultSet.getTimestamp("DataOraAppuntamento").toLocalDateTime();
        byte[] report = resultSet.getBytes("Referto");
        int statusId = resultSet.getInt("IDStato");

        Animal animal = animalDAO.findById(animalId);
        Vet vet = vetDAO.findById(vetId);
        AppointmentState status = appointmentStateDAO.findById(statusId);

        return new Appointment(appointmentId, animal, vet, dateTime, report, status);
    }
}
