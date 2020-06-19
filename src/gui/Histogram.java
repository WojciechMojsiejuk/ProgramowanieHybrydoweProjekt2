package gui;

import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class Histogram{

        private Image image;

        private long luminous[] = new long[256];
        private long red[] = new long[256];
        private long green[] = new long[256];
        private long blue[] = new long[256];

        XYChart.Series seriesLuminous;
        XYChart.Series seriesRed;
        XYChart.Series seriesGreen;
        XYChart.Series seriesBlue;

        Histogram(Image src) {
            calcHist(src);
        }

    protected void calcHist(Image src) {
        image = src;

        //init
        for (int i = 0; i < 256; i++) {
            luminous[i] = red[i] = green[i] = blue[i] = 0;
        }

        PixelReader pixelReader = image.getPixelReader();
        if (pixelReader == null) {
            return;
        }

        //count pixels
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = pixelReader.getArgb(x, y);
                int r = (0xff & (argb >> 16));
                int g = (0xff & (argb >> 8));
                int b = (0xff & argb);

                luminous[(int)(pixelReader.getColor(x,y).getBrightness()*255)]++;
                red[r]++;
                green[g]++;
                blue[b]++;

            }
        }

        seriesLuminous = new XYChart.Series();
        seriesRed = new XYChart.Series();
        seriesGreen = new XYChart.Series();
        seriesBlue = new XYChart.Series();
        seriesLuminous.setName("luminous");

        seriesRed.setName("red");
        seriesGreen.setName("green");
        seriesBlue.setName("blue");

        //copy alpha[], red[], green[], blue[]
        //to seriesAlpha, seriesRed, seriesGreen, seriesBlue
        for (int i = 0; i < 256; i++) {
            seriesLuminous.getData().add(new XYChart.Data(String.valueOf(i), luminous[i]));
            seriesRed.getData().add(new XYChart.Data(String.valueOf(i), red[i]));
            seriesGreen.getData().add(new XYChart.Data(String.valueOf(i), green[i]));
            seriesBlue.getData().add(new XYChart.Data(String.valueOf(i), blue[i]));
        }
    }


    public XYChart.Series getSeriesLuminous() {
            return seriesLuminous;
        }

        public XYChart.Series getSeriesRed() {
            return seriesRed;
        }

        public XYChart.Series getSeriesGreen() {
            return seriesGreen;
        }


        public XYChart.Series getSeriesBlue() {
            return seriesBlue;
        }
}