package application.model;

public class Competition {
    private int id;
    private String name;
    private String field;
    private String serie;
    private String gender;

    // Constructor
    public Competition(int id, String name, String field, String serie, String gender) {
        this.id = id;
        this.name = name;
        this.field = field;
        this.serie = serie;
        this.gender = gender;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}



