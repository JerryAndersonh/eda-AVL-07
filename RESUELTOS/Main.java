public class Main {
    public static void main(String[] args) {
        AVLTree<Integer> avlTree = new AVLTree<>();
        
        System.out.println("Insertando valores: 100 , 200 , 300 , 400 , 500 , 50 , 25 , 350 , 375 , 360 , 355 , 150 , 175 , 120 , 190 ");
        avlTree.insert(100);
        avlTree.insert(200);
        avlTree.insert(300);
        avlTree.insert(400);
        avlTree.insert(500);
        avlTree.insert(50);
        avlTree.insert(25);
        avlTree.insert(350);
        avlTree.insert(375);
        avlTree.insert(360);
        avlTree.insert(355);
        avlTree.insert(150);
        avlTree.insert(175);
        avlTree.insert(120);
        avlTree.insert(190);
        
        System.out.println("Recorrido inorden del árbol AVL:");
        avlTree.inOrder();
    }
}