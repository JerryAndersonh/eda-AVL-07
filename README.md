# Árbol AVL - Trabajo Práctico

Este proyecto implementa un Árbol AVL en Java como parte del laboratorio de estructuras de datos. Se desarrollaron tres ejercicios principales, cubriendo inserción, eliminación, recorridos, balanceo y visualización del árbol.

## Ejercicio 1: Dinámica de inserción y eliminación

- **Inserciones realizadas**:  
  `100, 200, 300, 400, 500, 50, 25, 350, 375, 360, 355, 150, 175, 120, 190`

- **Recorridos mostrados**:
  - InOrder
  - PreOrder
  - PostOrder

- **Eliminaciones**:
  Se eliminaron uno por uno los mismos nodos insertados, aplicando las rotaciones necesarias.

- **Requisitos adicionales**:
  - Mostrar el paso a paso de inserción y eliminación.
  - Indicar las rotaciones aplicadas (simples y dobles) para mantener el árbol balanceado.

---

## Ejercicio 2: Implementación completa del árbol AVL

Se implementaron los siguientes métodos usando clases genéricas:

- **Operaciones básicas**:
  - `insert(x)`, `remove(x)`, `search(x)`
  - `min()`, `max()`, `predecesor(x)`, `sucesor(x)`
  - `destroy()`, `isEmpty()`

- **Recorridos**:
  - `inOrder()`, `preOrder()`, `postOrder()`

- **Balanceo**:
  - `balancearIzquierda()`, `balancearDerecha()`
  - `rotacionSimpleIzquierda()`, `rotacionSimpleDerecha()`

- **Clase de prueba**:
  Se creó una clase `TestAVL` con un menú interactivo para probar todas las funcionalidades del árbol desde consola.

---

## Ejercicio 3: Visualización del árbol

- Se desarrolló un método para **graficar el árbol AVL** completo.
- Se utilizaron **clases y métodos genéricos**.
- Se empleó la librería **JGraphT** para generar un grafo dirigido.
- Los nodos se visualizaron con:
  - Altura de cada nodo
  - Aristas izquierda (L) y derecha (R) diferenciadas
- Se exportó el árbol a un archivo HTML para visualizarlo en el navegador con **D3.js y Graphviz.js**.

---

## Notas

- Este trabajo fue desarrollado como parte del curso de **Estructuras de Datos y Algoritmos**.
- El código es modular, con separación clara de clases, recorridos, balanceos y visualización.

---

## Autor

**Jerry Anderson Huaynacho Mango**  
Escuela Profesional de Ingeniería de Sistemas  
Universidad Nacional de San Agustín - 2025
