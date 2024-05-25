package application.database;

import application.model.Competitor;
import application.model.CompetitorForTable;

import java.util.List;
import java.sql.SQLException;

public interface CompetitorDao {
    void addCompetitor(Competitor competitor) throws SQLException;
    Competitor getCompetitorById(int id) throws SQLException;
    List<Competitor> getAllCompetitors() throws SQLException;
    boolean deleteCompetitor(int id) throws SQLException;
    boolean deleteCompetitorByName(String name) throws SQLException;
    boolean updateCompetitor(Competitor competitor) throws SQLException;
    boolean competitorExists(String name) throws SQLException;
	int getCompetitorIdByName(String name) throws SQLException;
	List<CompetitorForTable> getAllCompetitorsTable() throws SQLException;
}
