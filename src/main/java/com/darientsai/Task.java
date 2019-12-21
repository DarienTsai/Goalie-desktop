package com.darientsai;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

/**
 * @author Darien Tsai
 * UI component for a task
 */
public class Task extends HBox{

  //Fields
  private App views;
  private boolean completion;
  private TextField task;
  
  //Construct
  public Task(int idx, App views, boolean completion, String task){
    super(0);

    //Fill Fields
    this.views = views;
    this.completion = completion;

    //UI Component
    Button remove = new Button("x");
    remove.setPrefSize(30, 30);
    remove.setTextAlignment(TextAlignment.CENTER);
    remove.getStyleClass().add("removeTask");
    CheckBox complete = new CheckBox();
    complete.setPrefSize(30, 30);
    complete.setContentDisplay(ContentDisplay.RIGHT);
    complete.setAlignment(Pos.CENTER_RIGHT);
    complete.setSelected(this.completion);
    complete.getStyleClass().add("check");
    this.task  = new TextField(task);
    this.task.setPrefSize(285, 30);
    this.task.setAlignment(Pos.BASELINE_LEFT);
    this.task.getStyleClass().add("taskDescription");
    this.getChildren().addAll(remove, complete, this.task);
    this.setPrefSize(350, 30);
    this.setAlignment(Pos.CENTER_LEFT);
    this.getStyleClass().add("task");
    this.setFillHeight(false);

    //Toggle Completion
    complete.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        toggle();
      }
    });

    //Remove Listener
    remove.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        views.getSounds().removeTask.stop();
        views.getSounds().removeTask.play();
        views.getModel().removeTask(idx);
      }
    });

    //Hover sounds
    this.setOnMouseEntered(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent event){
        views.getSounds().browseTask.stop();
        views.getSounds().browseTask.play();
      }
    });
  }

  // Toggle completion
  public void toggle(){
    if(this.completion){
      views.getSounds().uncheck.stop();
      views.getSounds().uncheck.play();
      this.completion = false;
      return;
    }
      views.getSounds().check.stop();
      views.getSounds().check.play();
      this.completion = true;
  }

  //Getters
  public boolean getCompletion(){return this.completion;}
  public String getTask(){return this.task.getText();}
}