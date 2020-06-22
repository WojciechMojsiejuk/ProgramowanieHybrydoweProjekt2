package gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
    protected String fileExtension;

    public ImageProcessing imageProcessing;

    public void ConnectToPython(ImageProcessing imgProc) {
        this.imageProcessing = imgProc;
        System.out.println("Connection established");
    }

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
            String fileName=selectedImage.getName();

            if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)
            {
                fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);

            }
            else
            {
                fileExtension = "png";
            }
            imageViewer.setImage(image);
            CalcHist();
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

    public void CalcHist()
    {
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
    public void SaveTemp() throws IOException
    {
        String filename = "temp.png";
        String pathToFile = Paths.get("").toAbsolutePath().toString();
        System.out.println(pathToFile);
        File temp = new File(pathToFile, filename);
        ImageIO.write(SwingFXUtils.fromFXImage(imageViewer.getImage(),null),"png", temp);
    }

    public void LoadTemp() throws IOException
    {
        String filename = "temp.png";
        String pathToFile = Paths.get("").toAbsolutePath().toString();
        File temp = new File(pathToFile, filename);
        this.image = new Image(temp.toURI().toString());
        imageViewer.setImage(image);
    }

    public void blurImage(ActionEvent actionEvent) throws IOException {
        SaveTemp();
        imageProcessing.blur();
        LoadTemp();
        CalcHist();
    }

    public void sharpenImage(ActionEvent actionEvent) throws IOException {
        SaveTemp();
        imageProcessing.sharpen();
        LoadTemp();
        CalcHist();
    }

    public void contrastImage(ActionEvent actionEvent) throws IOException {
        ContrastDialog dlg = new ContrastDialog();
        dlg.display();
        if(dlg.success)
        {
            double value = dlg.getValue();
            System.out.println(value);
            SaveTemp();
            imageProcessing.contrast(value);
            LoadTemp();
            CalcHist();
        }
    }

    public void brightnessImage(ActionEvent actionEvent) throws IOException {
        BrightnessDialog dlg = new BrightnessDialog();
        dlg.display();
        if(dlg.success)
        {
            double value = dlg.getValue();
            System.out.println(value);
            SaveTemp();
            imageProcessing.brightness(value);
            LoadTemp();
            CalcHist();
        }
    }

    public void colorBalanceImage(ActionEvent actionEvent) throws IOException {
        ColorBalanceDialog dlg = new ColorBalanceDialog();
        dlg.display();
        if(dlg.success)
        {
            double value = dlg.getValue();
            System.out.println(value);
            SaveTemp();
            imageProcessing.color_balance(value);
            LoadTemp();
            CalcHist();
        }
    }

    public void coolingImage(ActionEvent actionEvent) throws IOException {
        SaveTemp();
        imageProcessing.cool();
        LoadTemp();
        CalcHist();
    }

    public void warmingImage(ActionEvent actionEvent) throws IOException {
        SaveTemp();
        imageProcessing.warm();
        LoadTemp();
        CalcHist();
    }
}

