package application.database;

import application.model.Competition;
import application.model.CompetitionForTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CompetitionDaoImplementation implements CompetitionDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/frisbeegolfapp";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Elyas";

    private static final String INSERT_COMPETITION_SQL = "INSERT INTO Competitions (name, field, serie, gender) VALUES (?, ?, ?, ?);";
    private static final String SELECT_COMPETITION_BY_ID = "SELECT id, name, field, serie, gender FROM Competitions WHERE id = ?";
    private static final String SELECT_ALL_COMPETITIONS = "SELECT name, field, serie, gender FROM Competitions";
    private static final String DELETE_COMPETITION_BY_ID_SQL = "DELETE FROM Competitions WHERE id = ?";
    private static final String DELETE_COMPETITION_BY_NAME_SQL = "DELETE FROM Competitions WHERE name = ?";
    private static final String UPDATE_COMPETITION_SQL = "UPDATE Competitions SET name = ?, field = ?, serie = ?, gender = ? WHERE id = ?";
    private static final String SELECT_COMPETITION_BY_NAME = "SELECT id FROM Competitions WHERE name = ?";

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
    public List<CompetitionForTable> getAllCompetitionsTable() throws SQLException {
        List<CompetitionForTable> competitions = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMPETITIONS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String field = rs.getString("field");
                String serie = rs.getString("serie");
                String gender = rs.getString("gender");

                competitions.add(new CompetitionForTable(name, field, serie, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competitions;
    }
    
    @Override
    public void addCompetition(Competition competition) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPETITION_SQL)) {
            preparedStatement.setString(1, competition.getName());
            preparedStatement.setString(2, competition.getField());
            preparedStatement.setString(3, competition.getSerie());
            preparedStatement.setString(4, competition.getGender());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    


    @Override
    public int getCompetitionIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM competitions WHERE name = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1; // Return -1 if not found
    }

    @Override
    public Competition getCompetitionById(int id) throws SQLException {
        Competition competition = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPETITION_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String field = rs.getString("field");
                String serie = rs.getString("serie");
                String gender = rs.getString("gender");
                competition = new Competition(id, name, field, serie, gender);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return competition;
    }

    @Override
    public List<Competition> getAllCompetitions() throws SQLException {
        List<Competition> competitions = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMPETITIONS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String field = rs.getString("field");
                String serie = rs.getString("serie");
                String gender = rs.getString("gender");
                competitions.add(new Competition(id, name, field, serie, gender));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return competitions;
    }

    @Override
    public boolean deleteCompetition(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COMPETITION_BY_ID_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean deleteCompetitionByName(String name) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COMPETITION_BY_NAME_SQL)) {
            statement.setString(1, name);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateCompetition(Competition competition) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COMPETITION_SQL)) {
            statement.setString(1, competition.getName());
            statement.setString(2, competition.getField());
            statement.setString(3, competition.getSerie());
            statement.setString(4, competition.getGender());
            statement.setInt(5, competition.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }


    
    @Override
    public boolean competitionExists(String name) throws SQLException {
        String sql = "SELECT COUNT(id) FROM competitions WHERE name = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
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
