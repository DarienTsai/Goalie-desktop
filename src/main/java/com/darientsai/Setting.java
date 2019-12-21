package com.darientsai;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Darien Tsai Window for adding a new goal
 */
class Setting{

  //Fields
  private App app;
  private Stage stage;
  private CheckBox audio;
  private CheckBox theme;
  private double xWindowOffset;
  private double yWindowOffset;

  //Construct
  public Setting(App app){
    this.app = app;

    //Layout
    Button save = new Button("Save");
    save.setPrefSize(80, 30);
    Button cancel = new Button("Cancel");
    cancel.setPrefSize(80, 30);
    HBox conclusion = new HBox(0, cancel, save);
    conclusion.setPrefSize(160, 30);
    conclusion.setAlignment(Pos.CENTER);
    Label disclaim = new Label("*New settings require restart*");
    disclaim.setPrefSize(150, 45);
    disclaim.setAlignment(Pos.CENTER);
    this.audio = new CheckBox("Sound FX");
    audio.setPrefSize(130, 23);
    audio.setSelected(app.getModel().getAudio());
    this.theme = new CheckBox("Dark Mode");
    theme.setPrefSize(130, 23);
    theme.setSelected(app.getModel().getTheme());
    VBox checks = new VBox(4, audio, theme);
    checks.setPrefSize(150, 50);
    checks.setAlignment(Pos.CENTER);
    Label title = new Label("Settings");
    title.setPrefSize(150, 25);
    title.setTextAlignment(TextAlignment.CENTER);
    title.setAlignment(Pos.CENTER);
    VBox settings = new VBox(0, title, checks, disclaim, conclusion);
    settings.setAlignment(Pos.CENTER);
    Scene scene = new Scene(settings);
    scene.getStylesheets().add(getClass().getResource("/css/LimeLight.css").toExternalForm());
    this.stage = new Stage(StageStyle.UNDECORATED);
    stage.setScene(scene);
    stage.setWidth(160);
    stage.setHeight(150);
    stage.initModality(Modality.APPLICATION_MODAL);

    settings.setId("settings");
    save.getStyleClass().add("setButton");
    cancel.getStyleClass().add("setButton");
    disclaim.setId("disclaim");


    // Window Dragging
    settings.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
          xWindowOffset = event.getSceneX();
          yWindowOffset = event.getSceneY();
      }
    });

    settings.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
          stage.setX(event.getScreenX() - xWindowOffset);
          stage.setY(event.getScreenY() - yWindowOffset);
      }
    });

    //Setting buttons
    cancel.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        app.getSounds().close.pause();
        app.getSounds().close.play();
        close();
      }
    });

    cancel.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        app.getSounds().genHover.stop();
        app.getSounds().genHover.play();
      }
    });

    save.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        app.getSounds().close.pause();
        app.getSounds().close.play();
        resets();
        close();
      }
    });

    save.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        app.getSounds().genHover.stop();
        app.getSounds().genHover.play();
      }
    });
  }

  //Open settings
  public void open(){
    this.stage.show();
  }

  //Close settings
  public void close(){
    this.stage.close();
  }

  //Reset settings
  public void resets(){
    app.getModel().setAudio(audio.isSelected());
    app.getModel().setTheme(theme.isSelected());
  }
}