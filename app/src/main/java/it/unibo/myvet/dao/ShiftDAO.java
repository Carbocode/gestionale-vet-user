package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.model.Shift;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class ShiftDAO {

    public Shift findById(int vetId, DayOfWeek day) {
        Shift shift = null;
        String sql = "SELECT * FROM Shifts WHERE IDVeterinario = ? AND Giorno = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            statement.setString(2, day.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    shift = mapToShift(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shift;
    }

    public List<Shift> findByVetId(int vetId) {
        List<Shift> shifts = new ArrayList<>();
        String sql = "SELECT * FROM Shifts WHERE IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    shifts.add(mapToShift(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shifts;
    }

    public List<Shift> findAll() {
        List<Shift> shifts = new ArrayList<>();
        String sql = "SELECT * FROM Shifts";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                shifts.add(mapToShift(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shifts;
    }

    public void save(Shift shift) {
        String sql = "INSERT INTO Shifts (Giorno, IDVeterinario, OraInizio, OraFine) VALUES (?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, shift.getDay().name());
            statement.setInt(2, shift.getVetId());
            statement.setTime(3, java.sql.Time.valueOf(shift.getStartTime()));
            statement.setTime(4, java.sql.Time.valueOf(shift.getEndTime()));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Shift shift) {
        String sql = "UPDATE Shifts SET OraInizio = ?, OraFine = ? WHERE Giorno = ? AND IDVeterinario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setTime(1, java.sql.Time.valueOf(shift.getStartTime()));
            statement.setTime(2, java.sql.Time.valueOf(shift.getEndTime()));
            statement.setString(3, shift.getDay().name());
            statement.setInt(4, shift.getVetId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int vetId, DayOfWeek day) {
        String sql = "DELETE FROM Shifts WHERE IDVeterinario = ? AND Giorno = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, vetId);
            statement.setString(2, day.name());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Shift mapToShift(ResultSet resultSet) throws SQLException {
        DayOfWeek day = DayOfWeek.valueOf(resultSet.getString("Giorno"));
        int vetId = resultSet.getInt("IDVeterinario");
        LocalTime startTime = resultSet.getTime("OraInizio").toLocalTime();
        LocalTime endTime = resultSet.getTime("OraFine").toLocalTime();

        return new Shift(day, vetId, startTime, endTime);
    }

}