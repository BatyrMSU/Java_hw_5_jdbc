package queries;

import entities.Product;
import mypackage.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Query2 {

    public static void execute(Connection connection, @NotNull String productName, @NotNull int totalPriceThreshold) {

        try (PreparedStatement statement= connection.prepareStatement(sql)) {
            statement.setString(1, productName);
            statement.setInt(2, totalPriceThreshold);
            try (var resultSet = statement.executeQuery()) {
                System.out.println("name\t\t" + "price sum");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name") + ":\t\t" + resultSet.getInt("price_sum") );
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static String sql = "SELECT o.name, g.name, SUM(price*amount) AS price_sum FROM organizations AS o\n" +
                                "JOIN waybills AS w ON o.id = w.sender\n" +
                                "JOIN waybills_positions AS wp ON w.id = wp.waybill\n" +
                                "JOIN products AS g ON wp.product = g.id\n" +
                                "WHERE g.name = ?\n" +
                                "GROUP BY o.name, g.name\n" +
                                "HAVING SUM(price*amount) > ?\n" +
                                "ORDER BY price_sum DESC;";

}
