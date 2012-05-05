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



import com.google.gson.Gson;
import database.conexion;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import json.Items;
import reportes.CrearReporte;



/**
 * Clase encargada de realizar el chequeo de los dispositivos.
 * @author Reynol Zacapala.
 */
public class CheckStatus {
 /**
  * Metodo encargado de realizar el chequeo de los dispositivios.
  * @param idmapa Mapa donde se encuentan los dispositivos a monitorear.
  * @param tipoMapa Tipo del mapa a monitorear.
  * @param idusuario Id del usuario.
  * @param dir_report Direccion donde se localiza la plantilla del reporte.
  * @return 
  */
 
    String check(Integer idmapa, Integer tipoMapa, String idusuario, String dir_report ){
        
        String regreso=null;
         Boolean unoFalle;
         Boolean todosFallen;
         
         Integer rojos=0;
         Integer naranjas=0;
         Integer verdes=0;

       Map parameters = new HashMap();

    //Almacena todos los devices
    ArrayList <Items> devices = new ArrayList<Items>();
        
        if(tipoMapa==1){
             try {

                conexion cn= new conexion();
                
                            
                //Obtengo el email del destinatatario
                String email=cn.getUserEmail(idusuario);
                
                
                
                //obtengo el evento y la accion a realizar
                ResultSet rs_edo= cn.getSettings(idusuario);
                rs_edo.next();
                Integer evento=rs_edo.getInt(3);
                Integer accion=rs_edo.getInt(4);
                
                rs_edo= cn.getEdosFromParent(idmapa.toString());
                
                ArrayList <String> status = new ArrayList<String>();

                
                rojos=0;
                naranjas=0;
                verdes=0;  
                
                    while (rs_edo.next()) {
                        System.out.println("EDO "+rs_edo.getString(4));
                        ResultSet rs= cn.getDevicesByEvento(rs_edo.getString(4));
                        Items item;
                        
                        unoFalle=false;
                        todosFallen=true;
                        
                        devices = new ArrayList<Items>();
                        
                        rojos=0;
                        naranjas=0;
                        verdes=0;  
                
                
                                              
                        
                        while (rs.next()) {
                        
                                        item= new Items(rs.getInt (3),rs.getInt (4),
                                            rs.getInt(1), rs.getString(5),  rs.getString(6), rs.getString(2), rs.getInt(7));

                                                    // modo ping :
                                                    if (item.getPuerto() == 0) { 
                                                            try { 
                                                                    if (InetAddress.getByName(item.getDireccion()).isReachable(3000)) {
                                                                         todosFallen=false;
                                                                         verdes++;
                                                                         item.setStatus(1);
                                                                        System.out.println(item.getDireccion()+" = VERDE");
                                                                    } else {
                                                                        unoFalle=true;
                                                                        System.out.println(item.getDireccion()+" = ROJO");
                                                                        item.setStatus(3);
                                                                        rojos++;
                                                                      
                                                                    }
                                                            } catch (IOException e) {
                                                                        unoFalle=true;
                                                                    System.out.println(item.getDireccion()+" = ANARANJADO");
                                                                    item.setStatus(2);
                                                                    naranjas++;
                                                                    
                                                            }
                                                    // modo port:
                                                    } else {
                                                            try { 
                                                                    //progress.setToolTipText("checking: "+item.getIP()+":"+item.getPort());
                                                                    Socket sock = new Socket(item.getDireccion(), item.getPuerto());
                                                                    sock.close();
                                                                    todosFallen=false;
                                                                    item.setStatus(1);
                                                                    verdes++;
                                                                    System.out.println(item.getDireccion()+":"+item.getPuerto() +" = VERDE");
                                                                    
                                                            } catch (UnknownHostException e) {
                                                                unoFalle=true;
                                                                item.setStatus(2);
                                                                naranjas++;
                                                                System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ANARANJADO");
                                                                
                                                            } catch (IOException e) {
                                                                unoFalle=true;
                                                                item.setStatus(3);
                                                                rojos++;
                                                                System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ROJO");
                                                                
                                                            }


                                                    }
                                  //Se agrega el device a la lista
                                    devices.add(item);
                                                    
                                }
                        if(evento==1){
                                    if(unoFalle){
                                         System.out.println("Todos Fallaron:"+ todosFallen);

                                      if(accion==1){
                                        parameters.put("nombre_mapa", rs_edo.getString(2));
                                        parameters.put("num_rojos",rojos);
                                        parameters.put("num_naranjas",naranjas);
                                        parameters.put("num_verdes",verdes);  
                                        new CrearReporte().reporteErrores(devices, parameters, dir_report, email);
                                      }
                                        
                                        status.add("{\"nombre\":\""+rs_edo.getString(2)+"\",\"tipoMapa\":"+tipoMapa+", \"status\":\"2\"}");
                                       
                                    }else{
                                        status.add("{\"nombre\":\""+rs_edo.getString(2)+"\",\"tipoMapa\":"+tipoMapa+", \"status\":\"1\"}");
                                        
                                    }
                                    
                     }else{
                             if(evento==2 ){
                                        if(todosFallen){
                                            
                                      if(accion==1){                                      
                                        parameters.put("nombre_mapa", rs_edo.getString(2));
                                        parameters.put("num_rojos",rojos);
                                        parameters.put("num_naranjas",naranjas);
                                        parameters.put("num_verdes",verdes);  
                                        new CrearReporte().reporteErrores(devices, parameters,dir_report, email);
                                      }       
                                            
                                            status.add("{\"nombre\":\""+rs_edo.getString(2)+"\",\"tipoMapa\":"+tipoMapa+", \"status\":\"2\"}");  
                                            
                                        }else{
                                            status.add("{\"nombre\":\""+rs_edo.getString(2)+"\",\"tipoMapa\":"+tipoMapa+", \"status\":\"1\"}");  
                                            
                                        }
                             }
                     }
                                    
                                
                        
                            }

                                regreso=status.toString();

			} catch (Exception e) {e.printStackTrace();}
            
        
        }else{
                try {

                conexion cn= new conexion();
                
                //Obtengo el email del destinatatario
                String email=cn.getUserEmail(idusuario);
                
                //Se obtienen  el evento y accion
                ResultSet rs_edo= cn.getSettings(idusuario);
                rs_edo.next();
                Integer evento=rs_edo.getInt(3);
                Integer accion=rs_edo.getInt(4);
                
                 ResultSet rs= cn.getMapById(idmapa);
                 rs.next();
                 String nombreMapa=rs.getString(2);
                 
                 rs= cn.getDevices(idmapa.toString());
                                
                                
                                ArrayList <Status> status = new ArrayList<Status>();
                                Items item;
                                
                        unoFalle=false;
                        todosFallen=true;
                        
                        rojos=0;
                        naranjas=0;
                        verdes=0;  
                
				
				while (rs.next()) {
                                    item= new Items(rs.getInt (3),rs.getInt (4),
                                     rs.getInt(1), rs.getString(5),  rs.getString(6), rs.getString(2), rs.getInt(7));

						if (item.getPuerto() == 0) { 
							try { 
								if (InetAddress.getByName(item.getDireccion()).isReachable(3000)) {
									if ( rs.getInt(10)==1) todosFallen=false;
					
                                                                    
                                                                    System.out.println(item.getDireccion()+" = VERDE");
                                                                    status.add(new Status(item.getId(),0, item.getImg()));
                                                                    verdes++;
                                                                    item.setStatus(1);
								} else {
									if ( rs.getInt(10)==1) unoFalle=true;
                                                                    System.out.println(item.getDireccion()+" = ROJO");
                                                                    status.add(new Status(item.getId(),1, item.getImg().replaceAll(".png", "_1.png")));
                                                                    rojos++;
                                                                    item.setStatus(3);
								}
							} catch (IOException e) {
								if ( rs.getInt(10)==1) unoFalle=true;
                                                                System.out.println(item.getDireccion()+" = ANARANJADO");
                                                                status.add(new Status(item.getId(),2, item.getImg().replaceAll(".png", "_2.png")));
                                                                naranjas++;
                                                                item.setStatus(2);
							}
						} else {
							try { 
								Socket sock = new Socket(item.getDireccion(), item.getPuerto());
								sock.close();
								if ( rs.getInt(10)==1) todosFallen=false;
                                                                System.out.println(item.getDireccion()+":"+item.getPuerto() +" = VERDE");
                                                                status.add(new Status(item.getId(),0, item.getImg()));
                                                                verdes++;
                                                                item.setStatus(1);
							} catch (UnknownHostException e) {
								if ( rs.getInt(10)==1) unoFalle=true;
                                                            System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ANARANJADO");
                                                            status.add(new Status(item.getId(),2, item.getImg().replaceAll(".png", "_2.png")));
                                                            naranjas++;
                                                            item.setStatus(2);
							} catch (IOException e) {
								if ( rs.getInt(10)==1) unoFalle=true;
                                                            System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ROJO");
                                                            status.add(new Status(item.getId(),1, item.getImg().replaceAll(".png", "_1.png")));
                                                            rojos++;
                                                            item.setStatus(3);
							}
                                                         
                                                        
						}
                                                
						devices.add(item);
					}
                                System.out.println("Todos Fallaron:"+ todosFallen);
                                System.out.println("Uno fallo:"+ unoFalle);
                                System.out.println("Evento :"+ evento);
                                System.out.println("Accion :"+ accion);
                                
                                // evento: uno falle, accion enviar correo
                                if(evento==1  && accion==1){
                                    if(unoFalle){
                                        
                                            parameters.put("nombre_mapa", nombreMapa);
                                            parameters.put("num_rojos",rojos);
                                            parameters.put("num_naranjas",naranjas);
                                            parameters.put("num_verdes",verdes);  

                                            new CrearReporte().reporteErrores(devices, parameters,dir_report, email);

                                            
                                        }
                                }else{
                                    // evento todos fallen, accion enviar 
                                    if(evento==2 && accion==1){
                                        if(todosFallen){
                                            parameters.put("nombre_mapa", nombreMapa);
                                            parameters.put("num_rojos",rojos);
                                            parameters.put("num_naranjas",naranjas);
                                            parameters.put("num_verdes",verdes);  

                                            new CrearReporte().reporteErrores(devices, parameters,dir_report, email);

                                            
                                        }
                                    }
                                }
                                
                                
                                Gson gson = new Gson();
                                String json = gson.toJson(status);

                                regreso= json;

			} catch (Exception e) {e.printStackTrace();}
        }
        
      
        System.out.println(regreso);
        return regreso;
    }
    
}
