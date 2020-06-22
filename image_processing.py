from py4j.java_gateway import JavaGateway
gateway = JavaGateway()
print("Gateway established")
ip = gateway.entry_point.getImageProcessing()
print(gateway.entry_point.getImageProcessing)