import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LineWindow {

    // Color palette
    private final Color darkGrey = Color.hsb(0, 0, 0.16);
    private final Background darkGreyBackground = new Background(new BackgroundFill(darkGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color midGrey = Color.hsb(0, 0, 0.25);
    private final Background midGreyBackground = new Background(new BackgroundFill(midGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color lightGrey = Color.hsb(0, 0, 0.39);
    private final Background lightGreyBackground = new Background(new BackgroundFill(lightGrey, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background buttonBackground = new Background(new BackgroundFill(Color.hsb(240, 0.01, 0.68), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background buttonSelectedBackground = new Background(new BackgroundFill(Color.hsb(180, 0.09, 0.86), CornerRadii.EMPTY, Insets.EMPTY));

    // Line Window variables
    private Stage newLineWindow;
    private GridPane pane;
    private Label startXLabel = new Label("Start X");
    private Label startYLabel = new Label("Start Y");
    private Label endXLabel = new Label("End X");
    private Label endYLabel = new Label("End Y");
    private Label startChangeInXLabel = new Label("Start Change in X");
    private Label startChangeInYLabel = new Label("Start Change In Y");
    private  Label endChangeInXLabel = new Label("End Change in X");
    private  Label endChangeInYLabel = new Label("End Change In Y");
    private  Label iterationsLabel = new Label("Iterations");
    private Label lineNameLabel = new Label("Line Name");
    private TextField startX = new TextField();
    private TextField startY = new TextField();
    private TextField endX = new TextField();
    private TextField endY = new TextField();
    private TextField startChangeInX = new TextField();
    private TextField startChangeInY = new TextField();
    private TextField endChangeInX = new TextField();
    private TextField endChangeInY = new TextField();
    private TextField iterations = new TextField();
    private TextField lineName = new TextField();

    // Data and Viewing variables
    private ArrayList<OrderedPane> layers;      // contains all layerPanes in order
    private VBox layerContainer;                // this where all of the layer names are displayed to the user
    private StackPane viewer;                   // the primary VIEWER
    private static Gui gui;

    // temporary variables
    OrderedPane previousPreviewLayer = null;
    OrderedPane originalLayer = null;

    // sets up the entire window for creating NEW LINES (not updating an existing line)
    public LineWindow(ArrayList<OrderedPane> layers, VBox layerContainer, StackPane viewer, Gui gui) {
        // create pane and Gridpane
        newLineWindow = new Stage();
        pane = new GridPane();
        this.gui = gui;

        // save data and viewing variables
        this.layers = layers;
        this.layerContainer = layerContainer;
        this.viewer = viewer;

        // setup elements in the pane
        setupPane();

        // setup buttons
        setupPreview();
        setupSave();
        setupCancel();
    }

    // sets up the entire window for UPDATING existing lines
    public LineWindow(ArrayList<OrderedPane> layers, VBox layerContainer, StackPane viewer, OrderedPane originalLayer, Gui gui) {
        // create pane and Gridpane
        newLineWindow = new Stage();
        pane = new GridPane();

        // save data and viewing variables
        this.layers = layers;
        this.layerContainer = layerContainer;
        this.viewer = viewer;
        this.originalLayer = originalLayer;
        this.previousPreviewLayer = originalLayer;
        this.gui = gui;

        // setup elements in the pane
        setupPane();

        // setup buttons
        setupPreview();
        setupSave();
        setupCancel();

        // update the textfields with new data
        updateTextfields();
    }

    private Region getNewShapeSpacer() {
        Region spacer = new Region();
        spacer.setMinWidth(25);
        spacer.setMaxWidth(25);
        spacer.setMaxHeight(15);
        spacer.setMinHeight(15);
        return spacer;
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

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void setupPane() {
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setBackground(midGreyBackground);

        Text lineTitle = new Text("New Line");
        lineTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        lineTitle.setFill(Color.WHITE);
        pane.add(lineTitle, 0, 0);

        Font labelFont = Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18);

        // setup startX
        startXLabel.setFont(labelFont);
        startXLabel.setTextFill(Color.WHITE);
        startX.setPrefWidth(70);
        pane.add(startXLabel, 0, 1);
        pane.add(startX, 1, 1);

        pane.add(getNewShapeSpacer(), 2, 1);

        // setup startY
        startYLabel.setFont(labelFont);
        startYLabel.setTextFill(Color.WHITE);
        startY.setPrefWidth(70);
        pane.add(startYLabel, 3, 1);
        pane.add(startY, 4, 1);

        // setup endX
        endXLabel.setFont(labelFont);
        endXLabel.setTextFill(Color.WHITE);
        endX.setPrefWidth(70);
        pane.add(endXLabel, 0, 2);
        pane.add(endX, 1, 2);

        pane.add(getNewShapeSpacer(), 2, 2);

        // setup endY
        endYLabel.setFont(labelFont);
        endYLabel.setTextFill(Color.WHITE);
        endY.setPrefWidth(70);
        pane.add(endYLabel, 3, 2);
        pane.add(endY, 4, 2);

        // setup startChangeInX
        startChangeInXLabel.setFont(labelFont);
        startChangeInXLabel.setTextFill(Color.WHITE);
        startChangeInX.setPrefWidth(70);
        pane.add(startChangeInXLabel, 0, 3);
        pane.add(startChangeInX, 1, 3);

        pane.add(getNewShapeSpacer(), 2, 3);

        // setup startChangeInY
        startChangeInYLabel.setFont(labelFont);
        startChangeInYLabel.setTextFill(Color.WHITE);
        startChangeInY.setPrefWidth(70);
        pane.add(startChangeInYLabel, 3, 3);
        pane.add(startChangeInY, 4, 3);

        // setup endChangeInX
        endChangeInXLabel.setFont(labelFont);
        endChangeInXLabel.setTextFill(Color.WHITE);
        endChangeInX.setPrefWidth(70);
        pane.add(endChangeInXLabel, 0, 4);
        pane.add(endChangeInX, 1, 4);

        pane.add(getNewShapeSpacer(), 2, 4);

        //setup endChangeInY
        endChangeInYLabel.setFont(labelFont);
        endChangeInYLabel.setTextFill(Color.WHITE);
        endChangeInY.setPrefWidth(70);
        pane.add(endChangeInYLabel, 3, 4);
        pane.add(endChangeInY, 4, 4);

        pane.add(getNewShapeSpacer(), 0, 5);

        // setup iterations
        iterationsLabel.setFont(labelFont);
        iterationsLabel.setTextFill(Color.WHITE);
        iterations.setPrefWidth(70);
        pane.add(iterationsLabel, 0, 6);
        pane.add(iterations, 1, 6);

        pane.add(getNewShapeSpacer(), 0, 7);

        // setup LineName
        lineNameLabel.setFont(labelFont);
        lineNameLabel.setTextFill(Color.WHITE);
        lineName.setPrefWidth(100);
        pane.add(lineNameLabel, 0, 8);
        pane.add(lineName, 1, 8);

        pane.add(getNewShapeSpacer(), 0, 9);
    }

    private void setupPreview() {
        Button preview = new Button("Preview");
        preview.setOnAction(event -> {
            if (isValidLine(startX.getText(), startY.getText(), endX.getText(), endY.getText(), startChangeInX.getText(),
                    startChangeInY.getText(), endChangeInX.getText(), endChangeInY.getText(), iterations.getText(), lineName.getText())) {

                // PARAMETERS ARE VALID

                int index = getIndex();
                previousPreviewLayer = new OrderedPane(index, gui);

                // create new algorithmic line with current data from user
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

//                // remove old preview
////                removeOldPreview(index);

                // store the line to the preview layer
                previousPreviewLayer.setAlgorithmicShape(line);
                previousPreviewLayer.setName(new Text(lineName.getText()));
                line.draw(viewer, previousPreviewLayer);

                // Add the new preview to the display
                viewer.getChildren().add(index, previousPreviewLayer);
                layers.add(index, previousPreviewLayer);

                layerContainer.getChildren().add(index, previousPreviewLayer.getName());
            }
        });
        preview.setBackground(buttonBackground);
        preview.setOnMouseEntered(event -> preview.setBackground(buttonSelectedBackground));
        preview.setOnMouseExited(event -> preview.setBackground(buttonBackground));
        pane.add(preview, 0, 10);
    }

    private void setupSave() {
        Button save = new Button("Save");
        save.setOnAction(event -> {
            // follows same procedure as a preview, but takes finalization step to exit the window

            if (isValidLine(startX.getText(), startY.getText(), endX.getText(), endY.getText(), startChangeInX.getText(),
                    startChangeInY.getText(), endChangeInX.getText(), endChangeInY.getText(), iterations.getText(), lineName.getText())) {

                // Parameters are valid
                int index = getIndex();
                previousPreviewLayer = new OrderedPane(index, gui);

                // create new algorithmic line with current data from user
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

                // store the line to the preview layer
                previousPreviewLayer.setAlgorithmicShape(line);
                previousPreviewLayer.setName(new Text(lineName.getText()));
                line.draw(viewer, previousPreviewLayer);

                // Add the new preview to the display
                viewer.getChildren().add(index, previousPreviewLayer);
                layers.add(index, previousPreviewLayer);
                layerContainer.getChildren().add(index, previousPreviewLayer.getName());

//                // remove old preview
//                removeOldPreview(index);

                for (OrderedPane p : layers) {
                    System.out.print(p.name.getText() + "  |  ");
                }

                System.out.println("\nViewer size: " + viewer.getChildren().size());
                System.out.println("Layers size: " + layers.size());
                System.out.println("Layer Container size: " + layerContainer.getChildren().size() + "\n");

                // close window
                newLineWindow.close();
            }
        });
        save.setBackground(buttonBackground);
        save.setOnMouseEntered(event -> save.setBackground(buttonSelectedBackground));
        save.setOnMouseExited(event -> save.setBackground(buttonBackground));
        pane.add(save, 2, 10);


        newLineWindow.close();
    }

    private void setupCancel() {
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            if (originalLayer == null) {
                // remove preview layer only
                if (previousPreviewLayer != null) {
                    removeOldPreview(previousPreviewLayer.getIndex());
                }
            } else {
                // restore original layer
                // removing preview
                removeOldPreview(previousPreviewLayer.getIndex());

                int index = originalLayer.getIndex();

                // restoring
                viewer.getChildren().add(index, originalLayer);
                layers.add(index, originalLayer);
                layerContainer.getChildren().add(index, originalLayer.getName());
            }
            newLineWindow.close();
        });
        cancel.setBackground(buttonBackground);
        cancel.setOnMouseEntered(event -> cancel.setBackground(buttonSelectedBackground));
        cancel.setOnMouseExited(event -> cancel.setBackground(buttonBackground));
        pane.add(cancel, 4, 10);
    }

    private int getIndex() {
        int index;

        if (previousPreviewLayer != null) {
            // there is already a preview layer we need to remove
            index = previousPreviewLayer.getIndex();
            viewer.getChildren().remove(previousPreviewLayer);
            layers.remove(index);
            layerContainer.getChildren().remove(index);
        } else {
            // this is a new layer that hasn't been previewed
            index = layers.size();
        }

        System.out.println("Got index " + index);

        return index;
    }

    private void removeOldPreview(int index) {
        if (previousPreviewLayer != null && index >= 0 && index < layerContainer.getChildren().size()) {
            viewer.getChildren().remove(index);
            layers.remove(index);
            layerContainer.getChildren().remove(index);
        }

        System.out.println("New num of layers is " + viewer.getChildren().size());
    }

    private void updateTextfields() {
        AlgorithmicLine outdatedLine = (AlgorithmicLine) originalLayer.getAlgorithmicShape();

        // Set prompt Text in window
        startX.setPromptText(outdatedLine.getStartX() + "");
        startY.setPromptText(outdatedLine.getStartY() + "");
        endX.setPromptText(outdatedLine.getEndX() + "");
        endY.setPromptText(outdatedLine.getEndY() + "");
        startChangeInX.setPromptText(outdatedLine.getStartDx() + "");
        startChangeInY.setPromptText(outdatedLine.getStartDy() + "");
        endChangeInX.setPromptText(outdatedLine.getEndDx() + "");
        endChangeInY.setPromptText(outdatedLine.getEndDy() + "");
        iterations.setPromptText(outdatedLine.getIterations() + "");
        lineName.setPromptText(outdatedLine.getName() + "");

        // set actual text in window
        startX.setText(outdatedLine.getStartX() + "");
        startY.setText(outdatedLine.getStartY() + "");
        endX.setText(outdatedLine.getEndX() + "");
        endY.setText(outdatedLine.getEndY() + "");
        startChangeInX.setText(outdatedLine.getStartDx() + "");
        startChangeInY.setText(outdatedLine.getStartDy() + "");
        endChangeInX.setText(outdatedLine.getEndDx() + "");
        endChangeInY.setText(outdatedLine.getEndDy() + "");
        iterations.setText(outdatedLine.getIterations() + "");
        lineName.setText(outdatedLine.getName() + "");
    }

    public Stage getStage() {
        return newLineWindow;
    }

    public GridPane getPane() {
        return pane;
    }
}