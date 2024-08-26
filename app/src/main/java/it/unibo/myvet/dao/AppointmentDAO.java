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
    private ServiceDAO serviceDAO = new ServiceDAO(); // Nuovo DAO per gestire il Service
    private AppointmentStateDAO appointmentStateDAO = new AppointmentStateDAO();

    public Appointment findById(int appointmentId) {
        Appointment appointment = null;
        String sql = "SELECT * FROM Appuntamenti WHERE IDAppuntamento = ?";

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
        String sql = "SELECT * FROM Appuntamenti WHERE IDAnimale = ?";

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
        String sql = "SELECT * FROM Appuntamenti WHERE IDVeterinario = ?";

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
        String sql = "INSERT INTO Appuntamenti (IDAnimale, IDVeterinario, IDServizio, DataOra, Referto, IDStato, Durata) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, appointment.getAnimal().getAnimalId());
            statement.setInt(2, appointment.getVet().getVetId());
            statement.setInt(3, appointment.getService().getServiceId()); // Aggiungi ID del servizio
            statement.setTimestamp(4, Timestamp.valueOf(appointment.getDateTime()));
            statement.setBytes(5, appointment.getReport()); // Gestione del file come array di byte
            statement.setInt(6, appointment.getStatus().getStateId()); // Inserisce l'ID dello stato
            statement.setInt(7, appointment.getDuration()); // Inserisce la durata
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
        String sql = "UPDATE Appuntamenti SET IDAnimale = ?, IDVeterinario = ?, IDServizio = ?, DataOra = ?, Referto = ?, IDStato = ?, Durata = ? WHERE IDAppuntamento = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, appointment.getAnimal().getAnimalId());
            statement.setInt(2, appointment.getVet().getVetId());
            statement.setInt(3, appointment.getService().getServiceId()); // Aggiungi ID del servizio
            statement.setTimestamp(4, Timestamp.valueOf(appointment.getDateTime()));
            statement.setBytes(5, appointment.getReport()); // Gestione del file come array di byte
            statement.setInt(6, appointment.getStatus().getStateId()); // Aggiorna l'ID dello stato
            statement.setInt(7, appointment.getDuration()); // Aggiorna la durata
            statement.setInt(8, appointment.getAppointmentId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int appointmentId) {
        String sql = "DELETE FROM Appuntamenti WHERE IDAppuntamento = ?";

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
        int serviceId = resultSet.getInt("IDServizio"); // Recupera ID del servizio
        LocalDateTime dateTime = resultSet.getTimestamp("DataOra").toLocalDateTime();
        byte[] report = resultSet.getBytes("Referto");
        int statusId = resultSet.getInt("IDStato");
        int duration = resultSet.getInt("Durata");

        Animal animal = animalDAO.findById(animalId);
        Vet vet = vetDAO.findById(vetId);
        Service service = serviceDAO.findById(serviceId); // Recupera l'oggetto Service
        AppointmentState status = appointmentStateDAO.findById(statusId);

        return new Appointment(appointmentId, animal, vet, dateTime, service, duration, status, report);
    }
}