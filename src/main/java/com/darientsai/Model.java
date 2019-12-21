package com.darientsai;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Darien Tsai
 * Model for data
 */
public class Model{
  
  //Fields
  @JsonInclude(Include.NON_NULL)
  private int current;
  private boolean audio;
  private boolean theme;
  private ArrayList<Goal> goals;
  private App views;

  //Construct
  public Model(int current, boolean audio, boolean theme, App views){
    this.current = current;
    this.audio = audio;
    this.theme = theme;
    this.views = views;
  }

  //Setters
  public void setCurrent(int current){this.current = current;}
  public void setAudio(boolean audio){this.audio = audio;}
  public void setTheme(boolean theme){this.theme = theme;}
  public void setGoals(ArrayList<Goal> goals){this.goals = goals;}

  //Getters
  public boolean getAudio(){return this.audio;}
  public boolean getTheme(){return this.theme;}
  public int getCurrent(){return this.current;}
  public ArrayList<Goal> getGoals(){return this.goals;}

  //Toggle audio
  public void toggleAudio(){
    this.audio = !this.audio;
  }

  //Toggle theme
  public void toggleTheme(){this.theme = !this.theme;}

  //Add goal
  public void addGoal(Category type){
    this.write(0);
    this.goals.add(new Goal(type));
    this.views.reloadGoals(this.goals);
  }

  //Write to goal
  public void write(int caret){
    if(this.goals.size() == 0){return;}
    this.goals.get(current).setTitle(this.views.getGoalTitle().getText());
    this.goals.get(current).setNotes(this.views.getNotes().getText());
    ArrayList<Task> load = views.getLoad();
    ArrayList<String> items = new ArrayList<>();
    ArrayList<Boolean> completion = new ArrayList<>();
    for(int i = 0; i < load.size(); i++){
      items.add(load.get(i).getTask());
      completion.add(load.get(i).getCompletion());
    }
    this.goals.get(current).setItems(items);
    this.goals.get(current).setCompletion(completion);
    int currentIdx = this.current;
    this.views.reloadGoals(this.goals);
    this.current = currentIdx;
    this.views.load(currentIdx);
    this.views.getGoalTitle().positionCaret(caret);
  }
  

  //Remove goal
  public void removeGoal(int idx){
    this.goals.remove(idx);
    this.views.reloadGoals(this.goals);
  }

  //Add a task
  public void addTask(){
    int currentIdx = this.current;
    this.write(0);
    if(this.goals.size() != 0){
      this.goals.get(current).addItem();
      this.views.reloadGoals(this.goals);
      this.current = currentIdx;
      this.views.load(currentIdx);
    }
  }

  //Remove a task
  public void removeTask(int task){
    int currentIdx = this.current;
    this.write(0);
    this.goals.get(currentIdx).removeItem(task);
    this.views.reloadGoals(this.goals);
    this.current = currentIdx;
    this.views.load(currentIdx);
  }

  //Swap goals
  public void prioritize(int src, int offset){
    int reposit = src - offset;
    if(reposit < 0){
      reposit = 0;
    }
    else if(reposit >= this.goals.size()){
      reposit = this.goals.size() - 1;
    }
    this.goals.add(reposit, this.goals.remove(src));
    int currentIdx = reposit;
    views.reloadGoals(this.goals);
    this.setCurrent(currentIdx);
    views.load(currentIdx);
  }

  //Generate state string
  public String getState(){
    return (this.current + ",\n" + this.audio + ",\n" + this.theme);
  }

  //test Print
  public void test(){
    System.out.println(this.current + " " + this.audio + " " + this.theme);
    //this.goals.get(0).test();
  }
}