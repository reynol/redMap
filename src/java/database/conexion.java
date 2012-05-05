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
package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Administra las conexiones y consultas a la base de datos.
 * @author Reynol Zacapala
 */
public class conexion {

    Connection conexion;

/**
 * Constructor que inicia la conexion con la base de datos.
 */    
    public conexion() {
        try
        {
           Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e)
        {
           e.printStackTrace();
        }

        try{
        conexion = DriverManager.getConnection ("jdbc:mysql://localhost/netmap","root", "reynol");
        }catch(SQLException e){
            e.printStackTrace();
        
        }

    }
    /**
    * Regresa la conexion establecida a la base de datos.
    * @return Objeto Connection con la conexion a la base de datos.
    */    
    public Connection getConnection(){
        return conexion;
    }
    /**
     * Establece si se realiza un autocommit despues de realizar un query.
     * @param autocomit True para realizar commits automaticos, False para deshabilitar el autocommit.
     */
    public void setAutocommit(Boolean autocomit){
        try{
        conexion.setAutoCommit(autocomit);
        }catch(SQLException e){
        e.printStackTrace();
        }
    }
    /**
     * Revierte todas las modificaciones realizadas en la transacción al devolver los datos al estado en que estaban al inicio de la transacción.
     */
    public void rollback(){
        try{
         if (conexion != null) {
             
                conexion.rollback();
                System.out.println("Connection rollback...");
             
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    /**
     * Cierra la conexion con la base de datos.
     */
    public void close(){
        
        try{
            if (conexion != null && !conexion.isClosed()) {
             conexion.close();
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * Garantiza que todas las modificaciones de la transacción se conviertan en una parte permanente de la base de datos
     */
    public void commit(){
       try{
         if (conexion != null) {
             
                conexion.commit();
                System.out.println("Connection rollback...");
             
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }

/**
 * Regresa los disposotivos relacionados a un mapa.
 * @param mapa id del mapa.
 * @return Conjunto de resultados con el conjunto de dispositivos.
 */
    public ResultSet getDevices(String mapa){
        // Preparamos la consulta
        ResultSet rs=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select * from device where idmapa="+mapa);


         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }
  /**
   * Regresa el conjunto de configuraciones relacionadas con un usuario.
   * @param idusuario id del usuario.
   * @return Conjunto de resultados con el conjunto de configuraciones.
   */  
        public ResultSet getSettings(String idusuario){
        // Preparamos la consulta
        ResultSet rs=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select * from settings where idusuario="+idusuario);


         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }
 /**
  * Actualiza el evento y accion relacionado con un usuario.
  * @param idusuario id del usuario.
  * @param evento Evento.
  * @param accion Accion.
  * @return Devuelve verdadero o falso dependiendo de si se realizaron loas actualizaciones con exito.
  */       
     public Boolean updateSettings1(String idusuario, String evento, String accion ){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update settings set"
                + " evento="+evento
                + ", accion="+accion
                + " where idusuario=" + idusuario );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }  
/**
 * Actualiza el mapa inicial que se muestra en la aplicacion y el intervalo entre chequeos.
 * @param idusuario id del usuario.
 * @param mapaini id del mapa inicial.
 * @param intervalo intervalo de tiempo en segundos.
 * @return Regresa verdadero o falso dependiendo de si se realizaron las actualizaciones con exito.
 */     
     public Boolean updateSettings2(String idusuario, String mapaini, String intervalo ){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update settings set"
                + " idmapainicial="+mapaini
                + ", intervalo="+intervalo
                + " where idusuario=" + idusuario );
       
            if(res==1)
            status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }       
/**
 * Regresa el conjunto de dispositivos relacionados con un mapa.
 * @param mapa id del mapa.
 * @return Conjunto de dispositivos.
 */        
public ResultSet getDevicesByEvento(String mapa){
        // Preparamos la consulta
        ResultSet rs=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select * from device where idmapa="+mapa+" and evento=1");


         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }    
    
    
  /**
   * Regresa el conjunto de enlaces relacionados con un dispositivo en particular.
   * @param device id del dispositivo.
   * @return Conjunto de enlaces.
   */  

    public ResultSet getEnlaces(int device){
        // Preparamos la consulta
        ResultSet rs=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select * from enlace where iddevice="+device);


         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }
/**
 * Obtiene el ultimo id de los dispositivos.
 * @return Numero de id.
 */    
    public ResultSet getLastId(){
        // Preparamos la consulta
        ResultSet rs=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select max(iddevice) from device");


         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }
/**
 * Obtiene el id del mapa relacionado con un determinado usuario.
 * @param id id de usuario.
 * @return id del mapa.
 */    
    public Integer getUserMap(String id){
        // Preparamos la consulta
        ResultSet rs=null;
        Integer idmapa=0;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select idmapa from usuario where idusuario="+id);
        if(rs.next())
        idmapa=rs.getInt(1);

         }catch(SQLException e){
            e.printStackTrace();

        }
        return idmapa;
    }    
    
    /**
     * Obtiene el id del mapa actual con el que se encuentra trabajando un determinado usuario.
     * @param id id de usuario.
     * @return id del mapa.
     */
    public Integer getCurrentUserMap(String id){
        // Preparamos la consulta
        ResultSet rs=null;
        Integer idmapa=0;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select idmapaActual from usuario where idusuario="+id);
        if(rs.next())
        idmapa=rs.getInt(1);

         }catch(SQLException e){
            e.printStackTrace();

        }
        return idmapa;
    }   
/**
 * Obtiene el email de un determinado usuario.
 * @param id id del usuario.
 * @return email del usuario.
 */    
    public String getUserEmail(String id){
        // Preparamos la consulta
        ResultSet rs=null;
        String email=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select email from usuario where idusuario="+id);
        if(rs.next())
        email=rs.getString(1);

         }catch(SQLException e){
            e.printStackTrace();

        }
        return email;
    }  
   /**
    * Actualiza el email de un determinado usuario.
    * @param idusuario id del usuario.
    * @param email nuevo email.
    * @return True o False dependendo de si los cambios se realizaroncon exito.
    */ 
     public Boolean updateUserEmail(String idusuario, String email){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update usuario set"
                + " email='"+email+"'"
                + " where idusuario=" + idusuario );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }    
/**
 * Obtiene el mapa que se muestra al iniciar la aplicacion, de un determinado usuario.
 * @param id id del usuario.
 * @return id del mapa.
 */    
   public Integer getInitialUserMap(String id){
        // Preparamos la consulta
        ResultSet rs=null;
        Integer idmapa=0;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select idmapainicial from settings where idusuario="+id);
        if(rs.next())
        idmapa=rs.getInt(1);

         }catch(SQLException e){
            e.printStackTrace();

        }
        return idmapa;
    }
   /**
    * Obtiene la URL de un mapa.
    * @param id id del mapa.
    * @return String con la direccion del documento SVG que forma un mapa de nivel superior.
    */
      public String getURLMap(String id){
        // Preparamos la consulta
        ResultSet rs=null;
        String urlmapa=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select url from mapa where idmapa="+id);
        if(rs.next())
        urlmapa=rs.getString(1);

         }catch(SQLException e){
            e.printStackTrace();

        }
        return urlmapa;
    }
      
/**
 * Obtiene un listado de los mapas segun su tipo.
 * @param todos True indica que se deben obtener los mapas de dispositivos y false indica que se deben obtener los mapas de nivel superior.
 * @return Conjunto de resulados con el listadode mapas.
 */   
    public ResultSet getListMap(Boolean todos){
        // Preparamos la consulta
        ResultSet rs=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select * from mapa"+((todos)?"":" where id_tipo_mapa=2"));


         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }
/**
 * Obtiene los datos de un determinado mapa segun su ID.
 * @param id id del mapa.
 * @return Conjunto de resultados que representa una fila con la informacion de un mapa.
 */    
    public ResultSet getMapById(Integer id){
        // Preparamos la consulta
        ResultSet rs=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select * from mapa where idmapa="+id);


         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }    
/**
 * Obtiene el tipo de mapa.
 * @param id id del mapa.
 * @return Numero entero que indica el tipo de mapa.
 */
    public Integer getTipoMap(String id){
        // Preparamos la consulta
        ResultSet rs=null;
        Integer idmapa=0;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select id_tipo_mapa from mapa where idmapa="+id);
        if(rs.next())
        idmapa=rs.getInt(1);

         }catch(SQLException e){
            e.printStackTrace();

        }
        return idmapa;
    }  
    
/**
 * Actualiza los daros de un mapa.
 * @param idMapa id del mapa a maodificar.
 * @param nombre Nuevo nombre.
 * @param tipo Nuevo tipo.
 * @return  True o False dependiendo del resultado de la operacion.
 */    
     public Boolean updateMap(Integer idMapa, String nombre, String tipo){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update mapa set"
                + " nombre="+nombre
                + " where idmapa=" + idMapa );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }
/**
 * Elimina un mapa.
 * @param idMapa id del mapa.
 * @return True o False dependiendo del resultado de la operacion.
 */     
     public Boolean deleteMap(Integer idMapa){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("delete from mapa where idmapa=" + idMapa );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }     
   
 /**
  * Crea un nuevo mapa.
  * @param nombre Nombre del mapa.
  * @param tipo Tipo de mapa.
  * @return  id del mapa recien agregado.
  */ 
    public Integer insertMap(String  nombre, String tipo){
        // Preparamos la consulta
        Integer status= null;
        ResultSet rs=null;
        try{
           
        Statement s = conexion.createStatement();
        int res= s.executeUpdate("insert into mapa (nombre, id_tipo_mapa) values("
                + "'"+ nombre+"',"+tipo+" )"
                );

        if(res==1){
            rs = s.executeQuery ("select max(idmapa) from mapa");
            if(rs.next())
            status=rs.getInt(1);
            System.out.println("maxID mapa"+status);
        }
         
         
         
         }catch(SQLException e){
            e.printStackTrace();

        }
        
        return status;
    }
    
/**
 * Actualiza el mapa actual en el que se encuentra trabajando el usuario.
 * @param map id del mapa.
 * @param id id del usuario.
 * @return True o False dependiendo del resultado de la operacion.
 */  
     public Boolean updateCurrentUserMap(Integer map, String id){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update usuario set"
                + " idmapaActual="+map
                + " where idusuario=" + id );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }
/**
 * Agrega una relacion Estado-Mapa.
 * @param idmapa Id del mapa.
 * @param nombre Nombre del estado a relacionar.
 * @return True o False dependiendo del resultado de la operacion.
 */     
public Boolean updateEdoMap(Integer idmapa, String nombre){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update estado set"
                + " idmapa="+idmapa
                + " where nombre='" + nombre+"'" );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }  

/**
 * Elimina la relacion mapa- estado.
 * @param idmapa id del mapa.
 * @return True o False dependiendo del resultado de la operacion.
 */
public Boolean updateEdoMapResetMap(Integer idmapa){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update estado set"
                + " idmapa=null"
                + " where idmapa=" + idmapa );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }     
/**
 * Obtiene el id del mapa relacionado con un determinado estado.
 * @param edo Id del estado.
 * @return Id del mapa.
 */     
      public Integer getMapFromEdo(String edo){
        // Preparamos la consulta
        ResultSet rs=null;
        Integer idmapa=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select idmapa from estado where nombre='"+edo+"'");
        if(rs.next())
            idmapa=rs.getInt(1);

         }catch(SQLException e){
            e.printStackTrace();

        }
        return idmapa;
    }
/**
 * Obtiene en mapa nivel superior relacionado con un determinado mapa.
 * @param idmapa id del mapa.
 * @return Id del mapa de nivel superior.
 */      
 public Integer getParentMapFromDev(String idmapa){
        // Preparamos la consulta
        ResultSet rs=null;
        Integer idmapa_p=null;
        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select idmapa_padre from estado where idmapa="+idmapa);
        if(rs.next())
            idmapa_p=rs.getInt(1);
         }catch(SQLException e){
            e.printStackTrace();

        }
        return idmapa_p;
    }      
 /**
  * Obtiene los estados relacionados con un mapa de nivel superior.
  * @param idmapa Id del mapa.
  * @return Conjunto de estados relacionados con el mapa de nivel superior.
  */
  public ResultSet getEdosFromParent(String idmapa){
        // Preparamos la consulta
        ResultSet rs=null;

        try{
        Statement s = conexion.createStatement();
        rs = s.executeQuery ("select * from estado where idmapa_Padre="+idmapa+" and idmapa IS NOT NULL ");

         }catch(SQLException e){
            e.printStackTrace();

        }
        return rs;
    }     
      
/**
 * Agrega un nuevo dispositivo en un mapa.
 * @param ip IP del nuevo dispositivo.
 * @param x Posicion en el eje de las X.
 * @param y Posicion en el eje de las Y.
 * @param img Direccion de la imagen.
 * @param nombre Nombre del dispositivo.
 * @param puerto Puerto.
 * @param  id del mapa destino.
 * @return 
 */      
    
    public Integer insertItem(String  ip, Integer x, Integer y, String img, String nombre, Integer puerto, Integer idmapa){
        // Preparamos la consulta
        Integer id= null;
        ResultSet rs=null;
        try{
            System.out.println("puerto"+puerto);
        Statement s = conexion.createStatement();
        s.executeUpdate("insert into device (ip, x,y,img, nombre, puerto, idmapa ) values("
                + "'"+ip+"',"
                + x+","
                + y+","
                + "'"+img+"',"
                + "'"+nombre+"',"
                +puerto+","
                +idmapa+ ")"
                );

        rs = s.executeQuery ("select max(iddevice) from device");
        if(rs.next())
        id=rs.getInt(1);
        System.out.println("ID "+id);
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return id;
        
    }
/**
 * Actualiza la posicion de un dispositivo.
 * @param id id del dispositivo.
 * @param x posicion en el eje de las X.
 * @param y posicion en el eje de las Y.
 * @return True o False dependiendo del resultado de la operacion.
 */    
    public Boolean updatePosItem(Integer id, Integer x, Integer y){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
        s.executeUpdate("update device set x="+ x+", y="+ y+" where iddevice=" + id );
        
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }      
/**
 * Actualiza la informacion de un dispositivo.
 * @param id id del dispositivo.
 * @param img Direccion de la nueva imagen.
 * @param ip Direccion IP nueva.
 * @param puerto Puerto nuevo.
 * @param nombre Nombre del nuevo dispositivo.
 * @return True o False dependiendo del resultado de la operacion.
 */
     public Boolean updateInfoItem(Integer id, String img, String ip, Integer puerto, String nombre){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
        s.executeUpdate("update device set"
                + " img='"+img+"',"
                + " ip='"+ip +"',"
                + "puerto="+puerto+","
                + "nombre='"+nombre+"' "
                + " where iddevice=" + id );
        
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }
     
/**
 * Actualiza la tabla para indicar que un dispositivo no debe ser tomado en cuenta para un evento.
 * Cero indica que el dipositivo no debe ser tomado en cuenta, y uno para tomarlo en cuenta.
 * @param idmapa id del mapa.
 * @param iddevice id del dispositivo.
 * @param evento evento.
 * @return True o False dependiendo del resultado de la operacion.
 */
public Boolean updateEeventItem(Integer idmapa, Integer iddevice, Integer evento){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
       int res= s.executeUpdate("update device set"
                + " evento="+evento
                + " where idmapa=" + idmapa+" and iddevice="+iddevice );
        if(res==1)
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    }     

 /**
  * Elimina un dispositivo.
  * @param id id del dispositivo.
  * @return True o False dependiendo del resultado de la operacion.
  */    
    public Boolean deleteItem(Integer id){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
        s.executeUpdate("delete from device "     
                + " where iddevice=" + id );
        
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    } 
     
    /**
     * Agrega un nuevo enlace a un disposiivo.
     * @param destino id del sispositivo con el que se relacionara.
     * @param iddevice dispositivo que da origen al enlace.
     * @param enlace combinacion de IDS con la forma: idorigen-iddestino.
     * @return id del enlace creado.
     */ 

    public Integer insertPath(Integer destino, Integer iddevice, String  enlace){
        // Preparamos la consulta
        Integer id_pth= null;
        ResultSet rs=null;
        try{
           
        Statement s = conexion.createStatement();
        s.executeUpdate("insert into enlace ( destino, iddevice, enlace) values("
                + destino+","
                + iddevice+","
                + "'"+enlace+"')"
                );

        rs = s.executeQuery ("select max(iddevice) from enlace");
        if(rs.next())
        id_pth=rs.getInt(1);
        System.out.println("ID "+id_pth);
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return id_pth;
        
    }
    /**
     * Elimina un determinado enlace.
     * @param enlace id del enlace.
     * @return True o False dependiendo del resultado de la operacion.
     */
    public Boolean deletePath(String enlace){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
        s.executeUpdate("delete from enlace "     
                + " where enlace='" + enlace+"'" );
        
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    } 
    /**
     * Elimina los enlaces relacionados con un dispositivo.
     * @param iddevice id del dispositivo.
     * @return True o False dependiendo del resultado de la actualizacion.
     */
    public Boolean deletePathsuByDevice(String iddevice){
        // Preparamos la consulta
        Boolean status= false;
        
        try{
           
        Statement s = conexion.createStatement();
        s.executeUpdate("delete from enlace "     
                + " where iddevice=" + iddevice );
        
        status=true;
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    } 
/**
 * Utilizadaza para el logueo de la aplicacion.
 * @param usuario Usuario.
 * @param pass contraseña.
 * @return ArrayList de dos elementos el primero indica el numero de coinsidencias encontradas, el segundo es el id del usuario.
 */    
      public ArrayList<Integer> logeo(String usuario, String pass){
        // Preparamos la consulta
        ArrayList <Integer> status= new ArrayList<Integer>();
        ResultSet rs=null;
        try{
           
        Statement s = conexion.createStatement();
         rs = s.executeQuery("select count(idusuario), idusuario from usuario "     
                + " where usuario='" + usuario+"' and contrasenia='"+pass+"'" );
        if(rs.next())
        status.add(rs.getInt(1));
        if(status.get(0)==1)
            status.add(rs.getInt(2));
        
         }catch(SQLException e){
            e.printStackTrace();

        }
        return status;
        
    } 
        
/**
 * Cierra la conexion con la base de datos.
 */
 public void cerrar(){
     try{
     conexion.close();
     }catch(SQLException e){
            e.printStackTrace();

        }


 }
}
