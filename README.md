# Calculadora MVC

Proyecto en Java desarrollado para la actividad de Programación Orientada a Objetos. Implementa una calculadora con el patrón Modelo–Vista–Controlador (MVC), operaciones de multiplicación y suma, y manejo exacto de enteros, decimales y fracciones.

## Video explicativo

**Enlace:** https://drive.google.com/file/d/1YDckOJIbaFpcsMCwr3iEvA8AduJY2G9j/view?usp=sharing

## Estructura del proyecto

```text
.
├── README.md
├── .gitignore
├── src/                          # Código fuente Java
│   ├── CalcModel.java            # Modelo y clase Fraction
│   ├── CalcView.java             # Vista
│   ├── CalcController.java       # Controlador
│   ├── Main.java                 # Inicio de la versión base
│   ├── CalcModelPlus.java        # Modelo extendido con suma
│   ├── CalcViewPlus.java         # Vista extendida con botón Add
│   ├── CalcControllerPlus.java   # Controlador extendido
│   └── MainPlus.java             # Inicio de la versión extendida
├── docs/
│   ├── 01-requerimientos.md
│   ├── 02-diagrama-de-clases.md
│   ├── 03-documentacion-tecnica.md
│   └── img/                      # Diagrama de clases en PNG, SVG y DOT
└── video/
```

## Patrón MVC

- **Modelo:** conserva el total y realiza las operaciones.
- **Vista:** muestra la ventana, los campos y los botones.
- **Controlador:** recibe las acciones del usuario y comunica la vista con el modelo.

La versión extendida agrega la suma mediante las clases `*Plus`, reutilizando las clases base.

## Ejecución

Se requiere Java JDK 21 o compatible.

Desde la carpeta raíz del proyecto, compilar:

```bash
javac -encoding UTF-8 -d bin src/*.java
```

Ejecutar la versión base:

```bash
java -cp bin Main
```

Ejecutar la versión con suma:

```bash
java -cp bin MainPlus
```

## Entradas admitidas

| Tipo | Ejemplos |
|---|---|
| Enteros | `25`, `-8` |
| Decimales | `3.75`, `-0.125` |
| Fracciones | `3/4`, `-2/5`, `1.5/0.25` |

La clase `Fraction` utiliza enteros grandes para conservar la precisión de los cálculos. Por ejemplo, `1/3` multiplicado por `3` produce exactamente `1`.

## Documentación

1. [Requerimientos](docs/01-requerimientos.md)
2. [Diagrama de clases y relaciones](docs/02-diagrama-de-clases.md)
3. [Documentación técnica, pruebas y evaluación del patrón Command](docs/03-documentacion-tecnica.md)

