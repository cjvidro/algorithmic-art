import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OrderedPane extends javafx.scene.layout.Pane {
    private static Gui gui;
    private int index;
    private AlgorithmicShape algorithmicShape;
    HBox nameBox;

    // color Palette
    private final Color midGreen = Color.hsb(125, 0.16, 0.50);
    private final Background midGreenBackground = new Background(new BackgroundFill(midGreen, CornerRadii.EMPTY, Insets.EMPTY));
    private final Color darkGrey = Color.hsb(0, 0, 0.16);
    private final Background darkGreyBackground = new Background(new BackgroundFill(darkGrey, CornerRadii.EMPTY, Insets.EMPTY));

    public OrderedPane(int index, Gui gui) {
        super();
        this.index = index;
        this.gui = gui;
    }

    public int getIndex() {
        return index;
    }

    public AlgorithmicShape getAlgorithmicShape() {
        return algorithmicShape;
    }

    public void setAlgorithmicShape(AlgorithmicShape algorithmicShape) {
        this.algorithmicShape = algorithmicShape;
    }

    public HBox getName() {
        return nameBox;
    }

    public void setName(Text name) {
        Text adjustedText = new OrderedText(name.getText());
        adjustedText.setFill(Color.WHITE);
        adjustedText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));

        nameBox = new HBox();
        nameBox.setPadding(new Insets(2, 0, 0, 7));
        nameBox.getChildren().add(adjustedText);

        nameBox.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // trigger update algorithm
                if (algorithmicShape instanceof AlgorithmicLine) {
                    gui.updateLineAlgorithm(this);
                }
            }
        });

        nameBox.setBackground(darkGreyBackground);
        nameBox.setOnMouseEntered(event -> {
            nameBox.setBackground(midGreenBackground);
        });
        nameBox.setOnMouseExited(event -> {
            nameBox.setBackground(darkGreyBackground);
        });
    }
}
