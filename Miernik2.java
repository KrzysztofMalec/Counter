import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Miernik2 extends Application implements EventHandler<ActionEvent>{

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        final Double[] displayTime = new Double[]{1d};
        double seconds, minutes, hours, days;
        
        GridPane window = new GridPane();
        Label label = new Label();
        Button startStop = new Button();
        Button zero = new Button("Stop");
        Scene scene = new Scene(window, 300,300);
        Text title = new Text("Count time:");
          
        
  
        DoubleProperty time = new SimpleDoubleProperty();
        label.textProperty().bind(time.asString("%.3f seconds"));
        final BooleanProperty running = new SimpleBooleanProperty();

        AnimationTimer timer = new AnimationTimer() {
            private long startTime;
            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                running.set(true);
                super.start();
            }
            @Override
            public void stop() {
                displayTime[0] = time.get();
                running.set(false);
                super.stop();
            }

            @Override
            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                double display = displayTime[0] + (now - startTime)/1000.0;  
                time.set(display);

            }

        };
        zero.setOnAction(e->{
        	displayTime[0]=0.00;
        }
        );
        startStop.textProperty().bind(
                Bindings
                        .when(running)
                        .then("Pause")
                        .otherwise("Start"));
        startStop.setOnAction(e-> {
            if (running.get()) {
                timer.stop();
            }else{
                timer.start();
            }
        });

        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        window.add(title, 0,0,2,2);
        window.add(startStop, 1, 3);
        window.add(zero, 2, 3);
        window.add(label, 1, 4);
        window.setAlignment(Pos.TOP_LEFT);
        window.setHgap(10);
        window.setVgap(10);
        window.setPadding(new Insets(20,20,20,20));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void handle(ActionEvent event) {
        System.out.println("Error");

    }

}