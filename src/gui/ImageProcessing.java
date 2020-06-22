package gui;

public interface ImageProcessing {
    void blur();
    void sharpen();
    void contrast(double value);
    void brightness(double value);
    void color_balance(double value);
    void cool();
    void warm();
}
