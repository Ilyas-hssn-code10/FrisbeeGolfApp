package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompetitionForTable {
    private StringProperty name;
    private StringProperty field;
    private StringProperty serie;
    private StringProperty gender;

    public CompetitionForTable(String name, String field, String serie, String gender) {
        this.name = new SimpleStringProperty(name);
        this.field = new SimpleStringProperty(field);
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

    public String getField() {
        return field.get();
    }

    public void setField(String field) {
        this.field.set(field);
    }

    public StringProperty fieldProperty() {
        return field;
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

