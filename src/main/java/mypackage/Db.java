package mypackage;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Locale;

public class Db {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void createDb() {

        // creating database
        try (Connection connection = DriverManager.getConnection(CREDS.url_server(), CREDS.login(), CREDS.password())) {
            try (Statement statement = connection.createStatement()) {
                //statement.executeUpdate("DROP DATABASE " + Main.DB_NAME);
                statement.executeUpdate("CREATE DATABASE " + CREDS.dbName());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


        // adding tables
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {

            try (Statement statement = connection.createStatement()) {
                String sql = "CREATE TABLE organizations " +
                        "(id SERIAL, " +
                        " name VARCHAR, " +
                        " inn INT NOT NULL, " +
                        " account INT NOT NULL, " +
                        " PRIMARY KEY ( id ))";
                statement.executeUpdate(sql);
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
            }

            try (Statement statement = connection.createStatement()) {
                String sql = "CREATE TABLE products " +
                        "(id SERIAL, " +
                        " name VARCHAR, " +
                        " code INT NOT NULL, " +
                        " PRIMARY KEY ( id ))";
                statement.executeUpdate(sql);
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
            }

            try (Statement statement = connection.createStatement()) {
                String sql = "CREATE TABLE waybills " +
                        "(id SERIAL, " +
                        " date DATE, " +
                        " sender INT NOT NULL REFERENCES organizations (id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                        " PRIMARY KEY ( id ))";
                statement.executeUpdate(sql);
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
            }

            try (Statement statement = connection.createStatement()) {
                String sql = "CREATE TABLE waybills_positions " +
                        "(id SERIAL, " +
                        " waybill INT NOT NULL REFERENCES waybills (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                        " price INT, " +
                        " product INT NOT NULL REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                        " amount INT," +
                        " PRIMARY KEY ( id ))";
                statement.executeUpdate(sql);
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
            }


        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
