public class Main {
    public static void main(String[] args) {
        Btree arbol = new Btree();

        System.out.println("=== 1. PRUEBA DE INSERCIONES Y SPLITS ===");
        int[] inserciones = {20, 40, 10, 30, 50, 60, 70, 5, 15, 25, 35, 45};
        
        for (int valor : inserciones) {
            System.out.println("Insertando: " + valor);
            arbol.insertar(valor);
        }

        System.out.println("\n=== 2. IMPRESION DEL ARBOL COMPLETO ===");
        arbol.imprimir();

        System.out.println("\n=== 3. PRUEBAS DE BUSQUEDA ===");
        probarBusqueda(arbol, 35);
        probarBusqueda(arbol, 99);

        System.out.println("\n=== 4. PRUEBAS DE ELIMINACION Y REPARACION ===");
        int[] eliminaciones = {25, 10, 70, 5};
        
        for (int valor : eliminaciones) {
            System.out.println("Eliminando: " + valor);
            arbol.eliminar(valor);
        }

        System.out.println("\n=== 5. IMPRESION DEL ARBOL FINAL ===");
        arbol.imprimir();

        System.out.println("\n=== 6. VERIFICACION POST-ELIMINACION ===");
        probarBusqueda(arbol, 25);
        probarBusqueda(arbol, 35);
    }

    private static void probarBusqueda(Btree arbol, int valor) {
        boolean encontrado = arbol.buscar(valor);
        System.out.println("Buscar(" + valor + ") -> " + (encontrado ? "FOUND" : "NOT_FOUND"));
    }
}