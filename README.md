# Practica 2

Practica sobre Árboles B.

* Equipo:

  * Ángel Aaron López Cruz
  * Gabriel Lugo Rosete

1. lenguaje utilizado: Java.

2. instrucciones para ejecutar el programa:

   * Abrir una terminal en la carpeta donde se encuentran los archivos .java.
   * Compilar usando `javac *.java`
   * Ejecutar el Main con el comando `java Main`

3. explicación de cómo ejecutar los casos de prueba:

   * Al ejecutar el Main se ejecutarán los casos de prueba

4. explicación de la representación de un nodo:

   * Cada nodo del árbol esta representado por la clase Vertice
   * Un nodo contiene:
   	- n: número actual de llaves almacenadas.
   	- llaves: arreglo que contiene las llaves del nodo.
   	- hijos: arreglo de referencias a los hijos.
   	- padre: referencia al nodo padre.
   	- esHoja: representa si el nodo es una hoja.

5. explicación de qué significa m = 4 y por qué cada nodo admite máximo tres llaves:

   * m = 4 significa que un nodo puede tener como máximo 4 hijos.
   * Como entre los hijos se encuentran las llaves que separan sus rangos un nodo con m hijos puede tener como máximo m-1 llaves.
   * máximo de llaves = m - 1 = 4 - 1 = 3

6. explicación de cómo se decide qué hijo seguir durante una búsqueda:

   * Como las llaves de cada nodo están ordenadas, al buscar una llave n se recorren las llaves del nodo actual. Hay 3 casos:
   
   * Caso 1: La llave se encuentra
   Si n == llave
   La búsqueda termina, ya que la llave fue encontrada
   
   * Caso 2: La llave es menor
   Si n < llave[i]
   Se continua por el hijo i, ya que ese hijo contiene los vales entre la llave anterior y la llave[i]-
   
   *Caso 3: La llave es mayor que todas
   
   Si n es mayor que todas las llaves del nodo, se continúa por el último hijo.
   
7. explicación de qué ocurre cuando un nodo alcanza cuatro llaves:
	
   * Cuando un nodo alcanza cuatro llaves las almacena temporalmente, pero se produce un desbordamiento (overflow).
   
   * Cómo el máximo permitido es tres llaves, se realiza un split.
   
   * Se toma la llave central (k3) como la llave que será promovida y el nodo se divide en dos. La llave central se coloca en el padre.
   
   * Por ejemplo, si temporalmente tenemos:
   [10 | 20 | 30 | 40]
   
   Se toma la llave k3: 30
   como la llave que será promovida
   
   Dividir el nodo en 2:
   [10 | 20] [40]
   
   Se coloca 30 como padre.
   
8. explicación de la convención de promoción usada en la práctica:
	
   * La convención usada para la práctica es:
   Al exisitir cuatro llaves ordenadas:
   [k1,k2,k3,k4]
   -se promueve k3
   -el nodo izquierdo conserva [k1,k2]
   -el nodo derecho conserva[k4]
   
   * Es decir, se toma siempre la 3a llave, k3, que será promovida, se separan k1,k2 en el nodo izquierdo, y k4, en el nodo derecho. Finalmente se promueve la llave k3 como padre, volviéndose k1,k2 su hijo izquierdo y k4 su hijo derecho.
   
   * También se considera que si el nodo dividido es la raíz, se crea una nueva raíz que contiene la llave promovida.
   
   * Si el nodo dividido no es la raíz, la llave promovida se inserta directamente en su padre. Si el padre también se desborda, el split se propaga hacia arriba.
   
9. explicación breve de redistribución y fusión:
	
   * Se utilizan principalmente durante la eliminación, cuando un nodo queda con menos llaves de las permitidas.
   
   * Como m = 4:
   q = ceil(4 / 2) - 1
   q = 2 - 1
   q = 1
   
   Por lo tanto, los nodos que no son la raíz deben tener al menos una llave.
   
   - Redistribución
   
   Si un nodo queda con menos de q llaves, primero se intenta obtener una llave de un hermano que tenga más de q.
   
   Primero se intenta con el hermano izquierdo y después con el hermano derecho.
   
   Si un hermano tiene más de q llaves, una llave del padre baja al nodo con menos de q llaves y una llave del hermano sube al padre.
   
   - Fusión
   
   Si ninguno de los hermanos puede ceder una llave, se realiza una fusión.
   
   Para una fusión se combina:
   
   hijo izquierdo + llave del padre + hijo derecho
   
   en un solo nodo
   
   La llave baja desde el padre al nodo fusionado. Después se elimina del padre la llave usada para la fusión.

10. respuesta a las preguntas marcadas:

* ¿Por qué al insertar una llave nueva no podemos decidir el hijo únicamente comparando con la primera llave del nodo?

  * Porque un nodo puede tener varias llaves, y cada llave divide a los valores en diferentes rangos según sus hijos.
  
  * No es suficiente comparar la llave a insertar solamente con la primera, ya que la comparación con la primera llave solo nos dice que no pertenece al primer rango si es que no está dentro del primer rango, pero todavía debe determinarse si pertenece al segundo, tercer o cuarto rango, es decir al hijo 2, hijo 3, hijo 4.
  
  * Por ejemplo si se tiene:
  [20 | 40 | 60]
  Los hijos con rangos son:
  H1 los valores menores que 20
  H2 los valores entre 20 y 40
  H3 los valores entre 40 y 60
  H4 los valores mayores que 60
  
  Y se quiere insertar 50
  
  50 > 20
  
  Entonces no pertenece a H1, pero debemos determinar si pertenece a H2, H3 o H4
  
  50 > 40
  50 < 60
  
  Por lo que debe ir en H3
  
* ¿Por qué una búsqueda no debe recorrer todos los hijos de un nodo?

  * No debe recorrer todos los hijos de un nodo, ya que en los árboles B las llaves del nodo dividen los valores en rangos, por lo que solamente uno de los hijos puede contener la llave que estamos buscando.
  
  * En cada nodo se utilizan las llaves para descartar varios subárboles y continuar solamente por el hijo que puede contener el valor buscado. 
  
  


  
