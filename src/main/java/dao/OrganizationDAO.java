package dao;

import entities.Organization;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO implements DAO<Organization> {

    private final @NotNull Connection connection;

    public OrganizationDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @NotNull
    @Override
    public Organization get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, name, inn, account FROM organizations WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new Organization(resultSet.getInt("id"),
                                            resultSet.getString("name"),
                                            resultSet.getInt("inn"),
                                            resultSet.getInt("account"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @NotNull
    @Override
    public List<Organization> all() {
        final List<Organization> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM organizations")) {
                while (resultSet.next()) {
                    result.add(new Organization(resultSet.getInt("id"),
                                                resultSet.getString("name"),
                                                resultSet.getInt("id"),
                                                resultSet.getInt("account")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO organizations (id,name,inn,account) VALUES(?,?,?,?)")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getInn());
            preparedStatement.setInt(4, entity.getAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Organization entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE organizations SET name = ? WHERE id = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull Organization entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM organizations WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
