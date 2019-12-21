package com.darientsai;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
 
/**
 * @author Darien Tsai
 * Sounds :D
 */

public class Sounds{
  //Fields
  MediaPlayer browseGoal;
  MediaPlayer browseTask;
  MediaPlayer cancel;
  MediaPlayer check;
  MediaPlayer close;
  MediaPlayer genHover;
  MediaPlayer deletePrompt;
  MediaPlayer menuBar;
  MediaPlayer removeTask;
  MediaPlayer select;
  MediaPlayer uncheck;
  MediaPlayer windowPop;

  //Consruct
  public Sounds(){
      //Sounds
      String browseGoalPath = getClass().getResource("/sounds/browseGoal.wav").toExternalForm();
      String browseTaskPath = getClass().getResource("/sounds/browseTask.wav").toExternalForm();
      String cancelPath = getClass().getResource("/sounds/cancel.wav").toExternalForm();
      String checkPath = getClass().getResource("/sounds/check.wav").toExternalForm();
      String closePath = getClass().getResource("/sounds/close.wav").toExternalForm();
      String genHoverPath = getClass().getResource("/sounds/genHover.wav").toExternalForm();
      String deletePromptPath = getClass().getResource("/sounds/deletePrompt.wav").toExternalForm();
      String menuBarPath = getClass().getResource("/sounds/menuBar.wav").toExternalForm();
      String removeTaskPath = getClass().getResource("/sounds/removeTask.wav").toExternalForm();
      String selectPath = getClass().getResource("/sounds/select.wav").toExternalForm();
      String uncheckPath = getClass().getResource("/sounds/uncheck.wav").toExternalForm();
      String windowPopPath = getClass().getResource("/sounds/windowPop.wav").toExternalForm();
      Media browseGoalSrc = new Media(browseGoalPath);
      Media browseTaskSrc = new Media(browseTaskPath);
      Media cancelSrc = new Media(cancelPath);
      Media checkSrc = new Media(checkPath);
      Media closeSrc = new Media(closePath);
      Media genHoverSrc = new Media(genHoverPath);
      Media deletePromptSrc = new Media(deletePromptPath);
      Media menuBarSrc = new Media(menuBarPath);
      Media removeTaskSrc = new Media(removeTaskPath);
      Media selectSrc = new Media(selectPath);
      Media uncheckSrc = new Media(uncheckPath);
      Media windowPopSrc = new Media(windowPopPath);
      browseGoal = new MediaPlayer(browseGoalSrc);
      browseTask = new MediaPlayer(browseTaskSrc);
      cancel = new MediaPlayer(cancelSrc);
      check = new MediaPlayer(checkSrc);
      close = new MediaPlayer(closeSrc);
      genHover = new MediaPlayer(genHoverSrc);
      deletePrompt = new MediaPlayer(deletePromptSrc);
      menuBar = new MediaPlayer(menuBarSrc);
      removeTask = new MediaPlayer(removeTaskSrc);
      select = new MediaPlayer(selectSrc);
      uncheck = new MediaPlayer(uncheckSrc);
      windowPop = new MediaPlayer(windowPopSrc);
  }

  public void mute(){
    browseGoal.setVolume(0);
    browseTask.setVolume(0);
    cancel.setVolume(0);
    check.setVolume(0);
    close.setVolume(0);
    genHover.setVolume(0);
    deletePrompt.setVolume(0);
    menuBar.setVolume(0);
    removeTask.setVolume(0);
    select.setVolume(0);
    uncheck.setVolume(0);
    windowPop.setVolume(0);
  }
}
