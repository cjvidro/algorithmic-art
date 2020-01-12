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

    // if the change is a constant integer
    private int startDx;
    private int startDy;
    private int endDx;
    private int endDy;

    // if the change is an expression -- this is the postfix expression
    private String startDxExpression = null;
    private String startDyExpression = null;
    private String endDxExpression = null;
    private String endDyExpression = null;

    // constructors
    public AlgorithmicLine(int startX, int startY, int endX, int endY, String startDx, String startDy,
                           String endDx, String endDy, int iterations, String name, Boolean first) {
            // These variables MUST be integers
            this.startX = startX;
            this.startY = -startY;
            this.endX = endX;
            this.endY = -endY;
            this.setIterations(iterations);
            this.setName(name);

            // These variables might be an int, or might be an expression with a variable
            // assign correct variable type based on if user input is a constant or an expression with a variable
            try {
                this.startDx = Integer.parseInt(startDx);
            } catch (NumberFormatException e) {
                this.startDxExpression = startDx;
            }
            try {
                this.startDy = Integer.parseInt(startDy);
            } catch (NumberFormatException e) {
                this.startDyExpression = startDy;
            }
            try {
                this.endDx = Integer.parseInt(endDx);
            } catch (NumberFormatException e) {
                this.endDxExpression = endDx;
            }
            try {
                this.endDy = Integer.parseInt(endDy);
            } catch (NumberFormatException e) {
                this.endDyExpression = endDy;
            }

            //TODO stop the adjustment happening unnecessarily in the first place?
            // undoes the adjustment that happens unnecessarily
            if (!first) {
                this.startY = -this.startY;
                this.endY = -this.endY;
                this.startDy = -this.startDy;
                this.endDy = -this.endDy;
            }
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

    public String getStartDxExpression() {
        return startDxExpression;
    }

    public String getStartDyExpression() {
        return startDyExpression;
    }

    public String getEndDxExpression() {
        return endDxExpression;
    }

    public String getEndDyExpression() {
        return endDyExpression;
    }

    /**
     * Draws the complete set of shapes for this algorithmic shape. A helper method will be needed.
     */
    @Override
    public OrderedPane draw(Pane viewer, OrderedPane layerPane) {
        return draw(viewer, layerPane, startX, startY, endX, endY, this.getIterations());
    }

    private OrderedPane draw(Pane viewer, OrderedPane layerPane, int startX, int startY, int endX, int endY, int remainingIterations) {
        System.out.println("Called draw! with " + remainingIterations + " iterations");

        while (remainingIterations > 0) {
            System.out.println("startX: " + startX + "\n" + "startY: " + startY + "\n" + "endX: " + endX + "\n" + "endY: " + endY + "\n");

            // draw the next line
            Line line = new Line(startX, startY, endX, endY);
            line.translateXProperty().bind(viewer.widthProperty().divide(2.0));
            line.translateYProperty().bind(viewer.heightProperty().divide(2.0));
            line.setFill(Color.BLACK);
            layerPane.getChildren().add(line);

            // adjust variables by constant or evaluated expression where iteration takes place for the variable
            if (startDxExpression == null) {
                startX += startDx;
            } else {
                startX += PostfixExpression.evaluate(InfixToPostfix.convert(startDxExpression, this.getIterations() - remainingIterations + 1));
            }
            if (startDyExpression == null) {
                startY += startDy;
            } else {
                startY += PostfixExpression.evaluate(InfixToPostfix.convert(startDyExpression, this.getIterations() - remainingIterations + 1));
            }
            if (endDxExpression == null) {
                endX += endDx;
            } else {
                endX += PostfixExpression.evaluate(InfixToPostfix.convert(endDxExpression, this.getIterations() - remainingIterations + 1));
            }
            if (endDyExpression == null) {
                endY += endDy;
            } else {
                endY += PostfixExpression.evaluate(InfixToPostfix.convert(endDyExpression, this.getIterations() - remainingIterations + 1));
            }

            // adjust variables if constant
//            if (startDxExpression == null) { // assumes that there are no expression
//                startX += startDx;
//                startY += startDy;
//                endX += endDx;
//                endY += endDy;
//            } else {
//                // expressional changes
//                startX =
//            }

            return draw(viewer, layerPane, startX, startY, endX, endY, --remainingIterations);
        }

        return layerPane;
    }
}