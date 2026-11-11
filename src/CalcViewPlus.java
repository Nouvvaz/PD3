import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * VISTA EXTENDIDA: hereda la ventana de CalcView y agrega el botón "Add"
 * justo después de "Multiply". No se modifica CalcView.
 */
class CalcViewPlus extends CalcView {
    private JButton m_addBtn = new JButton("Add");

    CalcViewPlus(CalcModelPlus model) {
        super(model);

        // Orden del panel: Input(0) campo(1) Multiply(2) -> Add va en la posición 3
        getContentPane().add(m_addBtn, 3);

        setTitle("Simple Calc - MVC (Multiply + Add)");
        pack();
        setLocationRelativeTo(null);
    }

    void addAddListener(ActionListener al) {
        m_addBtn.addActionListener(al);
    }
}
