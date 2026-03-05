package controlador
import vistas.{ cliente, factura, libro, principal, proveedor, transacciones,caja}

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JFrame

class ctrl_Principal (){
  var principal = new principal
  var cliente = new cliente
  var factura = new factura
  var libro = new libro
  var proveedor = new proveedor
  var transacciones = new transacciones
  var caja = new caja

  def Iniciar(): Unit = {
    principal.setTitle("Menú Principal")
    principal.setLocationRelativeTo(null)
    principal.setVisible(true)
    principal.setResizable(false)

    principal.jMenuItemCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        Caracteristicas_Ventana("Cliente", cliente)
        val c_r = new ctrl_Cliente(cliente)
        c_r.Control_Clientes()
      }
    })

    principal.jMenuItemProveedores.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        Caracteristicas_Ventana("Proveedor", proveedor)
        val c_r = new ctrl_Proveedor(proveedor)
        c_r.Control_Proveedor()
      }
    })

    principal.jMenuItemLibros.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        Caracteristicas_Ventana("Proveedor", libro)
        val c_r = new ctrl_Libro(libro)
        c_r.Control_Libro()
      }
    })


    principal.jMenuItemTransaccion.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        Caracteristicas_Ventana("Transacciones", transacciones)
        val c_r = new ctrl_Transacciones(transacciones)
        c_r.Control_Transaccion()
      }
    })
    principal.jMenuItemVender.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        Caracteristicas_Ventana("Vender", factura)
        val c_r = new ctrl_Factura(factura)
        c_r.Control_Factura()
      }
    })

    principal.jMenuItemCaja.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        Caracteristicas_Ventana("Caja", caja)
        val c_r = new ctrl_Caja(caja)
        c_r.Control_Caja()
      }
    })

  }

  def Caracteristicas_Ventana(nombre: String, ventana: JFrame): Unit = {
    ventana.setTitle(nombre) //Título
    ventana.setDefaultCloseOperation(2) //Que no se cierre el programa al cerrar esta ventana
    ventana.setLocationRelativeTo(null) //Ubicación al centro de la pantalla
    ventana.setVisible(true) //Sea visible
    ventana.setResizable(false)
  }
}
