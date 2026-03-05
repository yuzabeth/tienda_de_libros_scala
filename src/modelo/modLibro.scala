package modelo

import java.io.{File, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import java.nio.file.Paths
import javax.swing.{ImageIcon, JFileChooser}
import scala.collection.mutable.ListBuffer

class Libro (var Isbn:String, var Titulo:String, var Imagen:String, var PrecioCompra:Double,var PrecioVenta:Double, var Cantidad:Double, var Proveedor:String) extends Serializable {
  //Attribute of book
  var isbn: String = Isbn
  var titulo: String = Titulo
  var imagen: String = Imagen
  var precioCompra : Double= PrecioCompra
  var precioVenta: Double = PrecioVenta
  var cantidad: Double = Cantidad
  var proveedores: String= Proveedor


}

class GestionLibros(){
  val direccionArchivo = "libros.dat"
  def guardarLibros(lista: ListBuffer[Libro], direccion: String): Unit = {
    try {
      val archivo = new ObjectOutputStream(new FileOutputStream(direccion))
      archivo.writeObject(lista)
      archivo.flush()
      archivo.close()
    }
  }

  def cargarLibros(direccion: String): ListBuffer[Libro] = {
    try {
      var archivo: ObjectInputStream = null
      val path = new File(direccion)
      if (path.exists) {
        archivo = new ObjectInputStream(new FileInputStream(direccion))
        val lista: ListBuffer[Libro] = archivo.readObject.asInstanceOf[ListBuffer[Libro]]
        archivo.close()
        return lista
      } else {
        return new ListBuffer[Libro]()
      }
    } catch {
      case e@(_: ClassNotFoundException | _: IOException) =>
        println("Error al abrir la lista")
        return new ListBuffer[Libro]()
    }
  }

  def addLibros(libro: Libro): String = {
    val lista= cargarLibros(direccionArchivo)
    lista.append(libro)
    guardarLibros(lista, direccionArchivo)
    return "Agregación Exitosa"
  }

  def buscarLibro(isbn: String): Libro = {
    val lista = cargarLibros(direccionArchivo)
    for (l <- lista) {
      if (l.isbn == isbn){
        return l
      }
    }
    return null
  }

  def existeLibro(isbn:String): Boolean = {
    val lista = cargarLibros(direccionArchivo)
    for (l <- lista) {
      if (l.isbn == isbn) {
        return true
      }
    }
    return false
  }

  def modificarLibro(isbn: String ,titulo:String,imagen:String, precioCompra:Double,precioVenta:Double, cantidad:Double, proveedor:String): String = {
    val lista = cargarLibros(direccionArchivo)
    for (l <- lista) {
      if (l.isbn == isbn){
        l.titulo = titulo
        l.imagen = imagen
        l.precioCompra = precioCompra
        l.precioVenta = precioVenta
        l.cantidad = cantidad
        l.proveedores = proveedor
        guardarLibros(lista, direccionArchivo)
        return "Libro Modificado"
      }else{
        return "Primero Agrege el Libro"
      }
    }

    return null
  }

  def eliminarLibro(isbn: String): String = {
    val lista = cargarLibros(direccionArchivo)
    for (l <- lista) {
      if (l.isbn == isbn){
        lista -=l
        guardarLibros(lista,direccionArchivo)
        return "Libro Eliminado"
      }else{
        return "Libros no encontrado"
      }
    }
    return null
  }


}
