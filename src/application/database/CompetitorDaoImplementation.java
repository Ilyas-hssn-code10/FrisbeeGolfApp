package application.database;

import application.model.Competitor;
import application.model.CompetitorForTable;
import application.model.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CompetitorDaoImplementation implements CompetitorDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/frisbeegolfapp";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Elyas";

    private static final String INSERT_COMPETITOR_SQL = "INSERT INTO Competitors (name, serie, gender) VALUES (?, ?, ?);";
    private static final String SELECT_COMPETITOR_BY_ID = "SELECT id, name, serie, gender FROM Competitors WHERE id = ?";
    private static final String SELECT_ALL_COMPETITORS = "SELECT name, serie, gender FROM Competitors";
    private static final String DELETE_COMPETITOR_BY_ID_SQL = "DELETE FROM Competitors WHERE id = ?";
    private static final String DELETE_COMPETITOR_BY_NAME_SQL = "DELETE FROM Competitors WHERE name = ?";
    private static final String UPDATE_COMPETITOR_SQL = "UPDATE Competitors SET name = ?, serie = ?, gender = ? WHERE id = ?";
    private static final String SELECT_COMPETITOR_BY_NAME = "SELECT id FROM Competitors WHERE name = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void addCompetitor(Competitor competitor) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPETITOR_SQL)) {
            preparedStatement.setString(1, competitor.getName());
            preparedStatement.setString(2, competitor.getSerie());
            preparedStatement.setString(3, competitor.getGender());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    @Override
    public int getCompetitorIdByName(String name) throws SQLException {
        int id = -1;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPETITOR_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return id;
    }

    @Override
    public Competitor getCompetitorById(int id) throws SQLException {
        Competitor competitor = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPETITOR_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String serie = rs.getString("serie");
                String gender = rs.getString("gender");
                competitor = new Competitor(id, name, serie, gender);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return competitor;
    }

    @Override
    public List<Competitor> getAllCompetitors() throws SQLException {
        List<Competitor> competitors = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMPETITORS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String serie = rs.getString("serie");
                String gender = rs.getString("gender");
                competitors.add(new Competitor(id, name, serie, gender));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return competitors;
    }

    @Override
    public List<CompetitorForTable> getAllCompetitorsTable() throws SQLException {
        List<CompetitorForTable> competitors = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMPETITORS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String serie = rs.getString("serie");
                String gender = rs.getString("gender");

                competitors.add(new CompetitorForTable(name, serie, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competitors;
    }
    
    @Override
    public boolean deleteCompetitor(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COMPETITOR_BY_ID_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean deleteCompetitorByName(String name) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COMPETITOR_BY_NAME_SQL)) {
            statement.setString(1, name);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateCompetitor(Competitor competitor) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COMPETITOR_SQL)) {
            statement.setString(1, competitor.getName());
            statement.setString(2, competitor.getSerie());
            statement.setString(3, competitor.getGender());
            statement.setInt(4, competitor.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public boolean competitorExists(String name) throws SQLException {
        boolean exists = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPETITOR_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            exists = rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return exists;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
