package gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

public class Controller {

    @FXML
    public ImageView imageViewer;

    protected Image image;
    @FXML
    public void openFile(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.gif", "*.jpg", "*.png", "*.bmp")
        );
        File selectedImage = fileChooser.showOpenDialog(null);

        if(selectedImage!=null)
        {
            image = new Image(selectedImage.toURI().toString());
            imageViewer.setImage(image);
        }

    }
}
