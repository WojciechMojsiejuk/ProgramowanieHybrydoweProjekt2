package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

public class Controller {

    @FXML
    public ImageView imageViewer;
    @FXML
    public CategoryAxis xHistAxis;
    @FXML
    public NumberAxis yHistAxis;
    @FXML
    public LineChart HistogramChart;

    protected Histogram histogram;
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
            histogram = new Histogram(image);
            HistogramChart.getData().clear();
            HistogramChart.getData().addAll(
                    histogram.getSeriesRed(),
                    histogram.getSeriesGreen(),
                    histogram.getSeriesBlue());
        }
    }
}

