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
package monitoreo;

/**
 * Alamacena dispositivos para organizarlos segun su estado.
 * @author Reynol Zacapala.
 */
public class Status {
    Integer id;
    Integer status;
    String imagen;
    String mapa;
    String nombre;
    String direccion;

    public Status(Integer id, Integer status, String imagen) {
        this.id = id;
        this.status = status;
        this.imagen = imagen;
    }

    public Status(Integer id, Integer status, String imagen, String mapa, String nombre, String direccion) {
        this.id = id;
        this.status = status;
        this.imagen = imagen;
        this.mapa = mapa;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Status() {
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMapa() {
        return mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
