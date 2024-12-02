package com.example.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField emailField; // Used for ID input

    @FXML
    void handleLogin() {
        String id = emailField.getText();
        if (id.isEmpty()) {
            showErrorMessage("Input Error", "ID field is empty. Please provide your ID.");
            return;
        }

        int accountType = idChecker(id);

        switch (accountType) {
            case 2:
                showAccountMessage("Student account detected.");
                navigateToEventInput();
                break;
            case 1:
                showAccountMessage("Faculty account detected.");
                navigateToApprovalInput();
                break;
            case 0:
                showAccountMessage("Administrator account detected.");
                navigateToAdmin();
                break;
            default:
                showErrorMessage("Invalid ID", "The entered ID is invalid. Please try again.");
        }
    }

    private static int idChecker(String idNo) {
        char[] array = idNo.toCharArray();
        ArrayList<Integer> nums = new ArrayList<>();
        int iteration = array.length;
        int sum = 0;

        for (char number : array) {
            nums.add(Integer.parseInt(String.valueOf(number)) * iteration);
            iteration--;
        }

        for (int n : nums) {
            sum = n + sum;
        }

        if ((sum % 11) == 0) {
            int identifier = sum / 11;
            if (identifier < 16) {
                return 2;
            } else {
                return 1;
            }
        } else if (idNo.equals("19110516")) {
            return 0;
        } else {
            return -1;
        }
    }

    private void navigateToEventInput() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventInput.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Event Input");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Navigation Error", "Unable to load the Event Input screen.");
        }
    }

    private void navigateToApprovalInput() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("faculty.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Approval");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Navigation Error", "Unable to load the Event Input screen.");
        }
    }

    private void showAccountMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Account Detected");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void navigateToAdmin() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Administrator Panel");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Navigation Error", "Unable to load the Administrator Panel.");
        }
    }
    private void showErrorMessage(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }
}
