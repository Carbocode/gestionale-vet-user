package it.unibo.myvet;

import java.sql.SQLException;

import it.unibo.myvet.view.LoginView;

public final class App {

    public static void main(String[] args) throws SQLException {
        new LoginView();
    }
}
