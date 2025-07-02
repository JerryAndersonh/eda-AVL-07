public class AVLTree<T extends Comparable<T>> {
    private Node<T> root;

    public AVLTree() {
        root = null;
    }

    // Método público para insertar
    public void insert(T data) {
        root = insert(root, data);
    }

    // Método privado recursivo para insertar
    private Node<T> insert(Node<T> node, T data) {
        if (node == null) {
            return new Node<>(data);
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(insert(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(insert(node.getRight(), data));
        } else {
            return node; // No se permiten duplicados
        }

        // Actualizar factor de balance
        updateBalance(node);

        // Balancear el árbol si es necesario
        if (node.getBf() < -1 || node.getBf() > 1) {
            return balance(node);
        }

        return node;
    }

    // Actualizar factor de balance
    private void updateBalance(Node<T> node) {
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        node.setBf(rightHeight - leftHeight);
    }

    // Calcular altura
    private int height(Node<T> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    // Balancear el nodo
    private Node<T> balance(Node<T> node) {
        if (node.getBf() > 1) {
            if (node.getRight().getBf() < 0) {
                node.setRight(rotateSR(node.getRight()));
            }
            return rotateSL(node);
        } else if (node.getBf() < -1) {
            if (node.getLeft().getBf() > 0) {
                node.setLeft(rotateSL(node.getLeft()));
            }
            return rotateSR(node);
        }
        return node;
    }

    // Rotación simple a la izquierda (SL)
    private Node<T> rotateSL(Node<T> node) {
        Node<T> newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        updateBalance(node);
        updateBalance(newRoot);
        return newRoot;
    }

    // Rotación simple a la derecha (SR)
    private Node<T> rotateSR(Node<T> node) {
        Node<T> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        updateBalance(node);
        updateBalance(newRoot);
        return newRoot;
    }

    // Balancear a la izquierda (método solicitado)
    private Node<T> balanceToLeft(Node<T> node) {
        Node<T> hijo = node.getRight();
        switch (hijo.getBf()) {
            case 1:
                node.setBf(0);
                hijo.setBf(0);
                node = rotateSL(node);
                break;
            case -1:
                Node<T> nieto = hijo.getLeft();
                switch (nieto.getBf()) {
                    case -1:
                        node.setBf(0);
                        hijo.setBf(1);
                        break;
                    case 0:
                        node.setBf(0);
                        hijo.setBf(0);
                        break;
                    case 1:
                        node.setBf(-1);
                        hijo.setBf(0);
                        break;
                }
                nieto.setBf(0);
                node.setRight(rotateSR(hijo));
                node = rotateSL(node);
        }
        return node;
    }

    // Método para imprimir el árbol (inorden)
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node<T> node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(node.getData() + " ");
            inOrder(node.getRight());
        }
    }
}