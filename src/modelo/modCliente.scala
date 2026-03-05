package modelo

import java.io.{File, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable.ListBuffer

class Clientes(var Cedula:String,var Ruc:String,var Nombre:String, var Apellido:String,var Direccion:String, var Telefono:String, var Correo:String) extends Serializable{
  //Atribut of peope
  var cedula: String = Cedula
  var ruc: String = Ruc
  var nombre: String = Nombre
  var apellido: String = Apellido
  var direccion: String = Direccion
  var telefono: String = Telefono
  var correo: String = Correo
}

class GestionClientes(){
  val direccionArchivo = "clientes.dat"

  def NuevoCliente(lista : ListBuffer[Clientes], direccion:String) :Unit = {
    try{
      val archivo = new ObjectOutputStream(new FileOutputStream(direccion))
      archivo.writeObject(lista)
      archivo.flush()
      archivo.close()
    }
  }

  def cargarClientes(direccion: String ): ListBuffer[Clientes] = {
    try {
      var archivo: ObjectInputStream = null
      val path = new File(direccion)
      if (path.exists) {
        archivo = new ObjectInputStream(new FileInputStream(direccion))
        val lista: ListBuffer[Clientes] = archivo.readObject.asInstanceOf[ListBuffer[Clientes]]
        archivo.close()
        return lista
      } else {
        return new ListBuffer[Clientes]()
      }
    } catch {
      case e@(_: ClassNotFoundException | _: IOException) =>
        println("Error al abrir la lista")
        return new ListBuffer[Clientes]()
    }
  }

  def addClientes(clientes: Clientes):String = {
    var listaCliente = cargarClientes(direccionArchivo)
    listaCliente.append(clientes)
    NuevoCliente(listaCliente,direccionArchivo)
    return "Agregación Exitosa"
  }

  def BuscarCliente(identificacion: String, parametro:String ): Clientes ={
    var listaCliente = cargarClientes(direccionArchivo)
    for (l <- listaCliente){
      if (parametro=="Cédula"){
        if (l.cedula == identificacion){
          return l
        }
      }else if (parametro == "Ruc"){
        if (l.ruc == identificacion){
          return l
        }
      }
    }
    return null
  }

  def vereficarCliente(identificacion: String, parametro: String): Boolean = {
    var listaCliente = cargarClientes(direccionArchivo)
    for (l <- listaCliente) {
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

  def ModificarCliente(identificacion: String, parametro: String, nombre:String, apellido:String, direccion:String, telefono:String, correo:String):String ={
    var listaCliente = cargarClientes(direccionArchivo)
    for (l <- listaCliente) {
      if (parametro == "Cédula") {
        l.ruc="0"
        if (l.cedula == identificacion) {
          l.nombre = nombre
          l.apellido = apellido
          l.direccion = direccion
          l.telefono = telefono
          l.correo = correo
          NuevoCliente(listaCliente,direccionArchivo)
          return "Cliente Modificado"
        }else{
          return "Agrege al Cliente"
        }
      } else if (parametro == "Ruc") {
        l.cedula = "0"
        if (l.ruc == identificacion) {
          l.nombre = nombre
          l.apellido = apellido
          l.direccion = direccion
          l.telefono = telefono
          l.correo = correo
          NuevoCliente(listaCliente,direccionArchivo)
          return "Cliente Modificado"
        }else{
          return "Agrege al Cliente"
        }
      }
    }
    return null
  }

  def EliminarCliente(identificacion: String, parametro: String): String = {
    var listaCliente = cargarClientes(direccionArchivo)
    for (l <- listaCliente) {
      if (parametro == "Cédula") {
        if (l.cedula == identificacion) {
          listaCliente -= l
          NuevoCliente(listaCliente,direccionArchivo)
          return "Cliente Eliminado"
        }
      } else if (parametro == "Ruc") {
        if (l.ruc == identificacion) {
          listaCliente -= l
          NuevoCliente(listaCliente,direccionArchivo)
          return "Cliente Eliminado"
        }
      }
    }
    return null
  }

}
