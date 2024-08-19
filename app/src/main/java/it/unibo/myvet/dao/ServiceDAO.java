package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Service;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class ServiceDAO {

    public Service findById(Integer serviceId) {
        Service service = null;
        String sql = "SELECT * FROM Services WHERE IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, serviceId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    service = mapToService(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return service;
    }

    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Services";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                services.add(mapToService(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }

    public void save(Service service) {
        String sql = "INSERT INTO Services (Nome) VALUES (?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, service.getName());
            statement.executeUpdate();

            // Recupera l'ID generato automaticamente e impostalo sull'oggetto Service
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    service.setServiceId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Service service) {
        String sql = "UPDATE Services SET Nome = ? WHERE IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, service.getName());
            statement.setInt(2, service.getServiceId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer serviceId) {
        String sql = "DELETE FROM Services WHERE IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, serviceId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Service mapToService(ResultSet resultSet) throws SQLException {
        int serviceId = resultSet.getInt("IDServizio");
        String name = resultSet.getString("Nome");

        return new Service(serviceId, name);
    }
}
