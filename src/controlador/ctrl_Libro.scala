package controlador

import modelo.{GestionLibros, GestionTransaccion, Libro, ManejoProveedor, Transaccion, Validaciones}
import vistas.libro

import java.awt.event.{ActionEvent, ActionListener}
import java.nio.file.{Files, Paths}
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.swing.{ImageIcon, JFileChooser, JOptionPane}
import javax.swing.table.DefaultTableModel
import scala.collection.mutable.ListBuffer


class ctrl_Libro (aux : libro){

  var validacion = new Validaciones
  val outform = new SimpleDateFormat("dd / MM / yy")
  val transaccion = new GestionTransaccion
  val proveedor = new ManejoProveedor
  val libros = new GestionLibros
  val tablaLibros : DefaultTableModel = aux.tabLibro.getModel.asInstanceOf[DefaultTableModel]
  var sourcePath = Paths.get("")
  var targetPath = Paths.get("")

  def Control_Libro():Unit = {
    val listaProveedores = proveedor.cargarProveedores("proveedores.dat")
    for (l <- listaProveedores) {
      if (l.cedula != "0") {
        aux.comBoxProveedorLibro.addItem(l.cedula)
      } else if (l.ruc != "0") {
        aux.comBoxProveedorLibro.addItem(l.ruc)
      }
    }
    aux.btnNuevoLibro.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new GestionLibros
        val isbn = aux.txtIsbnLibro.getText
        val titulo = aux.txtTituloLibro.getText
        val precioCompra = aux.txtPrecioCompraLibro.getText
        val precioVenta = aux.txtPrecioVentaLibro.getText
        val cantidad = aux.txtCantidadLibro.getText
        val proveedor = aux.comBoxProveedorLibro.getSelectedItem.toString
        val imagen ="src/imagenes/"+ isbn +".png"
        if (isbn != ""&& titulo != "" && precioCompra != "" && precioVenta != "" && cantidad != "" && proveedor != "Elija un proveedor"){
          var precioC = precioCompra.toDouble
          var precioV = precioVenta.toDouble
          if (validacion.validarPrecio(precioC.toString) && validacion.validarPrecio(precioV.toString)){
            if (validacion.validarEnteros(cantidad)){
              if (contenedor.existeLibro(isbn)) {
                JOptionPane.showMessageDialog(null, "Libro Existente")
              } else {
                val tem = Calendar.getInstance()
                val date = outform.format(tem.getTime)
                val libro = new Libro(isbn, titulo, imagen, precioCompra.toDouble, precioVenta.toDouble, cantidad.toDouble, proveedor)
                val mensaje = contenedor.addLibros(libro)
                var transaccion2 = new Transaccion(date,isbn,titulo,"Compra")
                transaccion.addTransaccion(transaccion2)
                JOptionPane.showMessageDialog(null, mensaje)
                vaciarCampos()
              }
            }else{
              JOptionPane.showMessageDialog(null,"La cantidad debe ser entera")
            }
          }else{
            JOptionPane.showMessageDialog(null,"Precio Incorrecto")
          }
        }else{
          JOptionPane.showMessageDialog(null,"Existen Campos Vacíos")
        }
      }
    })

    aux.btnbuscarLibro.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        var contenedor = new GestionLibros
        val isbn = aux.txtIsbnLibro.getText
        if (isbn != ""){
          if (contenedor.existeLibro(isbn)) {
            val libro = contenedor.buscarLibro(isbn)
            aux.txtTituloLibro.setText(libro.titulo)
            aux.txtPrecioCompraLibro.setText(libro.precioCompra.toString)
            aux.txtPrecioVentaLibro.setText(libro.precioVenta.toString)
            aux.txtCantidadLibro.setText(libro.cantidad.toString)
            aux.comBoxProveedorLibro.setSelectedItem(libro.proveedores)
          } else {
            JOptionPane.showMessageDialog(null, "Libro No Existe")
          }
        }else{
          JOptionPane.showMessageDialog(null,"Campo Isbn Vacío")
        }

      }
    })

    aux.btnListarLibro.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val filas = aux.tabLibro.getRowCount
        for (i <- 0 until filas) {
          tablaLibros.removeRow(0)
        }
        var listLibros = libros.cargarLibros("libros.dat").toArray
        for (l <- listLibros) {
          var fila = new Array[Object](5)
          fila.update(0, l.isbn)
          fila.update(1, l.titulo)
          fila.update(2, l.precioCompra.toString)
          fila.update(3, l.precioVenta.toString)
          fila.update(4, l.cantidad.toString)

          tablaLibros.addRow(fila)
        }
      }
    })

    aux.btnMostrarLibro.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val parametro = aux.comBoxLibro.getSelectedItem.toString
        val filas = aux.tabLibro.getRowCount
        for (i <- 0 until filas) {
          tablaLibros.removeRow(0)
        }
        var listaPrecios = new ListBuffer[Double]()
        var listaLibros = libros.cargarLibros("libros.dat")
        for (l <- listaLibros) {
          listaPrecios.append(l.precioVenta)
        }
        if (parametro == "Más costoso") {
          var maximo = listaPrecios.max
          var listLibros = libros.cargarLibros("libros.dat").toArray
          for (l <- listLibros) {
            var fila = new Array[Object](5)
            if (l.precioVenta == maximo) {
              fila.update(0, l.isbn)
              fila.update(1, l.titulo)
              fila.update(2, l.precioCompra.toString)
              fila.update(3, l.precioVenta.toString)
              fila.update(4, l.cantidad.toString)
              tablaLibros.addRow(fila)
            }
          }
          vaciarCampos()
        }else if(parametro == "Menos costoso") {
          var minimo = listaPrecios.min
          var listLibros = libros.cargarLibros("libros.dat").toArray
          for (l <- listLibros) {
            var fila = new Array[Object](5)
            if (l.precioVenta == minimo) {
              fila.update(0, l.isbn)
              fila.update(1, l.titulo)
              fila.update(2, l.precioCompra.toString)
              fila.update(3, l.precioVenta.toString)
              fila.update(4, l.cantidad.toString)
              tablaLibros.addRow(fila)
            }
          }
        }else{
          var listaCantidad = new ListBuffer[Double]()
          var listaLibros = libros.cargarLibros("librosVendidos.dat")
          for (l <- listaLibros) {
            listaCantidad.append(l.cantidad)
          }
          var maximo = listaCantidad.max
          var listLibros = libros.cargarLibros("librosVendidos.dat").toArray
          for (l <- listLibros) {
            var fila = new Array[Object](5)
            if (l.cantidad == maximo) {
              fila.update(0, l.isbn)
              fila.update(1, l.titulo)
              fila.update(2, l.precioCompra.toString)
              fila.update(3, l.precioVenta.toString)
              fila.update(4, l.cantidad.toString)
              tablaLibros.addRow(fila)
            }
          }
        }

      }
    })

    aux.btnModificarLibro.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new GestionLibros
        val isbn = aux.txtIsbnLibro.getText
        val titulo = aux.txtTituloLibro.getText
        val precioCompra = aux.txtPrecioCompraLibro.getText
        val precioVenta = aux.txtPrecioVentaLibro.getText
        val cantidad = aux.txtCantidadLibro.getText
        val proveedor = aux.comBoxProveedorLibro.getSelectedItem.toString
        val imagen = "src/imagenes/"+ isbn +".png"
        if (isbn != "" && titulo != "" && precioCompra != "" && precioVenta != "" && cantidad != "" && proveedor != "Elija un proveedor") {
          var precioC = precioCompra.toDouble
          var precioV = precioVenta.toDouble
          if (validacion.validarPrecio(precioC.toString) && validacion.validarPrecio(precioV.toString)) {
            if (validacion.validarEnteros(cantidad)) {
              val tem = Calendar.getInstance()
              val date = outform.format(tem.getTime)
              val mensaje = contenedor.modificarLibro(isbn, titulo, imagen, precioCompra.toDouble, precioVenta.toDouble, cantidad.toDouble, proveedor)
              val transaccion2 = new Transaccion(date, isbn, titulo, "Compra")
              transaccion.addTransaccion(transaccion2)
              JOptionPane.showMessageDialog(null, mensaje)
              vaciarCampos()
            } else {
              JOptionPane.showMessageDialog(null, "La cantidad debe ser entera")
            }
          } else {
            JOptionPane.showMessageDialog(null, "Precio Incorrecto")
          }
        } else {
          JOptionPane.showMessageDialog(null, "Existen Campos Vacíos")
        }
      }
    })


    aux.btnEliminarLibro.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val contenedor = new GestionLibros
        val isbn = aux.txtIsbnLibro.getText
        if (isbn != ""){
          val mensaje = contenedor.eliminarLibro(isbn)
          JOptionPane.showMessageDialog(null, mensaje)
          vaciarCampos()
        }else{
          JOptionPane.showMessageDialog(null,"Isbn vacío")
        }
      }
    })

    aux.btnSalirCliente.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        aux.dispose()
      }
    })

    aux.btnPortadaLibro.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        seleccionar_imagen()
        guardar_imagen()

      }
    })
  }

  def seleccionar_imagen(): Unit = {
    val fileChooser = new JFileChooser()
    val result = fileChooser.showOpenDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) {
      val selectedFile = fileChooser.getSelectedFile()
      sourcePath = Paths.get(selectedFile.getAbsolutePath)
      mostrar_imagen(selectedFile.getAbsolutePath)
    }

  }

  def mostrar_imagen(ruta: String): Unit
  = {
    val icon = new ImageIcon(ruta)
    val image = icon.getImage()
    val newIcon = new ImageIcon(image.getScaledInstance(aux.btnPortadaLibro.getWidth, aux.btnPortadaLibro.getHeight, java.awt.Image.SCALE_SMOOTH))
    aux.btnPortadaLibro.setIcon(newIcon)
    aux.btnPortadaLibro.setText("")
  }

  def guardar_imagen(): Unit = {
    Files.copy(sourcePath, targetPath)
  }

  def vaciarCampos(): Unit ={
    aux.txtIsbnLibro.setText("")
    aux.txtTituloLibro.setText("")
    aux.txtPrecioCompraLibro.setText("")
    aux.txtPrecioVentaLibro.setText("")
    aux.txtCantidadLibro.setText("")
    aux.btnPortadaLibro.setText("")
  }
}
