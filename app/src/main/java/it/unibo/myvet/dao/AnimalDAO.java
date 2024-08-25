package it.unibo.myvet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Animal;
import it.unibo.myvet.model.Breed;
import it.unibo.myvet.model.User;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class AnimalDAO {

    private final UserDAO userDAO = new UserDAO();
    private final BreedDAO breedDAO = new BreedDAO();

    public Animal findById(Integer animalId) {
        Animal animal = null;
        String sql = "SELECT * FROM animali WHERE IDAnimale = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, animalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    animal = mapToAnimal(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animal;
    }

    public List<Animal> findByOwnerId(int ownerId) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animali WHERE IDUtente = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    animals.add(mapToAnimal(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }

    public void save(Animal animal) {
        String sql = "INSERT INTO animali (Nome, IDRazza, DataNascita, IDUtente) VALUES (?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, animal.getName());
            statement.setInt(2, animal.getBreed().getBreedId());
            statement.setDate(3, Date.valueOf(animal.getBirthDate()));
            statement.setInt(4, animal.getOwner().getUserId());
            statement.executeUpdate();

            // Retrieve the generated ID and set it on the Animal object
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    animal.setAnimalId(generatedKeys.getInt(1)); // Set the auto-generated ID
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Animal animal) {
        String sql = "UPDATE animali SET Nome = ?, IDRazza = ?, DataNascita = ?, IDUtente = ? WHERE IDAnimale = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, animal.getName());
            statement.setInt(2, animal.getBreed().getBreedId());
            statement.setDate(3, Date.valueOf(animal.getBirthDate()));
            statement.setInt(4, animal.getOwner().getUserId());
            statement.setInt(5, animal.getAnimalId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer animalId) {
        String sql = "DELETE FROM animali WHERE IDAnimale = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, animalId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Animal mapToAnimal(ResultSet resultSet) throws SQLException {
        int animalId = resultSet.getInt("IDAnimale");
        String name = resultSet.getString("Nome");
        Date birthDate = resultSet.getDate("DataNascita");
        int ownerId = resultSet.getInt("IDUtente");
        int breedId = resultSet.getInt("IDRazza");

        User owner = userDAO.findById(ownerId);
        Breed breed = breedDAO.findById(breedId);

        return new Animal(animalId, name, birthDate.toLocalDate(), owner, breed);
    }
}
