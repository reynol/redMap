//    RedMap es un software de monitoreo de red diseñado para un ambiente web.
//    Autor: Reynol Zacapala.
//    http://www.reynol.net
//
//    This file is part of RedMap.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package json;

import json.Path;
import java.util.ArrayList;

/**
 * Almacena la informacion de un dispositivo.
 * @author Reynol Zacapala.
 */
public class Items implements Comparable<Items> {

Integer id;
Integer posX;
Integer posY;
String img;
String nombre;
String direccion;
Integer puerto;
String status_desc;
Integer status;
public ArrayList <Path> enlaces;
/**
 * Constructor vacio.
 */
    public Items() {
    }
/**
 * Contrucconr que inicializa loos atributos de la clase.
 * @param x posicion X.
 * @param y posicion Y
 * @param ID Id del dispositivo.
 * @param Src Direccion de la imagen.
 * @param nombre nombre del dispositivo.
 * @param direccion direccion ip del dispositivo.
 * @param puerto puerto del dispositivo.
 */    
    public Items(Integer x, Integer y, Integer ID, String Src, String nombre, String direccion,Integer puerto) {
        this.posX = x;
        this.posY = y;
        this.id = ID;
        this.img = Src;
        this.nombre=nombre;
        this.direccion=direccion;
        this.puerto=puerto;
        enlaces= new ArrayList<Path>();
    }

/**
 * Obtiene el ID.
 * @return id del dispositivo.
 */
    public Integer getId() {
        return id;
    }
/**
 * Establece un ID.
 * @param id nuevo ID
 */
    public void setId(Integer id) {
        this.id = id;
    }
/**
 * Obtiene la direccion de la imagen.
 * @return direccion de la imagen. 
 */
    public String getImg() {
        return img;
    }
/**
 * Establece una imagen.
 * @param img direccion de la imagen.
 */
    public void setImg(String img) {
        this.img = img;
    }
/**
 * Obtiene el nombre del dispositivo.
 * @return Nombre del dispositivo.
 */
    public String getNombre() {
        return nombre;
    }
/**
 * Establece el nombre del dispositivo.
 * @param nombre Nombre del dispositivo.
 */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
/**
 * Obtiene la posicion en el eje de las X.
 * @return Posicion en el eje de las X.
 */
    public Integer getPosX() {
        return posX;
    }
/**
 * Establece la posicion en el eje de las X.
 * @param posX Posicion en el eje de las X.
 */
    public void setPosX(Integer posX) {
        this.posX = posX;
    }
/**
 * Obtiene la posicion en el eje de las Y.
 * @return Posicion en el eje de las Y.
 */
    public Integer getPosY() {
        return posY;
    }
/**
 * Establece la posicion en el eje de las Y.
 * @param posY Posicion en el eje de las Y.
 */
    public void setPosY(Integer posY) {
        this.posY = posY;
    }
/**
 * Obtiene la direccion IP del dispositivo.
 * @return direccion ip del dispositivo.
 */
    public String getDireccion() {
        return direccion;
    }
/**
 * Establece la direccion IP del dispositivo.
 * @param direccion Direccion IP.
 */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
/**
 * Obtiene el puerto del dispositovo.
 * @return Puerto del dispositivo.
 */
    public Integer getPuerto() {
        return puerto;
    }
/**
 * Establece el puerto del dispositivo.
 * @param puerto puerto del dispositivo.
 */
    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }
/**
 * Numero entero que indica el estado del dispositivo.
 * @return Estado del dispositivo.
 */
    public Integer getStatus() {
        return status;
    }
/**
 * Establece una cadena de texto que indica el estado del dispositivo.
 * @param status Indica el estado del dispositivo.
 */
    public void setStatus(Integer status) {
        if(status==1)
            setStatus_desc("Sin Fallos");
       if(status==2)
           setStatus_desc("No se encontro");
       if(status==3)
           setStatus_desc("Fallos");
        
        this.status = status;
    }
/**
 * Obtiene el estado del dispositivo.
 * @return Estado del dispositivo.
 */
    public String getStatus_desc() {
        return status_desc;
    }
/**
 * Establece el estado del dispositivo despues del monitoreo.
 * @param status_desc Estado del dispositivo.
 */
    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

/**
 * Ordena los dispositicos segun su estatus.
 * @param device Objeti tipo Item.
 * @return Numero entero utilizado por la interfaz comparable.
 */
 public int compareTo(Items device) {  
      if (this.getStatus() < device.getStatus()) {  
         return -1;  
      } else if (this.getStatus() == device.getStatus()) {  
         return 0;  
      }  
  
      return 1;  
   }
    
}
