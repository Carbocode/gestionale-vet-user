package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void save(Vet vet) {
        accountDAO.save(vet); // Salva l'Account prima
        String sql = "INSERT INTO Veterinari (IDVeterinario, CF) VALUES (?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vet.getVetId());
            statement.setString(2, vet.getCf());
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
}
