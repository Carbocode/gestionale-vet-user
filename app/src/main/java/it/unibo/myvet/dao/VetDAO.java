package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Account;
import it.unibo.myvet.model.Specialization;
import it.unibo.myvet.model.Vet;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class VetDAO {

    private final AccountDAO accountDAO = new AccountDAO(); // Utilizzo di AccountDAO
    private final SpecializationDAO specializationDAO = new SpecializationDAO();

    public Vet findById(int vetId) {
        Vet vet = null;
        String sql = "SELECT * FROM Veterinari WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String cf = resultSet.getString("CF");
                    int idSpecialization = resultSet.getInt("IDSpecializzazione");
                    // Recupera i dettagli dell'account utilizzando l'AccountDAO
                    Account account = accountDAO.findByCf(cf);
                    Specialization specialization = specializationDAO.findById(idSpecialization);

                    vet = new Vet(
                            account.getCf(),
                            account.getPassword(),
                            account.getFirstName(),
                            account.getLastName(),
                            account.getPhoneNumber(),
                            vetId,
                            specialization);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vet;
    }

    public void save(Vet vet) {
        accountDAO.save(vet);
        String sql = "INSERT INTO veterinari (CF, IDSpecializzazione) VALUES (?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            System.out.print(vet);

            statement.setString(1, vet.getCf());
            statement.setInt(2, vet.getSpecialization().getSpecializationId());
            statement.executeUpdate();
            // Recupera l'ID generato automaticamente e impostalo sull'oggetto User
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vet.setVetId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Vet vet) {
        accountDAO.update(vet); // Aggiorna l'Account prima
        String sql = "UPDATE Veterinari SET IDSpecializzazione = ? WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vet.getSpecialization().getSpecializationId());
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

            statement.setString(1, '%' + searchText + '%');
            statement.setString(2, '%' + searchText + '%');
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idVet = resultSet.getInt("IDVeterinario");
                    String cf = resultSet.getString("CF");
                    int idSpecialization = resultSet.getInt("IDSpecializzazione");

                    // Recupera i dettagli dell'account utilizzando l'AccountDAO
                    Account account = accountDAO.findByCf(cf);
                    Specialization specialization = specializationDAO.findById(idSpecialization);

                    vets.add(new Vet(
                            account.getCf(),
                            account.getPassword(),
                            account.getFirstName(),
                            account.getLastName(),
                            account.getPhoneNumber(),
                            idVet,
                            specialization));
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
                int idSpecialization = resultSet.getInt("IDSpecializzazione");

                // Recupera i dettagli dell'account utilizzando l'AccountDAO
                Account account = accountDAO.findByCf(cf);
                Specialization specialization = specializationDAO.findById(idSpecialization);

                vets.add(new Vet(
                        account.getCf(),
                        account.getPassword(),
                        account.getFirstName(),
                        account.getLastName(),
                        account.getPhoneNumber(),
                        idVet,
                        specialization));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vets;
    }

    public Vet findByCf(String cf) {
        System.out.println(cf);
        Vet vet = null;
        String sql = "SELECT * FROM Veterinari WHERE CF = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, cf);
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println(resultSet.toString());
                if (resultSet.next()) {
                    int vetId = resultSet.getInt("IDVeterinario");
                    int idSpecialization = resultSet.getInt("IDSpecializzazione");

                    // Recupera i dettagli dell'account utilizzando l'AccountDAO
                    Account account = accountDAO.findByCf(cf);
                    Specialization specialization = specializationDAO.findById(idSpecialization);

                    vet = new Vet(
                            account.getCf(),
                            account.getPassword(),
                            account.getFirstName(),
                            account.getLastName(),
                            account.getPhoneNumber(),
                            vetId,
                            specialization);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vet;
    }

}
