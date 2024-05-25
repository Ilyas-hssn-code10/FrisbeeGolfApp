package application.database;

import application.model.CompetitionResult;
import application.model.Score;
import application.model.SeasonPointsTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ScoreDaoImplementation implements ScoreDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/frisbeegolfapp";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Elyas";

    private static final String INSERT_SCORE_SQL = "INSERT INTO Scores (competition_id, competitor_id, lane_number, throws_number) VALUES (?, ?, ?, ?);";
    private static final String SELECT_SCORE_BY_ID = "SELECT id, competition_id, competitor_id, lane_number, throws_number FROM Scores WHERE id = ?";
    private static final String SELECT_ALL_SCORES = "SELECT * FROM Scores";
    private static final String DELETE_SCORE_SQL = "DELETE FROM Scores WHERE id = ?";
    private static final String UPDATE_SCORE_SQL = "UPDATE Scores SET competition_id = ?, competitor_id = ?, lane_number = ?, throws_number = ? WHERE id = ?";
    private static final String SELECT_SEASONAL_POINTS = 
    	    "WITH RankedThrows AS ( " +
    	    "    SELECT s.competition_id, s.competitor_id, SUM(s.throws_number) AS totalThrows, " +
    	    "           RANK() OVER (PARTITION BY s.competition_id ORDER BY SUM(s.throws_number) ASC) AS rank " +
    	    "    FROM Scores s " +
    	    "    GROUP BY s.competition_id, s.competitor_id " +
    	    "), " +
    	    "Points AS ( " +
    	    "    SELECT rt.competitor_id, " +
    	    "           CASE WHEN rt.rank = 1 THEN 10 " +
    	    "                WHEN rt.rank = 2 THEN 9 " +
    	    "                WHEN rt.rank = 3 THEN 8 " +
    	    "                WHEN rt.rank = 4 THEN 7 " +
    	    "                WHEN rt.rank = 5 THEN 6 " +
    	    "                WHEN rt.rank = 6 THEN 5 " +
    	    "                WHEN rt.rank = 7 THEN 4 " +
    	    "                WHEN rt.rank = 8 THEN 3 " +
    	    "                WHEN rt.rank = 9 THEN 2 " +
    	    "                ELSE 1 " +
    	    "           END AS points " +
    	    "    FROM RankedThrows rt " +
    	    ") " +
    	    "SELECT c.name AS competitorName, SUM(p.points) AS seasonalTotalPoints " +
    	    "FROM Points p " +
    	    "JOIN Competitors c ON p.competitor_id = c.id " +
    	    "GROUP BY c.name " +
    	    "ORDER BY seasonalTotalPoints DESC;";

    private static final String SELECT_SCORES_BY_COMPETITION_ID = "SELECT * FROM Scores WHERE competition_id = ?";
    private static final String SELECT_SCORES_BY_COMPETITOR_ID = "SELECT * FROM Scores WHERE competitor_id = ?";
    private static final String SELECT_TOTAL_THROWS_FOR_COMPETITION = "SELECT competitor_id, SUM(throws_number) as total_throws FROM Scores WHERE competition_id = ? GROUP BY competitor_id";
    private static final String DELETE_SCORE_BY_COMPETITION_COMPETITOR_LANE_SQL = "DELETE FROM Scores WHERE competition_id = ? AND competitor_id = ? AND lane_number = ?";
    private static final String SELECT_SCORES_BY_COMPETITION_SERIE_GENDER =
            "SELECT c.name AS competitionName, p.name AS competitorName, c.field, s.lane, s.throws " +
            "FROM Scores s " +
            "JOIN Competitions c ON s.competition_id = c.id " +
            "JOIN Competitors p ON s.competitor_id = p.id " +
            "WHERE c.serie = ? AND c.gender = ?";

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
    public void addScore(Score score) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SCORE_SQL)) {
            preparedStatement.setInt(1, score.getCompetitionId());
            preparedStatement.setInt(2, score.getCompetitorId());
            preparedStatement.setInt(3, score.getLaneNumber());
            preparedStatement.setInt(4, score.getThrowsNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public List<CompetitionResult> getCompetitionResults(String serie, String gender) throws SQLException {
        List<CompetitionResult> results = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCORES_BY_COMPETITION_SERIE_GENDER)) {
            preparedStatement.setString(1, serie);
            preparedStatement.setString(2, gender);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String competitionName = rs.getString("competitionName");
                String competitorName = rs.getString("competitorName");
                String field = rs.getString("field");
                int lane = rs.getInt("lane");
                int throwsNumber = rs.getInt("throws");
                // Process and add the result to the list
                results.add(new CompetitionResult(competitionName, competitorName, field, lane, throwsNumber));
            }
        }
        return results;
    }

    @Override
    public Score getScoreById(int id) throws SQLException {
        Score score = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCORE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int competitionId = rs.getInt("competition_id");
                int competitorId = rs.getInt("competitor_id");
                int laneNumber = rs.getInt("lane_number");
                int throwsNumber = rs.getInt("throws_number");
                score = new Score(id, competitionId, competitorId, laneNumber, throwsNumber);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return score;
    }

    @Override
    public List<Score> getAllScores() throws SQLException {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SCORES)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int competitionId = rs.getInt("competition_id");
                int competitorId = rs.getInt("competitor_id");
                int laneNumber = rs.getInt("lane_number");
                int throwsNumber = rs.getInt("throws_number");
                scores.add(new Score(id, competitionId, competitorId, laneNumber, throwsNumber));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return scores;
    }
    
    @Override
    public List<CompetitionResult> calculateCompetitionResults(String series, String gender) {
        List<CompetitionResult> results = new ArrayList<>();

        String sql = "SELECT c.name AS competitionName, p.name AS competitorName, c.field, SUM(s.throws_number) AS totalThrows " +
                "FROM Competitions c " +
                "JOIN Scores s ON c.id = s.competition_id " +
                "JOIN Competitors p ON p.id = s.competitor_id " +
                "WHERE c.serie = ? AND c.gender = ? " +
                "GROUP BY c.name, p.name, c.field " +
                "ORDER BY c.name ASC, totalThrows ASC";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, series);
            preparedStatement.setString(2, gender);

            ResultSet rs = preparedStatement.executeQuery();

            String currentCompetition = "";
            int rank = 10;

            while (rs.next()) {
                String competitionName = rs.getString("competitionName");
                String competitorName = rs.getString("competitorName");
                String field = rs.getString("field");
                int totalThrows = rs.getInt("totalThrows");

                // Check if we have moved to a new competition
                if (!competitionName.equals(currentCompetition)) {
                    currentCompetition = competitionName;
                    rank = 10; // Reset rank for new competition
                }

                // Assign score based on rank
                int score = Math.max(rank, 1); // Ensure the score does not go below 1
                results.add(new CompetitionResult(competitionName, competitorName, field, totalThrows, score));

                rank--; // Decrease the rank for the next competitor
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


    @Override
    public boolean deleteScore(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SCORE_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    @Override
    public boolean deleteScoreByCompetitionCompetitorLane(int competitionId, int competitorId, int lane) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SCORE_BY_COMPETITION_COMPETITOR_LANE_SQL)) {
            statement.setInt(1, competitionId);
            statement.setInt(2, competitorId);
            statement.setInt(3, lane);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateScore(Score score) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SCORE_SQL)) {
            statement.setInt(1, score.getCompetitionId());
            statement.setInt(2, score.getCompetitorId());
            statement.setInt(3, score.getLaneNumber());
            statement.setInt(4, score.getThrowsNumber());
            statement.setInt(5, score.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public List<Score> getScoresByCompetitionId(int competitionId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCORES_BY_COMPETITION_ID)) {
            preparedStatement.setInt(1, competitionId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int competitorId = rs.getInt("competitor_id");
                int laneNumber = rs.getInt("lane_number");
                int throwsNumber = rs.getInt("throws_number");
                scores.add(new Score(id, competitionId, competitorId, laneNumber, throwsNumber));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return scores;
    }

    @Override
    public List<Score> getScoresByCompetitorId(int competitorId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SCORES_BY_COMPETITOR_ID)) {
            preparedStatement.setInt(1, competitorId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int competitionId = rs.getInt("competition_id");
                int laneNumber = rs.getInt("lane_number");
                int throwsNumber = rs.getInt("throws_number");
                scores.add(new Score(id, competitionId, competitorId, laneNumber, throwsNumber));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return scores;
    }
    
    @Override
    public boolean scoreExists(int competitionId, int competitorId, int laneNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Scores WHERE competition_id = ? AND competitor_id = ? AND lane_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, competitionId);
            preparedStatement.setInt(2, competitorId);
            preparedStatement.setInt(3, laneNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    

    
    @Override
    public List<SeasonPointsTable> getSeasonPoints() throws SQLException {
        List<SeasonPointsTable> seasonPoints = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SEASONAL_POINTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("competitorName");
                int totalPoints = rs.getInt("seasonalTotalPoints");

                seasonPoints.add(new SeasonPointsTable(name, totalPoints));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasonPoints;
    }



    @Override
    public List<Score> getTotalThrowsForCompetition(int competitionId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOTAL_THROWS_FOR_COMPETITION)) {
            preparedStatement.setInt(1, competitionId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int competitorId = rs.getInt("competitor_id");
                int totalThrows = rs.getInt("total_throws");
                scores.add(new Score(0, competitionId, competitorId, 0, totalThrows));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return scores;
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

