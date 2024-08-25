package it.unibo.myvet.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Therapy;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class TherapyDAO {

    public Therapy findById(Integer therapyId) {
        Therapy therapy = null;
        String sql = "SELECT * FROM Terapie WHERE IDTerapia = ?";

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

    public List<Therapy> findByAppointmentId(int appointmentId) {
        List<Therapy> therapies = new ArrayList<>();
        String sql = "SELECT * FROM Terapie WHERE IDAppuntamento = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    therapies.add(mapToTherapy(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return therapies;
    }

    public void save(Therapy therapy) {
        String sql = "INSERT INTO Terapie (IDAppuntamento, Nome, Descrizione, DataInizio, DataFine) VALUES (?, ?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, therapy.getAppointmentId());
            statement.setString(2, therapy.getName());
            statement.setString(3, therapy.getDescription());
            statement.setDate(4, Date.valueOf(therapy.getStartDate()));
            statement.setDate(5, Date.valueOf(therapy.getStartDate()));
            statement.executeUpdate();

            // Recupera l'ID generato automaticamente e impostalo sull'oggetto Therapy
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    therapy.setTherapyId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Therapy therapy) {
        String sql = "UPDATE Terapie SET IDAppuntamento = ?, Nome = ?, Descrizione = ?, DataInizio = ?, DataFine = ? WHERE IDTerapia = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, therapy.getAppointmentId());
            statement.setString(2, therapy.getName());
            statement.setString(3, therapy.getDescription());
            statement.setDate(4, Date.valueOf(therapy.getStartDate()));
            statement.setDate(5, Date.valueOf(therapy.getEndDate()));
            statement.setInt(6, therapy.getTherapyId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer therapyId) {
        String sql = "DELETE FROM Terapie WHERE IDTerapia = ?";

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
        int appointmentId = resultSet.getInt("IDAppuntamento");
        String name = resultSet.getString("Nome");
        String description = resultSet.getString("Descrizione");
        LocalDate startDate = resultSet.getDate("DataInizio").toLocalDate();
        LocalDate endDate = resultSet.getDate("DataFine").toLocalDate();

        return new Therapy(therapyId, appointmentId, name, description, startDate, endDate);
    }
}
