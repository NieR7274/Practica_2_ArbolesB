public  class Btree {

    int m = 4; // maximo de hijos
    int r = m - 1 ; // maximo de llaves
    int q = (int) Math.ceil((double) m / 2) -1 ; // minimo de llaves de los hijos = 1


     class Vertice{

        int n; //numero de llaves que se tienen actualmete

         int[] llaves = new int[m]; //llaves guardadas

        Vertice[] hijos = new Vertice[r + 2]; //hijos maximmos, mas 2 en caso de de desbordamiento
        Vertice padre; //padreS

        boolean esHoja = true; //es hoja

        public Vertice(int n) {
            this.n = n;
        }

    }

    protected Vertice raiz; //raiz del arbol  
    
    protected Btree(){

        raiz = null;
    }

    public  void insertar(int n){}

    public  void eliminar(int n){}

    public boolean buscar(int n) {
    return buscar(n, raiz);
}

    public boolean buscar(int n, Vertice v) {
        // 1. Caso base: si el nodo es nulo (árbol vacío o hijo inexistente)
        if (v == null) {
            return false; 
        }

        int i = 0;

        // 2. Recorremos las llaves del nodo actual
        for (i = 0; i < v.n; i++) {

            // Si encontramos la llave en este nodo
            if (n == v.llaves[i]) {
                return true; 
            } 
            
            // Si el valor buscado es menor, pertenece al intervalo del hijo i
            if (n < v.llaves[i]) {
                if (v.esHoja) {
                    return false; // Si es hoja y no estaba, no existe[cite: 1]
                } else {
                    return buscar(n, v.hijos[i]); // Seguir por el hijo i[cite: 1]
                }
            }
        }

        // 3. Si 'n' es mayor que TODAS las llaves del nodo:
        // Debemos revisar el último hijo 
        if (v.esHoja) {
            return false;
        } else {
            return buscar(n, v.hijos[i]); // 'i' aquí vale exactamente 'v.n'[cite: 1]
        }
    }

    public  void imprimir(){}




}
