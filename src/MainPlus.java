import javax.swing.SwingUtilities;

/** Punto de entrada de la calculadora con Multiply + Add. (Main.java original sigue intacto.) */
public class MainPlus {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalcModelPlus model = new CalcModelPlus();
            CalcViewPlus view = new CalcViewPlus(model);
            new CalcControllerPlus(model, view);
            view.setVisible(true);
        });
    }
}
