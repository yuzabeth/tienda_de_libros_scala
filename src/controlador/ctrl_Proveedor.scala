package controlador

import modelo.{ManejoProveedor, Proveedores, Validaciones}
import vistas.proveedor

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JOptionPane
import javax.swing.table.DefaultTableModel

class ctrl_Proveedor (aux: proveedor){
  var validacion = new Validaciones
  val tablaProveedores: DefaultTableModel = aux.tabProveedor.getModel.asInstanceOf[DefaultTableModel]
  val manejoProveedor = new ManejoProveedor
  def Control_Proveedor(): Unit = {
    aux.btnNuevoCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new ManejoProveedor
        val identificacion = aux.txtCedulaProveedor.getText
        val nombre = aux.txtNombreProveedor.getText
        val apellido = aux.txtApellidoProveedor.getText
        val direccion = aux.txtDireccionProveedor.getText
        val telefono = aux.txtTelefonoProveedor.getText
        val correo = aux.txtCorreoProveedor.getText
        if (identificacion != "" && nombre != "" && apellido != "" && direccion != "" && telefono != "" && correo != ""){
          if (validacion.validarTextos(nombre) && validacion.validarTextos(apellido) && validacion.validarEnteros(telefono)){
            if (validacion.validarCedula(identificacion)){
              val parametro = aux.comBoxProveedor.getSelectedItem.toString
              if (!contenedor.existeProveedor(identificacion, parametro)){
                if (parametro == "Cédula"){
                  val proveedor =  new Proveedores(identificacion,"0",nombre,apellido,direccion,telefono,correo)
                  val mensaje = contenedor.addProveedor(proveedor)
                  JOptionPane.showMessageDialog(null,mensaje)
                  limpiarCampos()
                }else{
                  val proveedor = new Proveedores("0",identificacion, nombre, apellido, direccion, telefono, correo)
                  val mensaje = contenedor.addProveedor(proveedor)
                  JOptionPane.showMessageDialog(null,mensaje)
                  limpiarCampos()
                }
              }else{
                JOptionPane.showMessageDialog(null,"El proveedor ya existe")
              }
            }else{
              JOptionPane.showMessageDialog(null,"Identificacion no valida")
            }
          }else{
            JOptionPane.showMessageDialog(null,"Nombres o teléfonos Incorrectos")
          }
        }else{
          JOptionPane.showMessageDialog(null,"Campos vacíos")
        }
      }
    })

    aux.btnbuscarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new ManejoProveedor
        val identificacion = aux.txtCedulaProveedor.getText
        val parametro = aux.comBoxProveedor.getSelectedItem.toString
        if (identificacion != ""){
          if(contenedor.existeProveedor(identificacion,parametro)){
            val proveedor = contenedor.buscarProveedor(identificacion, parametro)
            aux.txtNombreProveedor.setText(proveedor.nombre)
            aux.txtApellidoProveedor.setText(proveedor.apellido)
            aux.txtDireccionProveedor.setText(proveedor.direccion)
            aux.txtTelefonoProveedor.setText(proveedor.telefono)
            aux.txtCorreoProveedor.setText(proveedor.correo)
          }else{
            JOptionPane.showMessageDialog(null,"El proveedor no existe")
          }
        }else{
          JOptionPane.showMessageDialog(null,"cedula vacia")
        }

      }
    })

    aux.btnListarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val filas = aux.tabProveedor.getRowCount
        for (i <- 0 until filas) {
          tablaProveedores.removeRow(0)
        }
        var listLibros = manejoProveedor.cargarProveedores("proveedores.dat").toArray
        for (l <- listLibros) {
          var fila = new Array[Object](6)
          fila.update(0, l.cedula)
          fila.update(1, l.nombre)
          fila.update(2, l.apellido.toString)
          fila.update(3, l.direccion.toString)
          fila.update(4, l.telefono.toString)
          fila.update(5, l.correo.toString)
          tablaProveedores.addRow(fila)
        }
      }
    })

    aux.btnModificarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new ManejoProveedor
        val parametro = aux.comBoxProveedor.getSelectedItem.toString
        val identificacion = aux.txtCedulaProveedor.getText
        val nombre = aux.txtNombreProveedor.getText
        val apellido = aux.txtApellidoProveedor.getText
        val direccion = aux.txtDireccionProveedor.getText
        val telefono = aux.txtTelefonoProveedor.getText
        val correo = aux.txtCorreoProveedor.getText
        if(identificacion != "" && nombre != "" && apellido != "" && direccion != "" && telefono != "" && correo != ""){
          val mensaje = contenedor.modificarProveedor(identificacion, parametro, nombre, apellido, direccion, telefono, correo)
          JOptionPane.showMessageDialog(null, mensaje)
          limpiarCampos()
        }else{
          JOptionPane.showMessageDialog(null,"Campos vacíos")
        }
      }
    })

    aux.btnEliminarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new ManejoProveedor
        val parametro = aux.comBoxProveedor.getSelectedItem.toString
        val identificacion = aux.txtCedulaProveedor.getText
        if(identificacion != ""){
          var mensaje = contenedor.eliminarProveedor(identificacion, parametro)
          JOptionPane.showMessageDialog(null,mensaje)
        }else{
          JOptionPane.showMessageDialog(null,"isbn vacío")
        }
      }
    })
    aux.btnSalirCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        aux.dispose()
      }
    })
  }

  def limpiarCampos(): Unit = {
    aux.txtCedulaProveedor.setText("")
    aux.txtNombreProveedor.setText("")
    aux.txtApellidoProveedor.setText("")
    aux.txtDireccionProveedor.setText("")
    aux.txtTelefonoProveedor.setText("")
    aux.txtCorreoProveedor.setText("")
  }
}
