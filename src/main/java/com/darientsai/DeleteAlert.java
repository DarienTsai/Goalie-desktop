package com.darientsai;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Darien Tsai Window to confirm deletion
 */
public class DeleteAlert{

  //Fields
  private static Stage stage;
  private double xWindowOffset;
  private double yWindowOffset;

  //Construct
  public DeleteAlert(Selection parent, Stage primary, Sounds sounds, Model model){
    
    //Layout
    Button cancel = new Button("no");
    cancel.setPrefSize(60, 20);
    cancel.setAlignment(Pos.CENTER);
    cancel.setTextAlignment(TextAlignment.CENTER);
    cancel.getStyleClass().add("deleteButton");
    Button confirm = new Button("yes");
    confirm.setPrefSize(60, 20);
    confirm.setAlignment(Pos.CENTER);
    confirm.setTextAlignment(TextAlignment.CENTER);
    confirm.getStyleClass().add("deleteButton");
    HBox buttons = new HBox(0, confirm, cancel);
    buttons.setPrefSize(120, 20);
    buttons.setAlignment(Pos.CENTER);
    Label dialogue = new Label("( ºロº) X\nDelete?");
    dialogue.setPrefSize(120, 100);
    dialogue.setAlignment(Pos.CENTER);
    dialogue.setTextAlignment(TextAlignment.CENTER);
    dialogue.setId("deleteDialogue");
    VBox container = new VBox(0, dialogue, buttons);
    container.setPrefSize(120, 120);
    container.setAlignment(Pos.CENTER);
    container.setId("deleteContainer");
    
    //Scene and stage
    Scene scene = new Scene(container);
    if(!model.getTheme()){
      scene.getStylesheets().add(getClass().getResource("/css/LimeLight.css").toExternalForm());
    }
    else {
        scene.getStylesheets().add(getClass().getResource("/css/StrikerGreen.css").toExternalForm());
    }
    stage = new Stage(StageStyle.UNDECORATED);

    //Window Dragging
    dialogue.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        xWindowOffset = event.getSceneX();
        yWindowOffset = event.getSceneY();
      }
    });

    dialogue.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        stage.setX(event.getScreenX() - xWindowOffset);
        stage.setY(event.getScreenY() - yWindowOffset);
      }
    });

    //Confirmation
    cancel.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        sounds.close.stop();
        sounds.close.play();
        stage.close();
      }
    });

    cancel.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        sounds.genHover.stop();
        sounds.genHover.play();
      }
    });

    confirm.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        sounds.close.stop();
        sounds.close.play();
        stage.close();
        parent.delete();
      }
    });

    confirm.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        sounds.genHover.stop();
        sounds.genHover.play();
      }
    });

    //Show
    stage.setScene(scene);
    stage.setWidth(120);
    stage.setHeight(120);
    stage.initOwner(primary);
    stage.initModality(Modality.APPLICATION_MODAL);
    }

    public void show(){
      stage.show();
    }
    
}