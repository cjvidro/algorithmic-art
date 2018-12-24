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
    public AlgorithmicLine(int startX, int startY, int endX, int endY, int startDx, int startDy, int endDx, int endDy, int iterations) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.startDx = startDx;
        this.startDy = startDy;
        this.endDx = endDx;
        this.endDy = endDy;
        this.setIterations(iterations);
    }

    /**
     * Draws the complete set of shapes for this algorithmic shape. A helper method will be needed.
     */
    @Override
    public void draw(Pane viewer) {
        draw(viewer, startX, startY,  endX, endY, this.getIterations());
    }

    private void draw(Pane viewer, int startX, int startY, int endX, int endY, int remainingTterations) {
        if (remainingTterations > 0) {
            Line line = new Line(startX, startY, endX, endY);
            line.setFill(Color.BLACK);
            viewer.getChildren().add(line);
            draw(viewer, startX + startDx, startY + startDy, endX + endDx, endY + endDy, --remainingTterations);
        }
    }
}
