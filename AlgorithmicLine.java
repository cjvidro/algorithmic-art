import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * AlgorithmicLine object
 *
 * @author Charles Vidro
 */
public class AlgorithmicLine extends AlgorithmicShape {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int startDx;
    private int startDy;
    private int endDx;
    private int endDy;

    // constructors
    public AlgorithmicLine(int startX, int startY, int endX, int endY, int startDx, int startDy,
                           int endDx, int endDy, int iterations, String name) {
        this.startX = startX;
        this.startY = -startY;
        this.endX = endX;
        this.endY = -endY;
        this.startDx = startDx;
        this.startDy = -startDy;
        this.endDx = endDx;
        this.endDy = -endDy;
        this.setIterations(iterations);
        this.setName(name);
    }

    // getters and setters
    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getStartDx() {
        return startDx;
    }

    public void setStartDx(int startDx) {
        this.startDx = startDx;
    }

    public int getStartDy() {
        return startDy;
    }

    public void setStartDy(int startDy) {
        this.startDy = startDy;
    }

    public int getEndDx() {
        return endDx;
    }

    public void setEndDx(int endDx) {
        this.endDx = endDx;
    }

    public int getEndDy() {
        return endDy;
    }

    public void setEndDy(int endDy) {
        this.endDy = endDy;
    }

    /**
     * Draws the complete set of shapes for this algorithmic shape. A helper method will be needed.
     */
    @Override
    public OrderedPane draw(Pane viewer, OrderedPane layerPane) {
        return draw(viewer, layerPane, startX, startY, endX, endY, this.getIterations());
    }

    private OrderedPane draw(Pane viewer, OrderedPane layerPane, int startX, int startY, int endX, int endY, int remainingIterations) {
        if (remainingIterations > 0) {
            Line line = new Line(startX, startY, endX, endY);
            line.translateXProperty().bind(viewer.widthProperty().divide(2.0));
            line.translateYProperty().bind(viewer.heightProperty().divide(2.0));
            line.setFill(Color.BLACK);
            layerPane.getChildren().add(line);
            return draw(viewer, layerPane, startX + startDx, startY + startDy, endX + endDx, endY + endDy, --remainingIterations);
        } else {
            //System.out.println("\nAdded: " + layerPane.toString());
            return layerPane;
        }
    }
}