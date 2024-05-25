package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompetitorForTable {
    private StringProperty name;
    private StringProperty serie;
    private StringProperty gender;

    public CompetitorForTable(String name, String serie, String gender) {
        this.name = new SimpleStringProperty(name);
        this.serie = new SimpleStringProperty(serie);
        this.gender = new SimpleStringProperty(gender);
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

    public String getSerie() {
        return serie.get();
    }

    public void setSerie(String serie) {
        this.serie.set(serie);
    }

    public StringProperty serieProperty() {
        return serie;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public StringProperty genderProperty() {
        return gender;
    }
}

