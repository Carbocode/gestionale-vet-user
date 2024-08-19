package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Animal;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class AnimalDAO {

    public Animal findById(Integer animalId) {
        Animal animal = null;
        String sql = "SELECT * FROM Animals WHERE IDAnimale = ?";

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

    public List<Animal> findAll() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM Animals";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                animals.add(mapToAnimal(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }

    public void save(Animal animal) {
        String sql = "INSERT INTO Animals (IDAnimale, Nome, Specie, Razza, Età, IDProprietario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, animal.getAnimalId());
            statement.setString(2, animal.getName());
            statement.setString(3, animal.getSpecies());
            statement.setString(4, animal.getBreed());
            statement.setInt(5, animal.getAge());
            statement.setInt(6, animal.getOwnerId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Animal animal) {
        String sql = "UPDATE Animals SET Nome = ?, Specie = ?, Razza = ?, Età = ?, IDProprietario = ? WHERE IDAnimale = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, animal.getName());
            statement.setString(2, animal.getSpecies());
            statement.setString(3, animal.getBreed());
            statement.setInt(4, animal.getAge());
            statement.setInt(5, animal.getOwnerId());
            statement.setInt(6, animal.getAnimalId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer animalId) {
        String sql = "DELETE FROM Animals WHERE IDAnimale = ?";

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
        String species = resultSet.getString("Specie");
        String breed = resultSet.getString("Razza");
        int age = resultSet.getInt("Età");
        int ownerId = resultSet.getInt("IDProprietario");

        return new Animal(animalId, name, species, breed, age, ownerId);
    }
}
