package queries;

import entities.Product;
import org.gradle.internal.impldep.com.google.common.base.StandardSystemProperty;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query4 {

    public static void execute(Connection connection, @NotNull String productName, @NotNull Date start, @NotNull Date end) {

        try (PreparedStatement statement= connection.prepareStatement(sql)) {
            statement.setString(1, productName);
            statement.setDate(2, start);
            statement.setDate(3, end);
            try (var resultSet = statement.executeQuery()) {
                System.out.println("name" + String.format("%20s", "avg price"));
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("name") +
                                        String.format("%20s", resultSet.getInt("avg")));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static String sql = "SELECT g.name, AVG(wp.price)\n" +
                                "FROM waybills_positions AS wp\n" +
                                "JOIN products AS g ON wp.product = g.id\n" +
                                "JOIN waybills AS w ON wp.waybill = w.id\n" +
                                "WHERE g.name = ? AND w.date BETWEEN ? AND ?\n" +
                                "GROUP BY name;\n";

}
