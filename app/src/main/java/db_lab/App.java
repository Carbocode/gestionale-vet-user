package db_lab;

import db_lab.data.DAOException;
import db_lab.data.DAOUtils;
import db_lab.view.LoginView;

import java.sql.SQLException;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("myvet", "root", "Dajeroma");
        var view = new LoginView();
    }
}
