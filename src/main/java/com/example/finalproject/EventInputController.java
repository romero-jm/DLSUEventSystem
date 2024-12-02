package com.example.finalproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class EventInputController {

    @FXML
    private TextField userReqField;

    @FXML
    private TextField userAppField;

    @FXML
    private TextField timeStartField;

    @FXML
    private TextField timeEndField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private Label confirmationLabel;

    Queue<Event> q = new Queue<>();

    @FXML
    private void handleSaveEvent() {
        try {
            // Get input values
            String userReq = userReqField.getText();
            String eventName = userAppField.getText();
            String timeStart = timeStartField.getText();
            String timeEnd = timeEndField.getText();
            LocalDate eventDate = eventDatePicker.getValue();

            // Check for missing inputs
            if (userReq == null || userReq.trim().isEmpty()) {
                throw new IllegalArgumentException("Requester ID is required.");
            }
            if (eventName == null || eventName.trim().isEmpty()) {
                throw new IllegalArgumentException("Event Name is required.");
            }
            if (timeStart == null || timeStart.trim().isEmpty()) {
                throw new IllegalArgumentException("Start Time is required.");
            }
            if (timeEnd == null || timeEnd.trim().isEmpty()) {
                throw new IllegalArgumentException("End Time is required.");
            }
            if (eventDate == null) {
                throw new IllegalArgumentException("Event Date is required.");
            }

            // Validate time format (basic validation)
            if (!timeStart.matches("\\d{2}:\\d{2}")) {
                throw new IllegalArgumentException("Invalid Start Time format. Use HH:mm.");
            }
            if (!timeEnd.matches("\\d{2}:\\d{2}")) {
                throw new IllegalArgumentException("Invalid End Time format. Use HH:mm.");
            }

            // Create an Event object with the date
            q.enqueue(new Event(userReq, eventName, 0, timeStart, timeEnd, eventDate));
            Event peekEvent = q.peekLast();

            // Display confirmation message
            confirmationLabel.setText("Event saved successfully: " + peekEvent.getUserApp() + " by " + peekEvent.getUserReq());
            confirmationLabel.setStyle("-fx-text-fill: green;");

            // Reset fields
            userReqField.clear();
            userAppField.clear();
            timeStartField.clear();
            timeEndField.clear();
            eventDatePicker.setValue(null);

        } catch (IllegalArgumentException e) {
            // Display specific validation error messages
            confirmationLabel.setText(e.getMessage());
            confirmationLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            // Catch any unexpected errors
            e.printStackTrace();
            confirmationLabel.setText("Unexpected error occurred. Please check your input.");
            confirmationLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void storage() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/pending.txt"))) {
            while (!q.isEmpty()) {
                Event event = q.dequeue();
                writer.write(String.format("%s,%s,%d,%s,%s,%s",
                        event.getUserReq(),
                        event.getUserApp(),
                        event.getStatus(),
                        event.getTimeStart(),
                        event.getTimeEnd(),
                        event.getEventDate().toString()));
                writer.newLine();
            }
            confirmationLabel.setText("Events saved to CSV file successfully.");
            confirmationLabel.setStyle("-fx-text-fill: green;");
        } catch (IOException e) {
            e.printStackTrace();
            confirmationLabel.setText("Error saving events to CSV file.");
            confirmationLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            e.printStackTrace();
            confirmationLabel.setText("An unexpected error occurred.");
            confirmationLabel.setStyle("-fx-text-fill: red;");
        }

        Platform.exit();
    }

}
