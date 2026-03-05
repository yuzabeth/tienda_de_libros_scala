package modelo

import java.io.{File, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.ListBuffer

class Facturaciones (var Identificacion:String, var Precio:Double) extends Serializable{


  //Attribute of Facturacion
  var identificacionF:String = Identificacion
  var precioF:Double = Precio

  //Method override
  override def toString: String = s"Identificación: $identificacionF,Precio: $precioF"

}

class GestionFactura(){

  val direccionArchivo = "facturas.dat"

  def guardarLibros(lista: ListBuffer[Facturaciones], direccion: String): Unit = {
    try {
      val archivo = new ObjectOutputStream(new FileOutputStream(direccion))
      archivo.writeObject(lista)
      archivo.flush()
      archivo.close()
    }
  }

  def cargarLibros(direccion: String): ListBuffer[Facturaciones] = {
    try {
      var archivo: ObjectInputStream = null
      val path = new File(direccion)
      if (path.exists) {
        archivo = new ObjectInputStream(new FileInputStream(direccion))
        val lista: ListBuffer[Facturaciones] = archivo.readObject.asInstanceOf[ListBuffer[Facturaciones]]
        archivo.close()
        return lista
      } else {
        return new ListBuffer[Facturaciones]()
      }
    } catch {
      case e@(_: ClassNotFoundException | _: IOException) =>
        println("Error al abrir la lista")
        return new ListBuffer[Facturaciones]()
    }
  }

  def addLibros(facturaciones: Facturaciones): String = {
    val lista = cargarLibros(direccionArchivo)
    lista.append(facturaciones)
    guardarLibros(lista, direccionArchivo)
    return "Agregación Exitosa"
  }

}