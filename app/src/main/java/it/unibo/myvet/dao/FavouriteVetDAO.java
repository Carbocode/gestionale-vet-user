package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.FavouriteVet;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class FavouriteVetDAO {

    public FavouriteVet findById(int userId, int vetId) {
        FavouriteVet favouriteVet = null;
        String sql = "SELECT * FROM preferito WHERE IDUtente = ? AND IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, vetId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    favouriteVet = mapToFavouriteVet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favouriteVet;
    }

    public List<FavouriteVet> findByUserId(int userId) {
        List<FavouriteVet> favouriteVets = new ArrayList<>();
        String sql = "SELECT * FROM preferito WHERE IDUtente = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    favouriteVets.add(mapToFavouriteVet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favouriteVets;
    }

    public void save(FavouriteVet favouriteVet) {
        String sql = "INSERT INTO preferito (IDUtente, IDVeterinario) VALUES (?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, favouriteVet.getUserId());
            statement.setInt(2, favouriteVet.getVetId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId, int vetId) {
        String sql = "DELETE FROM preferito WHERE IDUtente = ? AND IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, vetId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private FavouriteVet mapToFavouriteVet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("IDUtente");
        int vetId = resultSet.getInt("IDVeterinario");

        return new FavouriteVet(userId, vetId);
    }
}
