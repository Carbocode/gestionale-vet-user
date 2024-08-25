package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.AppointmentState;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class AppointmentStateDAO {

    public AppointmentState findById(int stateId) {
        AppointmentState state = null;
        String sql = "SELECT * FROM Stati WHERE IDStato = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, stateId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    state = mapToAppointmentState(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return state;
    }

    public List<AppointmentState> findAll() {
        List<AppointmentState> states = new ArrayList<>();
        String sql = "SELECT * FROM Stati";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                states.add(mapToAppointmentState(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return states;
    }

    public void save(AppointmentState state) {
        String sql = "INSERT INTO Stati (Nome) VALUES (?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, state.getStateName());
            statement.executeUpdate();

            // Recupera l'ID generato automaticamente e impostalo sull'oggetto
            // AppointmentState
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    state.setStateId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(AppointmentState state) {
        String sql = "UPDATE Stati SET Nome = ? WHERE IDStato = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, state.getStateName());
            statement.setInt(2, state.getStateId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int stateId) {
        String sql = "DELETE FROM Stati WHERE IDStato = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, stateId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AppointmentState mapToAppointmentState(ResultSet resultSet) throws SQLException {
        int stateId = resultSet.getInt("IDStato");
        String stateName = resultSet.getString("Nome");

        return new AppointmentState(stateId, stateName);
    }
}
