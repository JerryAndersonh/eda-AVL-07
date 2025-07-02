public class Node<T> {
    private T data;
    private Node<T> left;
    private Node<T> right;
    private int bf; // Factor de balance

    public Node(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.bf = 0;
    }

    // Getters y setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public int getBf() {
        return bf;
    }

    public void setBf(int bf) {
        this.bf = bf;
    }
}