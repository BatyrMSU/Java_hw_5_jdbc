package dao;

import entities.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product> {

    private final @NotNull Connection connection;

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Product get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, name, code FROM products WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new Product(resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @Override
    public @NotNull List<Product> all() {
        final List<Product> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM products")) {
                while (resultSet.next()) {
                    result.add(new Product(resultSet.getInt("id"),
                                        resultSet.getString("name"),
                                        resultSet.getInt("id")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (id,name,code) VALUES(?,?,?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Product entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET name = ? WHERE id = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull Product entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
