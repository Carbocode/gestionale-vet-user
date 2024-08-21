package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Account;
import it.unibo.myvet.model.User;
import it.unibo.myvet.model.Vet;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class VetDAO {

    private final AccountDAO accountDAO = new AccountDAO(); // Utilizzo di AccountDAO

    public Vet findById(int vetId) {
        Vet vet = null;
        String sql = "SELECT * FROM Veterinari WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String cf = resultSet.getString("CF");
                    // Recupera i dettagli dell'account utilizzando l'AccountDAO
                    Vet tempVet = (Vet) accountDAO.findByCf(cf);
                    vet = new Vet(tempVet.getCf(), tempVet.getPassword(), tempVet.getFirstName(), tempVet.getLastName(),
                            tempVet.getPhoneNumber(), vetId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vet;
    }

    public void save(Vet account) {
        String sql = "INSERT INTO veterinari (CF, Password) VALUES (?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, account.getCf());
            statement.setString(2, account.getPassword());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Vet vet) {
        accountDAO.update(vet); // Aggiorna l'Account prima
        String sql = "UPDATE Veterinari SET CF = ? WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, vet.getCf());
            statement.setInt(2, vet.getVetId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int vetId) {
        Vet vet = findById(vetId);
        if (vet != null) {
            accountDAO.delete(vet.getCf()); // Elimina l'Account prima
            String sql = "DELETE FROM Veterinari WHERE IDVeterinario = ?";

            try (Database dbWrapper = DAOUtils.getConnection();
                    PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

                statement.setInt(1, vetId);
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Vet> searchVet(String searchText) {
        List<Vet> vets = new ArrayList<>();
        String sql = "SELECT * FROM veterinari JOIN account ON account.CF=veterinari.CF WHERE nome LIKE ? OR cognome LIKE ?";
        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, searchText);
            statement.setString(2, searchText);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idVet = resultSet.getInt("IDVeterinario");
                    String cf = resultSet.getString("CF");
                    // Recupera i dettagli dell'account utilizzando l'AccountDAO
                    Vet tempVet = (Vet) accountDAO.findByCf(cf);
                    vets.add(new Vet(
                            tempVet.getCf(),
                            tempVet.getPassword(),
                            tempVet.getFirstName(),
                            tempVet.getLastName(),
                            tempVet.getPhoneNumber(),
                            idVet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vets;
    }

    public List<Vet> findAll() {
        List<Vet> vets = new ArrayList<>();
        String sql = "SELECT * FROM veterinari JOIN account ON account.CF = veterinari.CF";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idVet = resultSet.getInt("IDVeterinario");
                String cf = resultSet.getString("CF");

                // Recupera i dettagli dell'account utilizzando l'AccountDAO
                Vet tempVet = (Vet) accountDAO.findByCf(cf);
                vets.add(new Vet(
                        tempVet.getCf(),
                        tempVet.getPassword(),
                        tempVet.getFirstName(),
                        tempVet.getLastName(),
                        tempVet.getPhoneNumber(),
                        idVet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vets;
    }

}
