package com.example.studentgradetracker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.UnaryOperator;

public class StudentGradeTracker extends Application {

    private ArrayList<Double> grades = new ArrayList<>();
    private TextArea gradesArea;
    private TextField gradeInputField;
    private Label averageLabel;
    private Label highestLabel;
    private Label lowestLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Grade Tracker");

        // Create UI elements
        gradeInputField = new TextField();
        gradeInputField.setPromptText("Enter grade");

        // Limit input to numbers only and max length of 3
        UnaryOperator<Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*") && (gradeInputField.getText().length() + text.length() <= 3)) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        gradeInputField.setTextFormatter(textFormatter);

        Button addGradeButton = new Button("Add Grade");
        addGradeButton.setOnAction(e -> addGrade());

        gradesArea = new TextArea();
        gradesArea.setEditable(false);
        gradesArea.setWrapText(true);

        averageLabel = new Label("Average: ");
        highestLabel = new Label("Highest: ");
        lowestLabel = new Label("Lowest: ");

        VBox gradesBox = new VBox(10, gradeInputField, addGradeButton, gradesArea);
        gradesBox.setPadding(new Insets(10));

        VBox resultBox = new VBox(10, averageLabel, highestLabel, lowestLabel);
        resultBox.setPadding(new Insets(10));
        resultBox.setAlignment(Pos.CENTER);

        HBox root = new HBox(20, gradesBox, resultBox);
        root.setPadding(new Insets(20));

        // Create scene and show stage
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addGrade() {
        try {
            double grade = Double.parseDouble(gradeInputField.getText());
            grades.add(grade);
            updateGradesArea();
            computeStatistics();
            gradeInputField.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter a valid number.");
        }
    }

    private void updateGradesArea() {
        StringBuilder sb = new StringBuilder();
        for (double grade : grades) {
            sb.append(grade).append("\n");
        }
        gradesArea.setText(sb.toString());
    }

    private void computeStatistics() {
        if (grades.isEmpty()) {
            averageLabel.setText("Average: ");
            highestLabel.setText("Highest: ");
            lowestLabel.setText("Lowest: ");
            return;
        }

        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        double average = sum / grades.size();
        double highest = Collections.max(grades);
        double lowest = Collections.min(grades);

        averageLabel.setText(String.format("Average: %.2f", average));
        highestLabel.setText(String.format("Highest: %.2f", highest));
        lowestLabel.setText(String.format("Lowest: %.2f", lowest));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
