package queries;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query5 {

    public static void execute(Connection connection, @NotNull Date start, @NotNull Date end) {

        try (PreparedStatement statement= connection.prepareStatement(sql)) {
            statement.setDate(1, start);
            statement.setDate(2, end);
            try (var resultSet = statement.executeQuery()) {
                System.out.println("company\t\t" + String.format("%20s", "product"));
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("company") + ":\t" +
                            String.format("%20s", resultSet.getString("product")));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    private static String sql = "SELECT o.name AS company, g.name AS product FROM organizations AS o\n" +
            "LEFT JOIN waybills AS w ON o.id = w.sender\n" +
            "LEFT JOIN waybills_positions AS wp ON w.id = wp.waybill\n" +
            "LEFT JOIN products AS g ON wp.product = g.id\n" +
            "WHERE (w.date BETWEEN ? AND ?) OR (w.date IS NULL)\n" +
            "ORDER BY o.name;";

}
