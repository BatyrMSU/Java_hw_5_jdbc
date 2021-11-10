package queries;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query3 {

    public static void execute(Connection connection, @NotNull Date start, @NotNull Date end) {

        try (PreparedStatement statement= connection.prepareStatement(sql)) {
            statement.setDate(1, start);
            statement.setDate(2, end);
            try (var resultSet = statement.executeQuery()) {
                System.out.println("date\t\t" + String.format("%20s\t\t", "name") + "total amount\t\t" + "total sum");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("date") + ":\t" +
                            String.format("%20s", resultSet.getString("name")) + "\t\t" +
                            resultSet.getInt("total_amount") + "\t\t\t\t" +
                            resultSet.getInt("total_sum"));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static String sql = "SELECT date, g.name, SUM(amount) AS total_amount, SUM(price*amount) AS total_sum\n" +
                                "FROM organizations AS o\n" +
                                "JOIN waybills AS w ON o.id = w.sender\n" +
                                "JOIN waybills_positions AS wp ON wp.id = w.id\n" +
                                "JOIN products AS g ON wp.product = g.id\n" +
                                "WHERE date BETWEEN ? AND ?\n" +
                                "GROUP BY date, g.name\n" +
                                "ORDER BY date, g.name;";

}
