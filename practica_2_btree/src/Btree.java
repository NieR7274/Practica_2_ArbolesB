import java.util.LinkedList;
import java.util.Queue;

public class Btree {

    int m = 4; // maximo de hijos
    int r = m - 1; // maximo de llaves
    int q = (int) Math.ceil((double) m / 2) - 1; // minimo de llaves de los hijos = 1

    class Vertice {

        int n = 0; // numero de llaves que se tienen actualmente
        int[] llaves = new int[m]; // llaves guardadas
        Vertice[] hijos = new Vertice[r + 2]; // hijos maximos, mas 2 en caso de desbordamiento
        Vertice padre; // padre
        boolean esHoja = true; // es hoja

        public Vertice(int n) {
            this.n = n;
        }

        public String toString() {
            String contenido = "[";

            for (int i = 0; i < n; i++) {
                contenido += llaves[i];
                // Agregamos barra solo si no es la ultima llave
                if (i < n - 1) {
                    contenido += " | ";
                }
            }

            contenido += "]";
            return contenido;
        }
    }

    class Level {
        String[] Vertices;
        int level;

        public Level(String[] vertices){
            this.Vertices = vertices;
        }

        public String toString(){ 
            String viewLevel;
            viewLevel = "Nivel: " + level + " =>";

            for (int i = 0; i < Vertices.length; i++) {
                viewLevel += " " + Vertices[i];
            }

            return viewLevel;
        }
    }

    protected Vertice raiz; // raiz del arbol  
   
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

        for (int i = 0; i < v.n; i++){ // se comprueba si la llave ya existe
            if (n == v.llaves[i]){
                return; // la llave ya existe
            }
            else if (n < v.llaves[i]){ // si la llave es menor que la llave en el nodo actual, se inserta en el hijo correspondiente
                if (v.esHoja){
                    // insertar en el nodo actual
                    for (int j = v.n; j > i; j--){ // v.n empieza a contar en 1 e i en 0 por lo que es correcto
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

        if (v.esHoja){ // si la llave es mayor que todas las llaves en el nodo actual, se inserta en el hijo correspondiente
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

        // indice de la llave mediana, usamos division entera para asegurar indices exactos en el desbordamiento (5 / 2 = 2)
        int mid = v.n / 2;
        int llavePromovida = v.llaves[mid];

        Vertice nuevo = new Vertice(0); // nuevo nodo
        nuevo.esHoja = v.esHoja; // el nuevo nodo es hoja si el nodo actual es hoja

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

        if (v.padre == null){ // si el nodo actual es la raiz, se crea una nueva raiz
            Vertice nuevaRaiz = new Vertice(0);
            nuevaRaiz.esHoja = false;
            nuevaRaiz.llaves[0] = llavePromovida;
            nuevaRaiz.n++;
            nuevaRaiz.hijos[0] = v;
            nuevaRaiz.hijos[1] = nuevo;
            v.padre = nuevaRaiz;
            nuevo.padre = nuevaRaiz;
            raiz = nuevaRaiz;
        }
        else { // si el nodo actual no es la raiz, se inserta la llave mediana directamente en el padre
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

            // Si el padre tambien se desborda, propagamos el split hacia arriba
            if (padre.n > r) {
                split(padre);
            }
        }
    }

    public void eliminar(int n){
        if (raiz == null) return;
        eliminar(n, raiz);

        // 6. Tratar el caso especial de la raiz:
        // Si la raiz queda sin claves y tiene un unico hijo, ese hijo se convierte en la nueva raiz.
        if (raiz.n == 0 && !raiz.esHoja) {
            raiz = raiz.hijos[0];
            raiz.padre = null;
        } else if (raiz.n == 0 && raiz.esHoja) {
            raiz = null; // Arbol quedo vacio
        }
    }

    // basado en las notas del 3 de septiembre
    public void eliminar(int n, Vertice v){
        int i = 0;
        while (i < v.n && n > v.llaves[i]) {
            i++;
        }

        // 1. Si encontramos la clave en el nodo actual 'v'
        if (i < v.n && n == v.llaves[i]) {
            if (v.esHoja) {
                // 2. Si es hoja se elimina la llave reacomodando las llaves
                for (int j = i; j < v.n - 1; j++){
                    v.llaves[j] = v.llaves[j+1];
                }
                v.n--;
            } else {
                // 3. Si no es hoja, se busca el predecesor o sucesor segun las notas
                Vertice hijoIzq = v.hijos[i];
                Vertice hijoDer = v.hijos[i + 1];

                if (hijoIzq.n > q) {
                    // a) Si el hijo izquierdo contiene mas de q claves, usamos el predecesor
                    Vertice pred = hijoIzq;
                    while (!pred.esHoja) {
                        pred = pred.hijos[pred.n]; // El mayor del hijo izquierdo
                    }
                    int clavePred = pred.llaves[pred.n - 1];
                    v.llaves[i] = clavePred;
                    eliminar(clavePred, hijoIzq);
                } else if (hijoDer.n > q) {
                    // b) Si el hijo derecho contiene mas de q claves, usamos el sucesor
                    Vertice suc = hijoDer;
                    while (!suc.esHoja) {
                        suc = suc.hijos[0]; // El menor del hijo derecho
                    }
                    int claveSuc = suc.llaves[0];
                    v.llaves[i] = claveSuc;
                    eliminar(claveSuc, hijoDer);
                } else {
                    // c) Si ninguno tiene mas de q claves, fusionamos ambos hijos utilizando la clave actual
                    fusionarHijosEnNodo(hijoIzq, hijoDer, i, v);
                    eliminar(n, hijoIzq); // Continuar la eliminacion en el nodo fusionado
                }
            }
        } else {
            // Si la clave no esta en este nodo, descendemos por el hijo correspondiente
            if (v.esHoja) {
                return; // La clave no existe en el arbol
            }
            Vertice hijoDestino = v.hijos[i];
            eliminar(n, hijoDestino);
        }

        // 4. Reparar subocupacion (Underflow) si el nodo no es la raiz y tiene menos de q claves
        if (v != raiz && v.n < q) {
            repararSubocupacion(v);
        }
    }

    // Metodo auxiliar para fusionar dos hijos y una clave del padre (paso 3c de las notas)
    private void fusionarHijosEnNodo(Vertice hijoIzq, Vertice hijoDer, int indiceClavePadre, Vertice padre) {
        // Traer la clave del padre al hijo izquierdo
        hijoIzq.llaves[hijoIzq.n] = padre.llaves[indiceClavePadre];
        hijoIzq.n++;

        // Copiar las llaves del hijo derecho al hijo izquierdo
        for (int j = 0; j < hijoDer.n; j++) {
            hijoIzq.llaves[hijoIzq.n] = hijoDer.llaves[j];
            hijoIzq.n++;
        }

        // Copiar los hijos del hijo derecho al hijo izquierdo (si no es hoja)
        if (!hijoIzq.esHoja) {
            for (int j = 0; j <= hijoDer.n; j++) {
                hijoIzq.hijos[hijoIzq.n - hijoDer.n + j] = hijoDer.hijos[j];
                if (hijoDer.hijos[j] != null) {
                    hijoDer.hijos[j].padre = hijoIzq;
                }
            }
        }

        // Remover la clave y el apuntador del padre
        for (int j = indiceClavePadre; j < padre.n - 1; j++) {
            padre.llaves[j] = padre.llaves[j + 1];
            padre.hijos[j + 1] = padre.hijos[j + 2];
        }
        padre.n--;
        padre.hijos[padre.n + 1] = null;
    }

    // Metodo para reparar la subocupacion (Paso 4 de las notas)
    private void repararSubocupacion(Vertice v) {
        Vertice padre = v.padre;
        if (padre == null) return;

        // Encontrar el indice del hijo v en el padre
        int idxHijo = 0;
        while (idxHijo <= padre.n && padre.hijos[idxHijo] != v) {
            idxHijo++;
        }

        Vertice hermanoIzq = (idxHijo > 0) ? padre.hijos[idxHijo - 1] : null;
        Vertice hermanoDer = (idxHijo < padre.n) ? padre.hijos[idxHijo + 1] : null;

        // a) Redistribucion con el hermano izquierdo si tiene mas de q claves
        if (hermanoIzq != null && hermanoIzq.n > q) {
            // Mover espacio en v para la clave que baja del padre
            for (int j = v.n; j > 0; j--) {
                v.llaves[j] = v.llaves[j - 1];
            }
            if (!v.esHoja) {
                for (int j = v.n + 1; j > 0; j--) {
                    v.hijos[j] = v.hijos[j - 1];
                }
            }

            v.llaves[0] = padre.llaves[idxHijo - 1]; // Baja clave del padre
            padre.llaves[idxHijo - 1] = hermanoIzq.llaves[hermanoIzq.n - 1]; // Sube clave del hermano izq

            if (!v.esHoja) {
                v.hijos[0] = hermanoIzq.hijos[hermanoIzq.n];
                if (v.hijos[0] != null) v.hijos[0].padre = v;
                hermanoIzq.hijos[hermanoIzq.n] = null;
            }

            v.n++;
            hermanoIzq.n--;
        } 
        // a) Redistribucion con el hermano derecho si tiene mas de q claves
        else if (hermanoDer != null && hermanoDer.n > q) {
            v.llaves[v.n] = padre.llaves[idxHijo]; // Baja clave del padre
            padre.llaves[idxHijo] = hermanoDer.llaves[0]; // Sube clave del hermano der

            if (!v.esHoja) {
                v.hijos[v.n + 1] = hermanoDer.hijos[0];
                if (v.hijos[v.n + 1] != null) v.hijos[v.n + 1].padre = v;
               
                // Corregir hijos del hermano derecho
                for (int j = 0; j < hermanoDer.n; j++) {
                    hermanoDer.hijos[j] = hermanoDer.hijos[j + 1];
                }
                hermanoDer.hijos[hermanoDer.n] = null;
            }

            // Reacomodar llaves del hermano derecho
            for (int j = 0; j < hermanoDer.n - 1; j++) {
                hermanoDer.llaves[j] = hermanoDer.llaves[j + 1];
            }

            v.n++;
            hermanoDer.n--;
        } 
        // b) Fusion si ningun hermano puede ceder una clave
        else {
            if (hermanoIzq != null) {
                fusionarHijosEnNodo(hermanoIzq, v, idxHijo - 1, padre);
            } else if (hermanoDer != null) {
                fusionarHijosEnNodo(v, hermanoDer, idxHijo, padre);
            }
        }
    }

    public void fusionar(Vertice hermano1, Vertice hermano2, int llavePromovida){

        Vertice padre = hermano1.padre;

        if (padre == null){ // si es la raiz se crea una nueva con dos hijos
            Vertice nuevaRaiz = new Vertice(0);
            nuevaRaiz.esHoja = false;
            nuevaRaiz.llaves[0] = llavePromovida;
            nuevaRaiz.n++;
            nuevaRaiz.hijos[0] = hermano1;
            nuevaRaiz.hijos[1] = hermano2;
            hermano1.padre = nuevaRaiz;
            hermano2.padre = nuevaRaiz;
            raiz = nuevaRaiz;
        }
        
    }

    public boolean buscar(int n) {
        if (raiz == null) {
            return false; // Arbol vacio
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
        // 1. Caso base: si el nodo es nulo (arbol vacio o hijo inexistente)
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
        // Debemos revisar el ultimo hijo
        if (v.esHoja) {
            return null;
        } else {
            return buscar(n, v.hijos[i]); // 'i' aqui vale exactamente 'v.n'
        }
    }

    public void imprimir(){
        if (raiz == null) {
            System.out.println("Arbol vacio");
            return;
        }

        Queue<Vertice> cola = new LinkedList<>();
        cola.add(raiz);
        int nivelActual = 0;

        System.out.println("--- Estructura del Arbol B ---");

        while (!cola.isEmpty()) {
            int nodosEnNivel = cola.size();
            String[] verticesNivel = new String[nodosEnNivel];

            for (int i = 0; i < nodosEnNivel; i++) {
                Vertice actual = cola.poll();
                verticesNivel[i] = actual.toString();

                if (!actual.esHoja) {
                    for (int j = 0; j <= actual.n; j++) {
                        if (actual.hijos[j] != null) {
                            cola.add(actual.hijos[j]);
                        }
                    }
                }
            }


            Level lvl = new Level(verticesNivel);
            lvl.level = nivelActual;
            System.out.println(lvl);

            nivelActual++;
        }
        System.out.println("------------------------------");
    }
}