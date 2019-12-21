package com.darientsai;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Darien Tsai
 * Window for adding a new goal
 */
class Addition{
  
  private Stage owner;
  private Stage adder;
  private Sounds sounds;
  private double xWindowOffset;
  private double yWindowOffset;

  //Constructor
  public Addition(Stage owner, Model model, Sounds sounds){
    this.owner = owner;
    this.sounds = sounds;

    //UI and Layout
    Button cancel = new Button("cancel");
    cancel.setPrefWidth(600);
    cancel.setPrefHeight(25);
    Button academicCat = new Button(Category.ACADEMIC.face() + "\nAcademic");
    academicCat.setPrefSize(100, 100);
    academicCat.setAlignment(Pos.CENTER);
    academicCat.setTextAlignment(TextAlignment.CENTER);
    Button financialCat = new Button(Category.FINANCIAL.face() + "\nFinancial");
    financialCat.setPrefSize(100, 100);
    financialCat.setAlignment(Pos.CENTER);
    financialCat.setTextAlignment(TextAlignment.CENTER);
    Button healthCat = new Button(Category.WELLNESS.face() + "\nWellness");
    healthCat.setPrefSize(100, 100);
    healthCat.setAlignment(Pos.CENTER);
    healthCat.setTextAlignment(TextAlignment.CENTER);
    Button socialCat = new Button(Category.SOCIAL.face() + "\nSocial");
    socialCat.setPrefSize(100, 100);
    socialCat.setAlignment(Pos.CENTER);
    socialCat.setTextAlignment(TextAlignment.CENTER);
    Button proCat = new Button(Category.PROFESSIONAL.face() + "\nProfessional");
    proCat.setPrefSize(100, 100);
    proCat.setAlignment(Pos.CENTER);
    proCat.setTextAlignment(TextAlignment.CENTER);
    Button miscCat = new Button(Category.MISCELLANEOUS.face() + "\nMiscellaneous");
    miscCat.setPrefSize(100, 100);
    miscCat.setAlignment(Pos.CENTER);
    miscCat.setTextAlignment(TextAlignment.CENTER);
    TilePane categories = new TilePane(Orientation.HORIZONTAL, 0, 0, academicCat, financialCat, healthCat, socialCat, proCat, miscCat);
    categories.setPrefWidth(600);
    categories.setPrefHeight(100);
    categories.setPrefColumns(6);
    categories.setPrefRows(1);
    categories.setPrefTileWidth(100);
    categories.setPrefTileHeight(100);
    Label title = new Label("Goale Type");
    title.setStyle("-fx-background-color:#efefef");
    title.setPrefSize(600, 30);
    title.setTextAlignment(TextAlignment.CENTER);
    title.setAlignment(Pos.CENTER);
    VBox selection = new VBox(title, categories, cancel);
    selection.setAlignment(Pos.CENTER);

    //Set Stage and Scene
    Scene parent = new Scene(selection);
    if(!model.getTheme()){
      title.setStyle("-fx-background-color:#efefef;");
      parent.getStylesheets().add(getClass().getResource("/css/LimeLight.css").toExternalForm());
    }
    else {
      title.setStyle("-fx-background-color:#1d1d1d; -fx-text-fill: #c4c4c4;");
      cancel.getStyleClass().add("additionButton");
      academicCat.getStyleClass().add("additionButton");
      financialCat.getStyleClass().add("additionButton");
      socialCat.getStyleClass().add("additionButton");
      healthCat.getStyleClass().add("additionButton");
      proCat.getStyleClass().add("additionButton");
      miscCat.getStyleClass().add("additionButton");
      parent.getStylesheets().add(getClass().getResource("/css/StrikerGreen.css").toExternalForm());
    }
    this.adder = new Stage(StageStyle.UNDECORATED);

    //Window Dragging
    title.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            xWindowOffset = event.getSceneX();
            yWindowOffset = event.getSceneY();
        }
    });

    title.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            adder.setX(event.getScreenX() - xWindowOffset);
            adder.setY(event.getScreenY() - yWindowOffset);
        }
    });
    
    //Category Selections
    academicCat.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        selectSound();
        model.addGoal(Category.ACADEMIC);
        adder.close();
      }
    });

    financialCat.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        selectSound();
        model.addGoal(Category.FINANCIAL);
        adder.close();
      }
    });

    healthCat.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        selectSound();
        model.addGoal(Category.WELLNESS);
        adder.close();
      }
    });

    socialCat.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        selectSound();
        model.addGoal(Category.SOCIAL);
        adder.close();
      }
    });

    proCat.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        selectSound();
        model.addGoal(Category.PROFESSIONAL);
        adder.close();
      }
    });

    miscCat.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        selectSound();
        model.addGoal(Category.MISCELLANEOUS);
        adder.close();
      }
    });

    //Category Hovers
    academicCat.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        hoverSound();
      }
    });

    financialCat.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        hoverSound();
      }
    });

    healthCat.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        hoverSound();
      }
    });

    socialCat.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        hoverSound();
      }
    });

    proCat.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        hoverSound();
      }
    });

    miscCat.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        hoverSound();
      }
    });

    //Cancel
    cancel.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        sounds.cancel.stop();
        sounds.cancel.play();
        adder.close();
      }
    });

    cancel.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        sounds.genHover.stop();
        sounds.genHover.play();
      }
    });

    //Scene and Render
    adder.setResizable(false);
    adder.setTitle("Goal Type");
    adder.setWidth(600);
    adder.setHeight(155);
    adder.setScene(parent);
    adder.initModality(Modality.APPLICATION_MODAL);
    adder.initOwner(this.owner);
  }

  public void open(){
    this.adder.show();
  }

  //Sounds when selected
  private void selectSound(){
    sounds.select.stop();
    sounds.select.play();
  }

  //Sounds when hovered
  private void hoverSound(){
    sounds.browseGoal.stop();
    sounds.browseGoal.play();
  }
}