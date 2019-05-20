import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OrderedPane extends javafx.scene.layout.Pane {
    private int index;
    private AlgorithmicShape algorithmicShape;
    Text name;

    public OrderedPane() {
        super();
    }

    public OrderedPane(int index) {
        super();
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AlgorithmicShape getAlgorithmicShape() {
        return algorithmicShape;
    }

    public void setAlgorithmicShape(AlgorithmicShape algorithmicShape) {
        this.algorithmicShape = algorithmicShape;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        Text adjustedText = new OrderedText(name.getText());
        adjustedText.setFill(Color.WHITE);
        adjustedText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));

        this.name = adjustedText;
    }
}
