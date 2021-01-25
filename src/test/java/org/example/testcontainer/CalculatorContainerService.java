package org.example.testcontainer;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

@Service
public class CalculatorContainerService {
    private static final DockerImageName ADDITION_SERVICE = DockerImageName.
            parse("mbarkin26/addition-service:latest")
            .asCompatibleSubstituteFor("addition-service");
    private static final DockerImageName SUBTRACTION_SERVICE = DockerImageName
            .parse("mbarkin26/subtraction-service:latest")
            .asCompatibleSubstituteFor("subtraction-service");
    private static final DockerImageName CALCULATOR_SERVICE = DockerImageName
            .parse("mbarkin26/calculator-service:latest")
            .asCompatibleSubstituteFor("calculator-service");
//    private static final DockerImageName SELENIUM_SERVICE = DockerImageName
//            .parse("selenium/node-chrome:latest")
//            .asCompatibleSubstituteFor("node-chrome");



    private Network network;

    private GenericContainer<?> additionContainer;
    private GenericContainer<?> subtractionContainer;
    private GenericContainer<?> calculatorContainer;
    private BrowserWebDriverContainer<?> chromeContainer;



    public CalculatorContainerService() {
        network = Network.newNetwork();
        additionContainer = new GenericContainer<>(ADDITION_SERVICE)
                .withPrivilegedMode(true)
                .withExposedPorts(8070)
                .withNetwork(network)
                .withEnv("SERVER_PORT", "8070")
                .withEnv("SERVER_ADDRESS", "localhost");

        subtractionContainer = new GenericContainer<>(SUBTRACTION_SERVICE)
                .withPrivilegedMode(true)
//                .withExtraHost("subtraction-service","10.150.17.73")
                .withNetwork(network)
                .withEnv("SERVER_PORT", "8071")
                .withEnv("SERVER_ADDRESS", "localhost");

        calculatorContainer = new GenericContainer<>(CALCULATOR_SERVICE)
                .withPrivilegedMode(true)
                .withNetwork(network)
                .withEnv("SERVER_PORT", "8072")
                .withEnv("SERVER_ADDRESS", "localhost");

        chromeContainer = new BrowserWebDriverContainer<>()
                .withNetwork(network)
                .withNetworkAliases("chrome")
                .withCapabilities(DesiredCapabilities.chrome())
                .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("./target/"));
//                .withExposedPorts(8072)
//                .withNetwork(network)
//                .withEnv("SERVER_PORT", "8072")
//                .withEnv("SERVER_ADDRESS", "localhost");

        additionContainer.start();
        subtractionContainer.start();
        calculatorContainer.start();
//        chromeContainer.start();
    }

    public void stopContainers(){
        additionContainer.close();
        subtractionContainer.close();
        calculatorContainer.close();
//        chromeContainer.close();
        network.close();
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public GenericContainer<?> getAdditionContainer() {
        return additionContainer;
    }

    public void setAdditionContainer(GenericContainer<?> additionContainer) {
        this.additionContainer = additionContainer;
    }

        public GenericContainer<?> getSubtractionContainer() {
        return subtractionContainer;
    }

    public void setSubtractionContainer(GenericContainer<?> subtractionContainer) {
        this.subtractionContainer = subtractionContainer;
    }

    public GenericContainer<?> getCalculatorContainer() {
        return calculatorContainer;
    }

    public BrowserWebDriverContainer<?> getChromeContainer() {
        return chromeContainer;
    }

    public void setChromeContainer(BrowserWebDriverContainer<?> chromeContainer) {
        this.chromeContainer = chromeContainer;
    }

    public void setCalculatorContainer(GenericContainer<?> calculatorContainer) {
        this.calculatorContainer = calculatorContainer;
    }

}
