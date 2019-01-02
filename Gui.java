import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Gui for algorithmic art application
 *
 * @author Charles Vidro
 */
public class Gui extends Application {
    // GUI Variables
    private BorderPane mainWindow = new BorderPane();
    private StackPane viewer = new StackPane();
    private HBox MenuBar = new HBox();
    private Scene primaryScene;
    private Stage primaryStage;
    private Text ArtTitle = new Text("unnamed");
    private ScrollPane layers = new ScrollPane();
    private HBox layersAndFiles = new HBox();
    private VBox layerContainer = new VBox();
    private ScrollPane files = new ScrollPane();
    private VBox shapeAndLayers = new VBox();
    private ComboBox<String> newShape = new ComboBox<>();
    private VBox fileContainer = new VBox();
    private VBox filesTitleandFiles = new VBox();

    // DATA Variables
    private ArrayList<AlgorithmicShape> algorithmicShapes = new ArrayList<>();
    private ArrayList<AlgorithmicShape> shapePreviewReferences = new ArrayList<>();

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

        // Color palette
        Background DarkGreyBackground = new Background(new BackgroundFill(new Color(0.28, 0.28, 0.28,
                1.0), CornerRadii.EMPTY, Insets.EMPTY));

        // setting window spaces
        mainWindow.setLeft(viewer);
        mainWindow.setTop(MenuBar);
        mainWindow.setRight(layersAndFiles);
        layersAndFiles.getChildren().addAll(shapeAndLayers, filesTitleandFiles);
        shapeAndLayers.setSpacing(10);
        shapeAndLayers.setAlignment(Pos.TOP_CENTER);

        // viewer
        viewer.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.7));
        viewer.maxWidthProperty().bind(mainWindow.widthProperty().multiply(0.7));
        viewer.setStyle("-fx-border-color: green;");

        // Menu bar edits
        MenuBar.setAlignment(Pos.CENTER);
        MenuBar.setSpacing(15);
        MenuBar.setBackground(DarkGreyBackground);
        ArtTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        ArtTitle.setFill(Color.WHITE);
        MenuBar.getChildren().add(ArtTitle);
        MenuBar.setMaxHeight(40);
        MenuBar.setPadding(new Insets(10, 10, 10, 10));

        // new shape
        newShape.getItems().addAll("Line");
        newShape.setPromptText("Add Shape");

        newShape.setOnAction(event -> {
            System.out.println("Selected " + newShape.getValue());
            if (newShape.getValue().equals("Line")) {
                createNewLineAlgorithm();
            }
            createNewShapeComboBox();
        });

        // layers
        Text layerTitle = new Text("Layers");
        layerTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        shapeAndLayers.getChildren().addAll(newShape, layerTitle, layers);
        layers.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        layers.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        layers.setStyle("-fx-font-size: 18px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: red;");
        layers.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.15));
        layers.maxWidthProperty().bind(mainWindow.widthProperty().multiply(0.15));
        layers.minHeightProperty().bind(mainWindow.heightProperty().subtract(MenuBar.heightProperty().add(300)));
        layers.setContent(layerContainer);
        layerContainer.setBackground(DarkGreyBackground);
        layerContainer.minWidthProperty().bind(layers.minWidthProperty());
        layerContainer.maxWidthProperty().bind(layers.minWidthProperty());
        layerContainer.minHeightProperty().bind(layers.minHeightProperty().subtract(5));

        // Files
        Text fileTitle = new Text("Files");
        fileTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        filesTitleandFiles.getChildren().addAll(fileTitle, files);
        files.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        files.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        files.setStyle("-fx-font-size: 18px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: red;");
        files.minWidthProperty().bind(mainWindow.widthProperty().multiply(0.15));
        files.maxWidthProperty().bind(mainWindow.widthProperty().multiply(0.15));
        files.setContent(fileContainer);


        // stage & scene edits
        this.primaryStage = primaryStage;
        primaryScene = new Scene(mainWindow, 1280, 720);
        primaryScene.setFill(Color.GRAY);

        this.primaryStage.setScene(primaryScene);
        this.primaryStage.setTitle("Algorithmic Art");

        this.primaryStage.setMinWidth(700);
        this.primaryStage.show();
    }

    private void createNewShapeComboBox() {
        shapeAndLayers.getChildren().remove(newShape);

        newShape = new ComboBox<>();

        newShape.getItems().addAll("Line");
        newShape.setPromptText("Add Shape");

        newShape.setOnAction(event -> {
            System.out.println("Selected " + newShape.getValue());
            if (newShape.getValue().equals("Line")) {
                createNewLineAlgorithm();
            }
            createNewShapeComboBox();
        });

        shapeAndLayers.getChildren().add(0, newShape);
    }

    private void createNewLineAlgorithm() {
        Stage newLineWindow = new Stage();

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Text lineTitle = new Text("New Line");
        lineTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        pane.add(lineTitle, 0, 0);

        Font labelFont = Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16);

        Label startXLabel = new Label("Start X");
        startXLabel.setFont(labelFont);
        TextField startX = new TextField();
        startX.setPrefWidth(70);
        pane.add(startXLabel, 0, 1);
        pane.add(startX, 1, 1);

        pane.add(getNewShapeSpacer(), 2, 1);

        Label startYLabel = new Label("Start Y");
        startYLabel.setFont(labelFont);
        TextField startY = new TextField();
        startY.setPrefWidth(70);
        pane.add(startYLabel, 3, 1);
        pane.add(startY, 4, 1);

        Label endXLabel = new Label("End X");
        endXLabel.setFont(labelFont);
        TextField endX = new TextField();
        endX.setPrefWidth(70);
        pane.add(endXLabel, 0, 2);
        pane.add(endX, 1, 2);

        pane.add(getNewShapeSpacer(), 2, 2);

        Label endYLabel = new Label("End Y");
        endYLabel.setFont(labelFont);
        TextField endY = new TextField();
        endY.setPrefWidth(70);
        pane.add(endYLabel, 3, 2);
        pane.add(endY, 4, 2);

        Label startChangeInXLabel = new Label("Start Change in X");
        startChangeInXLabel.setFont(labelFont);
        TextField startChangeInX = new TextField();
        startChangeInX.setPrefWidth(70);
        pane.add(startChangeInXLabel, 0, 3);
        pane.add(startChangeInX, 1, 3);

        pane.add(getNewShapeSpacer(), 2, 3);

        Label startChangeInYLabel = new Label("Start Change In Y");
        startChangeInYLabel.setFont(labelFont);
        TextField startChangeInY = new TextField();
        startChangeInY.setPrefWidth(70);
        pane.add(startChangeInYLabel, 3, 3);
        pane.add(startChangeInY, 4, 3);

        Label endChangeInXLabel = new Label("End Change in X");
        endChangeInXLabel.setFont(labelFont);
        TextField endChangeInX = new TextField();
        endChangeInX.setPrefWidth(70);
        pane.add(endChangeInXLabel, 0, 4);
        pane.add(endChangeInX, 1, 4);

        pane.add(getNewShapeSpacer(), 2, 4);

        Label endChangeInYLabel = new Label("End Change In Y");
        endChangeInYLabel.setFont(labelFont);
        TextField endChangeInY = new TextField();
        endChangeInY.setPrefWidth(70);
        pane.add(endChangeInYLabel, 3, 4);
        pane.add(endChangeInY, 4, 4);

        pane.add(getNewShapeSpacer(), 0, 5);

        Label iterationsLabel = new Label("Iterations");
        iterationsLabel.setFont(labelFont);
        TextField iterations = new TextField();
        iterations.setPrefWidth(70);
        pane.add(iterationsLabel, 0, 6);
        pane.add(iterations, 1, 6);

        pane.add(getNewShapeSpacer(), 0, 7);

        Label lineNameLabel = new Label("Line Name");
        lineNameLabel.setFont(labelFont);
        TextField lineName = new TextField();
        lineName.setPrefWidth(100);
        pane.add(lineNameLabel, 0, 8);
        pane.add(lineName, 1, 8);

        pane.add(getNewShapeSpacer(), 0, 9);

        // preview references (one preview new shape window at a time. . .)
        AlgorithmicLine[] previousPreview = {null};
        Pane[] previousPreviewPane = {null};
        Text[] previousThisLineName = {null};

        Button preview = new Button("Preview");
        preview.setOnAction(event -> {
            if (isValidLine(startX.getText(), startY.getText(), endX.getText(), endY.getText(), startChangeInX.getText(),
                    startChangeInY.getText(), endChangeInX.getText(), endChangeInY.getText(), iterations.getText(), lineName.getText())) {

                AlgorithmicLine line = new AlgorithmicLine(
                        Integer.parseInt(startX.getText()),
                        Integer.parseInt(startY.getText()),
                        Integer.parseInt(endX.getText()),
                        Integer.parseInt(endY.getText()),
                        Integer.parseInt(startChangeInX.getText()),
                        Integer.parseInt(startChangeInY.getText()),
                        Integer.parseInt(endChangeInX.getText()),
                        Integer.parseInt(endChangeInY.getText()),
                        Integer.parseInt(iterations.getText()),
                        lineName.getText());

                if (previousPreview[0] == null) {
                    // brand new preview
                    previousPreview[0] = line;
                    algorithmicShapes.add(line);
                    shapePreviewReferences.add(line);

                    previousThisLineName[0] = new Text(line.getName());
                    previousThisLineName[0].setFill(Color.WHITE);
                    previousThisLineName[0].setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
                    layerContainer.getChildren().add(previousThisLineName[0]);

                    previousPreviewPane[0] = line.draw(viewer);
                    System.out.println("Line " + line.getName() + " previewed!");
                } else {
                    // remove old references
                    viewer.getChildren().remove(previousPreviewPane[0]);
                    shapePreviewReferences.remove(previousPreview[0]);
                    algorithmicShapes.remove(previousPreview[0]);

                    // add new references
                    previousPreview[0] = line;
                    algorithmicShapes.add(line);
                    shapePreviewReferences.add(line);

                    // update text name
                    previousThisLineName[0].setText(line.getName());

                    previousPreviewPane[0] = line.draw(viewer);
                    System.out.println("Line " + line.getName() + " previewed!");
                }
            }
        });
        pane.add(preview, 0, 10);

        Button save = new Button("Save");
        save.setOnAction(event -> {
            // situation 1: user saved with incorrect parameters
            // situation 2: correct parameters, same name as another shape (not the one previewed)
            // situation 3: correct parameters, matches another line (not the one previewed)
            // situation 4: user previewed, then saved with no change
            // situation 5: user previewed, made a change, then saved
            // situation 6: user saved with no preview

            if (isValidLine(startX.getText(), startY.getText(), endX.getText(), endY.getText(), startChangeInX.getText(),
                    startChangeInY.getText(), endChangeInX.getText(), endChangeInY.getText(), iterations.getText(), lineName.getText())) {

                AlgorithmicLine line = new AlgorithmicLine(
                        Integer.parseInt(startX.getText()),
                        Integer.parseInt(startY.getText()),
                        Integer.parseInt(endX.getText()),
                        Integer.parseInt(endY.getText()),
                        Integer.parseInt(startChangeInX.getText()),
                        Integer.parseInt(startChangeInY.getText()),
                        Integer.parseInt(endChangeInX.getText()),
                        Integer.parseInt(endChangeInY.getText()),
                        Integer.parseInt(iterations.getText()),
                        lineName.getText());

                // check if the name of this shape is already stored or it is the same as another line (excluding the preview)
                for (AlgorithmicShape shape : algorithmicShapes) {
                    // name is already stored
                    if (shape != previousPreview[0] && shape.getName().equals(lineName.getText())) {
                        // do NOT save, exit the method
                        System.out.println("Another shape named " + lineName.getText() + " exists!");
                        return;
                    }
                    // Does this new line match the parameters of another line (duplicate line)?
                    try {
                        AlgorithmicLine comparisonLine = (AlgorithmicLine) shape;

                        // make sure the preview is not tested
                        if (shape != previousPreview[0]) {
                            // test if ALL parameters are the same
                            if (line.getStartX() == comparisonLine.getStartX() && line.getStartY() == comparisonLine.getStartY()
                                    && line.getEndX() == comparisonLine.getEndX() && line.getEndY() == comparisonLine.getEndY()
                                    && line.getStartDx() == comparisonLine.getStartDx() && line.getStartDy() == comparisonLine.getStartDy()
                                    && line.getEndDx() == comparisonLine.getEndDx() && line.getEndDy() == comparisonLine.getEndDy()
                                    && line.getIterations() == comparisonLine.getIterations()) {
                                System.out.println("Another line with these parameters exists!");
                                return;
                            }
                        }
                    } catch (ClassCastException e) {
                        // go on to the next object
                    }
                }

                // user did not preview
                if (previousPreview[0] == null) {
                    // this is a new line
                    algorithmicShapes.add(line);
                    Text thisLineName = new Text(line.getName());
                    thisLineName.setFill(Color.WHITE);
                    thisLineName.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
                    layerContainer.getChildren().add(thisLineName);
                }
                // preview has the exact same contents as the to-save line
                else if (line.getStartX() == previousPreview[0].getStartX() && line.getStartY() == previousPreview[0].getStartY()
                        && line.getEndX() == previousPreview[0].getEndX() && line.getEndY() == previousPreview[0].getEndY()
                        && line.getStartDx() == previousPreview[0].getStartDx() && line.getStartDy() == previousPreview[0].getStartDy()
                        && line.getEndDx() == previousPreview[0].getEndDx() && line.getEndDy() == previousPreview[0].getEndDy()
                        && line.getIterations() == previousPreview[0].getIterations() && line.getName().equals(previousPreview[0].getName())) {
                    // do nothing exit
                }
                // preview is different then the to-save line
                else {
                    // remove old references
                    viewer.getChildren().remove(previousPreviewPane[0]);
                    algorithmicShapes.remove(previousPreview[0]);

                    // add new references
                    algorithmicShapes.add(line);

                    // update text name
                    previousThisLineName[0].setText(line.getName());
                }

                line.draw(viewer);
                System.out.println("Line " + line.getName() + " saved!");

                // reset references, close window
                shapePreviewReferences.remove(previousPreview[0]);
                previousPreview[0] = null;
                previousPreviewPane[0] = null;
                previousThisLineName[0] = null;
                newLineWindow.close();
            }
        });
        pane.add(save, 2, 10);

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            if (previousPreview[0] != null) {
                viewer.getChildren().remove(previousPreviewPane[0]);
                shapePreviewReferences.remove(previousPreview[0]);
                algorithmicShapes.remove(previousPreview[0]);
                layerContainer.getChildren().remove(previousThisLineName[0]);
                previousPreview[0] = null;
                previousPreviewPane[0] = null;
                previousThisLineName[0] = null;
                previousThisLineName[0] = null;
            }

            newLineWindow.close();
        });
        pane.add(cancel, 4, 10);

        Scene mainWindow = new Scene(pane, 600, 400);
        newLineWindow.setScene(mainWindow);
        newLineWindow.setTitle("Algorithmic Art - Add New Line");
        newLineWindow.show();
    }

    private Boolean isValidLine(String startX, String startY, String endX, String endY, String startChangeInX,
                                String startChangeInY, String endChangeInX, String endChangeInY, String iterations,
                                String lineName) {

        if (startX != null && isNumeric(startX)
                && startY != null && isNumeric(startY)
                && endX != null && isNumeric(endX)
                && endY != null && isNumeric(endY)
                && startChangeInX != null && isNumeric(startChangeInX)
                && startChangeInY != null && isNumeric(startChangeInY)
                && endChangeInX != null && isNumeric(endChangeInX)
                && endChangeInY != null && isNumeric(endChangeInY)
                && iterations != null && isNumeric(iterations)
                && lineName != null) {
            return true;
        }

        System.out.println("This line does not have valid parameters!");
        return false;
    }

    private Region getNewShapeSpacer() {
        Region spacer = new Region();
        spacer.setMinWidth(25);
        spacer.setMaxWidth(25);
        spacer.setMaxHeight(15);
        spacer.setMinHeight(15);
        return spacer;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}