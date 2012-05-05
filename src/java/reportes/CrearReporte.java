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
package reportes;


import correo.EnviarCorreo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import json.Items;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *  Se encarga de crear un reporte de fallaos.
 * @author Reynol Zacapala
 */
public class CrearReporte
{
/**
 * Crea un correo para posteriormente enviarlo por correo electronico antes de crear un documento fisico.
 * @param dispositivos Lista de dispositivos a monitorear para realizar el reporte.
 * @param parametros Parametros necesarios para crear el reporte.
 * @param dir_report direccion donde se encuentra la plantilla del reporte.
 * @param email direccion de correo electronico a la que se adjuntara el reportey enviara el reporte.
 * @throws Exception 
 */
    public void reporteErrores(List<Items> dispositivos, Map parametros, String dir_report, String email) throws Exception
    {
        
        // <editor-fold defaultstate="collapsed" desc="Usando ParticipantesDatasource">
//        ParticipantesDatasource datasource = new ParticipantesDatasource();
//        
//        for (int i = 1; i <= 10; i++)
//        {
//            Participante p = new Participante(i, "Particpante " + i, "Usuario " + i, "Pass " + i, "Comentarios para " + i);
//            datasource.addParticipante(p);
//        }
//        
//        JasperReport reporte = (JasperReport) JRLoader.loadObject("reporte2.jasper");
//        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Usando JRBeanCollectionDataSource.">
  /*      List<Participante> listaPariticipantes = new ArrayList<Participante>();
        for (int i = 1; i <= 10; i++)
        {
            Participante p = new Participante(i, "Particpante " + i, "Usuario " + i, "Pass " + i, "Comentarios para " + i);
            listaPariticipantes.add(p);
        }

*/
        Collections.sort(dispositivos);
        
        //JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("D:\\java\\report1.jasper"));
        
        JasperReport reporte = (JasperReport) JRLoader.loadObject(dir_report);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(dispositivos));
//                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource());
        // </editor-fold>

        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
      //  exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("D:\\java\\reporte2PDF.pdf"));
      //  exporter.exportReport();
        
 //InputStream arrayInputStream=new InputStream();     
 ByteArrayOutputStream bos=new ByteArrayOutputStream();
 //ByteArrayInputStream bis=new ByteArrayInputStream();
             
      
//new EnviarCorreo().getSource(JRExporterParameter.OUTPUT_STREAM);
      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bos);
      exporter.exportReport();
      
      System.out.println("MEdida de bos "+bos.size());
      System.out.println("MEdida de bos barray "+bos.toByteArray().length);
  new EnviarCorreo().getSource( new ByteArrayInputStream(bos.toByteArray()), email );
    //  exporter.exportReport();
        
    }
}
