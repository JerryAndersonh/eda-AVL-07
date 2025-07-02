import java.util.Scanner;

public class TestAVL {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArbolAVL<Integer> arbol = new ArbolAVL<>();
        int opcion, valor;

        do {
            System.out.println("\n--- MENÚ ÁRBOL AVL ---");
            System.out.println("1. Insertar elemento");
            System.out.println("2. Eliminar elemento");
            System.out.println("3. Buscar elemento");
            System.out.println("4. Mostrar mínimo");
            System.out.println("5. Mostrar máximo");
            System.out.println("6. Mostrar predecesor");
            System.out.println("7. Mostrar sucesor");
            System.out.println("8. Recorrido InOrder");
            System.out.println("9. Recorrido PreOrder");
            System.out.println("10. Recorrido PostOrder");
            System.out.println("11. Verificar si está vacío");
            System.out.println("12. Destruir árbol");
            System.out.println("13. Mostrar árbol");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el valor a insertar: ");
                    valor = scanner.nextInt();
                    arbol.insert(valor);
                    System.out.println("Valor insertado correctamente.");
                    break;
                case 2:
                    System.out.print("Ingrese el valor a eliminar: ");
                    valor = scanner.nextInt();
                    arbol.remove(valor);
                    System.out.println("Valor eliminado correctamente.");
                    break;
                case 3:
                    System.out.print("Ingrese el valor a buscar: ");
                    valor = scanner.nextInt();
                    System.out.println("El valor " + valor + (arbol.search(valor) ? " existe" : " no existe") + " en el árbol.");
                    break;
                case 4:
                    Integer min = arbol.min();
                    System.out.println("El valor mínimo es: " + (min != null ? min : "Árbol vacío"));
                    break;
                case 5:
                    Integer max = arbol.max();
                    System.out.println("El valor máximo es: " + (max != null ? max : "Árbol vacío"));
                    break;
                case 6:
                    System.out.print("Ingrese el valor para encontrar su predecesor: ");
                    valor = scanner.nextInt();
                    Integer pred = arbol.predecesor(valor);
                    System.out.println("El predecesor es: " + (pred != null ? pred : "No existe"));
                    break;
                case 7:
                    System.out.print("Ingrese el valor para encontrar su sucesor: ");
                    valor = scanner.nextInt();
                    Integer suc = arbol.sucesor(valor);
                    System.out.println("El sucesor es: " + (suc != null ? suc : "No existe"));
                    break;
                case 8:
                    System.out.print("Recorrido InOrder: ");
                    arbol.inOrder();
                    break;
                case 9:
                    System.out.print("Recorrido PreOrder: ");
                    arbol.preOrder();
                    break;
                case 10:
                    System.out.print("Recorrido PostOrder: ");
                    arbol.postOrder();
                    break;
                case 11:
                    System.out.println("El árbol está " + (arbol.isEmpty() ? "vacío" : "no vacío"));
                    break;
                case 12:
                    arbol.destroy();
                    System.out.println("Árbol destruido correctamente.");
                    break;
                case 13:
                    System.out.println("Estructura del árbol:");
                    arbol.print();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}