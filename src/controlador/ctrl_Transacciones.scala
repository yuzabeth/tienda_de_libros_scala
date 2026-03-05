package controlador

import modelo.{GestionLibros, GestionTransaccion}
import vistas.transacciones
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.table.DefaultTableModel

class ctrl_Transacciones (aux : transacciones){
  val transaccion = new GestionTransaccion
  val libros = new GestionLibros
  val tablaTransacion : DefaultTableModel = aux.tabTransaccion.getModel.asInstanceOf[DefaultTableModel]

  def Control_Transaccion (): Unit = {
    val listaLibros = libros.cargarLibros("libros.dat")
    for (l <- listaLibros){
      aux.comBoxTransacciones.addItem(l.isbn)
    }
    aux.btnConsultarTransacciones.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val isbn = aux.comBoxTransacciones.getSelectedItem.toString
        val filas = aux.tabTransaccion.getRowCount
        for (i <- 0 until filas) {
          tablaTransacion.removeRow(0)
        }
        var listaT = transaccion.cargarProveedores("transacciones.dat")
        for (l <- listaT){
          if(l.isbnT == isbn){
            var fila = new Array[Object](4)
            fila.update(0, l.isbnT)
            fila.update(1, l.tituloT)
            fila.update(2, l.fechaT)
            fila.update(3, l.tipoT)
            tablaTransacion.addRow(fila)
          }
        }
      }
    })
    aux.btnSalirTransacciones.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        aux.dispose()
      }
    })
  }

}