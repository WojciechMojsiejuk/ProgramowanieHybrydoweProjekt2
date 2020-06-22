from py4j.java_gateway import JavaGateway, CallbackServerParameters

class ImageProcessing():

    class Java:
        implements = ["gui.ImageProcessing"]

    def __init__(self, gateway):
        self.gateway = gateway

    def function(self):
        print("Hello")

if __name__ == "__main__":
    gateway = JavaGateway(
        callback_server_parameters=CallbackServerParameters()
    )
    imgproc = ImageProcessing(gateway)
    gateway.entry_point.ConnectToPython(imgproc)