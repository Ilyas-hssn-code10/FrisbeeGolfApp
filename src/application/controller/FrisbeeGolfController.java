package application.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.database.CompetitionDaoImplementation;
import application.database.CompetitorDaoImplementation;
import application.database.ScoreDaoImplementation;
import application.model.Competition;
import application.model.CompetitionForTable;
import application.model.CompetitionResult;
import application.model.Competitor;
import application.model.CompetitorForTable;
import application.model.Score;
import application.model.SeasonPointsTable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FrisbeeGolfController {

    @FXML
    private Tab CompetitionsTab;
    
    @FXML
    private Tab CompetitorsTab;
    
    @FXML
    private Tab SeasonPointsTab;
    
    @FXML
    private TableColumn<CompetitionForTable, String> CompetitionFieldColumn;

    @FXML
    private TableColumn<CompetitionForTable, String> CompetitionGenderColumn;

    @FXML
    private TableColumn<CompetitionForTable, String> CompetitionNameColumn;

    @FXML
    private TableColumn<CompetitionForTable, String> CompetitionSerieColumn;

    @FXML
    private TableView<CompetitionForTable> CompetitionTableView;

    @FXML
    private TableColumn<CompetitorForTable, String> CompetitorGenderColumn;

    @FXML
    private TableColumn<CompetitorForTable, String> CompetitorNameColumn;

    @FXML
    private TableColumn<SeasonPointsTable, String> CompetitorNameSeasonColumn;

    @FXML
    private TableColumn<CompetitorForTable, String> CompetitorSerieColumn;

    @FXML
    private TableView<CompetitorForTable> CompetitorTableView;

    @FXML
    private ComboBox<String> FieldComboCompetition;

    @FXML
    private ComboBox<String> FieldComboCompetitionDelete;


    @FXML
    private ComboBox<String> GenderCombo;

    @FXML
    private ComboBox<String> GenderComboCompetition;

    @FXML
    private ComboBox<String> GenderComboCompetitionDelete;

    @FXML
    private ComboBox<String> GenderComboDelete;

    @FXML
    private ComboBox<Integer> LaneComboScore;

    @FXML
    private ComboBox<Integer> LaneComboScoreDelete;

    @FXML
    private TableColumn<CompetitionResult, String> MenHobbyCompetitionNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> MenHobbyCompetitorNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> MenHobbyFieldColumn;

    @FXML
    private TableColumn<CompetitionResult, Integer> MenHobbyScoreColumn;

    @FXML
    private TableView<CompetitionResult> MenHobbySerieTableView;

    @FXML
    private TableColumn<CompetitionResult, Integer> MenHobbyTotalThrowsColumn;

    @FXML
    private TableColumn<CompetitionResult, String> MenRacingCompetitionNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> MenRacingCompetitorNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> MenRacingFieldColumn;

    @FXML
    private TableColumn<CompetitionResult, Integer> MenRacingScoreColumn;

    @FXML
    private TableView<CompetitionResult> MenRacingSerieTableView;

    @FXML
    private TableColumn<CompetitionResult, Integer> MenRacingTotalThrowsColumn;

    @FXML
    private TableView<SeasonPointsTable> SeasonPointsTableView;

    @FXML
    private TableColumn<SeasonPointsTable, Integer> SeasonalTotalScoreColumn;

    @FXML
    private ComboBox<String> SerieCombo;

    @FXML
    private ComboBox<String> SerieComboCompetition;

    @FXML
    private ComboBox<String> SerieComboCompetitionDelete;

    @FXML
    private ComboBox<String> SerieComboDelete;

    @FXML
    private TextArea TextInfoForCompetitions;

    @FXML
    private TextArea TextInfoForCompetitors;

    @FXML
    private TextArea TextInfoForScores;

    @FXML
    private TextField ThrowsForLane;

    @FXML
    private TableColumn<CompetitionResult, String> WomenHobbyCompetitionNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> WomenHobbyCompetitorNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> WomenHobbyFieldColumn;

    @FXML
    private TableColumn<CompetitionResult, Integer> WomenHobbyScoreColumn;

    @FXML
    private TableView<CompetitionResult> WomenHobbySerieTableView;

    @FXML
    private TableColumn<CompetitionResult, Integer> WomenHobbyTotalThrowsColumn;

    @FXML
    private TableColumn<CompetitionResult, String> WomenRacingCompetitionNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> WomenRacingCompetitorNameColumn;

    @FXML
    private TableColumn<CompetitionResult, String> WomenRacingFieldColumn;

    @FXML
    private TableColumn<CompetitionResult, Integer> WomenRacingScoreColumn;

    @FXML
    private TableView<CompetitionResult> WomenRacingSerieTableView;

    @FXML
    private TableColumn<CompetitionResult, Integer> WomenRacingTotalThrowsColumn;

    @FXML
    private Tab addCompetition;

    @FXML
    private Button addCompetitionButton;

    @FXML
    private TextField addCompetitionName;

    @FXML
    private TextField addCompetitionNameScore;

    @FXML
    private Tab addCompetitor;

    @FXML
    private Button addCompetitorButton;

    @FXML
    private TextField addCompetitorName;

    @FXML
    private TextField addCompetitorNameScore;

    @FXML
    private Tab addScore;

    @FXML
    private Button addScoreButton;

    @FXML
    private Tab deleteCompetition;

    @FXML
    private Button deleteCompetitionButton;

    @FXML
    private TextField deleteCompetitionName;

    @FXML
    private TextField deleteCompetitionNameScore;

    @FXML
    private Tab deleteCompetitor;

    @FXML
    private Button deleteCompetitorButton;

    @FXML
    private TextField deleteCompetitorName;

    @FXML
    private TextField deleteCompetitorNameScore;

    @FXML
    private Tab deleteScore;

    @FXML
    private Button deleteScoreButton;

    @FXML
    private Tab menHobby;

    @FXML
    private Tab menRacing;

    @FXML
    private Tab womenHobby;

    @FXML
    private Tab womenRacing;
    
    // Assuming you have DAO implementations set up
    private final CompetitionDaoImplementation competitionDao = new CompetitionDaoImplementation();
    private final CompetitorDaoImplementation competitorDao = new CompetitorDaoImplementation();
    private final ScoreDaoImplementation scoreDao = new ScoreDaoImplementation();

    @FXML
    public void initialize() {
    	 // Initialize the ComboBoxes with data if required
        // For example:

    	FieldComboCompetition.setItems(FXCollections.observableArrayList("Field 1", "Field 2", "Field 3", "Field 4", "Field 5", "Field 6", "Field 7", "Field 8"));
    	FieldComboCompetitionDelete.setItems(FXCollections.observableArrayList("Field 1", "Field 2", "Field 3", "Field 4", "Field 5", "Field 6", "Field 7", "Field 8"));
    	GenderCombo.setItems(FXCollections.observableArrayList("Male", "Female"));
        GenderComboDelete.setItems(FXCollections.observableArrayList("Male", "Female"));
        GenderComboCompetition.setItems(FXCollections.observableArrayList("Male", "Female"));
        GenderComboCompetitionDelete.setItems(FXCollections.observableArrayList("Male", "Female"));
        SerieCombo.setItems(FXCollections.observableArrayList("Racing", "Hobby"));
        SerieComboDelete.setItems(FXCollections.observableArrayList("Racing", "Hobby"));
        SerieComboCompetition.setItems(FXCollections.observableArrayList("Racing", "Hobby"));
        SerieComboCompetitionDelete.setItems(FXCollections.observableArrayList("Racing", "Hobby"));
        LaneComboScore.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5)); 
        LaneComboScoreDelete.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
      }
    
    @FXML
    void PopulateCompetitionTableView() throws SQLException {
        List<CompetitionForTable> competitions = competitionDao.getAllCompetitionsTable();
        ObservableList<CompetitionForTable> data = FXCollections.observableArrayList(competitions);

        CompetitionNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        CompetitionFieldColumn.setCellValueFactory(cellData -> cellData.getValue().fieldProperty());
        CompetitionSerieColumn.setCellValueFactory(cellData -> cellData.getValue().serieProperty());
        CompetitionGenderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        CompetitionTableView.setItems(data);
    }

    @FXML
    void PopulateCompetitorTableView() throws SQLException {
        List<CompetitorForTable> competitors = competitorDao.getAllCompetitorsTable();
        ObservableList<CompetitorForTable> data = FXCollections.observableArrayList(competitors);

        CompetitorNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        CompetitorSerieColumn.setCellValueFactory(cellData -> cellData.getValue().serieProperty());
        CompetitorGenderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        CompetitorTableView.setItems(data);
    }

    @FXML
    void PopulateMenHobbyTableView() throws SQLException {
        List<CompetitionResult> competitors = scoreDao.calculateCompetitionResults("Hobby", "Male");
        ObservableList<CompetitionResult> data = FXCollections.observableArrayList(competitors);

        MenHobbyCompetitionNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitionNameProperty());
        MenHobbyCompetitorNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitorNameProperty());
        MenHobbyTotalThrowsColumn.setCellValueFactory(cellData -> cellData.getValue().totalThrowsProperty().asObject());
        MenHobbyScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());

        MenHobbySerieTableView.setItems(data);
    }

    @FXML
    void PopulateMenRacingTableView() throws SQLException {
        List<CompetitionResult> competitors = scoreDao.calculateCompetitionResults("Racing", "Male");
        ObservableList<CompetitionResult> data = FXCollections.observableArrayList(competitors);

        MenRacingCompetitionNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitionNameProperty());
        MenRacingCompetitorNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitorNameProperty());
        MenRacingTotalThrowsColumn.setCellValueFactory(cellData -> cellData.getValue().totalThrowsProperty().asObject());
        MenRacingScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());

        MenRacingSerieTableView.setItems(data);
    }



    private List<CompetitionResult> calculateCompetitionResults(String serie, String gender) {
        List<CompetitionResult> results = new ArrayList<>();
        try {
            List<CompetitionResult> fetchedResults = scoreDao.getCompetitionResults(serie, gender);

            // Calculate total throws for each competitor
            Map<String, Integer> competitorTotalThrows = new HashMap<>();
            for (CompetitionResult result : fetchedResults) {
                String key = result.getCompetitorName() + "-" + result.getCompetitionName();
                competitorTotalThrows.put(key, competitorTotalThrows.getOrDefault(key, 0) + result.getTotalThrows());
            }

            // Create a list from the map and sort by total throws
            List<Map.Entry<String, Integer>> sortedCompetitors = new ArrayList<>(competitorTotalThrows.entrySet());
            sortedCompetitors.sort(Map.Entry.comparingByValue());

            // Assign scores based on ranking
            for (int i = 0; i < sortedCompetitors.size(); i++) {
                String[] names = sortedCompetitors.get(i).getKey().split("-");
                String competitorName = names[0];
                String competitionName = names[1];
                int totalThrows = sortedCompetitors.get(i).getValue();
                int score = 10 - i; // Assign score based on rank (1st = 10, 2nd = 9, etc.)

                for (CompetitionResult result : fetchedResults) {
                    if (result.getCompetitorName().equals(competitorName) && result.getCompetitionName().equals(competitionName)) {
                        results.add(new CompetitionResult(competitionName, competitorName, result.getField(), totalThrows, score));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }


    @FXML
    void PopulateSeasonPointsTableView() throws SQLException {
        List<SeasonPointsTable> seasonPoints = scoreDao.getSeasonPoints();
        ObservableList<SeasonPointsTable> data = FXCollections.observableArrayList(seasonPoints);

        CompetitorNameSeasonColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        SeasonalTotalScoreColumn.setCellValueFactory(cellData -> cellData.getValue().seasonalTotalPointsProperty().asObject());

        SeasonPointsTableView.setItems(data);
    }



    @FXML
    void PopulateWomenHobbyTableView() throws SQLException {
        List<CompetitionResult> competitors = scoreDao.calculateCompetitionResults("Hobby", "Female");
        ObservableList<CompetitionResult> data = FXCollections.observableArrayList(competitors);

        WomenHobbyCompetitionNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitionNameProperty());
        WomenHobbyCompetitorNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitorNameProperty());
        WomenHobbyTotalThrowsColumn.setCellValueFactory(cellData -> cellData.getValue().totalThrowsProperty().asObject());
        WomenHobbyScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());

        WomenHobbySerieTableView.setItems(data);
    }

    @FXML
    void PopulateWomenRacingTableView()  throws SQLException {
        List<CompetitionResult> competitors = scoreDao.calculateCompetitionResults("Racing", "Female");
        ObservableList<CompetitionResult> data = FXCollections.observableArrayList(competitors);

        WomenRacingCompetitionNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitionNameProperty());
        WomenRacingCompetitorNameColumn.setCellValueFactory(cellData -> cellData.getValue().competitorNameProperty());
        WomenRacingTotalThrowsColumn.setCellValueFactory(cellData -> cellData.getValue().totalThrowsProperty().asObject());
        WomenRacingScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());

        WomenRacingSerieTableView.setItems(data);
    }

    @FXML
    void addCompetition(ActionEvent event) {
        String name = addCompetitionName.getText().trim();
        String field = FieldComboCompetition.getSelectionModel().getSelectedItem().toString();
        String serie = SerieComboCompetition.getSelectionModel().getSelectedItem().toString();
        String gender = GenderComboCompetition.getSelectionModel().getSelectedItem().toString();

        if (name.isEmpty() || field == null || serie == null || gender == null) {
            TextInfoForCompetitions.appendText("Error: All fields must be filled out!\n");
            return;
        }

        try {
            Competition competition = new Competition(0, name, field, serie, gender); // Assuming id is auto-generated
            if (!competitionDao.competitionExists(name)) {
                competitionDao.addCompetition(competition);
                TextInfoForCompetitions.appendText("Competition added: " + name + "\n");
                PopulateCompetitionTableView(); // Refresh the table view
            } else {
                TextInfoForCompetitions.appendText("Error: Competition already exists.\n");
            }
        } catch (Exception e) {
            TextInfoForCompetitions.appendText("Error adding competition: " + e.getMessage() + "\n");
        }
    }

    @FXML
    void addCompetitor(ActionEvent event) {
        String name = addCompetitorName.getText().trim();
        String serie = SerieCombo.getSelectionModel().getSelectedItem().toString();
        String gender = GenderCombo.getSelectionModel().getSelectedItem().toString();

        if (name.isEmpty() || serie == null || gender == null) {
            TextInfoForCompetitors.appendText("Error: All fields must be filled out!\n");
            return;
        }

        try {
            Competitor competitor = new Competitor(0, name, serie, gender); // Assuming id is auto-generated
            if (!competitorDao.competitorExists(name)) {
                competitorDao.addCompetitor(competitor);
                TextInfoForCompetitors.appendText("Competitor added: " + name + "\n");
                PopulateCompetitorTableView(); // Refresh the table view
            } else {
                TextInfoForCompetitors.appendText("Error: Competitor already exists.\n");
            }
        } catch (Exception e) {
            TextInfoForCompetitors.appendText("Error adding competitor: " + e.getMessage() + "\n");
        }
    }

    @FXML
    void addScore(ActionEvent event) {
        String competitionName = addCompetitionNameScore.getText().trim();
        String competitorName = addCompetitorNameScore.getText().trim();
        int throwsNumber;
        
        try {
            throwsNumber = Integer.parseInt(ThrowsForLane.getText().trim());
        } catch (NumberFormatException e) {
            TextInfoForScores.appendText("Error: Number of throws must be a valid number.\n");
            return;
        }
        
        int laneNumber = Integer.parseInt(LaneComboScore.getSelectionModel().getSelectedItem().toString());

        if (competitionName.isEmpty() || competitorName.isEmpty() || laneNumber <= 0) {
            TextInfoForScores.appendText("Error: All fields must be filled out correctly!\n");
            return;
        }

        try {
            int competitionId = competitionDao.getCompetitionIdByName(competitionName);
            int competitorId = competitorDao.getCompetitorIdByName(competitorName);

            if (competitionId == -1) {
                TextInfoForScores.appendText("Error: Competition not found.\n");
                return;
            }

            if (competitorId == -1) {
                TextInfoForScores.appendText("Error: Competitor not found.\n");
                return;
            }

            if (scoreDao.scoreExists(competitionId, competitorId, laneNumber)) {
                TextInfoForScores.appendText("Error: Score for this lane already exists for this competitor in this competition.\n");
                return;
            }

            Score score = new Score(0, competitionId, competitorId, laneNumber, throwsNumber);
            scoreDao.addScore(score);
            TextInfoForScores.appendText("Score added for " + competitorName + " in " + competitionName + "\n");
        } catch (Exception e) {
            TextInfoForScores.appendText("Error adding score: " + e.getMessage() + "\n");
        }
    }


    @FXML
    void deleteCompetition(ActionEvent event) {
        String name = deleteCompetitionName.getText().trim();

        if (name.isEmpty()) {
            TextInfoForCompetitions.appendText("Error: Competition name is required!\n");
            return;
        }

        try {
            if (competitionDao.deleteCompetitionByName(name)) {
                TextInfoForCompetitions.appendText("Competition deleted: " + name + "\n");
                PopulateCompetitionTableView(); // Refresh the table view
            } else {
                TextInfoForCompetitions.appendText("Error: Competition not found.\n");
            }
        } catch (Exception e) {
            TextInfoForCompetitions.appendText("Error deleting competition: " + e.getMessage() + "\n");
        }
    }

    @FXML
    void deleteCompetitor(ActionEvent event) {
        String name = deleteCompetitorName.getText().trim();

        if (name.isEmpty()) {
            TextInfoForCompetitors.appendText("Error: Competitor name is required!\n");
            return;
        }

        try {
            if (competitorDao.deleteCompetitorByName(name)) {
                TextInfoForCompetitors.appendText("Competitor deleted: " + name + "\n");
                PopulateCompetitorTableView(); // Refresh the table view
            } else {
                TextInfoForCompetitors.appendText("Error: Competitor not found.\n");
            }
        } catch (Exception e) {
            TextInfoForCompetitors.appendText("Error deleting competitor: " + e.getMessage() + "\n");
        }
    }

    @FXML
    void deleteScore(ActionEvent event) {
        String competitionName = deleteCompetitionNameScore.getText().trim();
        String competitorName = deleteCompetitorNameScore.getText().trim();
        int laneNumber;
        
        try {
            laneNumber = Integer.parseInt(LaneComboScoreDelete.getSelectionModel().getSelectedItem().toString());
        } catch (NumberFormatException e) {
            TextInfoForScores.appendText("Error: Lane number must be a valid number.\n");
            return;
        }

        if (competitionName.isEmpty() || competitorName.isEmpty()) {
            TextInfoForScores.appendText("Error: Competition and competitor names are required!\n");
            return;
        }

        try {
            int competitionId = competitionDao.getCompetitionIdByName(competitionName);
            int competitorId = competitorDao.getCompetitorIdByName(competitorName);

            if (competitionId == -1) {
                TextInfoForScores.appendText("Error: Competition not found.\n");
                return;
            }

            if (competitorId == -1) {
                TextInfoForScores.appendText("Error: Competitor not found.\n");
                return;
            }

            if (scoreDao.deleteScoreByCompetitionCompetitorLane(competitionId, competitorId, laneNumber)) {
                TextInfoForScores.appendText("Score deleted for " + competitorName + " in " + competitionName + "\n");
            } else {
                TextInfoForScores.appendText("Error: Score not found.\n");
            }
        } catch (Exception e) {
            TextInfoForScores.appendText("Error deleting score: " + e.getMessage() + "\n");
        }
    }
    


}

