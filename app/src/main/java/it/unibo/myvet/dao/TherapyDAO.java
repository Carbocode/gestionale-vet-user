package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Therapy;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class TherapyDAO {

    public Therapy findById(Integer therapyId) {
        Therapy therapy = null;
        String sql = "SELECT * FROM Therapies WHERE IDTerapia = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, therapyId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    therapy = mapToTherapy(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return therapy;
    }

    public List<Therapy> findAll() {
        List<Therapy> therapies = new ArrayList<>();
        String sql = "SELECT * FROM Therapies";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                therapies.add(mapToTherapy(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return therapies;
    }

    public void save(Therapy therapy) {
        String sql = "INSERT INTO Therapies (IDTerapia, IDAnimale, Descrizione, DataInizio, DataFine) VALUES (?, ?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, therapy.getTherapyId());
            statement.setInt(2, therapy.getAnimalId());
            statement.setString(3, therapy.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(therapy.getStartDate()));
            statement.setTimestamp(5, Timestamp.valueOf(therapy.getEndDate()));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Therapy therapy) {
        String sql = "UPDATE Therapies SET IDAnimale = ?, Descrizione = ?, DataInizio = ?, DataFine = ? WHERE IDTerapia = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, therapy.getAnimalId());
            statement.setString(2, therapy.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(therapy.getStartDate()));
            statement.setTimestamp(4, Timestamp.valueOf(therapy.getEndDate()));
            statement.setInt(5, therapy.getTherapyId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer therapyId) {
        String sql = "DELETE FROM Therapies WHERE IDTerapia = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, therapyId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Therapy mapToTherapy(ResultSet resultSet) throws SQLException {
        int therapyId = resultSet.getInt("IDTerapia");
        int animalId = resultSet.getInt("IDAnimale");
        String description = resultSet.getString("Descrizione");
        LocalDateTime startDate = resultSet.getTimestamp("DataInizio").toLocalDateTime();
        LocalDateTime endDate = resultSet.getTimestamp("DataFine").toLocalDateTime();

        return new Therapy(therapyId, animalId, description, startDate, endDate);
    }
}
