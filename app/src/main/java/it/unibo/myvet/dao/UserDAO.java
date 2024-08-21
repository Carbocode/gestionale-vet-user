package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unibo.myvet.model.Account;
import it.unibo.myvet.model.User;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class UserDAO {

    private final AccountDAO accountDAO = new AccountDAO(); // Utilizzo di AccountDAO

    public User findById(int userId) {
        User user = null;
        String sql = "SELECT * FROM utenti WHERE IDUtente = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String cf = resultSet.getString("CF");
                    // Recupera i dettagli dell'account utilizzando l'AccountDAO
                    User tempUser = (User) accountDAO.findByCf(cf);
                    user = new User(tempUser.getCf(), tempUser.getPassword(), tempUser.getFirstName(),
                            tempUser.getLastName(), tempUser.getPhoneNumber(), userId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void save(User account) {
        String sql = "INSERT INTO utenti (CF, Password) VALUES (?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, account.getCf());
            statement.setString(2, account.getPassword());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        accountDAO.update(user); // Aggiorna l'Account prima
        String sql = "UPDATE utenti SET CF = ? WHERE IDUtente = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, user.getCf());
            statement.setInt(2, user.getUserId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        User user = findById(userId);
        if (user != null) {
            accountDAO.delete(user.getCf()); // Elimina l'Account prima
            String sql = "DELETE FROM utenti WHERE IDUtente = ?";

            try (Database dbWrapper = DAOUtils.getConnection();
                    PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

                statement.setInt(1, userId);
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
