package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompetitionResult {
    private StringProperty competitionName;
    private StringProperty competitorName;
    private StringProperty field;
    private IntegerProperty totalThrows;
    private IntegerProperty score;

    public CompetitionResult(String competitionName, String competitorName, String field, int totalThrows, int score) {
        this.competitionName = new SimpleStringProperty(competitionName);
        this.competitorName = new SimpleStringProperty(competitorName);
        this.field = new SimpleStringProperty(field);
        this.totalThrows = new SimpleIntegerProperty(totalThrows);
        this.score = new SimpleIntegerProperty(score);
    }

    // Getters and setters
    public String getCompetitionName() {
        return competitionName.get();
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName.set(competitionName);
    }

    public StringProperty competitionNameProperty() {
        return competitionName;
    }

    public String getCompetitorName() {
        return competitorName.get();
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName.set(competitorName);
    }

    public StringProperty competitorNameProperty() {
        return competitorName;
    }

    public String getField() {
        return field.get();
    }

    public void setField(String field) {
        this.field.set(field);
    }

    public StringProperty fieldProperty() {
        return field;
    }

    public int getTotalThrows() {
        return totalThrows.get();
    }

    public void setTotalThrows(int totalThrows) {
        this.totalThrows.set(totalThrows);
    }

    public IntegerProperty totalThrowsProperty() {
        return totalThrows;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }
}


