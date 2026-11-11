# 2. Diagrama de clases

## 2.1 Imagen

![Diagrama de clases](img/diagrama-clases.png)

También disponible en vectorial: [`img/diagrama-clases.svg`](img/diagrama-clases.svg) (código fuente Graphviz: [`img/diagrama-clases.dot`](img/diagrama-clases.dot)).

## 2.2 Versión Mermaid (GitHub la dibuja automáticamente)

```mermaid
classDiagram
    direction TB

    class JFrame {
        <<javax.swing>>
    }
    class ActionListener {
        <<interface>>
        +actionPerformed(ActionEvent e) void
    }

    class Fraction {
        <<final, package>>
        ~BigInteger num
        ~BigInteger den
        -int DECIMALS = 30
        ~Fraction(BigInteger n, BigInteger d)
        ~parse(String text) Fraction$
        -parseDecimal(String s) Fraction$
        ~multiply(Fraction o) Fraction
        ~divide(Fraction o) Fraction
        ~isInteger() boolean
        ~isTerminating() boolean
        ~toDecimalString() String
        +toString() String
    }

    class CalcModel {
        -String INITIAL_VALUE = "1"
        -Fraction m_total
        +CalcModel()
        +reset() void
        +setValue(String value) void
        +multiplyBy(String operand) void
        +getValue() String
        #getTotalFraction() Fraction
        #setTotalFraction(Fraction value) void
    }

    class CalcView {
        -String INITIAL_VALUE = "1"
        -JTextField m_userInputTf
        -JTextField m_totalTf
        -JButton m_multiplyBtn
        -JButton m_clearBtn
        -CalcModel m_model
        ~CalcView(CalcModel model)
        ~reset() void
        ~getUserInput() String
        ~setTotal(String newTotal) void
        ~showError(String errMessage) void
        ~addMultiplyListener(ActionListener mal) void
        ~addClearListener(ActionListener cal) void
    }

    class CalcController {
        -CalcModel m_model
        -CalcView m_view
        ~CalcController(CalcModel model, CalcView view)
    }
    class MultiplyListener {
        +actionPerformed(ActionEvent e) void
    }
    class ClearListener {
        +actionPerformed(ActionEvent e) void
    }
    class Main {
        +main(String[] args) void$
    }

    class CalcModelPlus {
        +addBy(String operand) void
        -add(Fraction a, Fraction b) Fraction$
    }
    class CalcViewPlus {
        -JButton m_addBtn
        ~CalcViewPlus(CalcModelPlus model)
        ~addAddListener(ActionListener al) void
    }
    class CalcControllerPlus {
        -CalcModelPlus m_modelPlus
        -CalcViewPlus m_viewPlus
        ~CalcControllerPlus(CalcModelPlus model, CalcViewPlus view)
    }
    class AddListener {
        +actionPerformed(ActionEvent e) void
    }
    class MainPlus {
        +main(String[] args) void$
    }

    JFrame <|-- CalcView
    CalcView <|-- CalcViewPlus
    CalcModel <|-- CalcModelPlus
    CalcController <|-- CalcControllerPlus

    ActionListener <|.. MultiplyListener
    ActionListener <|.. ClearListener
    ActionListener <|.. AddListener

    CalcModel *-- Fraction : m_total
    CalcView --> CalcModel : m_model
    CalcController --> CalcModel : m_model
    CalcController --> CalcView : m_view
    CalcControllerPlus --> CalcModelPlus : m_modelPlus
    CalcControllerPlus --> CalcViewPlus : m_viewPlus

    CalcController ..> MultiplyListener : crea
    CalcController ..> ClearListener : crea
    CalcControllerPlus ..> AddListener : crea
    Main ..> CalcController : crea
    MainPlus ..> CalcControllerPlus : crea
```

## 2.3 Leyenda de relaciones

| Símbolo | Significado | Ejemplo |
|---------|-------------|---------|
| Flecha con triángulo vacío (línea continua) | **Herencia** (`extends`) | `CalcModelPlus` → `CalcModel` |
| Flecha con triángulo vacío (línea punteada) | **Implementación** de interfaz (`implements`) | `MultiplyListener` → `ActionListener` |
| Rombo negro | **Composición**: el modelo posee y controla a su `Fraction` | `CalcModel` ◆→ `Fraction` |
| Flecha simple | **Asociación**: la clase guarda una referencia a la otra | `CalcController` → `CalcView` |
| Flecha punteada | **Dependencia**: una clase crea o usa a otra | `Main` ⇢ `CalcController` |

Visibilidad: `+` público, `-` privado, `~` paquete (sin modificador).

## 2.4 Responsabilidad de cada clase

| Clase | Capa | Responsabilidad |
|-------|------|-----------------|
| `Fraction` | Modelo (apoyo) | Representa un número como fracción exacta (`BigInteger`), lo interpreta desde texto y lo multiplica. |
| `CalcModel` | Modelo | Guarda el total y multiplica. No conoce la ventana. |
| `CalcView` | Vista | Dibuja la ventana y entrega/recibe texto. No hace cálculos. |
| `CalcController` | Controlador | Conecta botones con el modelo y avisa a la vista. |
| `MultiplyListener` / `ClearListener` | Controlador | Acciones de los botones Multiply y Clear (clases internas). |
| `Main` | Arranque | Crea modelo, vista y controlador (versión base). |
| `CalcModelPlus` | Modelo (extensión) | Hereda de `CalcModel` y agrega la **suma**. |
| `CalcViewPlus` | Vista (extensión) | Hereda de `CalcView` y agrega el botón **Add**. |
| `CalcControllerPlus` / `AddListener` | Controlador (extensión) | Hereda de `CalcController` y agrega la acción de Add. |
| `MainPlus` | Arranque | Crea las clases Plus (versión con suma). |
