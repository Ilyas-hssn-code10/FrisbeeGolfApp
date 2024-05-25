package application.database;

import application.model.CompetitionResult;
import application.model.Score;
import application.model.SeasonPointsTable;

import java.sql.SQLException;
import java.util.List;

public interface ScoreDao {
    void addScore(Score score) throws SQLException;
    Score getScoreById(int id) throws SQLException;
    List<Score> getAllScores() throws SQLException;
    boolean deleteScore(int id) throws SQLException;
    boolean updateScore(Score score) throws SQLException;
    List<Score> getScoresByCompetitionId(int competitionId) throws SQLException;
    List<Score> getScoresByCompetitorId(int competitorId) throws SQLException;
    List<Score> getTotalThrowsForCompetition(int competitionId) throws SQLException;
	boolean deleteScoreByCompetitionCompetitorLane(int competitionId, int competitorId, int lane) throws SQLException;
	List<CompetitionResult> calculateCompetitionResults(String series, String gender);
	List<SeasonPointsTable> getSeasonPoints() throws SQLException;
	boolean scoreExists(int competitionId, int competitorId, int laneNumber) throws SQLException;
}
