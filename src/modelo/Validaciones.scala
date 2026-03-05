package modelo

import scala.collection.mutable.ListBuffer

class Validaciones {

  //método para la validación de la cedula
  def validarCedula(cedula: String): Boolean = {
    if (cedula.length != 10) return false
    var i = 0
    var residuo = 0
    var aux = 0
    var sumador = 0
    var multiplicadorCadena = ""
    i = 0
    while ( {
      i < 10
    }) {
      if (i % 2 == 0) if (Character.getNumericValue(cedula.charAt(i)) > 4) aux = (Character.getNumericValue(cedula.charAt(i)) * 2) - 9
      else aux = Character.getNumericValue(cedula.charAt(i)) * 2
      else aux = Character.getNumericValue(cedula.charAt(i))
      multiplicadorCadena += Character.forDigit(aux, 10)

      i += 1
    }
    i = 0
    while ( {
      i < 9
    }) {
      sumador += Character.getNumericValue(multiplicadorCadena.charAt(i))

      i += 1
    }
    residuo = sumador % 10
    if (10 - residuo == Character.getNumericValue(cedula.charAt(9))) true
    else false
  }
  //valida que todos los datos estén ingresados
  def ingresaTexto(lista:ListBuffer[ListBuffer[String]]): Boolean={
    for (list <- lista) {
      if (list.isEmpty) {
        return false
      }
    }
    return true
  }
  //validar ruc
  def validarRuc(ruc: String): Boolean = {
    if (ruc.length != 13) {
      return false
    }
    val digitos = ruc.substring(0, 12).map(_.asDigit)
    val coeficientes = Array(3, 2, 7, 6, 5, 4, 3, 2)
    val verificador = ruc(12).asDigit
    var suma = 0
    for (i <- 0 until 8) {
      suma += digitos(i) * coeficientes(i)
    }
    val mod = suma % 11
    val digito = if (mod > 1) 11 - mod else 0
    digito == verificador
  }


  //método que valida si un espacio es vacío
  def textoVacio(texto: String): Boolean = {
    texto.isEmpty
  }

  //validación de enteros
  def validarEnteros (entero: String): Boolean = {
    if (entero.matches("-?\\d+")) {
      return true
    } else {
      return false
    }
  }

  //valida que se ingresen solo letras
  def validarTextos(texto: String): Boolean = {
    val pattern = "[a-zA-Z]+".r
    pattern.findFirstIn(texto).isDefined
  }
  //valida los precios en double
  def validarPrecio(precio: String): Boolean = {
    scala.util.Try(precio.toDouble).isSuccess
  }

}
