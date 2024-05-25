package application.model;

public class Score {
    private int id;
    private int competitionId;
    private int competitorId;
    private int laneNumber;
    private int throwsNumber;

    // Constructor
    public Score(int id, int competitionId, int competitorId, int laneNumber, int throwsNumber) {
        this.id = id;
        this.competitionId = competitionId;
        this.competitorId = competitorId;
        this.laneNumber = laneNumber;
        this.throwsNumber = throwsNumber;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(int laneNumber) {
        this.laneNumber = laneNumber;
    }

    public int getThrowsNumber() {
        return throwsNumber;
    }

    public void setThrowsNumber(int throwsNumber) {
        this.throwsNumber = throwsNumber;
    }
}


