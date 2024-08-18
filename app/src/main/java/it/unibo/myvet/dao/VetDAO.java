package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.api.Dao;
import it.unibo.myvet.model.Vet;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class VetDAO implements Dao<Vet, Integer> {

    @Override
    public Vet findById(Integer vetId) {
        Vet vet = null;
        String sql = "SELECT * FROM Account INNER JOIN Veterinari ON Account.CF = Veterinari.CF WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    vet = mapToVet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vet;
    }

    @Override
    public List<Vet> findAll() {
        List<Vet> vets = new ArrayList<>();
        String sql = "SELECT * FROM Account INNER JOIN Veterinari ON Account.CF = Veterinari.CF";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                vets.add(mapToVet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vets;
    }

    @Override
    public void save(Vet vet) {
        String sqlAccount = "INSERT INTO Account (CF, Password, Nome, Cognome, Telefono) VALUES (?, ?, ?, ?, ?)";
        String sqlVet = "INSERT INTO Veterinari (IDVeterinario, CF) VALUES (?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statementAccount = dbWrapper.prepareStatement(sqlAccount);
                PreparedStatement statementVet = dbWrapper.prepareStatement(sqlVet)) {

            // Salvare l'account
            statementAccount.setString(1, vet.getCf());
            statementAccount.setString(2, vet.getPassword());
            statementAccount.setString(3, vet.getFirstName());
            statementAccount.setString(4, vet.getLastName());
            statementAccount.setString(5, vet.getPhoneNumber());
            statementAccount.executeUpdate();

            // Salvare il veterinario
            statementVet.setInt(1, vet.getVetId());
            statementVet.setString(2, vet.getCf());
            statementVet.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Vet vet) {
        String sqlAccount = "UPDATE Account SET Password = ?, Nome = ?, Cognome = ?, Telefono = ? WHERE CF = ?";
        String sqlVet = "UPDATE Veterinari SET CF = ? WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statementAccount = dbWrapper.prepareStatement(sqlAccount);
                PreparedStatement statementVet = dbWrapper.prepareStatement(sqlVet)) {

            // Aggiornare l'account
            statementAccount.setString(1, vet.getPassword());
            statementAccount.setString(2, vet.getFirstName());
            statementAccount.setString(3, vet.getLastName());
            statementAccount.setString(4, vet.getPhoneNumber());
            statementAccount.setString(5, vet.getCf());
            statementAccount.executeUpdate();

            // Aggiornare il veterinario
            statementVet.setString(1, vet.getCf());
            statementVet.setInt(2, vet.getVetId());
            statementVet.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer vetId) {
        String sqlVet = "DELETE FROM Veterinari WHERE IDVeterinario = ?";
        String sqlAccount = "DELETE FROM Account WHERE CF = (SELECT CF FROM Veterinari WHERE IDVeterinario = ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statementVet = dbWrapper.prepareStatement(sqlVet);
                PreparedStatement statementAccount = dbWrapper.prepareStatement(sqlAccount)) {

            // Eliminare il veterinario
            statementVet.setInt(1, vetId);
            statementVet.executeUpdate();

            // Eliminare l'account
            statementAccount.setInt(1, vetId);
            statementAccount.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo per mappare un ResultSet su un oggetto Vet
    private Vet mapToVet(ResultSet resultSet) throws SQLException {
        String cf = resultSet.getString("CF");
        String password = resultSet.getString("Password");
        String firstName = resultSet.getString("Nome");
        String lastName = resultSet.getString("Cognome");
        String phoneNumber = resultSet.getString("Telefono");
        int vetId = resultSet.getInt("IDVeterinario");

        return new Vet(cf, password, firstName, lastName, phoneNumber, vetId);
    }
}
