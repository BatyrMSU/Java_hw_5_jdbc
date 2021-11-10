package dao;

import entities.WaybillPosition;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WaybillPositionDAO implements DAO<WaybillPosition> {

    private final @NotNull Connection connection;

    public WaybillPositionDAO(@NotNull Connection connection) {
        this.connection = connection;
    }


    @NotNull
    @Override
    public WaybillPosition get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, waybill, price, product, amount FROM waybills_positions WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new WaybillPosition(resultSet.getInt("id"),
                                               resultSet.getInt("waybill"),
                                               resultSet.getInt("price"),
                                               resultSet.getInt("product"),
                                               resultSet.getInt("amount"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");    }

    @NotNull
    @Override
    public List<WaybillPosition> all() {
        final List<WaybillPosition> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM waybill_positions")) {
                while (resultSet.next()) {
                    result.add(new WaybillPosition(resultSet.getInt("id"),
                                                    resultSet.getInt("waybill"),
                                                    resultSet.getInt("price"),
                                                    resultSet.getInt("product"),
                                                    resultSet.getInt("amount")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull WaybillPosition entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO waybills_positions (id,waybill,price,product,amount) VALUES(?,?,?,?,?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getWaybill());
            preparedStatement.setInt(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getProduct());
            preparedStatement.setInt(5, entity.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull WaybillPosition entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE waybills_positions SET name = ? WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setInt(2, entity.getWaybill());
            preparedStatement.setInt(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getProduct());
            preparedStatement.setInt(5, entity.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull WaybillPosition entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM waybills_positions WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
