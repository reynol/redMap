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

/**
 * Alamace la informacion de un enlace.
 * @author Reynol Zacapala
 * @version 1.0
 */
public class Path {
    Integer destino;
    String id;
/**
 * Constructor que inicializa los atributos de la clase.0
 * @param destino Id del dispositivo destino del enlace.
 * @param id id del dispositivo que dio origen al enlace.
 */
    public Path(Integer destino, String id) {
        this.destino = destino;
        this.id = id;
    }
/**
 * Constructor vacio.
 */
   public Path() {

    }



/**
 * Obtiene el id del dispositivo destino del enlace.
 * @return id del dispositivo destino.
 */
    public Integer getDestino() {
        return destino;
    }
/**
 * Establece el Id del dispositivo destino del enlace.
 * @param destino id del dispositivo destino.
 */
    public void setDestino(Integer destino) {
        this.destino = destino;
    }
/**
 * Obtiene el id del dispositivo que dio origen al enlace.
 * @return id del dispositivo.
 */
    public String getId() {
        return id;
    }
/**
 * Establece el id del dispositivo que dio origen al enlace.
 * @param id id del enlace.
 */
    public void setId(String id) {
        this.id = id;
    }



}
