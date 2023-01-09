package MainThreads;

import java.io.*;
import java.net.*;

import threads.tracker_xml;
import simple.http.connect.*;
import simple.http.load.*;
import simple.http.serve.*;
import trackerBT.Constants;

public class Tracker {

    public static void main(String[] args) {

        if (args.length > 0) {
            try {
                Constants.loadConfig(args[0]);//Toma la direccion de donde se encuentra el archivo de configuracion, en el que se encuentra el puerto a escuchar
            } catch (Exception e) {
                System.err.println(
                        "Archivo de configuracion no encontrado");
                System.exit(0);
            }
        }
        else {
            System.err.println(
                    "No se indicio la direccion del archivo de configuracion");
            System.exit(0);
        }
        File fichero = new File("./tracker/files/peers.xml");
        if (fichero.delete())
            System.out.print("");
        else
           System.out.print("");

         tracker_xml peersHilos =new tracker_xml(); //hilo actualizable


        new File((String) Constants.get("context")).mkdirs();
        try {
            FileWriter fw = new FileWriter((String) Constants.get("context") +
                    "Mapper.xml");
            fw.write("<?xml version=\"1.0\"?>\r\n<mapper>\r\n<lookup>\r\n" +
                    "<service name=\"file\" type=\"trackerBT.FileService\"/>\r\n" +
                    "<service name=\"tracker\" type=\"trackerBT.TrackerService\"/>\r\n" +
                    "<service name=\"upload\" type=\"trackerBT.UploadService\"/>\r\n" +
                    "</lookup>\r\n<resolve>\r\n" +
                    "<match path=\"/*\" name=\"file\"/>\r\n" +
                    "<match path=\"/announce*\" name=\"tracker\"/>\r\n" +
                    "<match path=\"/upload*\" name=\"upload\"/>\r\n" +
                    "</resolve>\r\n</mapper>");
            fw.flush();
            fw.close();
        } catch (IOException ioe) {
            System.err.println("No se pudo crear'Mapper.xml'");
            System.exit(0);
        }
        Context context = new FileContext(new File((String) Constants.get(
                "context")));

        try {
            MapperEngine engine = new MapperEngine(context);

            ConnectionFactory.getConnection(engine).connect(new ServerSocket(
                    Integer.parseInt((String)Constants.get("listeningPort"))));
            /*System.out.println(
                    "\n\n\nTracker iniciado! Escuchando en puerto: " + Constants.get("listeningPort") +
                            "\r\n\r\n\t********************************************\r\n" +
                            "\t*   Presiona enter para detener el tracker    *\r\n" +
                            "\t********************************************\r\n");*/

            //Instalar el I/O para la lectura del usuario, asi como mostrar la solicitud de archivos de los peers
            peersHilos.start();
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String s = null;
            try {
                s = br.readLine();
            } catch (IOException ioe) {
            }
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
