package it.unibo.myvet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Breed;
import it.unibo.myvet.model.Species;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class BreedDAO {

    private final SpeciesDAO speciesDAO = new SpeciesDAO();

    public Breed findById(int breedId) {
        Breed breed = null;
        String sql = "SELECT * FROM razze WHERE IDRazza = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, breedId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    breed = mapToBreed(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return breed;
    }

    public List<Breed> findBySpeciesId(int speciesId) {
        List<Breed> breeds = new ArrayList<>();
        String sql = "SELECT * FROM razze WHERE IDSpecie = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, speciesId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    breeds.add(mapToBreed(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return breeds;
    }

    public List<Breed> findAll() {
        List<Breed> breeds = new ArrayList<>();
        String sql = "SELECT * FROM razze";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                breeds.add(mapToBreed(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return breeds;
    }

    public void save(Breed breed) {
        String sql = "INSERT INTO razze (Nome, IDSpecie) VALUES (?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, breed.getBreedName());
            statement.setInt(2, breed.getSpecies().getSpeciesId());
            statement.executeUpdate();

            // Recupera l'ID generato automaticamente e impostalo sull'oggetto Breed
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    breed.setBreedId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Breed breed) {
        String sql = "UPDATE razze SET Nome = ?, IDSpecie = ? WHERE IDRazza = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, breed.getBreedName());
            statement.setInt(2, breed.getSpecies().getSpeciesId());
            statement.setInt(3, breed.getBreedId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int breedId) {
        String sql = "DELETE FROM razze WHERE IDRazza = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, breedId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Breed mapToBreed(ResultSet resultSet) throws SQLException {
        int breedId = resultSet.getInt("IDRazza");
        String breedName = resultSet.getString("Nome");
        int speciesId = resultSet.getInt("IDSpecie");

        Species species = speciesDAO.findById(speciesId);

        return new Breed(breedId, breedName, species);
    }

}
