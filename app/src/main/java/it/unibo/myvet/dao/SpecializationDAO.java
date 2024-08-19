package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Specialization;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class SpecializationDAO {

    public Specialization findById(Integer specializationId) {
        Specialization specialization = null;
        String sql = "SELECT * FROM Specializations WHERE IDSpecializzazione = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, specializationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    specialization = mapToSpecialization(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialization;
    }

    public List<Specialization> findAll() {
        List<Specialization> specializations = new ArrayList<>();
        String sql = "SELECT * FROM Specializations";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                specializations.add(mapToSpecialization(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specializations;
    }

    public void save(Specialization specialization) {
        String sql = "INSERT INTO Specializations (Nome) VALUES (?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, specialization.getName());
            statement.executeUpdate();

            // Recupera l'ID generato automaticamente e impostalo sull'oggetto
            // Specialization
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    specialization.setSpecializationId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Specialization specialization) {
        String sql = "UPDATE Specializations SET Nome = ? WHERE IDSpecializzazione = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, specialization.getName());
            statement.setInt(2, specialization.getSpecializationId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer specializationId) {
        String sql = "DELETE FROM Specializations WHERE IDSpecializzazione = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, specializationId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Specialization mapToSpecialization(ResultSet resultSet) throws SQLException {
        int specializationId = resultSet.getInt("IDSpecializzazione");
        String name = resultSet.getString("Nome");

        return new Specialization(specializationId, name);
    }

}
