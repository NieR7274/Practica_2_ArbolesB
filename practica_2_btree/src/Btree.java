public  class Btree {

    int m = 4; //llaves
    int r = m - 1 ; // hijos
    int q = (int) Math.ceil((double) m / 2); // minimo de llaves de los hijos


     class Vertice{

        int n; //numero de llaves que se tienen actualmete

       int[] llaves = new int[m]; //llaves guardadas

        Vertice[] hijos = new Vertice[r]; //hijos
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

    public  boolean buscar(int n){

        return buscar(n, raiz);

    }

    public  boolean buscar  (int n, Vertice v){

        return false;
        
    }

    public  void imprimir(){}




}
