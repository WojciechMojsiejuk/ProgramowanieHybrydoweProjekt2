package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ColorBalanceDialog {
    Slider slider;
    boolean success=false;

    public void display()
    {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Color balance dialog");
        dialog.setMinHeight(100);
        dialog.setMinWidth(200);

        Label label = new Label();
        label.setText("Set color balance value");

        slider = new Slider();
        slider.setMin(0);
        slider.setMax(2);
        slider.setValue(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(10);

        Button button = new Button("Apply");
        button.setOnAction(e-> {
            this.success = true;
            dialog.close();
        } );

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(label, slider, button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public double getValue()
    {
        return slider.getValue();
    }
}
