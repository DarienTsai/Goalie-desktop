package com.darientsai;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Darien Tsai Splash window
 */
class Splash{

  //Fields
  private Stage splash;

  //Construct
  public Splash(){
    Label splashLabel = new Label("Goalie");
    splashLabel.setAlignment(Pos.CENTER);
    splashLabel.setTextAlignment(TextAlignment.CENTER);
    splashLabel.setPrefSize(130, 30);
    ImageView splashLogo = new ImageView(getClass().getResource("/graphic/SplashLogo.png").toExternalForm());
    splashLogo.setFitWidth(90);
    splashLogo.setFitHeight(75);
    VBox splashContainer = new VBox(splashLogo, splashLabel);
    splashContainer.setAlignment(Pos.CENTER);
    Scene splashScene = new Scene(splashContainer);
    this.splash = new Stage(StageStyle.UNDECORATED);
    this.splash.setScene(splashScene);
    this.splash.initModality(Modality.NONE);
    this.splash.setWidth(130);
    this.splash.setHeight(130);
    this.splash.show();
  }

  public void close(){
    this.splash.close();
  }
}

