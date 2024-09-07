package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unibo.myvet.model.Account;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class AccountDAO {

    public Account findByCf(String cf) {
        Account account = null;
        String sql = "SELECT * FROM Account WHERE CF = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, cf);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    account = mapToAccount(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public void save(Account account) {
        String sql = "INSERT INTO Account (CF, Password, Nome, Cognome, Telefono) VALUES (?, ?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, account.getCf());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getFirstName());
            statement.setString(4, account.getLastName());
            statement.setString(5, account.getPhoneNumber());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Account account) {
        String sql = "UPDATE Account SET Password = ?, Nome = ?, Cognome = ?, Telefono = ? WHERE CF = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, account.getPassword());
            statement.setString(2, account.getFirstName());
            statement.setString(3, account.getLastName());
            statement.setString(4, account.getPhoneNumber());
            statement.setString(5, account.getCf());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String ssn) {
        String sql = "DELETE FROM Account WHERE CF = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, ssn);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account mapToAccount(ResultSet resultSet) throws SQLException {
        String cf = resultSet.getString("CF");
        String password = resultSet.getString("Password");
        String firstName = resultSet.getString("Nome");
        String lastName = resultSet.getString("Cognome");
        String phoneNumber = resultSet.getString("Telefono");

        return new Account(cf, password, firstName, lastName, phoneNumber);
    }

    public Account authenticate(String CF, String password) {
        Account account = null;
        String query = "SELECT * FROM account WHERE CF = ? AND password = ?";
        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(query)) {
            statement.setString(1, CF);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return findByCf(CF);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
}
