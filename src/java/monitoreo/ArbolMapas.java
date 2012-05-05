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
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import json.Items;
import json.Path;

/**
 * Servlet que retorna un objeto JSON con la informacion de los mapas disponibles, que serviran de insumos para el JTree. 
 * @author Reynol Zacapala.
 */
public class ArbolMapas extends HttpServlet {

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
             * TODO output your page here. You may use following sample code.
             */
ArrayList <Items> devices = new ArrayList<Items>();
Items img;

 HttpSession session = request.getSession();
 if(session!=null){

   conexion cn= new conexion();

   ResultSet rs= cn.getListMap(true);
//String json="[";
ArrayList<String> json= new ArrayList<String>();
ArrayList<String> children;
   try{
         while (rs.next())
        {
            /*
{title: "Folder", isFolder: true, key: "id3",
			children: [
				{title: "Sub-item 3.1",
					children: [
						{title: "Sub-item 3.1.1", key: "id3.1.1" },
						{title: "Sub-item 3.1.2", key: "id3.1.2" }
					]
				},
				{title: "Sub-item 3.2",
					children: [
						{title: "Sub-item 3.2.1", key: "id3.2.1" },
						{title: "Sub-item 3.2.2", key: "id3.2.2" }
					]
				}
			]
		},*/            
            
            //Si es Mapa
            if(rs.getInt(3)==1){
                
                children= new ArrayList<String>();
                //ResultSet rs2= cn.getDevices((new Integer (rs.getInt(1)).toString()));
                ResultSet rs2= cn.getDevices(rs.getString(1));
                while (rs2.next())
                {
                    children.add("{\"title\":\""+rs2.getString(6)+"\", \"key\":\""+rs2.getInt(1)+"\"}");
                       // img.enlaces.add(new Path(rs2.getInt(2),rs2.getString(4)));


                }
                
               json.add("{\"title\":\""+rs.getString(2)+"\", \"key\":\""+rs.getInt(1)+"\",\"icon\":\"map.png\"}");
            }else{
                children= new ArrayList<String>();
                ResultSet rs2= cn.getDevices(rs.getString(1));
                while (rs2.next())
                {
                    children.add("{\"title\":\""+rs2.getString(2)+"\","
                            + "\"select\":" + ((rs2.getInt(10)==1)?"true":"false") +","
                            + " \"key\":\""+rs2.getInt(1)+"\",\"icon\":\"pc.png\"}");
                       // img.enlaces.add(new Path(rs2.getInt(2),rs2.getString(4)));


                }
                /*
                Integer idParent= cn.getParentMapFromDev(rs.getString(1));
                //Si idparent es diferente de null significa que el mapa ya se asigno a un estado
                //y se procede a obtener este tiene el valor del Mapa de nivel Superior.
                System.out.println("El id del padre:"+idParent);
                if(idParent!=null){
                    ResultSet rs3=cn.getMapById(idParent);
                    rs3.next();
                    json.add("{title:'"+rs3.getString(2)+"', key:'"+rs3.getInt(1)+"', unselectable: true, expand: true, \n children:[ {title:'"+rs.getString(2)+"', key:'"+rs.getInt(1)+"', expand: true, \n children:"+children+"}]}");
                }else{
                    json.add("{title:'"+rs.getString(2)+"', key:'"+rs.getInt(1)+"', expand: true, \n children:"+children+"}");
                }
                */
                  json.add("{\"title\":\""+rs.getString(2)+"\", \"key\":\""+rs.getInt(1)+"\", \"expand\": true,\"icon\":\"net.png\", \"children\":"+children+"}");
            }
            
            
            
            
        }
            
         out.print(json);
    }catch(SQLException e){
            e.printStackTrace();

        }

/*
       Gson gson = new Gson();
        //String json = gson.toJson(devices);

        out.print(json);
        * 
        */
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
