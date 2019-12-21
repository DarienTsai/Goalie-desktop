package com.darientsai;

import java.util.ArrayList;

/**
 * @author Darien Tsai
 * Stores info for one goal
 */

public class Goal{

  //Fields
  private String type;
  private String title;
  private ArrayList<String> items;
  private ArrayList<Boolean> completion;
  private String notes;

  //Construct
  public Goal(){}
  public Goal(Category type){
    this.type = type.name();
    this.title = "New Goal";
    this.items = new ArrayList<>();
    this.completion = new ArrayList<>();
    this.notes = "";
  }

  //Add an item
  public void addItem(){
    this.items.add("New Task");
    this.completion.add(false);
  }

  //Remove an item
  public void removeItem(int idx){
    this.items.remove(idx);
    this.completion.remove(idx);
  }

  //Edit the notes
  public void editNotes(String edit){
    this.notes = edit;
  }

  //Getters
  public String getType(){return this.type;}
  public String getTitle(){return this.title;}
  public String face(){return Enum.valueOf(Category.class, this.type).face();}
  public ArrayList<String> getItems(){return this.items;}
  public ArrayList<Boolean> getCompletion(){return this.completion;}
  public String getNotes(){return this.notes;}
  public String evaluate(){
    int total = this.completion.size();
    int done = 0;
    for(int i = 0; i < total; i++){done += this.completion.get(i)? 1: 0;}
    return ("" + done + "/" + total + " completed");
  }

  //Setters
  public void setType(String type){this.type = type;}
  public void setTitle(String title){this.title = title;}
  public void setItems(ArrayList<String> items){this.items = items;}
  public void setCompletion(ArrayList<Boolean> completion){this.completion = completion;}
  public void setNotes(String notes){this.notes = notes;}
}