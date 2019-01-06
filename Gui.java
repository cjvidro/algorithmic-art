import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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

    // DATA Variables
    private ArrayList<AlgorithmicShape> algorithmicShapes = new ArrayList<>();
    private ArrayList<Text> layerTextList = new ArrayList<>();
    private ArrayList<Pane> layerPanes = new ArrayList<>();

    // Color palette
    private final Color darkGrey = Color.hsb(0,0,0.16);
    private final Background darkGreyBackground = new Background(new BackgroundFill(darkGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color midGrey = Color.hsb(0,0,0.25);
    private final Background midGreyBackground = new Background(new BackgroundFill(midGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color lightGrey = Color.hsb(0,0,0.39);
    private final Background lightGreyBackground = new Background(new BackgroundFill(lightGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background buttonBackground = new Background(new BackgroundFill(Color.hsb(  240,0.01,0.68), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background buttonSelectedBackground = new Background(new BackgroundFill(Color.hsb(  180,0.09,0.86), CornerRadii.EMPTY, Insets.EMPTY));

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
        viewer.setStyle("-fx-border-color: black; -fx-border-width: 1");

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
        Text fileText = new Text("File");
        fileText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        fileText.setFill(Color.WHITE);
        filePane.getChildren().add(fileText);

        menuBar.getChildren().addAll(artTitlePane, editPane, filePane);

        // Edit Controls
        // guides
//        Label guidesLabel = new Label("Guides");
//        guidesLabel.setTextFill(Color.WHITE);
//        guidesLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
//        guidesLabel.setOnMouseClicked(event -> addGuides());

        HBox guides = new HBox();
        guides.setAlignment(Pos.CENTER);
        guides.setSpacing(2);
        guides.minWidthProperty().bind(layerContainer.widthProperty());

        Button center = new Button("Center");
        center.setBackground(buttonBackground);
        center.setOnMouseEntered(event -> center.setBackground(buttonSelectedBackground));
        center.setOnMouseExited(event -> center.setBackground(buttonBackground));
        center.setOnAction(event -> {
            System.out.println("center selected");
        });

        Button grid = new Button("Grid");
        grid.setBackground(buttonBackground);
        grid.setOnMouseEntered(event -> grid.setBackground(buttonSelectedBackground));
        grid.setOnMouseExited(event -> grid.setBackground(buttonBackground));
        grid.setOnAction(event -> {
            System.out.println("grid selected");
        });

        guides.getChildren().addAll(center, grid);

        // rotate
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
        TextField zoom = new TextField("0.0");
        zoom.setPromptText("0.0");
        zoom.setMaxWidth(75);
        zoom.setBackground(buttonSelectedBackground);
        zoom.setOnAction(event -> {
            if (isNumeric(zoom.getText())) {
                Double zoomNumber = Double.parseDouble(zoom.getText()) / 10;
                viewer.setScaleX(zoomNumber);
                viewer.setScaleY(zoomNumber);
            }
        });

        // Background Color (slider)
        // Grid lines
        // Center Crosshair
        editVBox.getChildren().addAll(guides, rotate, zoom);

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
        Text layerTitle = new Text("Layers");
        layerTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));

        layerTitle.setFill(Color.WHITE);
        layerContainer.setBackground(darkGreyBackground);
        layers.setBackground(darkGreyBackground);

        layerContainer.setSpacing(10);
        editVBox.setSpacing(10);
        editVBox.setPadding(new Insets(10, 20, 10, 20));
        editVBox.getChildren().addAll(newShape, layerTitle, layers);

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
            createNewLineAlgorithm();
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

    private void createNewLineAlgorithm() {
        Object[] stageAndPane = getLineStage();
        Stage newLineWindow = (Stage) stageAndPane[0];
        GridPane pane = (GridPane) stageAndPane[1];

        Scene mainWindow = new Scene(pane, 600, 400);
        newLineWindow.setScene(mainWindow);
        newLineWindow.setTitle("Algorithmic Art - Add New Line");
        newLineWindow.initModality(Modality.APPLICATION_MODAL);
        newLineWindow.show();
    }

    private void updateLineAlgorithm(String lineName) {
        // Object[] lineStageElements = {newLineWindow, pane,  startX, startY, endX, endY, startChangeInX,
        //                                 0              1       2       3      4     5         6
        //
        // startChangeInY, endChangeInX, endChangeInY, iterations, lineName, preview, save, cancel
        //       7              8             9            10         11        12     13     14
        //
        // previousPreview, previousPreviewPane, previousThisLineName};
        //       15                  16                17


        Object[] lineStageElements = getLineStage();
        Stage updateLineWindow = (Stage) lineStageElements[0];
        GridPane pane = (GridPane) lineStageElements[1];
        TextField startX = (TextField) lineStageElements[2];
        TextField startY = (TextField) lineStageElements[3];
        TextField endX = (TextField) lineStageElements[4];
        TextField endY = (TextField) lineStageElements[5];
        TextField startChangeInX = (TextField) lineStageElements[6];
        TextField startChangeInY = (TextField) lineStageElements[7];
        TextField endChangeInX = (TextField) lineStageElements[8];
        TextField endChangeInY = (TextField) lineStageElements[9];
        TextField iterations = (TextField) lineStageElements[10];
        TextField thisLineName = (TextField) lineStageElements[11];
        Button preview = (Button) lineStageElements[12];
        Button save = (Button) lineStageElements[13];
        Button cancel = (Button) lineStageElements[14];
        //AlgorithmicLine[] previousPreview = (AlgorithmicLine[]) lineStageElements[15];
        Pane[] previousPreviewPane = (Pane[]) lineStageElements[16];
        //Text[] previousThisLineName = (Text[]) lineStageElements[17];

        // old data
        AlgorithmicLine[] outdatedLine = {null};
        Text[] outdatedText = {null};


        for (AlgorithmicShape shape : algorithmicShapes) {
            if (shape.getName().equals(lineName)) {
                outdatedLine[0] = (AlgorithmicLine) shape;
                break;
            }
        }

        if (outdatedLine[0] == null) {
            throw new RuntimeException("No Previous AlgorithmicLine Found!");
        }

        for (Text text : layerTextList) {
            if (text.getText().equals(lineName)) {
                outdatedText[0] = text;
                break;
            }
        }

        if (outdatedText[0] == null) {
            throw new RuntimeException("No Previous layer name Found!");
        }

        // Set prompt Text in window
        startX.setPromptText(outdatedLine[0].getStartX() + "");
        startY.setPromptText(outdatedLine[0].getStartY() + "");
        endX.setPromptText(outdatedLine[0].getEndX() + "");
        endY.setPromptText(outdatedLine[0].getEndY() + "");
        startChangeInX.setPromptText(outdatedLine[0].getStartDx() + "");
        startChangeInY.setPromptText(outdatedLine[0].getStartDy() + "");
        endChangeInX.setPromptText(outdatedLine[0].getEndDx() + "");
        endChangeInY.setPromptText(outdatedLine[0].getEndDy() + "");
        iterations.setPromptText(outdatedLine[0].getIterations() + "");
        thisLineName.setPromptText(outdatedLine[0].getName() + "");

        // set actual text in window
        startX.setText(outdatedLine[0].getStartX() + "");
        startY.setText(outdatedLine[0].getStartY() + "");
        endX.setText(outdatedLine[0].getEndX() + "");
        endY.setText(outdatedLine[0].getEndY() + "");
        startChangeInX.setText(outdatedLine[0].getStartDx() + "");
        startChangeInY.setText(outdatedLine[0].getStartDy() + "");
        endChangeInX.setText(outdatedLine[0].getEndDx() + "");
        endChangeInY.setText(outdatedLine[0].getEndDy() + "");
        iterations.setText(outdatedLine[0].getIterations() + "");
        thisLineName.setText(outdatedLine[0].getName() + "");

        // new data
        AlgorithmicLine adjustedLine = new AlgorithmicLine(
                outdatedLine[0].getStartX(),
                -outdatedLine[0].getStartY(),
                outdatedLine[0].getEndX(),
                -outdatedLine[0].getEndY(),
                outdatedLine[0].getStartDx(),
                -outdatedLine[0].getStartDy(),
                outdatedLine[0].getEndDx(),
                -outdatedLine[0].getEndDy(),
                outdatedLine[0].getIterations(),
                outdatedLine[0].getName());

        Text adjustedText = new Text(outdatedText[0].getText());
        adjustedText.setFill(Color.WHITE);
        adjustedText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        adjustedText.setOnMouseClicked(event1 -> {
            if (event1.getButton().equals(MouseButton.PRIMARY) && event1.getClickCount() == 2) {
                updateLineAlgorithm(adjustedLine.getName());
            }
        });
        Pane[] adjustedPane = {adjustedLine.draw(viewer, layerPanes)};

        // add new data to be displayed and stored in data lists
        algorithmicShapes.add(adjustedLine);
        layerContainer.getChildren().add(adjustedText);
        layerTextList.add(adjustedText);

        // remove old data
        algorithmicShapes.remove(outdatedLine[0]);
        layerContainer.getChildren().remove(outdatedText[0]);
        layerTextList.remove(outdatedText[0]);
        viewer.getChildren().remove(previousPreviewPane[0]);

        int oldIndex = viewer.getChildren().indexOf(outdatedLine);

//        Object[] viewerTest = viewer.getChildren().toArray();
//        System.out.println("\nBefore: ");
//        for (Object o : viewerTest) {
//            System.out.print(o + " ");
//        }

//        System.out.println("\n\nAttempting to remove " + lineStageElements[16].toString());
        viewer.getChildren().remove(oldIndex + 1);

//        viewerTest = viewer.getChildren().toArray();
//        System.out.println("\nAfter: ");
//        for (Object o : viewerTest) {
//            System.out.print((o) + " ");
//        }

        // update buttons
        preview.setOnAction(event -> {
            if (isValidLine(startX.getText(), startY.getText(), endX.getText(), endY.getText(), startChangeInX.getText(),
                    startChangeInY.getText(), endChangeInX.getText(), endChangeInY.getText(), iterations.getText(), thisLineName.getText())) {

                adjustedLine.setStartX(Integer.parseInt(startX.getText()));
                adjustedLine.setStartY(Integer.parseInt(startY.getText()));
                adjustedLine.setEndX(Integer.parseInt(endX.getText()));
                adjustedLine.setEndY(Integer.parseInt(endY.getText()));
                adjustedLine.setStartDx(Integer.parseInt(startChangeInX.getText()));
                adjustedLine.setStartDy(Integer.parseInt(startChangeInY.getText()));
                adjustedLine.setEndDx(Integer.parseInt(endChangeInX.getText()));
                adjustedLine.setEndDy(Integer.parseInt(endChangeInY.getText()));
                adjustedLine.setIterations(Integer.parseInt(iterations.getText()));
                adjustedLine.setName(thisLineName.getText());
                adjustedText.setText(thisLineName.getText());

                viewer.getChildren().remove(adjustedPane[0]);
                adjustedPane[0] = adjustedLine.draw(viewer, layerPanes);
            }
        });

        save.setOnAction(event -> {
            if (isValidLine(startX.getText(), startY.getText(), endX.getText(), endY.getText(), startChangeInX.getText(),
                    startChangeInY.getText(), endChangeInX.getText(), endChangeInY.getText(), iterations.getText(), thisLineName.getText())) {

                adjustedLine.setStartX(Integer.parseInt(startX.getText()));
                adjustedLine.setStartY(Integer.parseInt(startY.getText()));
                adjustedLine.setEndX(Integer.parseInt(endX.getText()));
                adjustedLine.setEndY(Integer.parseInt(endY.getText()));
                adjustedLine.setStartDx(Integer.parseInt(startChangeInX.getText()));
                adjustedLine.setStartDy(Integer.parseInt(startChangeInY.getText()));
                adjustedLine.setEndDx(Integer.parseInt(endChangeInX.getText()));
                adjustedLine.setEndDy(Integer.parseInt(endChangeInY.getText()));
                adjustedLine.setIterations(Integer.parseInt(iterations.getText()));
                adjustedLine.setName(thisLineName.getText());
                adjustedText.setText(thisLineName.getText());

                viewer.getChildren().remove(adjustedPane[0]);
                adjustedPane[0] = adjustedLine.draw(viewer, layerPanes);

                Object[] viewerTest = viewer.getChildren().toArray();
                System.out.println("\nviewer post-save: ");
                for (Object o : viewerTest) {
                    System.out.print(o + " ");
                }

                updateLineWindow.close();
            }
        });

        cancel.setOnAction(event -> {
            // remove any changes
            viewer.getChildren().remove(adjustedPane[0]);
            algorithmicShapes.remove(adjustedLine);
            layerContainer.getChildren().remove(adjustedText);
            layerTextList.remove(adjustedText);

            // restore original function
            algorithmicShapes.add(outdatedLine[0]);
            layerContainer.getChildren().add(outdatedText[0]);
            layerTextList.add(outdatedText[0]);
            outdatedLine[0].draw(viewer, layerPanes);

            updateLineWindow.close();
        });

        // move buttons, add remove button
        pane.getChildren().remove(save);
        pane.add(save, 1, 10);

        Button remove = new Button("Remove");
        remove.setOnAction(event -> {
            // remove any changes
            viewer.getChildren().remove(adjustedPane[0]);
            algorithmicShapes.remove(adjustedLine);
            layerContainer.getChildren().remove(adjustedText);
            layerTextList.remove(adjustedText);

            updateLineWindow.close();
        });
        remove.setBackground(buttonBackground);
        remove.setOnMouseEntered(event -> remove.setBackground(buttonSelectedBackground));
        remove.setOnMouseExited(event -> remove.setBackground(buttonBackground));
        pane.add(remove, 3, 10);

        Scene mainWindow = new Scene(pane, 600, 400);
        updateLineWindow.setScene(mainWindow);
        updateLineWindow.setTitle("Algorithmic Art - Update Line");
        updateLineWindow.initModality(Modality.APPLICATION_MODAL);
        updateLineWindow.show();
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
                && iterations != null && isNumeric(iterations) && Integer.parseInt(iterations) > 0
                && lineName != null && !lineName.equals("")) {
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
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private Object[] getLineStage() {
        Stage newLineWindow = new Stage();

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setBackground(midGreyBackground);
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setContent(pane);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        Text lineTitle = new Text("New Line");
        lineTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        lineTitle.setFill(Color.WHITE);
        pane.add(lineTitle, 0, 0);

        Font labelFont = Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18);

        Label startXLabel = new Label("Start X");
        startXLabel.setFont(labelFont);
        startXLabel.setTextFill(Color.WHITE);
        TextField startX = new TextField();
        startX.setPrefWidth(70);
        pane.add(startXLabel, 0, 1);
        pane.add(startX, 1, 1);

        pane.add(getNewShapeSpacer(), 2, 1);

        Label startYLabel = new Label("Start Y");
        startYLabel.setFont(labelFont);
        startYLabel.setTextFill(Color.WHITE);
        TextField startY = new TextField();
        startY.setPrefWidth(70);
        pane.add(startYLabel, 3, 1);
        pane.add(startY, 4, 1);

        Label endXLabel = new Label("End X");
        endXLabel.setFont(labelFont);
        endXLabel.setTextFill(Color.WHITE);
        TextField endX = new TextField();
        endX.setPrefWidth(70);
        pane.add(endXLabel, 0, 2);
        pane.add(endX, 1, 2);

        pane.add(getNewShapeSpacer(), 2, 2);

        Label endYLabel = new Label("End Y");
        endYLabel.setFont(labelFont);
        endYLabel.setTextFill(Color.WHITE);
        TextField endY = new TextField();
        endY.setPrefWidth(70);
        pane.add(endYLabel, 3, 2);
        pane.add(endY, 4, 2);

        Label startChangeInXLabel = new Label("Start Change in X");
        startChangeInXLabel.setFont(labelFont);
        startChangeInXLabel.setTextFill(Color.WHITE);
        TextField startChangeInX = new TextField();
        startChangeInX.setPrefWidth(70);
        pane.add(startChangeInXLabel, 0, 3);
        pane.add(startChangeInX, 1, 3);

        pane.add(getNewShapeSpacer(), 2, 3);

        Label startChangeInYLabel = new Label("Start Change In Y");
        startChangeInYLabel.setFont(labelFont);
        startChangeInYLabel.setTextFill(Color.WHITE);
        TextField startChangeInY = new TextField();
        startChangeInY.setPrefWidth(70);
        pane.add(startChangeInYLabel, 3, 3);
        pane.add(startChangeInY, 4, 3);

        Label endChangeInXLabel = new Label("End Change in X");
        endChangeInXLabel.setFont(labelFont);
        endChangeInXLabel.setTextFill(Color.WHITE);
        TextField endChangeInX = new TextField();
        endChangeInX.setPrefWidth(70);
        pane.add(endChangeInXLabel, 0, 4);
        pane.add(endChangeInX, 1, 4);

        pane.add(getNewShapeSpacer(), 2, 4);

        Label endChangeInYLabel = new Label("End Change In Y");
        endChangeInYLabel.setFont(labelFont);
        endChangeInYLabel.setTextFill(Color.WHITE);
        TextField endChangeInY = new TextField();
        endChangeInY.setPrefWidth(70);
        pane.add(endChangeInYLabel, 3, 4);
        pane.add(endChangeInY, 4, 4);

        pane.add(getNewShapeSpacer(), 0, 5);

        Label iterationsLabel = new Label("Iterations");
        iterationsLabel.setFont(labelFont);
        iterationsLabel.setTextFill(Color.WHITE);
        TextField iterations = new TextField();
        iterations.setPrefWidth(70);
        pane.add(iterationsLabel, 0, 6);
        pane.add(iterations, 1, 6);

        pane.add(getNewShapeSpacer(), 0, 7);

        Label lineNameLabel = new Label("Line Name");
        lineNameLabel.setFont(labelFont);
        lineNameLabel.setTextFill(Color.WHITE);
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

                if (previousPreviewPane[0] != null) {
//                    System.out.println("\nAttempting to remove " + previousPreviewPane[0].toString());
                    viewer.getChildren().remove(previousPreviewPane[0]);
                }

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

                    previousThisLineName[0] = new Text(line.getName());
                    previousThisLineName[0].setFill(Color.WHITE);
                    previousThisLineName[0].setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
                    previousThisLineName[0].setOnMouseClicked(event1 -> {
                        if (event1.getButton().equals(MouseButton.PRIMARY) && event1.getClickCount() == 2) {
                            updateLineAlgorithm(line.getName());
                        }
                    });
                    layerContainer.getChildren().add(previousThisLineName[0]);
                    layerTextList.add(previousThisLineName[0]);

                    previousPreviewPane[0] = line.draw(viewer, layerPanes);
                    System.out.println("Line " + line.getName() + " previewed!");
                } else {
                    // remove old references
                    viewer.getChildren().remove(previousPreviewPane[0]);
                    layerPanes.remove(previousPreviewPane[0]);
                    algorithmicShapes.remove(previousPreview[0]);

                    // add new references
                    previousPreview[0] = line;
                    algorithmicShapes.add(line);

                    // update text name
                    previousThisLineName[0].setText(line.getName());

                    previousPreviewPane[0] = line.draw(viewer, layerPanes);
                    System.out.println("Line " + line.getName() + " previewed!");
                }
            }
        });
        preview.setBackground(buttonBackground);
        preview.setOnMouseEntered(event -> preview.setBackground(buttonSelectedBackground));
        preview.setOnMouseExited(event -> preview.setBackground(buttonBackground));
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

                if (previousPreviewPane[0] != null) {
//                    System.out.println("\nAttempting to remove " + previousPreviewPane[0].toString());
                    viewer.getChildren().remove(previousPreviewPane[0]);
                    layerPanes.remove(previousPreviewPane[0]);
                }

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
                    thisLineName.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
                    thisLineName.setOnMouseClicked(event1 -> {
                        if (event1.getButton().equals(MouseButton.PRIMARY) && event1.getClickCount() == 2) {
                            updateLineAlgorithm(line.getName());
                        }
                    });
                    layerContainer.getChildren().add(thisLineName);
                    layerTextList.add(thisLineName);
                }
                // preview has the exact same contents as the to-save line
                else if (line.getStartX() == previousPreview[0].getStartX() && line.getStartY() == previousPreview[0].getStartY()
                        && line.getEndX() == previousPreview[0].getEndX() && line.getEndY() == previousPreview[0].getEndY()
                        && line.getStartDx() == previousPreview[0].getStartDx() && line.getStartDy() == previousPreview[0].getStartDy()
                        && line.getEndDx() == previousPreview[0].getEndDx() && line.getEndDy() == previousPreview[0].getEndDy()
                        && line.getIterations() == previousPreview[0].getIterations() && line.getName().equals(previousPreview[0].getName())) {
                    // do nothing
                }
                // preview is different then the to-save line
                else {
                    // remove old references
                    viewer.getChildren().remove(previousPreviewPane[0]);
                    layerPanes.remove(previousPreviewPane[0]);
                    algorithmicShapes.remove(previousPreview[0]);

                    // add new references
                    algorithmicShapes.add(line);

                    // update text name
                    previousThisLineName[0].setText(line.getName());
                }

                previousPreviewPane[0] = line.draw(viewer, layerPanes);
                System.out.println("Line " + line.getName() + " saved!");

//                // reset references, close window
//                previousPreview[0] = null;
//                previousPreviewPane[0] = null;
//                previousThisLineName[0] = null;

                Object[] viewerTest = viewer.getChildren().toArray();
                System.out.println("\nviewer post-save: ");
                for (Object o : viewerTest) {
                    System.out.print(o + " ");
                }

                newLineWindow.close();
            }
        });
        save.setBackground(buttonBackground);
        save.setOnMouseEntered(event -> save.setBackground(buttonSelectedBackground));
        save.setOnMouseExited(event -> save.setBackground(buttonBackground));
        pane.add(save, 2, 10);

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            if (previousPreview[0] != null) {
                viewer.getChildren().remove(previousPreviewPane[0]);
                layerPanes.remove(previousPreviewPane[0]);
                algorithmicShapes.remove(previousPreview[0]);
                layerContainer.getChildren().remove(previousThisLineName[0]);
                layerTextList.remove(previousThisLineName[0]);
                previousPreview[0] = null;
                previousPreviewPane[0] = null;
                previousThisLineName[0] = null;
            }

            newLineWindow.close();
        });
        cancel.setBackground(buttonBackground);
        cancel.setOnMouseEntered(event -> cancel.setBackground(buttonSelectedBackground));
        cancel.setOnMouseExited(event -> cancel.setBackground(buttonBackground));
        pane.add(cancel, 4, 10);

        return new Object[]{newLineWindow, pane, startX, startY, endX, endY, startChangeInX, startChangeInY,
                endChangeInX, endChangeInY, iterations, lineName, preview, save, cancel, previousPreview,
                previousPreviewPane, previousThisLineName};
    }

}