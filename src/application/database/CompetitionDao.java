package application.database;

import application.model.Competition;
import application.model.CompetitionForTable;

import java.sql.SQLException;
import java.util.List;

public interface CompetitionDao {
    void addCompetition(Competition competition) throws SQLException;
    Competition getCompetitionById(int id) throws SQLException;
    List<Competition> getAllCompetitions() throws SQLException;
    boolean deleteCompetition(int id) throws SQLException;
    boolean deleteCompetitionByName(String name) throws SQLException;
    boolean updateCompetition(Competition competition) throws SQLException;
    boolean competitionExists(String name) throws SQLException;
	int getCompetitionIdByName(String name) throws SQLException;
	List<CompetitionForTable> getAllCompetitionsTable() throws SQLException;
}




