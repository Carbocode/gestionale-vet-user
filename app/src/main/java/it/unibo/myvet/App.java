package it.unibo.myvet;

import java.sql.SQLException;

import it.unibo.myvet.utils.DAOException;
import it.unibo.myvet.utils.DAOUtils;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("myvet", "root", "");
        var view = new LoginView();
    }
}
