package com.darientsai;

/**
 * @author Darien Tsai
 * Enumeration for goal categories
 */

public enum Category{

  //Enumeration
  ACADEMIC       ("(´･ω･`) ?"),
  FINANCIAL      ("( ✧Д✧) $"),
  WELLNESS       ("(^ ▾^) ♡"),
  SOCIAL         ("(=ヮ=) /"),
  PROFESSIONAL   ("(⌐■-■) ."),
  MISCELLANEOUS  ("(☉o☉) !");

  //Fields
  private String cat;

  //Construct
  Category(String cat){
      this.cat = cat;
  }

  public String face(){
    return this.cat;
  }

}