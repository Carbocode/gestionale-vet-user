package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Service;
import it.unibo.myvet.model.VetService;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class VetServiceDAO {

    private ServiceDAO serviceDAO = new ServiceDAO();

    /**
     * Trova un VetService specifico dato l'ID del veterinario e l'ID del servizio.
     * 
     * @param vetId     L'ID del veterinario.
     * @param serviceId L'ID del servizio.
     * @return Il VetService trovato o null se non esiste.
     */
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

    /**
     * Trova tutti i VetService associati a un determinato veterinario.
     * 
     * @param vetId L'ID del veterinario.
     * @return Una lista di VetService.
     */
    public List<VetService> findByVetId(int vetId) {
        List<VetService> vetServices = new ArrayList<>();
        String sql = "SELECT * FROM Effettua WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vetServices.add(mapToVetService(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vetServices;
    }

    /**
     * Salva un nuovo VetService nel database.
     * 
     * @param vetService L'oggetto VetService da salvare.
     */
    public void save(VetService vetService) {
        String sql = "INSERT INTO Effettua (IDVeterinario, IDServizio, DurataMin) VALUES (?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetService.getVetId());
            statement.setInt(2, vetService.getService().getServiceId());
            statement.setInt(3, vetService.getDurationMinutes());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiorna un VetService esistente nel database.
     * 
     * @param vetService L'oggetto VetService con le nuove informazioni.
     */
    public void update(VetService vetService) {
        String sql = "UPDATE Effettua SET DurataMin = ? WHERE IDVeterinario = ? AND IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetService.getDurationMinutes());
            statement.setInt(2, vetService.getVetId());
            statement.setInt(3, vetService.getService().getServiceId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un VetService dal database dato l'ID del veterinario e l'ID del
     * servizio.
     * 
     * @param vetId     L'ID del veterinario.
     * @param serviceId L'ID del servizio.
     */
    public void delete(int vetId, int serviceId) {
        String sql = "DELETE FROM Effettua WHERE IDVeterinario = ? AND IDServizio = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            statement.setInt(2, serviceId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mappa un ResultSet a un oggetto VetService.
     * 
     * @param resultSet Il ResultSet da mappare.
     * @return L'oggetto VetService corrispondente.
     * @throws SQLException Se si verifica un errore nel mapping.
     */
    private VetService mapToVetService(ResultSet resultSet) throws SQLException {
        int vetId = resultSet.getInt("IDVeterinario");
        int serviceId = resultSet.getInt("IDServizio");
        int durationMinutes = resultSet.getInt("DurataMin");

        // Recupera l'oggetto Service utilizzando ServiceDAO
        Service service = serviceDAO.findById(serviceId);

        return new VetService(vetId, service, durationMinutes);
    }
}