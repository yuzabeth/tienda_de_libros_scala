package modelo

import java.io.{BufferedInputStream, BufferedOutputStream, File, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class LibrosVendidos(var Identificacion:String, var Isbn:String, var Cantidad:Double, var PrecioTotal:Double) extends Serializable{
  //Attribute of Ventas no facturadas
  var cedRuc:String = Identificacion
  var isbnLibro:String = Isbn
  var cantidadVn:Double = Cantidad
  var precioTotalVn:Double = PrecioTotal
}

class GestionVentas(){
  val direccionArchivo = "librosVendidos.dat"

  def guardarLibrosV(lista: ListBuffer[LibrosVendidos], direccion: String): Unit = {
    try {
      val archivo = new ObjectOutputStream(new FileOutputStream(direccion))
      archivo.writeObject(lista)
      archivo.flush()
      archivo.close()
    }
  }

  def cargarLibrosV(direccion: String): ListBuffer[LibrosVendidos] = {
    try {
      var archivo: ObjectInputStream = null
      val path = new File(direccion)
      if (path.exists) {
        archivo = new ObjectInputStream(new FileInputStream(direccion))
        val lista: ListBuffer[LibrosVendidos] = archivo.readObject.asInstanceOf[ListBuffer[LibrosVendidos]]
        archivo.close()
        return lista
      } else {
        return new ListBuffer[LibrosVendidos]()
      }
    } catch {
      case e@(_: ClassNotFoundException | _: IOException) =>
        println("Error al abrir la lista")
        return new ListBuffer[LibrosVendidos]()
    }
  }

  def addLibrosV(librosVendidos: LibrosVendidos): String = {
    val lista = cargarLibrosV(direccionArchivo)
    lista.append(librosVendidos)
    guardarLibrosV(lista, direccionArchivo)
    return "Agregación Exitosa"
  }

  def buscarLibro(isbn: String): LibrosVendidos = {
    val lista = cargarLibrosV(direccionArchivo)
    for (l <- lista) {
      if (l.isbnLibro == isbn) {
        return l
      }
    }
    return null
  }

  def existeLibro(isbn: String): Boolean = {
    val lista = cargarLibrosV(direccionArchivo)
    for (l <- lista) {
      if (l.isbnLibro == isbn) {
        return true
      }
    }
    return false
  }
  def reiniciarLibros():Unit = {
    var lista = ListBuffer[LibrosVendidos]()
    val archivo = new ObjectOutputStream(new FileOutputStream(direccionArchivo))
    archivo.writeObject(lista)
    archivo.flush()
    archivo.close()
  }

}
