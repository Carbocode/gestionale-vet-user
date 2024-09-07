package it.unibo.myvet.utils;

import java.sql.SQLException;

public final class DAOUtils {

    private static final String DRIVER = "jdbc";
    private static final String DB = "mysql";
    private static final String DOMAIN = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "myvet";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String URL = DRIVER + ":" + DB + "://" + DOMAIN + ":" + PORT + "/" + DB_NAME;

    static {
        try {
            // Carica il driver JDBC (necessario solo per alcune versioni di JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Errore nel caricare il driver JDBC", e);
        }
    }

    // Factory method per creare un'istanza di DatabaseWrapper
    public static Database getConnection() {
        try {
            return Database.connect(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la connessione al database", e);
        }
    }
}
