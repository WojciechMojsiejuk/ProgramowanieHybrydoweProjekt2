from py4j.java_gateway import JavaGateway, CallbackServerParameters
import PIL.Image
import PIL.ImageFilter
import PIL.ImageEnhance
import cv2
import numpy as np
from scipy.interpolate import UnivariateSpline

class ImageProcessing():

    class Java:
        implements = ["gui.ImageProcessing"]

    def __init__(self, gateway):
        self.gateway = gateway
        print("Established connection")


    def blur(self):
        print("Image bluring...")
        img = PIL.Image.open("temp.png")
        img = img.convert ('RGB')
        image = np.array(img)
        image = cv2.GaussianBlur(image,(5,5),0)
        img = PIL.Image.fromarray(image)
        img.save("temp.png")
        print("...done")


    def sharpen(self):
        print("Image sharpening...")
        img = PIL.Image.open("temp.png")
        img = img.filter(PIL.ImageFilter.SHARPEN)
        img.save("temp.png")
        print("...done")


    def contrast(self, value):
        print("Adjust contrast...")
        img = PIL.Image.open("temp.png")
        img = PIL.ImageEnhance.Contrast(img).enhance(value)
        img.save("temp.png")
        print("...done")


    def brightness(self, value):
        print("Adjust brightness...")
        img = PIL.Image.open("temp.png")
        img = PIL.ImageEnhance.Brightness(img).enhance(value)
        img.save("temp.png")
        print("...done")

    def color_balance(self, value):
        print("Adjust color balance...")
        img = PIL.Image.open("temp.png")
        img = PIL.ImageEnhance.Color(img).enhance(value)
        img.save("temp.png")
        print("...done")

    def spreadLookupTable(self, x, y):
        spline = UnivariateSpline(x, y)
        return spline(range(256))

    def warm(self):
        print("Warming image...")
        img = PIL.Image.open("temp.png")
        img = img.convert ('RGB')
        image = np.array(img)
        increaseLookupTable = self.spreadLookupTable([0, 64, 128, 256], [0, 80, 160, 256])
        decreaseLookupTable = self.spreadLookupTable([0, 64, 128, 256], [0, 50, 100, 256])
        red_channel, green_channel, blue_channel = cv2.split(image)
        red_channel = cv2.LUT(red_channel, increaseLookupTable).astype(np.uint8)
        blue_channel = cv2.LUT(blue_channel, decreaseLookupTable).astype(np.uint8)
        img = cv2.merge((red_channel, green_channel, blue_channel))
        img = PIL.Image.fromarray(img)
        img.save("temp.png")
        print("...done")

    def cool(self):
        print("Cooling image...")
        img = PIL.Image.open("temp.png")
        img = img.convert ('RGB')
        image = np.array(img)
        increaseLookupTable = self.spreadLookupTable([0, 64, 128, 256], [0, 80, 160, 256])
        decreaseLookupTable = self.spreadLookupTable([0, 64, 128, 256], [0, 50, 100, 256])
        red_channel, green_channel, blue_channel = cv2.split(image)
        red_channel = cv2.LUT(red_channel, decreaseLookupTable).astype(np.uint8)
        blue_channel = cv2.LUT(blue_channel, increaseLookupTable).astype(np.uint8)
        img = cv2.merge((red_channel, green_channel, blue_channel))
        img = PIL.Image.fromarray(img)
        img.save("temp.png")
        print("...done")

if __name__ == "__main__":
    gateway = JavaGateway(
        callback_server_parameters=CallbackServerParameters()
    )
    imgproc = ImageProcessing(gateway)
    gateway.entry_point.ConnectToPython(imgproc)