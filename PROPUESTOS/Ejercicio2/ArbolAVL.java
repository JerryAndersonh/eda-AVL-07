public class ArbolAVL<T extends Comparable<T>> {
    private NodoAVL<T> raiz;

    // Constructor
    public ArbolAVL() {
        raiz = null;
    }

    // Método para obtener la altura de un nodo
    private int altura(NodoAVL<T> nodo) {
        if (nodo == null)
            return 0;
        return nodo.altura;
    }

    // Método para obtener el balance de un nodo
    private int balance(NodoAVL<T> nodo) {
        if (nodo == null)
            return 0;
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Rotación simple a la derecha
    private NodoAVL<T> rotacionSimpleDerecha(NodoAVL<T> y) {
        NodoAVL<T> x = y.izquierdo;
        NodoAVL<T> T2 = x.derecho;

        // Realizar rotación
        x.derecho = y;
        y.izquierdo = T2;

        // Actualizar alturas
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;

        return x;
    }

    // Rotación simple a la izquierda
    private NodoAVL<T> rotacionSimpleIzquierda(NodoAVL<T> x) {
        NodoAVL<T> y = x.derecho;
        NodoAVL<T> T2 = y.izquierdo;

        // Realizar rotación
        y.izquierdo = x;
        x.derecho = T2;

        // Actualizar alturas
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y;
    }

    // Balancear a la izquierda
    private NodoAVL<T> balancearIzquierda(NodoAVL<T> nodo) {
        if (balance(nodo) < -1) {
            if (balance(nodo.derecho) > 0) {
                nodo.derecho = rotacionSimpleDerecha(nodo.derecho);
            }
            return rotacionSimpleIzquierda(nodo);
        }
        return nodo;
    }

    // Balancear a la derecha
    private NodoAVL<T> balancearDerecha(NodoAVL<T> nodo) {
        if (balance(nodo) > 1) {
            if (balance(nodo.izquierdo) < 0) {
                nodo.izquierdo = rotacionSimpleIzquierda(nodo.izquierdo);
            }
            return rotacionSimpleDerecha(nodo);
        }
        return nodo;
    }

    // Insertar un elemento
    public void insert(T dato) {
        raiz = insert(raiz, dato);
    }

    private NodoAVL<T> insert(NodoAVL<T> nodo, T dato) {
        // 1. Inserción normal en un BST
        if (nodo == null)
            return new NodoAVL<>(dato);

        if (dato.compareTo(nodo.dato) < 0)
            nodo.izquierdo = insert(nodo.izquierdo, dato);
        else if (dato.compareTo(nodo.dato) > 0)
            nodo.derecho = insert(nodo.derecho, dato);
        else // No se permiten duplicados
            return nodo;

        // 2. Actualizar altura del nodo actual
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        // 3. Obtener el factor de balance para ver si está desbalanceado
        int balance = balance(nodo);

        // Si está desbalanceado, hay 4 casos posibles

        // Caso izquierda-izquierda
        if (balance > 1 && dato.compareTo(nodo.izquierdo.dato) < 0)
            return rotacionSimpleDerecha(nodo);

        // Caso derecha-derecha
        if (balance < -1 && dato.compareTo(nodo.derecho.dato) > 0)
            return rotacionSimpleIzquierda(nodo);

        // Caso izquierda-derecha
        if (balance > 1 && dato.compareTo(nodo.izquierdo.dato) > 0) {
            nodo.izquierdo = rotacionSimpleIzquierda(nodo.izquierdo);
            return rotacionSimpleDerecha(nodo);
        }

        // Caso derecha-izquierda
        if (balance < -1 && dato.compareTo(nodo.derecho.dato) < 0) {
            nodo.derecho = rotacionSimpleDerecha(nodo.derecho);
            return rotacionSimpleIzquierda(nodo);
        }

        return nodo;
    }

    // Eliminar un elemento
    public void remove(T dato) {
        raiz = remove(raiz, dato);
    }

    private NodoAVL<T> remove(NodoAVL<T> nodo, T dato) {
        // Paso 1: Eliminación estándar en BST
        if (nodo == null)
            return nodo;

        if (dato.compareTo(nodo.dato) < 0)
            nodo.izquierdo = remove(nodo.izquierdo, dato);
        else if (dato.compareTo(nodo.dato) > 0)
            nodo.derecho = remove(nodo.derecho, dato);
        else {
            // Nodo con un solo hijo o sin hijos
            if ((nodo.izquierdo == null) || (nodo.derecho == null)) {
                NodoAVL<T> temp = null;
                if (temp == nodo.izquierdo)
                    temp = nodo.derecho;
                else
                    temp = nodo.izquierdo;

                // Caso sin hijos
                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else // Caso con un hijo
                    nodo = temp;
            } else {
                // Nodo con dos hijos: obtener el sucesor inorden (mínimo en el subárbol derecho)
                NodoAVL<T> temp = min(nodo.derecho);

                // Copiar el dato del sucesor inorden
                nodo.dato = temp.dato;

                // Eliminar el sucesor inorden
                nodo.derecho = remove(nodo.derecho, temp.dato);
            }
        }

        // Si el árbol tenía solo un nodo, retornar
        if (nodo == null)
            return nodo;

        // Paso 2: Actualizar la altura del nodo actual
        nodo.altura = Math.max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;

        // Paso 3: Balancear el nodo
        return balancear(nodo);
    }

    // Balancear el nodo después de inserción/eliminación
    private NodoAVL<T> balancear(NodoAVL<T> nodo) {
        // Obtener el factor de balance
        int balance = balance(nodo);

        // Caso izquierda-izquierda
        if (balance > 1 && balance(nodo.izquierdo) >= 0)
            return rotacionSimpleDerecha(nodo);

        // Caso izquierda-derecha
        if (balance > 1 && balance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionSimpleIzquierda(nodo.izquierdo);
            return rotacionSimpleDerecha(nodo);
        }

        // Caso derecha-derecha
        if (balance < -1 && balance(nodo.derecho) <= 0)
            return rotacionSimpleIzquierda(nodo);

        // Caso derecha-izquierda
        if (balance < -1 && balance(nodo.derecho) > 0) {
            nodo.derecho = rotacionSimpleDerecha(nodo.derecho);
            return rotacionSimpleIzquierda(nodo);
        }

        return nodo;
    }

    // Buscar un elemento
    public boolean search(T dato) {
        return search(raiz, dato);
    }

    private boolean search(NodoAVL<T> nodo, T dato) {
        if (nodo == null)
            return false;

        if (dato.compareTo(nodo.dato) < 0)
            return search(nodo.izquierdo, dato);
        else if (dato.compareTo(nodo.dato) > 0)
            return search(nodo.derecho, dato);
        else
            return true;
    }

    // Encontrar el mínimo
    public T min() {
        if (raiz == null)
            return null;
        return min(raiz).dato;
    }

    private NodoAVL<T> min(NodoAVL<T> nodo) {
        NodoAVL<T> actual = nodo;
        while (actual.izquierdo != null)
            actual = actual.izquierdo;
        return actual;
    }

    // Encontrar el máximo
    public T max() {
        if (raiz == null)
            return null;
        return max(raiz).dato;
    }

    private NodoAVL<T> max(NodoAVL<T> nodo) {
        NodoAVL<T> actual = nodo;
        while (actual.derecho != null)
            actual = actual.derecho;
        return actual;
    }

    // Encontrar predecesor
    public T predecesor(T dato) {
        NodoAVL<T> predecesor = null;
        NodoAVL<T> actual = raiz;

        while (actual != null) {
            if (dato.compareTo(actual.dato) > 0) {
                predecesor = actual;
                actual = actual.derecho;
            } else {
                actual = actual.izquierdo;
            }
        }

        return predecesor != null ? predecesor.dato : null;
    }

    // Encontrar sucesor
    public T sucesor(T dato) {
        NodoAVL<T> sucesor = null;
        NodoAVL<T> actual = raiz;

        while (actual != null) {
            if (dato.compareTo(actual.dato) < 0) {
                sucesor = actual;
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
        }

        return sucesor != null ? sucesor.dato : null;
    }

    // Recorrido inorden
    public void inOrder() {
        inOrder(raiz);
        System.out.println();
    }

    private void inOrder(NodoAVL<T> nodo) {
        if (nodo != null) {
            inOrder(nodo.izquierdo);
            System.out.print(nodo.dato + " ");
            inOrder(nodo.derecho);
        }
    }

    // Recorrido preorden
    public void preOrder() {
        preOrder(raiz);
        System.out.println();
    }

    private void preOrder(NodoAVL<T> nodo) {
        if (nodo != null) {
            System.out.print(nodo.dato + " ");
            preOrder(nodo.izquierdo);
            preOrder(nodo.derecho);
        }
    }

    // Recorrido postorden
    public void postOrder() {
        postOrder(raiz);
        System.out.println();
    }

    private void postOrder(NodoAVL<T> nodo) {
        if (nodo != null) {
            postOrder(nodo.izquierdo);
            postOrder(nodo.derecho);
            System.out.print(nodo.dato + " ");
        }
    }

    // Verificar si el árbol está vacío
    public boolean isEmpty() {
        return raiz == null;
    }

    // Destruir el árbol
    public void destroy() {
        raiz = null;
    }

    // Método para imprimir el árbol (auxiliar para pruebas)
    public void print() {
        print(raiz, 0);
    }

    private void print(NodoAVL<T> nodo, int nivel) {
        if (nodo != null) {
            print(nodo.derecho, nivel + 1);
            for (int i = 0; i < nivel; i++)
                System.out.print("   ");
            System.out.println(nodo.dato);
            print(nodo.izquierdo, nivel + 1);
        }
    }
}