package controlador

import modelo.{Facturaciones, LibrosVendidos, GestionClientes, GestionFactura, GestionLibros, GestionVentas, GestionTransaccion, Transaccion, Validaciones}
import vistas.factura
import java.awt.event.{ActionEvent, ActionListener}
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.swing.JOptionPane
import javax.swing.table.DefaultTableModel
import scala.collection.mutable.ListBuffer


class ctrl_Factura (aux : factura){
  val outform = new SimpleDateFormat("dd / MM / yy")
  val contTransacciones = new GestionTransaccion
  val libros = new GestionLibros
  val ventas = new GestionVentas
  val factura = new GestionFactura
  val clientes = new GestionClientes
  val validacion = new Validaciones
  val tablaFacturas: DefaultTableModel = aux.tabFactura.getModel.asInstanceOf[DefaultTableModel]

  def Control_Factura(): Unit = {
    var listaPrecios = new ListBuffer[Double]()
    var listaIva = new ListBuffer[Double]()
    val listaLibros = libros.cargarLibros("libros.dat")
    for (l <- listaLibros){
      aux.comBoxISBNFactura.addItem(l.isbn)
    }
    aux.btnBuscarFactura.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        ventas.reiniciarLibros()
        val cedRuc = aux.txtCedulaFactura.getText
        if (cedRuc != "") {
          if (cedRuc.length == 10) {
            if (validacion.validarCedula(cedRuc)){
              if(clientes.vereficarCliente(cedRuc,"Cédula")){
                val cliente = clientes.BuscarCliente(cedRuc, "Cédula")
                aux.txtNombreFactura.setText(cliente.nombre)
                aux.txtApellidoFactura.setText(cliente.apellido)
                aux.txtDireccionFactura.setText(cliente.direccion)
                aux.txtTelefonoFactura.setText(cliente.telefono)
                aux.txtCorreoFactura.setText(cliente.correo)
              }else{
                JOptionPane.showMessageDialog(null,"Ingrese el Cliente")
              }
            }else{
              JOptionPane.showMessageDialog(null,"Cédula invalida")
            }
          } else if (clientes.vereficarCliente(cedRuc,"Ruc")){
            val cliente = clientes.BuscarCliente(cedRuc, "Ruc")
            aux.txtNombreFactura.setText(cliente.nombre)
            aux.txtApellidoFactura.setText(cliente.apellido)
            aux.txtDireccionFactura.setText(cliente.direccion)
            aux.txtTelefonoFactura.setText(cliente.telefono)
            aux.txtCorreoFactura.setText(cliente.correo)
          }else{
            JOptionPane.showMessageDialog(null,"Ingrese el cliente!!")
          }
        } else {
          JOptionPane.showMessageDialog(null, "Campos Vacíos!!")
        }
      }
    })
    aux.btnAgregarFactura.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val cantidad = aux.txtCantidadFactura.getText
        val isbn = aux.comBoxISBNFactura.getSelectedItem.toString
        val libro = libros.buscarLibro(isbn)
        val cedRuc = aux.txtCedulaFactura.getText
        if(cantidad != "" && cantidad.toDouble <=libro.cantidad ){
          var vendidos = new LibrosVendidos(cedRuc,isbn,cantidad.toDouble,(libro.precioVenta*cantidad.toDouble))
          ventas.addLibrosV(vendidos)
          var listaLibVendidos = ventas.cargarLibrosV("librosVendidos.dat")
          val filas = aux.tabFactura.getRowCount
          for (i <- 0 until filas) {
            tablaFacturas.removeRow(0)
          }
          for (l <- listaLibVendidos) {
            var fila = new Array[Object](4)
            fila.update(0, l.isbnLibro)
            fila.update(1, libro.titulo)
            fila.update(2, l.cantidadVn.toString)
            fila.update(3, libro.precioVenta.toString)
            tablaFacturas.addRow(fila)
          }
          var mensaje = libros.modificarLibro(isbn,libro.titulo,libro.imagen,libro.precioCompra,libro.precioVenta,libro.cantidad-cantidad.toDouble,libro.proveedores)
          print("Mensaje")
          listaPrecios.append(cantidad.toDouble*libro.precioVenta)
          listaIva.append((cantidad.toDouble*libro.precioVenta)*0.12)
          val tem = Calendar.getInstance()
          val date = outform.format(tem.getTime)
          var transaccion = new Transaccion(date, isbn, libro.titulo, "Venta")
          contTransacciones.addTransaccion(transaccion)

        }else{
          JOptionPane.showMessageDialog(null,"Cantidad Incorrecta")
        }
      }
    })
    aux.btnGenerarFactura.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val subtotal = listaPrecios.sum
        aux.txtSubtotal.setText(subtotal.toString)
        val iva = listaIva.sum
        aux.txtIvaFactura.setText(iva.toString)
        val total=subtotal+iva
        aux.txtTotal.setText(total.toString)
        var factura2 = new Facturaciones(aux.txtTotal.getText,total)
        factura.addLibros(factura2)
        JOptionPane.showMessageDialog(null,"Venta exitosa")
        limpiarCampos()
      }
    })
    aux.btnSalirFactura.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        aux.dispose()
      }
    })
  }
  def limpiarCampos():Unit={
    aux.txtCedulaFactura.setText("")
    aux.txtNombreFactura.setText("")
    aux.txtApellidoFactura.setText("")
    aux.txtDireccionFactura.setText("")
    aux.txtTelefonoFactura.setText("")
    aux.txtCorreoFactura.setText("")
    aux.txtCantidadFactura.setText("")
    aux.txtSubtotal.setText("")
    aux.txtIvaFactura.setText("")
    aux.txtTotal.setText("")
  }
}
