package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Breed;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class BreedDAO {

    public Breed findById(int breedId) {
        Breed breed = null;
        String sql = "SELECT * FROM Breeds WHERE IDRazza = ?";

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
        String sql = "SELECT * FROM Breeds WHERE IDSpecie = ?";

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
        String sql = "SELECT * FROM Breeds";

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
        String sql = "INSERT INTO Breeds (IDRazza, NomeRazza, IDSpecie) VALUES (?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, breed.getBreedId());
            statement.setString(2, breed.getBreedName());
            statement.setInt(3, breed.getSpeciesId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Breed breed) {
        String sql = "UPDATE Breeds SET NomeRazza = ?, IDSpecie = ? WHERE IDRazza = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, breed.getBreedName());
            statement.setInt(2, breed.getSpeciesId());
            statement.setInt(3, breed.getBreedId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int breedId) {
        String sql = "DELETE FROM Breeds WHERE IDRazza = ?";

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
        String breedName = resultSet.getString("NomeRazza");
        int speciesId = resultSet.getInt("IDSpecie");

        return new Breed(breedId, breedName, speciesId);
    }
}
