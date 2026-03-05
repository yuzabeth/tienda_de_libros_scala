package controlador
import modelo.{Clientes, GestionClientes, Validaciones}
import vistas.cliente
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JOptionPane
import javax.swing.table.DefaultTableModel
class ctrl_Cliente (aux: cliente){
  var validacion = new Validaciones
  val tablaClientes : DefaultTableModel = aux.tabCliente.getModel.asInstanceOf[DefaultTableModel]
  val manejoClientes = new GestionClientes
  def Control_Clientes ():Unit={
    aux.btnNuevoCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new GestionClientes
        val cedRuc = aux.txtCedulaCliente.getText
        val nombre = aux.txtNombreCliente.getText
        val apellido = aux.txtApellidoCliente.getText
        val direccion = aux.txtDireccionCliente.getText
        val telefono = aux.txtTelefonoCliente.getText
        val correo = aux.txtCorreoCliente.getText
        if (cedRuc != "" && nombre != "" && apellido != "" && direccion != "" && telefono != "" && correo != "") {
          if (validacion.validarTextos(nombre) && validacion.validarTextos(apellido) && validacion.validarEnteros(telefono)) {
            if (validacion.validarCedula(cedRuc)) {
              val comboxOpcion = aux.comBoxCliente.getSelectedItem.toString
              if (!contenedor.vereficarCliente(cedRuc, comboxOpcion)) {
                if (comboxOpcion == "Cédula") {
                  val proveedor = new Clientes(cedRuc, "0", nombre, apellido, direccion, telefono, correo)
                  val mensaje = contenedor.addClientes(proveedor)
                  JOptionPane.showMessageDialog(null, mensaje)
                  vaciarCampos()
                } else {
                  val clientes = new Clientes("0", cedRuc, nombre, apellido, direccion, telefono, correo)
                  val mensaje = contenedor.addClientes(clientes)
                  JOptionPane.showMessageDialog(null, mensaje)
                  vaciarCampos()
                }
              } else {
                JOptionPane.showMessageDialog(null, "El cliente ya existe!!")
              }
            } else {
              JOptionPane.showMessageDialog(null, "Cédula o Ruc no validos!!")
            }
          } else {
            JOptionPane.showMessageDialog(null, "Nombres o teléfonos Incorrectos!!")
          }
        } else {
          JOptionPane.showMessageDialog(null, "Campos vacíos!!")
        }
      }
    })

    aux.btnModificarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new GestionClientes
        val comboxOpcion = aux.comBoxCliente.getSelectedItem.toString
        val cedRuc = aux.txtCedulaCliente.getText
        val nombre = aux.txtNombreCliente.getText
        val apellido = aux.txtApellidoCliente.getText
        val direccion = aux.txtDireccionCliente.getText
        val telefono = aux.txtTelefonoCliente.getText
        val correo = aux.txtCorreoCliente.getText
        if (cedRuc != "" && nombre != "" && apellido != "" && direccion != "" && telefono != "" && correo != "") {
          val mensaje = contenedor.ModificarCliente(cedRuc, comboxOpcion, nombre, apellido, direccion, telefono, correo)
          JOptionPane.showMessageDialog(null, mensaje)
          vaciarCampos()
        } else {
          JOptionPane.showMessageDialog(null, "Campos vacíos!!")
        }
      }
    })

    aux.btnListarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val filas = aux.tabCliente.getRowCount
        for (i <- 0 until filas) {
          tablaClientes.removeRow(0)
        }
        var listaClientes = manejoClientes.cargarClientes("clientes.dat").toArray
        for (l <- listaClientes) {
          var fila = new Array[Object](6)
          fila.update(0, l.cedula)
          fila.update(1, l.nombre)
          fila.update(2, l.apellido.toString)
          fila.update(3, l.direccion.toString)
          fila.update(4, l.telefono.toString)
          fila.update(5,l.correo.toString)
          tablaClientes.addRow(fila)
        }
      }
    })

    aux.btnbuscarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new GestionClientes

        val cedRuc = aux.txtCedulaCliente.getText
        val combBoxOpcion = aux.comBoxCliente.getSelectedItem.toString
        if (cedRuc != "") {
          if (contenedor.vereficarCliente(cedRuc, combBoxOpcion)) {
            var proveedor = contenedor.BuscarCliente(cedRuc, combBoxOpcion)
            aux.txtNombreCliente.setText(proveedor.nombre)
            aux.txtApellidoCliente.setText(proveedor.apellido)
            aux.txtDireccionCliente.setText(proveedor.apellido)
            aux.txtTelefonoCliente.setText(proveedor.telefono)
            aux.txtCorreoCliente.setText(proveedor.correo)
          } else {
            JOptionPane.showMessageDialog(null, "El cliente no existe!!")
          }
        } else {
          JOptionPane.showMessageDialog(null, "Cédula o ruc vacíos!! ")
        }
      }
    })
//elimina un cliente luego de buscarlo por la cedula
    aux.btnEliminarCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new GestionClientes
        val comboxOpcion = aux.comBoxCliente.getSelectedItem.toString
        val cedRuc = aux.txtCedulaCliente.getText
        if (cedRuc != "") {
          var mensaje = contenedor.EliminarCliente(cedRuc, comboxOpcion)
          JOptionPane.showMessageDialog(null, mensaje)
        } else {
          JOptionPane.showMessageDialog(null, "cedula o ruc vacíos")
        }
      }
    })
  }
//limpia los cuadros de texto
  def vaciarCampos(): Unit = {
    aux.txtCedulaCliente.setText("")
    aux.txtNombreCliente.setText("")
    aux.txtApellidoCliente.setText("")
    aux.txtDireccionCliente.setText("")
    aux.txtTelefonoCliente.setText("")
    aux.txtCorreoCliente.setText("")
  }
//sale de la ventana de clientes
  aux.btnSalirCliente.addActionListener(new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      aux.dispose()
    }
  })
}
