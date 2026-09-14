public  class Btree {

    int m = 4; // maximo de hijos
    int r = m - 1 ; // maximo de llaves
    int q = (int) Math.ceil((double) m / 2) -1 ; // minimo de llaves de los hijos = 1


     class Vertice{

        int n = 0; //numero de llaves que se tienen actualmete

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

    public  void insertar(int n){

        if (raiz == null){
            raiz = new Vertice(0);
        }

        insertar(n, raiz);


    }

    public void insertar(int n, Vertice v){

        for (int i = 0; i < v.n; i++){ //se comprueba si la llave ya existe

            if (n==v.llaves[i]){
                return; //la llave ya existe
            }
            else if (n<v.llaves[i]){ //si la llave es menor que la llave en el nodo actual, se inserta en el hijo correspondiente
                if (v.esHoja){
                    //insertar en el nodo actual
                    for (int j = v.n; j > i; j--){ //v.n empiiza a contar en 1 e i en 0 por lo es correcto 
                        v.llaves[j] = v.llaves[j-1];
                    }
                    v.llaves[i] = n;
                    v.n++;
                    return;
                } else {
                    insertar(n, v.hijos[i]);
                    return;
                }
            }
        }

        if (v.esHoja){ //si la llave es mayor que todas las llaves en el nodo actual, se inserta en el hijo correspondiente
            v.llaves[v.n] = n;
            v.n++;
            return;
        } else {
            insertar(n, v.hijos[v.n]);
            return;
        }

        


    }

    

    public  void eliminar(int n){}

    public boolean buscar(int n) {

    if (raiz == null) {
        return false; // Árbol vacío
    }

    boolean encontrado = false;

    if  (buscar(n , raiz) !=null) {
        encontrado = true;
    } else {
        encontrado = false;
    }
    return encontrado;
}

    public Vertice buscar(int n, Vertice v) {
        // 1. Caso base: si el nodo es nulo (árbol vacío o hijo inexistente)
        if (v == null) {
            return null; 
        }

        int i = 0;

        // 2. Recorremos las llaves del nodo actual
        for (i = 0; i < v.n; i++) {

            // Si encontramos la llave en este nodo
            if (n == v.llaves[i]) {
                return v; 
            } 
            
            // Si el valor buscado es menor, pertenece al intervalo del hijo i
            if (n < v.llaves[i]) {
                if (v.esHoja) {
                    return null; // Si es hoja y no estaba, no existe[cite: 1]
                } else {
                    return buscar(n, v.hijos[i]); // Seguir por el hijo i[cite: 1]
                }
            }
        }

        // 3. Si 'n' es mayor que TODAS las llaves del nodo:
        // Debemos revisar el último hijo 
        if (v.esHoja) {
            return null;
        } else {
            return buscar(n, v.hijos[i]); // 'i' aquí vale exactamente 'v.n'[cite: 1]
        }
    }

    public  void imprimir(){}




}
