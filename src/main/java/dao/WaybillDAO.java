package dao;

import entities.Waybill;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WaybillDAO implements DAO<Waybill> {

    private final @NotNull Connection connection;

    public WaybillDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @NotNull
    @Override
    public Waybill get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, date, sender FROM waybills WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new Waybill(resultSet.getInt("id"),
                                       resultSet.getDate("date"),
                                       resultSet.getInt("sender"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @NotNull
    @Override
    public List<Waybill> all() {
        final List<Waybill> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM waybills")) {
                while (resultSet.next()) {
                    result.add(new Waybill(resultSet.getInt("id"),
                                           resultSet.getDate("date"),
                                           resultSet.getInt("sender")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull Waybill entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO waybills (id,date,sender) VALUES(?,?,?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setDate(2, entity.getTime());
            preparedStatement.setInt(3, entity.getSender());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Waybill entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE waybills SET date = ? WHERE id = ?")) {
            preparedStatement.setDate(1, entity.getTime());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull Waybill entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM waybills WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
