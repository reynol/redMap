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


import database.conexion;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import json.Items;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet que procesa las solicitudes para descargar un reporte. 
 * @author Reynol Zacapala.
 */
public class CreaReporteWebSrv extends HttpServlet {

       /**
     * Procesa solicitudes HTTP para los metodos 
     * <code>GET</code> y
     * <code>POST</code>.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("application/pdf");
        
//        request.get

        ServletOutputStream out = response.getOutputStream();
        
        try {
            
                conexion cn= new conexion();
                HttpSession session = request.getSession();                               
                Integer idmapa=cn.getCurrentUserMap(session.getAttribute("idusuario").toString());
                Integer tipomapa=cn.getTipoMap(idmapa.toString());
                String idusuario=session.getAttribute("idusuario").toString();

                System.out.println(" MAPA A REPORTE "+idmapa+" TIPO DEL MAPA A REPORTE "+tipomapa);

           
            try{
                

                
                //Obtengo el email del destinatatario
                String email=cn.getUserEmail(idusuario);
                
                ResultSet rs_edo= cn.getSettings(idusuario);
                rs_edo.next();
                Integer evento=rs_edo.getInt(3);
                Integer accion=rs_edo.getInt(4);
                
                 ResultSet rs= cn.getMapById(idmapa);
                 rs.next();
                 String nombreMapa=rs.getString(2);
                 
                 rs= cn.getDevices(idmapa.toString());
                                
    ArrayList <Items> devices = new ArrayList<Items>();                                
                                ArrayList <Status> status = new ArrayList<Status>();
                                Items item;

                        Integer rojos=0;
                       Integer naranjas=0;
                       Integer verdes=0;  
                
				
				while (rs.next()) {
                                    item= new Items(rs.getInt (3),rs.getInt (4),
                                     rs.getInt(1), rs.getString(5),  rs.getString(6), rs.getString(2), rs.getInt(7));

						// modo Ping:
						if (item.getPuerto() == 0) { 
							try { 
								if (InetAddress.getByName(item.getDireccion()).isReachable(3000)) {
					
                                                                    System.out.println(item.getDireccion()+" = VERDE");
                                                                    verdes++;
                                                                    item.setStatus(1);
								} else {
								
                                                                    System.out.println(item.getDireccion()+" = ROJO");
                                                                    rojos++;
                                                                    item.setStatus(3);
								}
							} catch (IOException e) {
								
                                                                System.out.println(item.getDireccion()+" = ANARANJADO");
                                                                naranjas++;
                                                                item.setStatus(2);
							}
						// modo puerto:
						} else {
							try { 
								//progress.setToolTipText("checking: "+item.getIP()+":"+item.getPort());
								Socket sock = new Socket(item.getDireccion(), item.getPuerto());
								sock.close();
                                                                System.out.println(item.getDireccion()+":"+item.getPuerto() +" = VERDE");
                                                                verdes++;
                                                                item.setStatus(1);
							} catch (UnknownHostException e) {
							
                                                            System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ANARANJADO");
                                                            naranjas++;
                                                            item.setStatus(2);
							} catch (IOException e) {
							
                                                            System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ROJO");
                                                            rojos++;
                                                            item.setStatus(3);
							}
                                                         
                                                        
						}
                                                
						devices.add(item);
					}                
                
                

                
                
            JasperReport reporte = (JasperReport) JRLoader.loadObject(getServletContext().getRealPath("WEB-INF/report1.jasper"));

            Map parameters = new HashMap();
            parameters.put("nombre_mapa", nombreMapa);
            parameters.put("num_rojos",rojos);
            parameters.put("num_naranjas",naranjas);
            parameters.put("num_verdes",verdes);  

            Collections.sort(devices);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, new JRBeanCollectionDataSource(devices));

            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);      // se indica que el flujo de datos debe ser devuelto al usuario
            exporter.exportReport();
            
        }catch(Exception e){
            e.printStackTrace();
        }
           
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
