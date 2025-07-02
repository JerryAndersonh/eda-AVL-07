class NodoAVL<T extends Comparable<T>> {
    T dato;
    NodoAVL<T> izquierdo;
    NodoAVL<T> derecho;
    int altura;

    public NodoAVL(T dato) {
        this.dato = dato;
        this.altura = 1; // Nuevo nodo inicialmente es una hoja con altura 1
    }
}