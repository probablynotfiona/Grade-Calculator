import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.ArrayList;




public class GradeCalculator extends Application {
    private static int yLength = 50;
    private static int classCount = 0;
    private static int cLength = 50;
    private double grade;
    private double credits;
    private double qualityPoints = 0;
    private double totalcredit = 0;
    private static int classCountC = 0;
    private int semesterCount = 0;
    private double creditCount = 0;
    private double gpaCount = 0;
    boolean allSubmitted = false;
    public void start(Stage primaryStage) {
        Label label = new Label("Grade Calculator");
        label.setFont(new Font("Times New Roman", 38));
        label.setTextFill(Color.WHITE);
        label.setLayoutX(200);
        Pane startPane = new Pane(label);
        startPane.requestFocus();
        Scene startScreen = new Scene(startPane, 800, 600);
        primaryStage.setScene(startScreen);
        primaryStage.show();
        startPane.setStyle("-fx-background-color: lightblue;");
        addBox(startPane);
        addBox(startPane);
        addBox(startPane);
        addBox(startPane);
        addBox(startPane);
        Button addClass = new Button("Add class?");
        addClass.setLayoutX(700);
        addClass.setLayoutY(80);
        addClass.setOnAction(e ->  {
            if (classCount >= 8 ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Max classes reached");
                alert.showAndWait();
                }
            else {
            addBox(startPane);
            }
        });
        startPane.getChildren().add(addClass);
        Button calculateGrade = new Button("Calculate Grade");
        calculateGrade.setLayoutY(500);
        calculateGrade.setLayoutX(400);
        startPane.getChildren().add(calculateGrade);
        Button SemesterCalculator = new Button("Calculate by semester");
        SemesterCalculator.setLayoutX(500);
        SemesterCalculator.setLayoutY(20);
        startPane.getChildren().add(SemesterCalculator);
        SemesterCalculator.setOnAction(z -> {
            semesterScreen(primaryStage);
        });
        calculateGrade.setOnAction(e-> {
            if (!allSubmitted) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Be sure to submit all classes you want calculated before pressing submit!");
                alert.showAndWait();
            } else {
            calculateScreen(primaryStage);
            }
        });

    }
    public void addBox(Pane enterPane) {
        classCount++;
        Label label1 = new Label(String.format("Class %d credits", classCount));
        TextField field1 = new TextField();
        Label label2 = new Label(String.format("Class %d grade", classCount));
        TextField field2 = new TextField();
        Label label3 = new Label(String.format("Class %d name", classCount));
        TextField field3 = new TextField();
        label3.setLayoutX(50);
        field3.setLayoutX(50);
        label1.setLayoutX(250);
        label2.setLayoutX(450);
        field1.setLayoutX(250);
        field2.setLayoutX(450);
        label1.setLayoutY(yLength);
        field1.setLayoutY(yLength + 20);
        label2.setLayoutY(yLength);
        field2.setLayoutY(yLength + 20);
        field3.setLayoutY(yLength + 20);
        label3.setLayoutY(yLength);
        Button submit1 = new Button("Submit");
        submit1.setLayoutX(600);
        submit1.setLayoutY(yLength+20);
        Button editButton = new Button("edit");
        editButton.setLayoutX(600);
        editButton.setLayoutY(yLength+20);
        yLength += 50;
        enterPane.getChildren().addAll(label1, label2, field1, field2, submit1, field3, label3);
        grade = 0;
        credits = 0;
        submit1.setOnAction(e -> {
        try {
            allSubmitted = true;
            grade = Integer.parseInt(field2.getText().trim());
            credits = Integer.parseInt(field1.getText().trim());
            qualityPoints += grade*credits;
            totalcredit += credits;
            enterPane.getChildren().remove(submit1);
            enterPane.getChildren().add(editButton);
            field1.setEditable(false);
            field2.setEditable(false);
            field1.setFocusTraversable(false);
            field2.setFocusTraversable(false);
            enterPane.requestFocus();
        } catch (NumberFormatException f) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a number");
            alert.showAndWait(); 
            field1.deselect(); 
            field2.deselect();  
            }
    });
    editButton.setOnAction(h-> {
        allSubmitted = false;
        field1.setEditable(true);
        field2.setEditable(true);
        qualityPoints -= grade*credits;
        totalcredit -= credits;
        enterPane.getChildren().remove(editButton);
        enterPane.getChildren().add(submit1);
        enterPane.requestFocus();
    });
}
    public void calculateScreen(Stage primaryStage) {
        double finalgrade = qualityPoints/totalcredit;
        Label calculateLabel = new Label("Your Final Grade is");
        Label gradeLabel = new Label(String.format("%.4f", finalgrade));
        Label creditLabel = new Label(String.format("And You're taking %.1f credits", totalcredit));
        gradeLabel.setFont(new Font("Times New Roman", 38));
        calculateLabel.setFont(new Font("Times New Roman", 38));
        creditLabel.setFont(new Font("Times New Roman", 38));
        gradeLabel.setLayoutY(80);
        creditLabel.setLayoutY(160);
        calculateLabel.setTextFill(Color.WHITE);
        gradeLabel.setTextFill(Color.WHITE);
        Pane calculatePane = new Pane(calculateLabel, gradeLabel, creditLabel);
        calculatePane.setStyle("-fx-background-color: lightblue;");
        Scene calculateScene = new Scene(calculatePane, 800, 600);
        primaryStage.setScene(calculateScene);
        Button returnbutton = new Button("Return");
        returnbutton.setLayoutY(400);
        calculatePane.getChildren().add(returnbutton);
        returnbutton.setOnAction(e-> {
            classCount = 0;
            yLength = 50;
            grade = 0;
            credits = 0;
            qualityPoints = 0;
            totalcredit = 0;
            start(primaryStage);
        });
    }
    public void semesterScreen(Stage primaryStage) {
        Label label = new Label("Calculate by Semester");
        label.setFont(new Font("Times New Roman", 38));
        label.setTextFill(Color.WHITE);
        label.setLayoutX(200);
        Button addClass = new Button("Add Semester?");
        addClass.setLayoutX(700);
        addClass.setLayoutY(80);
        Button returnButton = new Button("Return to main?");
        returnButton.setLayoutX(700);
        returnButton.setLayoutY(180);
        Button calculateButton = new Button("Calculate gpa");
        calculateButton.setLayoutX(400);
        calculateButton.setLayoutY(550);
        Pane semesterPane = new Pane(label, addClass, returnButton, calculateButton);
        semesterPane.setStyle("-fx-background-color: lightblue;");
        Scene semesterScene = new Scene(semesterPane, 800, 600);
        primaryStage.setScene(semesterScene);
        addSemester(semesterPane);
        addSemester(semesterPane);
        addSemester(semesterPane);
        addSemester(semesterPane);
        addSemester(semesterPane);
        addClass.setOnAction(e -> {
            if (semesterCount >= 8 ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Max semesters reached");
                alert.showAndWait();
                }
            else {
            addSemester(semesterPane);
            }
        });
        returnButton.setOnAction(z ->  {
            yLength = 50;
            classCount = 0;
            cLength = 50;
            grade = 0;
            credits = 0;
            qualityPoints = 0;
            totalcredit = 0;
            classCountC = 0;
            semesterCount = 0;
            creditCount = 0;
            gpaCount = 0;
            start(primaryStage);
        });
        calculateButton.setOnAction(h ->  {
            bySemesterResults(primaryStage);
        });
    }
    public void addSemester(Pane enterPane) {
        classCountC++;
        Label label1 = new Label(String.format("Semester %d credits", classCountC));
        TextField field1 = new TextField();
        Label label2 = new Label(String.format("Semester %d grade", classCountC));
        TextField field2 = new TextField();
        label1.setLayoutX(250);
        label2.setLayoutX(450);
        field1.setLayoutX(250);
        field2.setLayoutX(450);
        label1.setLayoutY(cLength);
        field1.setLayoutY(cLength + 20);
        label2.setLayoutY(cLength);
        field2.setLayoutY(cLength + 20);
        Button submit1 = new Button("Submit");
        submit1.setLayoutX(600);
        submit1.setLayoutY(cLength+20);
        enterPane.getChildren().addAll(label1, label2, field1, field2, submit1);
        submit1.setOnAction(e -> {
            try {
                int creditsb = Integer.parseInt(field1.getText().trim());
                double gpab = Double.parseDouble(field2.getText().trim());
                gpaCount += gpab * creditsb;
                creditCount += creditsb;
                enterPane.getChildren().remove(submit1);
                field1.setEditable(false);
                field2.setEditable(false);
                field1.setFocusTraversable(false);
                field2.setFocusTraversable(false);
                enterPane.requestFocus();
            } catch (NumberFormatException f) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a number");
                alert.showAndWait(); 
                field1.deselect(); 
                field2.deselect();  
                }
            });
        cLength+= 50;
    }
    public void bySemesterResults(Stage primaryStage) {
        double finalgrade = gpaCount/creditCount;
        Label calculateLabel = new Label("Your Final Grade is");
        Label gradeLabel = new Label(String.format("%.4f", finalgrade));
        gradeLabel.setFont(new Font("Times New Roman", 38));
        calculateLabel.setFont(new Font("Times New Roman", 38));
        gradeLabel.setLayoutY(80);
        calculateLabel.setTextFill(Color.WHITE);
        gradeLabel.setTextFill(Color.WHITE);
        Pane calculatePane = new Pane(calculateLabel, gradeLabel);
        calculatePane.setStyle("-fx-background-color: lightblue;");
        Scene calculateScene = new Scene(calculatePane, 800, 600);
        primaryStage.setScene(calculateScene);
        Button returnbutton = new Button("Return");
        returnbutton.setLayoutY(400);
        calculatePane.getChildren().add(returnbutton);
        returnbutton.setOnAction(e-> {
            yLength = 50;
            classCount = 0;
            cLength = 50;
            grade = 0;
            credits = 0;
            qualityPoints = 0;
            totalcredit = 0;
            classCountC = 0;
            semesterCount = 0;
            creditCount = 0;
            gpaCount = 0;
            semesterScreen(primaryStage);
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
