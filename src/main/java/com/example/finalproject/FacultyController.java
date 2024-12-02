package com.example.finalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FacultyController {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> colRequester;

    @FXML
    private TableColumn<Event, String> colStatus;

    @FXML
    private TableColumn<Event, String> colStart;

    @FXML
    private TableColumn<Event, String> colEnd;

    @FXML
    private Button approveButton;

    @FXML
    private Button denyButton;

    @FXML
    private Label welcomeText;

    private ObservableList<Event> events;

    @FXML
    private TableColumn<Event, LocalDate> colDate;

    @FXML
    public void initialize() {
        events = FXCollections.observableArrayList();
        loadEventsFromFile(); // Load events from the CSV file

        colRequester.setCellValueFactory(new PropertyValueFactory<>("userReq"));
        colStatus.setCellValueFactory(cellData -> {
            int status = cellData.getValue().getStatus();
            String statusString = status == 0 ? "Pending" : (status == 1 ? "Approved" : "Denied");
            return new javafx.beans.property.SimpleStringProperty(statusString);
        });
        colStart.setCellValueFactory(new PropertyValueFactory<>("timeStart"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("timeEnd"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        eventTable.setItems(events);

        approveButton.setOnAction(e -> approveEvent());
        denyButton.setOnAction(e -> denyEvent());
    }

    private void loadEventsFromFile() {
        String filePath = "src/main/resources/pending.txt"; // Adjust the path as needed
        String line;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    String userReq = fields[0];
                    String eventName = fields[1];
                    int status = Integer.parseInt(fields[2]);
                    String timeStart = fields[3];
                    String timeEnd = fields[4];
                    LocalDate eventDate = null;

                    try {
                        eventDate = LocalDate.parse(fields[5], formatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Error", "Failed to parse date: " + fields[5]);
                        continue; // Skip this entry if there's an error
                    }

                    events.add(new Event(userReq, eventName, status, timeStart, timeEnd, eventDate));
                } else {
                    showAlert("Error", "Incorrect number of fields in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load events from file.");
        }
    }


    private void approveEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("No Selection", "Please select an event to approve.");
            return;
        }
        selectedEvent.setStatus(1);
        eventTable.refresh();
        showAlert("Approval", "Event has been approved.");


    }

    private void denyEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("No Selection", "Please select an event to deny.");
            return;
        }
        selectedEvent.setStatus(2);
        eventTable.refresh();
        showAlert("Denial", "Event has been denied.");
    }

    @FXML
    private void processEvents() {
        String filePath = "src/main/resources/approved.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event event : events) {
                if (event.getStatus() == 1) {
                    writer.write(String.format("%s,%s,%d,%s,%s,%s", event.getUserReq(),event.getUserApp(),event.getStatus(),event.getTimeStart(),event.getTimeEnd(),event.getEventDate().toString()));
                    writer.newLine();
                }
            }

            events.removeIf(event -> event.getStatus() == 2);
            eventTable.refresh();

            showAlert("Success", "Approved events saved to file and rejected events removed.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to process events.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
