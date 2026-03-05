package modelo


import java.io.{File, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.ListBuffer

class Transaccion( var Isbn:String,var Titulo:String,var Fecha:String,  var Tipo:String) extends Serializable{
  //Attribute of transaction
  var fechaT:String = Fecha
  var isbnT:String = Isbn
  var tituloT:String = Titulo
  var tipoT:String = Tipo // si el libro es comprado o vendido.

  //method override
  override def toString: String = s"Fecha: $fechaT,Isbn: $isbnT,Título: $tituloT,Tipo: $tipoT"
}
class GestionTransaccion(){
  val direccionArchivo = "transacciones.dat"

  def guardarProveedores(lista: ListBuffer[Transaccion], direccion: String): Unit = {
    try {
      val archivo = new ObjectOutputStream(new FileOutputStream(direccion))
      archivo.writeObject(lista)
      archivo.flush()
      archivo.close()
    }
  }

  def cargarProveedores(direccion: String): ListBuffer[Transaccion] = {
    try {
      var archivo: ObjectInputStream = null
      val path = new File(direccion)
      if (path.exists) {
        archivo = new ObjectInputStream(new FileInputStream(direccion))
        val lista: ListBuffer[Transaccion] = archivo.readObject.asInstanceOf[ListBuffer[Transaccion]]
        archivo.close()
        return lista
      } else {
        return new ListBuffer[Transaccion]()
      }
    } catch {
      case e@(_: ClassNotFoundException | _: IOException) =>
        println("Error al abrir la lista")
        return new ListBuffer[Transaccion]()
    }
  }

  def addTransaccion(transaccion: Transaccion): String = {
    val lista = cargarProveedores(direccionArchivo)
    lista.append(transaccion)
    guardarProveedores(lista, direccionArchivo)
    return "Agregación Exitosa"
  }

  def buscarTransacciones(isbn: String): ListBuffer[Transaccion] = {
    val lista = cargarProveedores(direccionArchivo)
    var listaTransacciones = ListBuffer[Transaccion]()
    for (l <- lista) {
      if(l.isbnT == isbn){
        listaTransacciones.append(l)
      }
    }
    return listaTransacciones
    }

}