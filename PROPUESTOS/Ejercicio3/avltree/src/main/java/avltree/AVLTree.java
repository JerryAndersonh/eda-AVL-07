package avltree;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import javax.swing.*;
import java.awt.*;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class NodoAVL<T extends Comparable<T>> {
    T dato;
    int altura;
    NodoAVL<T> izquierdo;
    NodoAVL<T> derecho;

    NodoAVL(T dato) {
        this.dato = dato;
        this.altura = 1;
    }
}

public class AVLTree<T extends Comparable<T>> {
    private NodoAVL<T> raiz;

    // Métodos AVL
    public void insertar(T dato) {
        raiz = insertar(raiz, dato);
    }

    private NodoAVL<T> insertar(NodoAVL<T> nodo, T dato) {
        if (nodo == null) return new NodoAVL<>(dato);
        
        int cmp = dato.compareTo(nodo.dato);
        if (cmp < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, dato);
        } else if (cmp > 0) {
            nodo.derecho = insertar(nodo.derecho, dato);
        } else {
            return nodo; // Duplicados no permitidos
        }
        
        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private void actualizarAltura(NodoAVL<T> n) {
        n.altura = 1 + Math.max(altura(n.izquierdo), altura(n.derecho));
    }

    private int altura(NodoAVL<T> n) {
        return n == null ? 0 : n.altura;
    }

    private int factorBalance(NodoAVL<T> n) {
        return n == null ? 0 : altura(n.izquierdo) - altura(n.derecho);
    }

    private NodoAVL<T> balancear(NodoAVL<T> nodo) {
        int fb = factorBalance(nodo);
        
        // Casos de desbalance
        if (fb > 1) {
            if (factorBalance(nodo.izquierdo) >= 0) {
                return rotarDerecha(nodo);
            } else {
                nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
                return rotarDerecha(nodo);
            }
        }
        
        if (fb < -1) {
            if (factorBalance(nodo.derecho) <= 0) {
                return rotarIzquierda(nodo);
            } else {
                nodo.derecho = rotarDerecha(nodo.derecho);
                return rotarIzquierda(nodo);
            }
        }
        
        return nodo;
    }

    private NodoAVL<T> rotarDerecha(NodoAVL<T> y) {
        NodoAVL<T> x = y.izquierdo;
        NodoAVL<T> T2 = x.derecho;
        
        x.derecho = y;
        y.izquierdo = T2;
        
        actualizarAltura(y);
        actualizarAltura(x);
        
        return x;
    }

    private NodoAVL<T> rotarIzquierda(NodoAVL<T> x) {
        NodoAVL<T> y = x.derecho;
        NodoAVL<T> T2 = y.izquierdo;
        
        y.izquierdo = x;
        x.derecho = T2;
        
        actualizarAltura(x);
        actualizarAltura(y);
        
        return y;
    }

    // Método de visualización con JGraphT/JGraphX
    public void graficar() {
        // Crear grafo dirigido para representar el árbol
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Map<String, NodoAVL<T>> nodeMap = new HashMap<>();
        
        // Recorrido en amplitud para agregar nodos y aristas
        if (raiz != null) {
            Queue<NodoAVL<T>> cola = new LinkedList<>();
            cola.add(raiz);
            graph.addVertex(raiz.dato.toString());
            nodeMap.put(raiz.dato.toString(), raiz);
            
            while (!cola.isEmpty()) {
                NodoAVL<T> actual = cola.poll();
                
                // Hijo izquierdo
                if (actual.izquierdo != null) {
                    String childId = actual.izquierdo.dato.toString();
                    graph.addVertex(childId);
                    graph.addEdge(actual.dato.toString(), childId);
                    cola.add(actual.izquierdo);
                    nodeMap.put(childId, actual.izquierdo);
                }
                
                // Hijo derecho
                if (actual.derecho != null) {
                    String childId = actual.derecho.dato.toString();
                    graph.addVertex(childId);
                    graph.addEdge(actual.dato.toString(), childId);
                    cola.add(actual.derecho);
                    nodeMap.put(childId, actual.derecho);
                }
            }
        }
        
        // Exportar a formato DOT
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> attrs = new HashMap<>();
            String label = v + "\\nAltura: " + nodeMap.get(v).altura;
            attrs.put("label", DefaultAttribute.createAttribute(label));
            attrs.put("shape", DefaultAttribute.createAttribute("circle"));
            attrs.put("fillcolor", DefaultAttribute.createAttribute("#90EE90"));
            attrs.put("style", DefaultAttribute.createAttribute("filled"));
            return attrs;
        });
        
        exporter.setEdgeAttributeProvider(e -> {
            Map<String, Attribute> attrs = new HashMap<>();
            String source = graph.getEdgeSource(e);
            String target = graph.getEdgeTarget(e);

            NodoAVL<T> padre = nodeMap.get(source);
            NodoAVL<T> hijo = nodeMap.get(target);

            String etiqueta = "";
            if (padre != null && hijo != null) {
                if (padre.izquierdo == hijo) {
                    etiqueta = "L";
                } else if (padre.derecho == hijo) {
                    etiqueta = "R";
                }
            }

            attrs.put("label", DefaultAttribute.createAttribute(etiqueta));
            return attrs;
        });


        StringWriter writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        String dotSource = writer.toString();

        try {
            String htmlContent = "<!DOCTYPE html>"
                + "<html><head><title>AVL Tree</title>"
                + "<script src='https://d3js.org/d3.v7.min.js'></script>"
                + "<script src='https://cdn.jsdelivr.net/npm/@hpcc-js/wasm@1.12.5/dist/index.min.js'></script>"
                + "<script src='https://cdn.jsdelivr.net/npm/d3-graphviz@3.1.0/build/d3-graphviz.min.js'></script>"
                + "</head><body style='margin:0;padding:0;height:100vh;'>"
                + "<div id='graph' style='width:100%;height:100%;'></div>"
                + "<script>"
                + "d3.select('#graph').graphviz()"
                + ".fit(true)"
                // Corrección 2: Escapar correctamente el DOT
                + ".renderDot(`" + dotSource.replace("`", "\\`").replace("\n", " ") + "`);"
                + "</script></body></html>";

            // Guarda en archivo
            String filePath = "avltree_output.html";
            java.nio.file.Files.writeString(java.nio.file.Paths.get(filePath), htmlContent);

            // Abre en navegador
            java.awt.Desktop.getDesktop().browse(new java.io.File(filePath).toURI());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        AVLTree<Integer> arbol = new AVLTree<>();
        arbol.insertar(100);
        arbol.insertar(200);
        arbol.insertar(300);
        arbol.insertar(400);
        arbol.insertar(500);
        arbol.insertar(50);
        arbol.insertar(25);
        arbol.insertar(350);
        arbol.insertar(375);
        arbol.insertar(360);
        arbol.insertar(355);
        arbol.insertar(150);
        arbol.insertar(175);
        arbol.insertar(120);
        arbol.insertar(190);
        
        arbol.graficar();
    }
}