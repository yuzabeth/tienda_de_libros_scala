package controlador

import modelo.{GestionFactura, GestionLibros}
import vistas.caja
import java.awt.event.{ActionEvent, ActionListener}
import scala.collection.mutable.ListBuffer

class ctrl_Caja (aux : caja) {

  val libros = new GestionLibros
  val factura = new GestionFactura

  def Control_Caja(): Unit = {
    var listaIngresos = new ListBuffer[Double]()
    var listaEgresos = new ListBuffer[Double]()
    aux.btnCargarCaja.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val listalLibros = libros.cargarLibros("libros.dat")
        val listaFactura = factura.cargarLibros("facturas.dat")
        for (l <- listalLibros) {
          listaEgresos.append(l.precioCompra * l.cantidad)
        }
        for (m <- listaFactura) {
          listaIngresos.append(m.precioF)
        }
        aux.txtIngresos.setText(listaIngresos.sum.toString)
        aux.txtEgresos.setText(listaEgresos.sum.toString)
        var capital = 1000000+ listaIngresos.sum - listaEgresos.sum
        aux.txtTotalActual.setText(capital.toString)
      }
    })

    //sale de la ventana de caja
    aux.btnSalirCaja.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {

        aux.dispose()
      }
    })
  }
}