public class Btree {

    int m = 4; // maximo de hijos
    int r = m - 1; // maximo de llaves
    int q = (int) Math.ceil((double) m / 2) - 1; // minimo de llaves de los hijos = 1

    class Vertice {
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

    public void insertar(int n){
        if (raiz == null){
            raiz = new Vertice(0);
        }

        insertar(n, raiz);

        // Si la raiz se desborda, hacemos split de la raiz
        if (raiz.n > r) {
            split(raiz);
        }
    }

    public void insertar(int n, Vertice v){
        for (int i = 0; i < v.n; i++){ //se comprueba si la llave ya existe
            if (n == v.llaves[i]){
                return; //la llave ya existe
            }
            else if (n < v.llaves[i]){ //si la llave es menor que la llave en el nodo actual, se inserta en el hijo correspondiente
                if (v.esHoja){
                    //insertar en el nodo actual
                    for (int j = v.n; j > i; j--){ //v.n empiiza a contar en 1 e i en 0 por lo es correcto 
                        v.llaves[j] = v.llaves[j-1];
                    }
                    v.llaves[i] = n;
                    v.n++;
                    
                    // Si el nodo se desborda, realizamos el split
                    if (v.n > r) {
                        split(v);
                    }
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
            
            // Si el nodo se desborda, realizamos el split
            if (v.n > r) {
                split(v);
            }
            return;
        } else {
            insertar(n, v.hijos[v.n]);
            return;
        }
    }

    public void split(Vertice v){
        int mid = v.n / 2; //indice de la llave mediana (para m=4, mid=2, que es la 3era llave)
        int llavePromovida = v.llaves[mid];

        Vertice nuevo = new Vertice(0); //nuevo nodo
        nuevo.esHoja = v.esHoja; //el nuevo nodo es hoja si el nodo actual es hoja

        // Se copian las llaves derechas del nodo actual al nuevo nodo (desde mid + 1)
        for (int i = mid + 1; i < v.n; i++){ 
            nuevo.llaves[nuevo.n] = v.llaves[i];
            nuevo.n++;
        }

        // Si el nodo actual no es hoja, se copian los hijos correspondientes al nuevo nodo
        if (!v.esHoja){ 
            for (int i = mid + 1; i <= v.n; i++){
                nuevo.hijos[i - mid - 1] = v.hijos[i];
                if (nuevo.hijos[i - mid - 1] != null){
                    nuevo.hijos[i - mid - 1].padre = nuevo;
                }
                v.hijos[i] = null; // Limpiamos referencia anterior
            }
        }

        // Actualizamos el numero de llaves del nodo actual (se queda con las de la izquierda)
        v.n = mid; 

        if (v.padre == null){ //si el nodo actual es la raiz, se crea una nueva raiz
            Vertice nuevaRaiz = new Vertice(0);
            nuevaRaiz.esHoja = false;
            nuevaRaiz.llaves[0] = llavePromovida;
            nuevaRaiz.n++;
            nuevaRaiz.hijos[0] = v;
            nuevaRaiz.hijos[1] = nuevo;
            v.padre = nuevaRaiz;
            nuevo.padre = nuevaRaiz;
            raiz = nuevaRaiz;
        } else { //si el nodo actual no es la raiz, se inserta la llave mediana directamente en el padre
            Vertice padre = v.padre;
            
            // Encontramos la posicion donde debe ir la llave mediana en el padre
            int i = 0;
            while (i < padre.n && llavePromovida > padre.llaves[i]) {
                i++;
            }

            // Desplazamos las llaves del padre para hacer espacio
            for (int j = padre.n; j > i; j--){
                padre.llaves[j] = padre.llaves[j - 1];
            }
            padre.llaves[i] = llavePromovida;
            padre.n++;

            // Desplazamos los hijos del padre para acomodar al nodo nuevo a la derecha de v
            for (int j = padre.n; j > i + 1; j--){
                padre.hijos[j] = padre.hijos[j - 1];
            }
            padre.hijos[i + 1] = nuevo;
            nuevo.padre = padre;

            // Si el padre también se desborda, propagamos el split hacia arriba
            if (padre.n > r) {
                split(padre);
            }
        }
    }

    public void eliminar(int n){}

    public boolean buscar(int n) {
        if (raiz == null) {
            return false; // Árbol vacío
        }

        boolean encontrado = false;

        if (buscar(n, raiz) != null) {
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
                    return null; // Si es hoja y no estaba, no existe
                } else {
                    return buscar(n, v.hijos[i]); // Seguir por el hijo i
                }
            }
        }

        // 3. Si 'n' es mayor que TODAS las llaves del nodo:
        // Debemos revisar el último hijo 
        if (v.esHoja) {
            return null;
        } else {
            return buscar(n, v.hijos[i]); // 'i' aquí vale exactamente 'v.n'
        }
    }

    public void imprimir(){}
}