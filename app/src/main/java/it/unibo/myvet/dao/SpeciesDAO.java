package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Species;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class SpeciesDAO {

    public Species findById(int speciesId) {
        Species species = null;
        String sql = "SELECT * FROM Species WHERE IDSpecie = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, speciesId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    species = mapToSpecies(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return species;
    }

    public List<Species> findAll() {
        List<Species> speciesList = new ArrayList<>();
        String sql = "SELECT * FROM Species";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                speciesList.add(mapToSpecies(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return speciesList;
    }

    public void save(Species species) {
        String sql = "INSERT INTO Species (NomeSpecie) VALUES (?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, species.getSpeciesName());
            statement.executeUpdate();

            // Recupera l'ID generato automaticamente e impostalo sull'oggetto Species
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    species.setSpeciesId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Species species) {
        String sql = "UPDATE Species SET NomeSpecie = ? WHERE IDSpecie = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, species.getSpeciesName());
            statement.setInt(2, species.getSpeciesId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int speciesId) {
        String sql = "DELETE FROM Species WHERE IDSpecie = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, speciesId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Species mapToSpecies(ResultSet resultSet) throws SQLException {
        int speciesId = resultSet.getInt("IDSpecie");
        String speciesName = resultSet.getString("NomeSpecie");

        return new Species(speciesId, speciesName);
    }
}
