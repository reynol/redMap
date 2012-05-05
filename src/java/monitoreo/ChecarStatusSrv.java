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
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import json.Items;

/**
 * Servlet que procesa las solicitudes de chequeo de dispositivos.
 * @author Reynol Zacapala.
 */
public class ChecarStatusSrv extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            


/*
   try{
         while (rs.next())
        {
            img= new Items(rs.getInt (3),rs.getInt (4),
                    rs.getInt(1), rs.getString(5),  rs.getString(6), rs.getString(2), rs.getInt(7));

            ResultSet rs2= cn.getEnlaces(rs.getInt(1));
             while (rs2.next())
            {
                    img.enlaces.add(new Path(rs2.getInt(2),rs2.getString(4)));
 

             }
            devices.add(img);

        }
         
    }catch(SQLException e){
            e.printStackTrace();

        }*/

			try {
                            
                               conexion cn= new conexion();
 HttpSession session = request.getSession();                               
Integer idmapa=cn.getCurrentUserMap(session.getAttribute("idusuario").toString());
Integer tipomapa=cn.getTipoMap(idmapa.toString());

System.out.println(" ID DEL MAPA A CHECAR "+idmapa+" TIPO DEL MAPA A CHECAR "+tipomapa);

String dir_report=getServletContext().getRealPath("WEB-INF/report1.jasper");

System.out.println("dir report: \n"+dir_report);

    String json = new CheckStatus().check(idmapa, tipomapa, session.getAttribute("idusuario").toString(), dir_report);
//  System.out.println("Regresa:\n"+json);
    out.print(json);

//ResultSet rs= cn.getDevices(idmapa.toString());
//                                
//                                
//                                ArrayList <Status> status = new ArrayList<Status>();
//                                Items item;
//				
//				while (rs.next()) {
//                                    item= new Items(rs.getInt (3),rs.getInt (4),
//                                     rs.getInt(1), rs.getString(5),  rs.getString(6), rs.getString(2), rs.getInt(7));
//
//						// ping mode:
//						if (item.getPuerto() == 0) { // 70000 = ping, so that way old .netmap won't fail to load
//							try { // try to connect and set appropriate color--300 para ping time out
//								if (InetAddress.getByName(item.getDireccion()).isReachable(3000)) {
//									//if (!item.getColor().equals(Color.green)) triggers.green(item);
//									//canvas.setStatus(item, 1);
//                                                                    
//                                                                    System.out.println(item.getDireccion()+" = VERDE");
//                                                                    status.add(new Status(item.getId(),0, item.getImg()));
//								} else {
//									//if (!item.getColor().equals(Color.red)) triggers.red(item);
//									//canvas.setStatus(item, 3);
//                                                                    System.out.println(item.getDireccion()+" = ROJO");
//                                                                    status.add(new Status(item.getId(),1, item.getImg().replaceAll(".png", "_1.png")));
//								}
//							} catch (IOException e) {
//								//if (!item.getColor().equals(Color.orange)) triggers.orange(item);
//								//canvas.setStatus(item, 2);
//                                                                System.out.println(item.getDireccion()+" = ANARANJADO");
//                                                                status.add(new Status(item.getId(),2, item.getImg().replaceAll(".png", "_2.png")));
//							}
//						// port mode:
//						} else {
//							try { // try to connect and set appropriate color
//								//progress.setToolTipText("checking: "+item.getIP()+":"+item.getPort());
//								Socket sock = new Socket(item.getDireccion(), item.getPuerto());
//								sock.close();
//								//if (!item.getColor().equals(Color.green)) triggers.green(item);
//								//canvas.setStatus(item, 1);
//                                                                System.out.println(item.getDireccion()+":"+item.getPuerto() +" = VERDE");
//                                                                status.add(new Status(item.getId(),0, item.getImg()));
//							} catch (UnknownHostException e) {
//								//if (!item.getColor().equals(Color.orange)) triggers.orange(item);
//								//canvas.setStatus(item, 2);
//                                                            System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ANARANJADO");
//                                                            status.add(new Status(item.getId(),2, item.getImg().replaceAll(".png", "_2.png")));
//							} catch (IOException e) {
//								//if (!item.getColor().equals(Color.red)) triggers.red(item);
//								//canvas.setStatus(item, 3);
//                                                            System.out.println(item.getDireccion()+":"+item.getPuerto() +" = ROJO");
//                                                            status.add(new Status(item.getId(),1, item.getImg().replaceAll(".png", "_1.png")));
//							}
//                                                         
//                                                        
//						}
//						//progress.setValue(progress.getValue() + (100/canvas.items.size()));
//					}
//                                
//                                
//                                Gson gson = new Gson();


			} catch (Exception e) {e.printStackTrace();}
		
	

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
