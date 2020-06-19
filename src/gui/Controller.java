package gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class Controller {

    @FXML
    public ImageView imageViewer;
    @FXML
    public LineChart RHistogramChart;
    @FXML
    public LineChart BHistogramChart;
    @FXML
    public LineChart GHistogramChart;
    @FXML
    public LineChart LuminousHistogramChart;

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

            LuminousHistogramChart.getData().clear();
            LuminousHistogramChart.getData().addAll(
                    histogram.getSeriesLuminous());
            LuminousHistogramChart.getStyleClass().add("-fx-stroke: black");
            LuminousHistogramChart.autosize();
            LuminousHistogramChart.applyCss();

            RHistogramChart.getData().clear();
            RHistogramChart.getData().addAll(
                    histogram.getSeriesRed());
            RHistogramChart.setStyle("-fx-stroke: red");
            RHistogramChart.autosize();
            RHistogramChart.applyCss();

            GHistogramChart.getData().clear();
            GHistogramChart.getData().addAll(
                    histogram.getSeriesGreen());
            GHistogramChart.setStyle("-fx-stroke: green");
            GHistogramChart.autosize();
            GHistogramChart.applyCss();

            BHistogramChart.getData().clear();
            BHistogramChart.getData().addAll(
                    histogram.getSeriesBlue());
            BHistogramChart.setStyle("-fx-stroke: blue");
            BHistogramChart.autosize();
            BHistogramChart.applyCss();
        }
    }

    public void saveFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.gif", "*.jpg", "*.png", "*.bmp")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                String fileExtension="";
                String fileName=file.getName();

                if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)
                {
                    fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);
                    ImageIO.write(SwingFXUtils.fromFXImage(imageViewer.getImage(),
                            null), fileExtension, file);
                }
                else
                {
                    ImageIO.write(SwingFXUtils.fromFXImage(imageViewer.getImage(),
                            null), "png", file);
                }
            } catch (IOException ex) {
                Logger.getAnonymousLogger().log(new LogRecord(SEVERE, "Failed to save a file"));
            }
        }
    }
}

