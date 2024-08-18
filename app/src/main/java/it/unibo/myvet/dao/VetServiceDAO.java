package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.VetService;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class VetServiceDAO {

    public VetService findById(int vetId, int serviceId) {
        VetService vetService = null;
        String sql = "SELECT * FROM VetServices WHERE IDVeterinario = ? AND IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            statement.setInt(2, serviceId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    vetService = mapToVetService(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vetService;
    }

    public List<VetService> findAll() {
        List<VetService> vetServices = new ArrayList<>();
        String sql = "SELECT * FROM VetServices";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                vetServices.add(mapToVetService(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vetServices;
    }

    public void save(VetService vetService) {
        String sql = "INSERT INTO VetServices (IDVeterinario, IDServizio, DurataMinuti) VALUES (?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetService.getVetId());
            statement.setInt(2, vetService.getServiceId());
            statement.setInt(3, vetService.getDurationMinutes());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(VetService vetService) {
        String sql = "UPDATE VetServices SET DurataMinuti = ? WHERE IDVeterinario = ? AND IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetService.getDurationMinutes());
            statement.setInt(2, vetService.getVetId());
            statement.setInt(3, vetService.getServiceId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int vetId, int serviceId) {
        String sql = "DELETE FROM VetServices WHERE IDVeterinario = ? AND IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            statement.setInt(2, serviceId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VetService mapToVetService(ResultSet resultSet) throws SQLException {
        int vetId = resultSet.getInt("IDVeterinario");
        int serviceId = resultSet.getInt("IDServizio");
        int durationMinutes = resultSet.getInt("DurataMinuti");

        return new VetService(vetId, serviceId, durationMinutes);
    }
}
