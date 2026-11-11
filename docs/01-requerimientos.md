# 1. Requerimientos — Calculadora MVC

Proyecto: **Calculadora MVC en Java** (Prueba de desempeño PD3 — Modularidad y Programación Orientada a Objetos).

## 1.1 Requerimientos funcionales (RF)

| ID | Requerimiento | Prioridad | Clase(s) responsable(s) |
|----|---------------|-----------|--------------------------|
| RF-01 | El sistema debe mostrar una ventana con un campo **Input**, un botón **Multiply**, un campo **Total** (solo lectura) y un botón **Clear**. | Alta | `CalcView` |
| RF-02 | El **Total** debe iniciar con el valor **1**. | Alta | `CalcModel`, `CalcView` |
| RF-03 | Al presionar **Multiply**, el sistema debe multiplicar el Total por el número escrito en Input y mostrar el nuevo Total. | Alta | `CalcController`, `CalcModel` |
| RF-04 | El sistema debe permitir multiplicar por **enteros de tamaño arbitrario** (por ejemplo, 30 dígitos o más). | Alta | `Fraction`, `CalcModel` |
| RF-05 | El sistema debe permitir multiplicar por **decimales largos** (positivos o negativos), por ejemplo `-3.75` o `0.000000000000123456789`. | Alta | `Fraction` |
| RF-06 | El sistema debe permitir multiplicar por **fracciones** con el formato `a/b`, por ejemplo `3/4` o `-2/5`. El numerador y el denominador pueden ser enteros o decimales (`1.5/0.25`). | Alta | `Fraction` |
| RF-07 | El Total debe mostrarse así: entero → `12`; fracción con decimal exacto → `3/4 = 0.75`; fracción con decimal infinito → `1/3 ≈ 0.333…` (30 decimales). | Media | `Fraction.toString()` |
| RF-08 | Al presionar **Clear**, el Total debe volver a **1** y el campo Input debe quedar vacío. | Alta | `CalcController`, `CalcModel`, `CalcView` |
| RF-09 | El sistema debe validar la entrada. Si el texto no es un número válido, está vacío o es una división entre cero (`5/0`), debe mostrar el mensaje `Bad input: '<texto>'` y conservar el Total anterior. | Alta | `Fraction.parse()`, `CalcController` |
| RF-10 | El campo Total **no** debe ser editable por el usuario. | Media | `CalcView` |
| RF-11 | **(Extensión Plus)** El sistema debe incluir un botón **Add** que sume al Total el número escrito en Input. Acepta los mismos formatos que Multiply (enteros largos, decimales y fracciones). | Alta | `CalcViewPlus`, `CalcControllerPlus`, `CalcModelPlus` |
| RF-12 | **(Extensión Plus)** La operación Add debe validar la entrada igual que Multiply (RF-09). | Alta | `CalcControllerPlus` |
| RF-13 | **(Extensión Plus)** Multiply, Add y Clear deben poder combinarse en cualquier orden sobre el mismo Total. | Media | `CalcModelPlus` |

## 1.2 Requerimientos no funcionales (RNF)

| ID | Categoría | Requerimiento | Cómo se cumple |
|----|-----------|---------------|----------------|
| RNF-01 | Precisión | Los resultados deben ser **exactos**, sin errores de redondeo de `float`/`double`. | Se usa `BigInteger` en una fracción (`Fraction`) y no se usan tipos primitivos decimales. |
| RNF-02 | Modularidad | La aplicación debe separar **datos, interfaz y control** (patrón MVC). | Clases separadas: `CalcModel`, `CalcView`, `CalcController`. |
| RNF-03 | Extensibilidad | Debe poder agregarse un operador nuevo **sin modificar** el código existente (principio abierto/cerrado). | `CalcModelPlus`, `CalcViewPlus` y `CalcControllerPlus` **heredan** de las clases base y solo agregan lo nuevo. |
| RNF-04 | Mantenibilidad | El código debe estar documentado y usar nombres consistentes. | Comentarios Javadoc en cada clase; atributos con prefijo `m_`; documentación en `docs/`. |
| RNF-05 | Usabilidad | La interfaz debe ser simple y los errores deben explicarse con un mensaje claro. | Ventana de una sola fila con etiquetas; diálogo `Bad input`. |
| RNF-06 | Robustez | Una entrada inválida **no** debe cerrar ni bloquear el programa ni alterar el Total. | `NumberFormatException` capturada en el controlador. |
| RNF-07 | Portabilidad | Debe ejecutarse en cualquier sistema con Java, sin librerías externas. | Solo usa la biblioteca estándar (`java.math`, `javax.swing`, `java.awt`). Compatible con JDK 8 o superior (probado con JDK 21). |
| RNF-08 | Rendimiento (objetivo) | Cada operación debe responder de forma casi inmediata para operandos de uso normal en clase. | Operaciones aritméticas de `BigInteger`; sin hilos ni procesos externos. |
| RNF-09 | Organización | El repositorio debe estar ordenado por carpetas (código, documentación y video). | Estructura `src/`, `docs/`, `video/` (ver `README.md`). |

## 1.3 Restricciones

- Lenguaje: **Java** (interfaz con Swing).
- Patrón de arquitectura: **Modelo – Vista – Controlador (MVC)**, basado en el ejemplo visto en clase.
- Los archivos de la versión base (`CalcModel`, `CalcView`, `CalcController`, `Main`) **no se modifican** al agregar la suma.

## 1.4 Fuera de alcance

- Operaciones distintas de multiplicar y sumar (resta, división, etc.). La arquitectura permite agregarlas con el mismo mecanismo de la extensión Plus.
- Guardar historial de operaciones.
