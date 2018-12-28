import javafx.scene.layout.Pane;

/**
 * Basic properties of an algorithmic shape
 *
 * @author Charles Vidro
 */
public abstract class AlgorithmicShape {
    private String name;
    private int iterations;
    private int scaleFactor;
    private int rotation;
    private int colorR;
    private int colorG;
    private int colorB;
    private int colorA;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(int scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public int getColorA() {
        return colorA;
    }

    public void setColorA(int colorA) {
        this.colorA = colorA;
    }

    // methods

    /**
     * Draws the complete set of shapes for this algorithmic shape. A helper method will be needed.
     */
    public abstract void draw(Pane viewer);
}
