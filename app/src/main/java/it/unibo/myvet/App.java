package it.unibo.myvet;

import java.sql.SQLException;

import it.unibo.myvet.model.Model;
import it.unibo.myvet.utils.DAOException;
import it.unibo.myvet.utils.DAOUtils;

public final class App {

    public static void main(String[] args) throws SQLException {
        // If you want to get a feel of the application before having implemented
        // all methods, you can pass the controller a mocked model instead:

        // var model = Model.mock();
        var connection = DAOUtils.localMySQLConnection("myvet", "root", "");
        var model = Model.fromConnection(connection);
        var view = new LoginView();
    }
}
