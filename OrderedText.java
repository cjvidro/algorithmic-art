public class OrderedText extends javafx.scene.text.Text {
    private int index;

    public OrderedText(String text) {
        super(text);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
