package modelo

import java.io.{BufferedInputStream, BufferedOutputStream, File, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class Proveedores(var Cedula:String,var Ruc:String,var Nombre:String,var Apellido:String, var Direccion:String, var Telefono:String, var Correo:String) extends  Serializable{
  //Attribute of people
  var cedula: String = Cedula
  var ruc: String = Ruc
  var nombre: String = Nombre
  var apellido:String = Apellido
  var direccion: String = Direccion
  var telefono: String = Telefono
  var correo: String = Correo
}

class ManejoProveedor(){
  val direccionArchivo = "proveedores.dat"

  def guardarProveedores(lista: ListBuffer[Proveedores], direccion: String): Unit = {
    try {
      val archivo = new ObjectOutputStream(new FileOutputStream(direccion))
      archivo.writeObject(lista)
      archivo.flush()
      archivo.close()
    }
  }

  def cargarProveedores(direccion: String): ListBuffer[Proveedores] = {
    try {
      var archivo: ObjectInputStream = null
      val path = new File(direccion)
      if (path.exists) {
        archivo = new ObjectInputStream(new FileInputStream(direccion))
        val lista: ListBuffer[Proveedores] = archivo.readObject.asInstanceOf[ListBuffer[Proveedores]]
        archivo.close()
        return lista
      } else {
        return new ListBuffer[Proveedores]()
      }
    } catch {
      case e@(_: ClassNotFoundException | _: IOException) =>
        println("Error al abrir la lista")
        return new ListBuffer[Proveedores]()
    }
  }

  def addProveedor(proveedor: Proveedores): String = {
    var lista = cargarProveedores(direccionArchivo)
    lista.append(proveedor)
    guardarProveedores(lista, direccionArchivo)
    return "Agregación Exitosa"
  }


  def buscarProveedor(identificacion: String, parametro: String): Proveedores = {
    var lista = cargarProveedores(direccionArchivo)
    for (l <- lista) {
      if (parametro == "Cédula") {
        if (l.cedula == identificacion) {
          return l
        }
      } else if (parametro == "Ruc") {
        if (l.ruc == identificacion) {
          return l
        }
      }
    }
    return null
  }

  def existeProveedor(identificacion: String, parametro: String): Boolean = {
    var lista = cargarProveedores(direccionArchivo)
    for (l <- lista) {
      if (parametro == "Cédula") {
        if (l.cedula == identificacion) {
          return true
        }
      } else if (parametro == "Ruc") {
        if (l.ruc == identificacion) {
          return true
        }
      }
    }
    return false
  }

  def modificarProveedor(identificacion: String, parametro: String, nombre: String, apellido: String, direccion: String, telefono: String, correo: String): String = {
    var lista= cargarProveedores(direccionArchivo)
    for (l <- lista) {
      if (parametro == "Cédula") {
        l.ruc = "0"
        if (l.cedula == identificacion) {
          l.nombre = nombre
          l.apellido = apellido
          l.direccion = direccion
          l.telefono = telefono
          l.correo = correo
          guardarProveedores(lista, direccionArchivo)
          return "Proveedor Modificado"
        } else {
          return "Agrege al Proveedor"
        }
      } else if (parametro == "Ruc") {
        l.cedula = "0"
        if (l.ruc == identificacion) {
          l.nombre = nombre
          l.apellido = apellido
          l.direccion = direccion
          l.telefono = telefono
          l.correo = correo
          guardarProveedores(lista, direccionArchivo)
          return "Proveedor Modificado"
        } else {
          return "Agrege al proveedor"
        }
      }
    }
    return "No se puede modificar al proveedor"
  }

  def eliminarProveedor(identificacion: String, parametro: String): String = {
    val lista = cargarProveedores(direccionArchivo)
    for (l <- lista) {
      if (parametro == "Cédula") {
        if (l.cedula == identificacion) {
          lista -= l
          guardarProveedores(lista, direccionArchivo)
          return "Proveedor Eliminado"
        }
      } else if (parametro == "Ruc") {
        if (l.ruc == identificacion) {
          lista -= l
          guardarProveedores(lista, direccionArchivo)
          return "Proveedor Eliminado"
        }
      }
    }
    return "No existe el proveedor"
  }

}
