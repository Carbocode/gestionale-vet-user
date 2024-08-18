package it.unibo.myvet.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.unibo.myvet.api.Dao;
import it.unibo.myvet.model.Shift;
import it.unibo.myvet.utils.DAOUtils;
import it.unibo.myvet.utils.Database;

public class ShiftDAO implements Dao<Shift, Integer> {

    @Override
    public Shift findById(Integer shiftId) {
        Shift shift = null;
        String sql = "SELECT * FROM Shifts WHERE IDOrario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, shiftId);
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

    @Override
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

    @Override
    public void save(Shift shift) {
        String sql = "INSERT INTO Shifts (IDOrario, Giorno, OraInizio, OraFine, IDVeterinario) VALUES (?, ?, ?, ?, ?)";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, shift.getShiftId());
            statement.setString(2, shift.getDay().name());
            statement.setTime(3, java.sql.Time.valueOf(shift.getStartTime()));
            statement.setTime(4, java.sql.Time.valueOf(shift.getEndTime()));
            statement.setInt(5, shift.getVetId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Shift shift) {
        String sql = "UPDATE Shifts SET Giorno = ?, OraInizio = ?, OraFine = ?, IDVeterinario = ? WHERE IDOrario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setString(1, shift.getDay().name());
            statement.setTime(2, java.sql.Time.valueOf(shift.getStartTime()));
            statement.setTime(3, java.sql.Time.valueOf(shift.getEndTime()));
            statement.setInt(4, shift.getVetId());
            statement.setInt(5, shift.getShiftId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer shiftId) {
        String sql = "DELETE FROM Shifts WHERE IDOrario = ?";

        try (Database dbWrapper = DAOUtils.getConnection();
                PreparedStatement statement = dbWrapper.prepareStatement(sql)) {

            statement.setInt(1, shiftId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Shift mapToShift(ResultSet resultSet) throws SQLException {
        int shiftId = resultSet.getInt("IDOrario");
        DayOfWeek day = DayOfWeek.valueOf(resultSet.getString("Giorno"));
        LocalTime startTime = resultSet.getTime("OraInizio").toLocalTime();
        LocalTime endTime = resultSet.getTime("OraFine").toLocalTime();
        int vetId = resultSet.getInt("IDVeterinario");

        return new Shift(shiftId, day, startTime, endTime, vetId);
    }
}
