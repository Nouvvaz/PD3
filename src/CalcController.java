import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** CONTROLADOR: conecta la Vista con el Modelo. */
public class CalcController {
    private CalcModel m_model;
    private CalcView  m_view;

    CalcController(CalcModel model, CalcView view) {
        m_model = model;
        m_view  = view;

        view.addMultiplyListener(new MultiplyListener());
        view.addClearListener(new ClearListener());
    }

    /**
     * 1. Obtiene el número de la Vista.
     * 2. Le pide al Modelo multiplicar.
     * 3. Obtiene el resultado del Modelo.
     * 4. Le dice a la Vista que lo muestre (o muestra el error).
     */
    class MultiplyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput = "";
            try {
                userInput = m_view.getUserInput();
                m_model.multiplyBy(userInput);
                m_view.setTotal(m_model.getValue());
            } catch (NumberFormatException nfex) {
                m_view.showError("Bad input: '" + userInput + "'");
            }
        }
    }

    /** 1. Reinicia el modelo. 2. Reinicia la vista. */
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            m_model.reset();
            m_view.reset();
        }
    }
}
