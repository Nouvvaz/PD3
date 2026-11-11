import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalcModel model = new CalcModel();
            CalcView view = new CalcView(model);
            new CalcController(model, view);
            view.setVisible(true);
        });
    }
}
