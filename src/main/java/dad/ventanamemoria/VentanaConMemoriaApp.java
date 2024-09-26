package dad.ventanamemoria;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class VentanaConMemoriaApp extends Application {

    //model
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty();
    private DoubleProperty height = new SimpleDoubleProperty();
    private Label redLabel;
    private Label greenLabel;
    private Label blueLabel;
    private IntegerProperty red = new SimpleIntegerProperty();
    private IntegerProperty green = new SimpleIntegerProperty();
    private IntegerProperty blue = new SimpleIntegerProperty();




    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("iniciando");

        File profileFolder = new File(System.getProperty("user.home"));
        File configFolder = new File(profileFolder, ".ventanaConMemoria");
        File configFile = new File(configFolder, "config.properties");

        if (configFile.exists()){
            //lo cargamos

            FileInputStream fis = new FileInputStream(configFile);

            Properties props = new Properties();
            props.load(fis);
            width.set(Double.parseDouble(props.getProperty("size.width")));
            height.set(Double.parseDouble(props.getProperty("size.height")));
            x.set(Double.parseDouble(props.getProperty("location.x")));
            y.set(Double.parseDouble(props.getProperty("location.y")));
            red.set(Integer.parseInt(props.getProperty("color.red")));
            green.set(Integer.parseInt(props.getProperty("color.green")));
            blue.set(Integer.parseInt(props.getProperty("color.blue")));

        } else {

            width.set(320);
            height.set(200);
            x.set(0);
            y.set(0);
            red.set(0);
            green.set(0);
            blue.set(0);


        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        redLabel = new Label("Red");
        redLabel.setStyle("-fx-font-weight: bold;");

        Slider redSlider = new Slider();
        redSlider.setMin(0);
        redSlider.setMax(255);
        redSlider.setShowTickLabels(true);
        redSlider.setShowTickMarks(true);
        redSlider.setMajorTickUnit(255);
        redSlider.setMinorTickCount(5);

        HBox redHB = new HBox();
        redHB.setSpacing(5);
        redHB.getChildren().addAll(redLabel, redSlider);

        greenLabel = new Label("Green");
        greenLabel.setStyle("-fx-font-weight: bold;");

        Slider greenSlider = new Slider();
        greenSlider.setMin(0);
        greenSlider.setMax(255);
        greenSlider.setShowTickLabels(true);
        greenSlider.setShowTickMarks(true);
        greenSlider.setMajorTickUnit(255);
        greenSlider.setMinorTickCount(5);

        HBox greenHB = new HBox();
        greenHB.setSpacing(5);
        greenHB.getChildren().addAll(greenLabel, greenSlider);

        blueLabel = new Label("Blue");
        blueLabel.setStyle("-fx-font-weight: bold;");

        Slider blueSlider = new Slider();
        blueSlider.setMin(0);
        blueSlider.setMax(255);
        blueSlider.setShowTickLabels(true);
        blueSlider.setShowTickMarks(true);
        blueSlider.setMajorTickUnit(255);
        blueSlider.setMinorTickCount(5);

        HBox blueHB = new HBox();
        blueHB.setSpacing(5);
        blueHB.getChildren().addAll(blueLabel, blueSlider);


        VBox root = new VBox();
        root.setFillWidth(false);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(redHB, greenHB, blueHB);

        Scene scene = new Scene(root, width.get(), height.get());

        primaryStage.setX(x.get());
        primaryStage.setY(y.get());
        primaryStage.setTitle("Ventana con memoria");
        primaryStage.setScene(scene);
        primaryStage.show();

        //bindings

        x.bind(primaryStage.xProperty());
        y.bind(primaryStage.yProperty());
        width.bind(primaryStage.widthProperty());
        height.bind(primaryStage.heightProperty());

        redSlider.valueProperty().bindBidirectional(red);

        red.addListener((o, ov, nv) -> {
            Color c = Color.rgb(nv.intValue(), green.get(), blue.get());
            root.setBackground(Background.fill(c));
        });

        greenSlider.valueProperty().bindBidirectional(green);

        green.addListener((o, ov, nv) -> {
            Color c = Color.rgb(red.get(), nv.intValue(), blue.get());
            root.setBackground(Background.fill(c));
        });

        blueSlider.valueProperty().bindBidirectional(blue);

        blue.addListener((o, ov, nv) -> {
            Color c = Color.rgb(red.get(), green.get(), nv.intValue());
            root.setBackground(Background.fill(c));
        });

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("cerrando");

        File profileFolder = new File(System.getProperty("user.home"));
        File configFolder = new File(profileFolder, ".ventanaConMemoria");
        File configFile = new File(configFolder, "config.properties");

        if (!configFolder.exists()) {
            configFolder.mkdir();
        }

        FileOutputStream fos = new FileOutputStream(configFile);

        Properties props = new Properties();
        props.setProperty("size.width","" + width.getValue());
        props.setProperty("size.height","" + height.getValue());
        props.setProperty("location.x","" + x.getValue());
        props.setProperty("location.y","" + y.getValue());
        props.setProperty("color.red","" + red.getValue());
        props.setProperty("color.green","" + green.getValue());
        props.setProperty("color.blue","" + blue.getValue());
        props.store(fos, "Estado de la ventana");
    }

}
