import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CONTROLADOR EXTENDIDO: hereda los listeners de Multiply y Clear
 * (los registra el constructor del padre) y agrega el listener de Add.
 * No se modifica CalcController.
 */
public class CalcControllerPlus extends CalcController {
    private CalcModelPlus m_modelPlus;
    private CalcViewPlus  m_viewPlus;

    CalcControllerPlus(CalcModelPlus model, CalcViewPlus view) {
        super(model, view);
        m_modelPlus = model;
        m_viewPlus  = view;

        view.addAddListener(new AddListener());
    }

    /**
     * 1. Obtiene el número de la Vista.
     * 2. Le pide al Modelo sumar.
     * 3. Obtiene el resultado del Modelo.
     * 4. Le dice a la Vista que lo muestre (o muestra el error).
     */
    class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput = "";
            try {
                userInput = m_viewPlus.getUserInput();
                m_modelPlus.addBy(userInput);
                m_viewPlus.setTotal(m_modelPlus.getValue());
            } catch (NumberFormatException nfex) {
                m_viewPlus.showError("Bad input: '" + userInput + "'");
            }
        }
    }
}
