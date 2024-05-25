package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class SeasonPointsTable {
    private StringProperty name;
    private IntegerProperty seasonalTotalPoints;

    public SeasonPointsTable(String name, int seasonalTotalPoints) {
        this.name = new SimpleStringProperty(name);
        this.seasonalTotalPoints = new SimpleIntegerProperty(seasonalTotalPoints);
    }

    // Getters and Setters
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getSeasonalTotalPoints() {
        return seasonalTotalPoints.get();
    }

    public void setSeasonalTotalPoints(int seasonalTotalPoints) {
        this.seasonalTotalPoints.set(seasonalTotalPoints);
    }

    public IntegerProperty seasonalTotalPointsProperty() {
        return seasonalTotalPoints;
    }
}
