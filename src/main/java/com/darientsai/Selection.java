package com.darientsai;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Darien Tsai
 * UI component for selectable goals
 */
class Selection extends VBox{

  //Fields
  private App parent;
  private int idx;
  private Selection self;
  private double xOffset;

  //Construct
  public Selection(int idx, App parent, Goal goal, Stage primary){
    super();

    //Set fields
    this.parent = parent;
    this.idx = idx;
    self = this;
    DeleteAlert confirm = new DeleteAlert(this, primary, parent.getSounds(), parent.getModel());
    
    //Layout
    Label goalCompletion = new Label(goal.evaluate());
    goalCompletion.setPrefSize(130, 20);
    goalCompletion.setAlignment(Pos.CENTER);
    goalCompletion.setTextAlignment(TextAlignment.CENTER);
    goalCompletion.getStyleClass().add("goalCompletion");
    Label goalTitle = new Label(goal.getTitle());
    goalTitle.setPrefSize(130, 30);
    goalTitle.setAlignment(Pos.CENTER);
    goalTitle.setTextAlignment(TextAlignment.CENTER);
    goalTitle.getStyleClass().add("goalTitle");
    Label goalFace = new Label(goal.face());
    goalFace.setPrefSize(130, 90);
    goalFace.setAlignment(Pos.CENTER);
    goalFace.setTextAlignment(TextAlignment.CENTER);
    goalFace.getStyleClass().add("goalFace");
    this.getChildren().addAll(goalFace, goalTitle, goalCompletion);
    this.setAlignment(Pos.CENTER);
    this.setPrefSize(130, 130);
    this.getStyleClass().add("selection");

    //Select and Delete
    this.setOnMouseClicked(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
          parent.getSounds().select.stop();
          parent.getSounds().select.play();
          parent.getModel().write(0);
          parent.load(idx);
        }
      }
    });

    //Drag
    this.setOnMousePressed(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
          parent.getSounds().deletePrompt.stop();
          parent.getSounds().deletePrompt.play();
          confirm.show();
          event.consume();
        }
        xOffset = event.getSceneX();
      }
    });

    this.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event){
        if(event.getSceneX() > 70 && event.getSceneX() < 900){
          self.setTranslateX(event.getSceneX() - xOffset);
        }
      }
    });

    this.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event){
        self.setTranslateX(0);
        parent.getModel().prioritize(idx, (int)(event.getSceneX() - xOffset)/140);
      }
    });

    // Hover sounds
    this.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        parent.getSounds().browseGoal.stop();
        parent.getSounds().browseGoal.play();
      }
    });

  }

  //Delete a goal
  public void delete(){

    this.parent.getModel().removeGoal(this.idx);
  }

}