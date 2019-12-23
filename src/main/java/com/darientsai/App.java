package com.darientsai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * @author Darien Tsai Goalie main application
 */
public class App extends Application {

    // Fields
    private Model model;
    private double xWindowOffset;
    private double yWindowOffset;
    private Splash splash;
    private int goalIdx;
    private int taskIdx;
    private HBox savedGoals;
    private VBox checks;
    private TextArea notes;
    private TextField goalTitle;
    private Stage primary;
    private File state;
    private ArrayList<Task> load;
    public static Sounds sounds;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primary) throws URISyntaxException {
        // Splash
        this.splash = new Splash();

        // Save stage
        this.primary = primary;

        // Load in data
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        try {
            // URL statePath = new
            // URL(getClass().getResource("/data/state.csv").toExternalForm());
            // this.state = new File(statePath.toURI());
            this.state = new File("data/state.csv");
            BufferedReader reader = new BufferedReader(new FileReader(state));
            int current = Integer.parseInt(reader.readLine().split(",")[0]);
            boolean audio = Boolean.parseBoolean(reader.readLine().split(",")[0]);
            boolean theme = Boolean.parseBoolean(reader.readLine().split(",")[0]);
            this.model = new Model(current, audio, theme, this);
            reader.close();
            // URL data = new URL(getClass().getResource("/data/data.json").toExternalForm());
            File data = new File("data/data.json");
            List<Goal> goals = mapper.reader().forType(new TypeReference<List<Goal>>() {
            }).readValue(data);
            this.model.setGoals((ArrayList<Goal>) goals);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Menu bar setup
        Button appExit = new Button("X");
        appExit.setPrefSize(15, 15);
        appExit.setAlignment(Pos.CENTER);
        appExit.setTextAlignment(TextAlignment.CENTER);
        appExit.setTooltip(new Tooltip("Exit"));
        Button minimize = new Button("-");
        minimize.setPrefSize(15, 15);
        minimize.setAlignment(Pos.CENTER);
        minimize.setTextAlignment(TextAlignment.CENTER);
        minimize.setTooltip(new Tooltip("Minimize"));
        /*
        Button importGoal = new Button("v");
        importGoal.setPrefSize(15, 15);
        importGoal.setAlignment(Pos.CENTER);
        importGoal.setTextAlignment(TextAlignment.CENTER);
        importGoal.setTooltip(new Tooltip("Import"));
        */
        Button goalieHelp = new Button("?");
        goalieHelp.setPrefSize(15, 15);
        goalieHelp.setAlignment(Pos.CENTER);
        goalieHelp.setTextAlignment(TextAlignment.CENTER);
        goalieHelp.setTooltip(new Tooltip("Help"));
        Button setting = new Button("*");
        setting.setPrefSize(15, 15);
        setting.setAlignment(Pos.CENTER);
        setting.setTextAlignment(TextAlignment.CENTER);
        setting.setTooltip(new Tooltip("Help"));
        Label menuTitle = new Label("Goalie");
        menuTitle.setPrefSize(60, 15);
        menuTitle.setAlignment(Pos.CENTER);
        menuTitle.setTextAlignment(TextAlignment.CENTER);
        Region menuSpacer = new Region();
        menuSpacer.setPrefSize(775, 15);
        HBox menuBar = new HBox(0, menuTitle, menuSpacer, setting, goalieHelp, minimize, appExit);
        menuBar.setPrefSize(900, 15);
        menuBar.setAlignment(Pos.CENTER);

        appExit.getStyleClass().add("menuBarButton");
        minimize.getStyleClass().add("menuBarButton");
        //importGoal.getStyleClass().add("menuBarButton");
        goalieHelp.getStyleClass().add("menuBarButton");
        setting.getStyleClass().add("menuBarButton");
        menuTitle.setId("menuTitle");
        menuBar.setId("menuBar");

        // Selection bar setup
        this.savedGoals = new HBox(10);
        this.savedGoals.setPrefHeight(158);
        this.savedGoals.setAlignment(Pos.CENTER_LEFT);
        this.savedGoals.setFillHeight(false);
        savedGoals.setPadding(new Insets(0, 0, 0, 10));
        ScrollPane savedGoalsContainer = new ScrollPane(this.savedGoals);
        savedGoalsContainer.setPrefSize(830, 175);
        savedGoalsContainer.setPrefViewportWidth(830);
        savedGoalsContainer.setPrefViewportHeight(160);
        savedGoalsContainer.setVbarPolicy(ScrollBarPolicy.NEVER);
        savedGoalsContainer.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        Button newGoal = new Button("+");
        newGoal.setPrefSize(50, 50);
        newGoal.setAlignment(Pos.CENTER);
        newGoal.setTextAlignment(TextAlignment.CENTER);
        newGoal.setTooltip(new Tooltip("New Goal"));
        HBox selectionBar = new HBox(10, newGoal, savedGoalsContainer);
        selectionBar.setPrefSize(900, 175);
        selectionBar.setAlignment(Pos.CENTER_RIGHT);

        savedGoals.setId("savedGoals");
        savedGoalsContainer.setId("savedGoalsContainer");
        newGoal.setId("newGoal");
        selectionBar.setId("selectionBar");

        // Work bar setup
        this.notes = new TextArea();
        this.notes.setPrefSize(550, 360);
        this.notes.setWrapText(true);
        this.checks = new VBox(0);
        this.checks.setPrefSize(350, 330);
        this.checks.setAlignment(Pos.TOP_CENTER);
        ScrollPane checkList = new ScrollPane(checks);
        checkList.setPrefSize(350, 331);
        checkList.setPrefViewportWidth(350);
        checkList.setPrefViewportHeight(310);
        checkList.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        checkList.setHbarPolicy(ScrollBarPolicy.NEVER);
        Button newTask = new Button("+");
        newTask.setPrefSize(50, 50);
        newTask.setTextAlignment(TextAlignment.CENTER);
        newTask.setTooltip(new Tooltip("New Task"));
        this.goalTitle = new TextField();
        this.goalTitle.setPrefSize(300, 50);
        this.goalTitle.setAlignment(Pos.BOTTOM_LEFT);
        HBox goalHeader = new HBox(5, newTask, goalTitle);
        goalHeader.setPrefSize(350, 50);
        goalHeader.setAlignment(Pos.CENTER_RIGHT);
        goalHeader.setFillHeight(false);
        VBox checksContainer = new VBox(0, goalHeader, checkList);
        checksContainer.setPrefSize(350, 310);
        checksContainer.setAlignment(Pos.CENTER);
        checksContainer.setFillWidth(false);
        HBox progressBar = new HBox(0, checksContainer, notes);
        progressBar.setPrefSize(900, 360);
        progressBar.setAlignment(Pos.CENTER);

        this.notes.setId("notes");
        this.checks.setId("checks");
        checkList.setId("checkList");
        newTask.setId("newTask");
        this.goalTitle.setId("goalTitle");
        checksContainer.setId("checksContainer");
        progressBar.setId("progressBar");

        // Scene Setup
        VBox parent = new VBox(menuBar, selectionBar, progressBar);
        Scene root = new Scene(parent);
        if(!this.model.getTheme()){
            root.getStylesheets().add(getClass().getResource("/css/LimeLight.css").toExternalForm());
        }
        else {
            root.getStylesheets().add(getClass().getResource("/css/StrikerGreen.css").toExternalForm());
        }
        parent.setId("parent");

        // Sounds
        sounds = new Sounds();
        if(!this.model.getAudio()){
            sounds.mute();
        }

        // Other windows
        Addition add = new Addition(primary, this.getModel(), sounds);
        Setting set = new Setting(this);

        // Window Dragging
        menuBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xWindowOffset = event.getSceneX();
                yWindowOffset = event.getSceneY();
            }
        });

        menuBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primary.setX(event.getScreenX() - xWindowOffset);
                primary.setY(event.getScreenY() - yWindowOffset);
            }
        });

        // Close Application
        appExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.write(0);
                Platform.exit();
            }
        });

        // Minimize Application
        minimize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primary.setIconified(true);
            }
        });

        /*
        // TODO Import Goals
        importGoal.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                sounds.menuBar.stop();
                sounds.menuBar.play();
                System.out.println("Not implemented yet");
            }
        });
        */

        // Help Page
        goalieHelp.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                sounds.menuBar.stop();
                sounds.menuBar.play();
                try {
                    java.awt.Desktop.getDesktop().browse(new URI("https://www.stackoverflow.com"));
                } catch (IOException e){
                    Platform.exit(); //Should be replaced with a cannot connect alert
                } catch (URISyntaxException e){
                    Platform.exit();
                }
            }
        });

        //Side Scrolling
        savedGoalsContainer.setOnScroll(new EventHandler<ScrollEvent>(){
            @Override
            public void handle(ScrollEvent event){
                savedGoalsContainer.setHvalue(savedGoalsContainer.getHvalue() - (0.1 * Math.signum(event.getDeltaY())));
            }
        });

        // Add Goal
        newGoal.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                sounds.windowPop.stop();
                sounds.windowPop.play();
                add.open();
            }
        });

        newGoal.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                sounds.genHover.stop();
                sounds.genHover.play();
            }
        });

        // Open Settings
        setting.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                sounds.windowPop.stop();
                sounds.windowPop.play();
                set.open();
            }
        });

        // Add Task
        newTask.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                model.addTask();
            }
        });

        newTask.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                sounds.genHover.stop();
                sounds.genHover.play();
            }
        });

        // Edit Goal Title
        this.goalTitle.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                model.write(goalTitle.getCaretPosition());
            }
        });

        // Render
        primary.setWidth(900);
        primary.setHeight(550);
        primary.setResizable(false);
        primary.initStyle(StageStyle.UNDECORATED);
        primary.setScene(root);
        primary.show();
        sounds.windowPop.play();
        //Load goals
        int preCurrent = this.model.getCurrent();
        reloadGoals(this.model.getGoals());
        this.model.setCurrent(preCurrent);
        this.load(preCurrent);
        this.splash.close();

    }

    @Override
    public void stop() throws URISyntaxException {

        // Save data
        try {
            FileWriter saveState = new FileWriter(this.state, false);
            saveState.write(this.model.getState());
            saveState.close();
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(this.model.getGoals());
            // URL dataPath = new
            // URL(getClass().getResource("/data/data.json").toExternalForm());
            // File data = new File(dataPath.toURI());
            File data = new File("data/data.json");
            FileWriter saveData = new FileWriter(data, false);
            saveData.write(jsonData);
            saveData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    
    // Access model
    public Model getModel(){
        return this.model;
    }

    // Reload Goals
    public void reloadGoals(ArrayList<Goal> goals){
        savedGoals.setPrefWidth(10);
        ArrayList<Node> reload = new ArrayList<>();
        for(this.goalIdx = 0; this.goalIdx < goals.size(); this.goalIdx++){
            savedGoals.setPrefWidth(savedGoals.getPrefWidth() + 140);
            reload.add(0, new Selection(this.goalIdx, this, goals.get(this.goalIdx), primary));
        }
        this.savedGoals.getChildren().setAll(reload);
        this.model.setCurrent(this.goalIdx - 1);
        this.load(model.getCurrent());
    }

    // Load a goal
    public void load(int idx){
        model.setCurrent(idx);
        if(idx == -1){
            this.checks.getChildren().removeAll(this.checks.getChildren());
            this.goalTitle.setText("");
            this.notes.setText("");
            return;
        }
        Goal loadThis = this.model.getGoals().get(idx);
        this.load = new ArrayList<>();
        for(this.taskIdx = 0; this.taskIdx < loadThis.getItems().size(); this.taskIdx++){
            load.add(new Task(this.taskIdx, this, loadThis.getCompletion().get(this.taskIdx), loadThis.getItems().get(this.taskIdx)));
        }
        this.notes.setText(loadThis.getNotes());
        this.goalTitle.setText(loadThis.getTitle());
        this.checks.getChildren().setAll(load);
    }

    // Get goalTitle
    public TextField getGoalTitle(){
        return this.goalTitle;
    }

    // Get checks container
    public VBox getChecks(){
        return this.checks;
    }

    // Get Notes container
    public TextArea getNotes(){
        return this.notes;
    }

    // Get Tasks
    public ArrayList<Task> getLoad(){
        return this.load;
    }

    // Get sounds
    public Sounds getSounds(){
        return sounds;
    }

    // Self launch
    public static void main(String[] args){
        App.launch(args);
    }
}