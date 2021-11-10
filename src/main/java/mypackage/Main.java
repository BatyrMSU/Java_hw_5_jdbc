package mypackage;

import dao.ProductDAO;
import dao.OrganizationDAO;
import entities.Product;
import entities.Organization;
import org.jetbrains.annotations.NotNull;
import queries.*;

import java.sql.*;

public class Main {


    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(String[] args) {

        // Creating the database
        Db.createDb();

        // Filling rows and executing queries
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {

            DbPopulate.populate(connection);

            System.out.println("\n============ Query 1 ============");
            Query1.execute(connection);

            System.out.println("\n============ Query 2 ============");
            Query2.execute(connection, "Desk", 100000);

            System.out.println("\n============ Query 3 ============");
            Query3.execute(connection, new Date(118, 5, 5), new Date(121, 1, 5));

            System.out.println("\n============ Query 4 ============");
            Query4.execute(connection, "Laptop A", new Date(118, 5, 5), new Date(121, 1, 5));

            System.out.println("\n============ Query 5 ============");
            Query5.execute(connection, new Date(118, 12, 1), new Date(121, 1, 1));


        } catch (SQLException exception) {
            exception.printStackTrace();
        }





    }


}
