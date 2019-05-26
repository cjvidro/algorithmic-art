import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Gui for algorithmic art application
 *
 * @author Charles Vidro
 */
public class Gui extends Application {
    // GUI Variables
    private BorderPane mainWindow = new BorderPane();
    private StackPane viewer = new StackPane();
    private HBox menuBar = new HBox();
    private Text artTitle = new Text("unnamed");
    private ScrollPane layers = new ScrollPane();
    private HBox editAndFileHBox = new HBox();
    private VBox layerContainer = new VBox();
    private ScrollPane files = new ScrollPane();
    private VBox editVBox = new VBox();
    private ComboBox<String> newShape = new ComboBox<>();
    private VBox fileContainer = new VBox();
    private VBox fileVBox = new VBox();
    private StackPane viewerTools = new StackPane();
    private Boolean gridState = false;
    private Boolean centerCrosshair = false;

    // DATA Variables
    private ArrayList<AlgorithmicShape> algorithmicShapes = new ArrayList<>();
    private ArrayList<OrderedText> layerTextList = new ArrayList<>();
    private ArrayList<OrderedPane> layerPanes = new ArrayList<>();

    // Color palette
    private final Color darkGrey = Color.hsb(0, 0, 0.16);
    private final Background darkGreyBackground = new Background(new BackgroundFill(darkGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color midGrey = Color.hsb(0, 0, 0.25);
    private final Background midGreyBackground = new Background(new BackgroundFill(midGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color lightGrey = Color.hsb(0, 0, 0.39);
    private final Background lightGreyBackground = new Background(new BackgroundFill(lightGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background buttonBackground = new Background(new BackgroundFill(Color.hsb(240, 0.01, 0.68), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background buttonSelectedBackground = new Background(new BackgroundFill(Color.hsb(180, 0.09, 0.86), CornerRadii.EMPTY, Insets.EMPTY));

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX setup window
     *
     * @param primaryStage the primary window stage
     */
    @Override
    public void start(Stage primaryStage) {

        // setting window spaces
        mainWindow.setCenter(viewer);
        mainWindow.setTop(menuBar);
        mainWindow.setRight(editAndFileHBox);
        editAndFileHBox.setBackground(midGreyBackground);
        editAndFileHBox.getChildren().addAll(editVBox, fileVBox);
        editVBox.setSpacing(20);
        editVBox.setAlignment(Pos.TOP_CENTER);

        // viewer
        viewer.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.7));
        viewer.maxWidthProperty().bind(mainWindow.widthProperty().multiply(0.7));

        // Menu bar edits
        menuBar.setAlignment(Pos.CENTER_LEFT);
        menuBar.setBackground(midGreyBackground);
        menuBar.setSpacing(20);

        StackPane artTitlePane = new StackPane();
        artTitlePane.setPadding(new Insets(3, 3, 3, 3));
        artTitlePane.setBackground(lightGreyBackground);
        artTitlePane.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.7));
        artTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        artTitle.setFill(Color.WHITE);
        artTitlePane.getChildren().add(artTitle);

        StackPane editPane = new StackPane();
        editPane.setPadding(new Insets(3, 3, 3, 3));
        editPane.setBackground(darkGreyBackground);
        editPane.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.15).subtract(20));
        Text editText = new Text("Edit");
        editText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        editText.setFill(Color.WHITE);
        editPane.getChildren().add(editText);

        StackPane filePane = new StackPane();
        filePane.setPadding(new Insets(3, 3, 3, 3));
        filePane.setBackground(darkGreyBackground);
        filePane.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.15).subtract(20));
        Text fileText = new Text("Files");
        fileText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        fileText.setFill(Color.WHITE);
        filePane.getChildren().add(fileText);

        menuBar.getChildren().addAll(artTitlePane, editPane, filePane);

        // Edit Controls
        // guides
        viewer.getChildren().add(viewerTools);
        viewerTools.maxWidthProperty().bind(viewer.widthProperty());
        viewerTools.maxHeightProperty().bind(viewer.heightProperty());

        HBox guides = new HBox();
        guides.setAlignment(Pos.CENTER);
        guides.setSpacing(2);
        guides.minWidthProperty().bind(layerContainer.widthProperty());

        Button center = new Button("Center");
        center.setBackground(buttonBackground);
        center.setOnMouseEntered(event -> {
            if (!centerCrosshair) {
                center.setBackground(buttonSelectedBackground);
            }
        });
        center.setOnMouseExited(event -> {
            if (!centerCrosshair) {
                center.setBackground(buttonBackground);
            }
        });
        Pane centerPane = new Pane();

        Line vertLine = new Line(0, -10, 0, 10);
        vertLine.translateXProperty().bind(viewer.widthProperty().divide(2));
        vertLine.translateYProperty().bind(viewer.heightProperty().divide(2));
        vertLine.setStrokeWidth(2);
        vertLine.setStroke(Color.RED);

        Line horizLine = new Line(-10, 0, 10, 0);
        horizLine.translateXProperty().bind(viewer.widthProperty().divide(2));
        horizLine.translateYProperty().bind(viewer.heightProperty().divide(2));
        horizLine.setStrokeWidth(2);
        horizLine.setStroke(Color.RED);

        centerPane.getChildren().addAll(vertLine, horizLine);

        center.setOnAction(event -> {
            if (centerCrosshair) {
                viewerTools.getChildren().remove(centerPane);
                center.setBackground(buttonBackground);
                centerCrosshair = false;
            } else {
                viewerTools.getChildren().add(centerPane);
                center.setBackground(buttonSelectedBackground);
                centerCrosshair = true;
            }

            System.out.println("center selected");
        });

        Button grid = new Button("Grid");
        grid.setBackground(buttonBackground);
        grid.setOnMouseEntered(event -> {
            if (!gridState) {
                grid.setBackground(buttonSelectedBackground);
            }
        });
        grid.setOnMouseExited(event -> {
            if (!gridState) {
                grid.setBackground(buttonBackground);
            }
        });

        Pane viewerGridPane = new Pane();
        for (int i = -600; i <= 600; i += 50) {
            Line vert = new Line(i, -600, i, 600);
            vert.setStroke(new Color(0, 0, 0, 0.2));
            vert.translateXProperty().bind(viewer.widthProperty().divide(2));
            vert.translateYProperty().bind(viewer.heightProperty().divide(2));

            Line horiz = new Line(-600, i, 600, i);
            horiz.setStroke(new Color(0, 0, 0, 0.2));
            horiz.translateXProperty().bind(viewer.widthProperty().divide(2));
            horiz.translateYProperty().bind(viewer.heightProperty().divide(2));

            viewerGridPane.getChildren().addAll(vert, horiz);
        }

        grid.setOnAction(event -> {
            if (gridState) {
                viewerTools.getChildren().remove(viewerGridPane);
                grid.setBackground(buttonBackground);
                gridState = false;
            } else {
                viewerTools.getChildren().add(viewerGridPane);
                grid.setBackground(buttonSelectedBackground);
                gridState = true;
            }

            System.out.println("grid selected");
        });

        guides.getChildren().addAll(center, grid);

        // rotate
        Text rotateText = new Text("Rotate");
        rotateText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        rotateText.setFill(Color.WHITE);

        TextField rotate = new TextField("0.0");
        rotate.setPromptText("0.0");
        rotate.setMaxWidth(75);
        rotate.setBackground(buttonSelectedBackground);
        rotate.setOnAction(event -> {
            if (isNumeric(rotate.getText())) {
                viewer.setRotate(Double.parseDouble(rotate.getText()));
            }
        });

        // zoom (scale)
        Text zoomText = new Text("Zoom");
        zoomText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        zoomText.setFill(Color.WHITE);

        TextField zoom = new TextField("1.0");
        zoom.setPromptText("1.0");
        zoom.setMaxWidth(75);
        zoom.setBackground(buttonSelectedBackground);
        zoom.setOnAction(event -> {
            if (isNumeric(zoom.getText()) && Double.parseDouble(zoom.getText()) >= 0) {
                double zoomNumber = Double.parseDouble(zoom.getText());
                viewer.setScaleX(zoomNumber);
                viewer.setScaleY(zoomNumber);
            } else {
                zoom.setText("1.0");
                viewer.setScaleX(1.0);
                viewer.setScaleY(1.0);
            }
        });

        // Background Color (slider)

        editVBox.getChildren().addAll(guides, rotateText, rotate, zoomText, zoom);

        // new shape
        newShape.getItems().addAll("Line");
        newShape.setPromptText("Add Shape");

        newShape.setOnAction(event -> comboBoxSelected());

        newShape.setBackground(buttonBackground);
        newShape.setOnMouseEntered(event -> newShape.setBackground(buttonSelectedBackground));
        newShape.setOnMouseExited(event -> {
            if (!newShape.isShowing()) {
                newShape.setBackground(buttonBackground);
            }
        });
        newShape.setOnShowing(event -> newShape.setBackground(buttonSelectedBackground));
        newShape.setOnHiding(event -> newShape.setBackground(buttonBackground));

        // layers
        StackPane layerTitlePane = new StackPane();
        layerTitlePane.setPadding(new Insets(3, 3, 3, 3));
        layerTitlePane.setBackground(darkGreyBackground);
        layerTitlePane.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.15).subtract(20));

        Text layerTitle = new Text("Layers");
        layerTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        layerTitle.setFill(Color.WHITE);

        layerTitlePane.getChildren().add(layerTitle);

        layerContainer.setBackground(darkGreyBackground);
        layers.setBackground(darkGreyBackground);

        layerContainer.setSpacing(8);
        editVBox.setSpacing(10);
        editVBox.setPadding(new Insets(10, 20, 10, 20));
        editVBox.getChildren().addAll(newShape, layerTitlePane, layers);

        layers.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        layers.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        layers.setStyle("-fx-font-size: 15px");
        layers.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.15).subtract(20));
        layers.maxWidthProperty().bind(mainWindow.widthProperty().multiply(0.15).subtract(20));
        layers.minHeightProperty().bind(mainWindow.heightProperty().subtract(menuBar.heightProperty().add(300)));
        layers.maxHeightProperty().bind(mainWindow.heightProperty().subtract(menuBar.heightProperty().add(300)));
        layers.setContent(layerContainer);
        layerContainer.minWidthProperty().bind(layers.minWidthProperty());
        layerContainer.maxWidthProperty().bind(layers.maxWidthProperty());
        layerContainer.minHeightProperty().bind(layers.minHeightProperty());

        // Files
        fileContainer.setBackground(darkGreyBackground);
        files.setBackground(darkGreyBackground);

        fileContainer.setSpacing(10);
        fileVBox.setSpacing(10);
        fileVBox.getChildren().addAll(files);
        fileVBox.setPadding(new Insets(10, 0, 0, 0));

        files.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        files.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        files.setStyle("-fx-font-size: 15px");
        files.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.15));
        files.maxWidthProperty().bind(mainWindow.widthProperty().multiply(0.15));
        files.minHeightProperty().bind(mainWindow.heightProperty().subtract(menuBar.heightProperty()));
        files.maxHeightProperty().bind(mainWindow.heightProperty().subtract(menuBar.heightProperty()));
        files.setContent(fileContainer);
        fileContainer.minWidthProperty().bind(files.minWidthProperty());
        fileContainer.maxWidthProperty().bind(files.maxWidthProperty());
        fileContainer.minHeightProperty().bind(files.minHeightProperty());

        // stage & scene edits
        Scene primaryScene = new Scene(mainWindow, 1280, 720);
        primaryScene.setFill(midGrey);


        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Algorithmic Art");
        primaryStage.getIcons().add(new Image("/icon.png"));

        primaryStage.setMinWidth(825);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    private void comboBoxSelected() {
        System.out.println("Selected " + newShape.getValue());
        if (newShape.getValue().equals("Line")) {
            AddNewLineAlgorithm();
        }

        createNewShapeComboBox();
    }

    private void createNewShapeComboBox() {
        int indexBox = editVBox.getChildren().indexOf(newShape);
        editVBox.getChildren().remove(newShape);

        newShape = new ComboBox<>();

        newShape.setBackground(buttonBackground);
        newShape.setOnMouseEntered(event -> newShape.setBackground(buttonSelectedBackground));
        newShape.setOnMouseExited(event -> {
            if (!newShape.isShowing()) {
                newShape.setBackground(buttonBackground);
            }
        });
        newShape.setOnShowing(event -> newShape.setBackground(buttonSelectedBackground));
        newShape.setOnHiding(event -> newShape.setBackground(buttonBackground));


        newShape.getItems().addAll("Line");
        newShape.setPromptText("Add Shape");

        newShape.setOnAction(event -> comboBoxSelected());

        editVBox.getChildren().add(indexBox, newShape);
    }

    // triggers the window to add a new line
    private void AddNewLineAlgorithm() {
        LineWindow lineWindow = new LineWindow(layerPanes, layerContainer, viewer, this);

        Stage newLineWindow = lineWindow.getStage();
        GridPane pane = lineWindow.getPane();

        Scene mainWindow = new Scene(pane, 600, 400);
        newLineWindow.setScene(mainWindow);
        newLineWindow.setTitle("Algorithmic Art - Add New Line");
        newLineWindow.initModality(Modality.APPLICATION_MODAL);
        newLineWindow.show();
    }

    public void updateLineAlgorithm(OrderedPane orderedPane) {
        LineWindow lineWindow = new LineWindow(layerPanes, layerContainer, viewer, orderedPane, this);

        Stage newLineWindow = lineWindow.getStage();
        GridPane pane = lineWindow.getPane();

        Scene mainWindow = new Scene(pane, 600, 400);
        newLineWindow.setScene(mainWindow);
        newLineWindow.setTitle("Algorithmic Art - Update Line (" + orderedPane.getAlgorithmicShape().getName() + ")");
        newLineWindow.initModality(Modality.APPLICATION_MODAL);
        newLineWindow.show();
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}