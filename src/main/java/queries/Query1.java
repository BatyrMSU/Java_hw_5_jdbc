package queries;

import entities.Product;
import mypackage.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Query1 {


    public static void execute(Connection connection) {

        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery(sql)) {
                System.out.println("name\t\t" + "total_amount");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name") + ":\t\t" + resultSet.getInt("total_amount") );
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    private static String sql = "SELECT name, SUM(amount) as total_amount FROM organizations\n" +
            "JOIN waybills ON organizations.id = waybills.id\n" +
            "JOIN waybills_positions ON waybills.id = waybills_positions.waybill\n" +
            "GROUP BY name\n" +
            "ORDER BY total_amount DESC\n" +
            "LIMIT 10;";

}
