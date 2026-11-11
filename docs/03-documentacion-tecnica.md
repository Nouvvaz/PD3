# 3. Documentación técnica

## 3.1 Arquitectura MVC

```
 Usuario ──clic──▶ CalcView ──evento──▶ CalcController ──▶ CalcModel
                      ▲                      │                 │
                      └────── setTotal / showError ◀───────────┘ getValue
```

1. El usuario escribe en **Input** y presiona un botón (**Vista**).
2. El **Controlador** recibe el evento, toma el texto de la vista (`getUserInput`) y le pide al **Modelo** la operación.
3. El Modelo calcula con `Fraction` y guarda el nuevo total.
4. El Controlador lee el resultado (`getValue`) y le ordena a la Vista mostrarlo (`setTotal`), o mostrar el error (`showError`).

La Vista nunca calcula y el Modelo nunca toca la interfaz: cada parte se puede cambiar sin romper las otras.

## 3.2 Conceptos de POO aplicados

| Concepto | Dónde se ve |
|----------|-------------|
| **Abstracción** | `Fraction` esconde cómo se guardan y reducen los números; el modelo solo pide `multiply` y `toString`. |
| **Encapsulamiento** | Atributos `private` (`m_total`, `m_model`, botones, campos). Solo se accede mediante métodos (`getValue`, `setTotal`, etc.). |
| **Herencia** | `CalcView extends JFrame`; `CalcModelPlus`, `CalcViewPlus` y `CalcControllerPlus` extienden a las clases base. |
| **Polimorfismo** | `MultiplyListener`, `ClearListener` y `AddListener` implementan `ActionListener`; la vista los registra sin saber cuál es cuál. Además, `CalcControllerPlus` puede usarse donde se espera un `CalcController`. |
| **Composición** | `CalcModel` *tiene un* `Fraction` (`m_total`). |
| **Clases internas** | Los listeners son clases internas del controlador y acceden directamente a `m_model` y `m_view`. |

## 3.3 Modularidad

- **Alta cohesión:** cada clase hace una sola cosa (calcular, mostrar o coordinar).
- **Bajo acoplamiento:** la vista y el modelo no dependen del controlador; el controlador solo usa sus métodos públicos/de paquete.
- **Principio abierto/cerrado (OCP):** las clases están abiertas a extensión y cerradas a modificación. Se demostró al agregar la suma.

## 3.4 Cómo se agregó la suma sin modificar lo anterior

Los archivos `CalcModel.java`, `CalcView.java`, `CalcController.java` y `Main.java` **quedaron idénticos**. Solo se crearon archivos nuevos:

| Archivo nuevo | Qué agrega |
|---------------|------------|
| `CalcModelPlus.java` | `addBy(String)`: suma el operando al total. |
| `CalcViewPlus.java` | Botón **Add** (insertado después de Multiply) y `addAddListener(...)`. |
| `CalcControllerPlus.java` | `AddListener` y su registro en la vista. Multiply y Clear se heredan: los registra el constructor del padre (`super(model, view)`). |
| `MainPlus.java` | Nuevo punto de entrada que arma las clases Plus. |

**Detalle de implementación de `addBy`:**

1. Obtiene el total exacto mediante `getTotalFraction()`, un método `protected` de `CalcModel`.
2. Convierte el operando con `Fraction.parse` (acepta entero, decimal o `a/b`).
3. Suma las fracciones: `a/b + c/d = (a·d + c·b) / (b·d)`.
4. Guarda el resultado con `setTotalFraction(result)`, sin convertirlo primero a texto.

**Mejora aplicada:** antes se recuperaba el total analizando el texto de `getValue()`. Eso funcionaba, pero dependía del formato que se muestra en pantalla. Se cambió por dos métodos `protected` para trabajar directamente con la fracción exacta. Así la extensión queda más clara y evita conversiones innecesarias.

## 3.5 Decisiones de diseño

- **`Fraction` con `BigInteger` en vez de `BigDecimal`:** `BigDecimal` no puede representar `1/3` de forma exacta. Con numerador y denominador, `1/3 × 3` da exactamente `1`.
- **Reducción automática:** el constructor divide por el máximo común divisor, así `2/4` se guarda como `1/2`.
- **Entradas aceptadas:** se usa `BigDecimal` solo para leer textos como `-3.75` y convertirlos a fracción.
- **Visualización:** se detecta si el decimal termina (denominador solo con factores 2 y 5). Si termina, se muestra exacto (`= 0.75`); si no, se aproxima con 30 decimales (`≈`).
- **Errores:** `Fraction` lanza `NumberFormatException` para texto inválido, vacío o `/0`; el controlador la captura y muestra `Bad input`.

## 3.6 Cómo compilar y ejecutar

**VS Code:** abrir la carpeta del proyecto (con *Extension Pack for Java*) y ejecutar `src/Main.java` (versión base) o `src/MainPlus.java` (con Add).

**Terminal (desde la carpeta raíz):**

```bash
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Main        # solo Multiply / Clear
java -cp bin MainPlus    # Multiply / Add / Clear
```

## 3.7 Plan de pruebas

Pruebas del **modelo** ejecutadas con JDK 21; todas dieron el resultado esperado. Las pruebas de la interfaz gráfica se hacen manualmente (columna "Pasos").

| # | Pasos (el Total empieza en 1; use **Clear** entre casos) | Resultado esperado |
|---|-----------------------------------------------------------|--------------------|
| 1 | Multiply `123456789012345678901234567890` | `123456789012345678901234567890` |
| 2 | Continuar con Multiply `10` | `1234567890123456789012345678900` |
| 3 | Multiply `3.75` | `15/4 = 3.75` |
| 4 | Multiply `3/4`, luego Multiply `1/3` | `3/4 = 0.75`, luego `1/4 = 0.25` |
| 5 | Multiply `1/3`, luego Multiply `3` | `1/3 ≈ 0.333333333333333333333333333333`, luego `1` |
| 6 | Multiply `abc` | Mensaje `Bad input: 'abc'`; el Total no cambia |
| 7 | Multiply `5/0` | Mensaje `Bad input: '5/0'` |
| 8 | Add `1/3`, luego Add `2/3` | `4/3 ≈ 1.333333333333333333333333333333`, luego `2` |
| 9 | Add `0.5`, luego Multiply `2` | `3/2 = 1.5`, luego `3` |
| 10 | Add `-1` | `0` |
| 11 | Add `99999999999999999999.5` | `200000000000000000001/2 = 100000000000000000000.5` |
| 12 | Add con el Input vacío | Mensaje `Bad input: ''` |
| 13 | Escribir algo en Input y presionar Clear | Total `1` e Input vacío |

## 3.8 Trazabilidad requerimientos → pruebas

| Requerimiento | Prueba(s) |
|---------------|-----------|
| RF-03, RF-04 | 1, 2 |
| RF-05 | 3, 11 |
| RF-06 | 4, 5 |
| RF-07 | 3, 4, 5, 11 |
| RF-08 | 13 |
| RF-09 | 6, 7 |
| RF-11, RF-13 | 8, 9, 10, 11 |
| RF-12 | 12 |
| RNF-01 | 5, 8 |
| RNF-06 | 6, 7, 12 |


## 3.9 Estrategia para agregar más operaciones y evaluación de Command

Para este proyecto conviene mantener MVC y agregar las operaciones mediante una **interfaz común de operación** (por ejemplo, `Operation`, con un método `execute(total, operand)`). Cada operación tendría su propia implementación: `AddOperation`, `SubtractOperation`, `MultiplyOperation`, etc. El controlador identifica qué botón se presionó y solicita ejecutar la operación correspondiente; el modelo conserva el total y la lógica numérica. La vista solo agrega el botón y muestra el resultado.

Esta alternativa se relaciona con el patrón **Strategy**: permite intercambiar la operación sin llenar el controlador de condiciones. Para el tamaño actual del proyecto, es una solución más directa que Command.

**¿Por qué no usar Command ahora?** Command encapsula cada acción en un objeto independiente. Es útil cuando se necesita guardar operaciones, deshacer/rehacer, repetir acciones, crear macros o ponerlas en una cola. En esta calculadora sencilla, que solo ejecuta una operación al presionar un botón, agregar comandos, un administrador de historial y posiblemente objetos para deshacer aumentaría la cantidad de clases y el mantenimiento sin aportar una ventaja inmediata.

**Ventajas de Command:** cada acción queda aislada, se puede registrar, repetir o deshacer y el emisor del botón no necesita conocer los detalles del cálculo. **Desventajas en este caso:** más clases y estructura, más código de coordinación y complejidad adicional para una necesidad que todavía no existe.

**Conclusión:** se recomienda conservar MVC y usar una interfaz de operación/Strategy para crecer con suma, resta, multiplicación y división. Si más adelante se solicita historial, deshacer/rehacer o repetir operaciones, entonces Command tendría una justificación clara. También podría combinarse con Strategy: Command representaría la acción solicitada y Strategy definiría el algoritmo matemático.
