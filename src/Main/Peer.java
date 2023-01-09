package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import jBittorrentAPI.*;

public class Peer {

    public Peer(String[] args) throws IOException {
        int opcion=0;
        String name="Archivo 1";
        boolean flag_opcion=true;
        Scanner scanner=new Scanner(System.in);
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));

        while(flag_opcion){
            String direccion_archivos="";
            String direccion_torrent="";
            String direccion_archivos_ip="";
            String socket="";
            System.out.println("1.--Compartir torrent\n" +
                                "2.--Descargar y compartir torrent\n" +
                                "3.--Agregar torrent al tracker\n" +
                                "4.--Salir\n");
            System.out.println("Selecciona la opcion que desees: ");
            opcion = scanner.nextInt();
            if (opcion==1||opcion==2||opcion==3)
                flag_opcion =false;
            else if (opcion==4)
                break;


            switch(opcion){
                case 1:
                    System.out.println("\nIngrese la direccion donde se ubican los archivos que hace referencia el torrent:");
                    direccion_archivos=bufferReader.readLine();
                    System.out.println("\nIngrese la direccion donde se ubica el torrent: ");
                    direccion_torrent = bufferReader.readLine();
                    break;
                case 2:
                    System.out.println("\nIngrese la direccion donde se van a guardar los archivos:");
                    direccion_archivos=bufferReader.readLine();
                    System.out.println("\nIngrese la direccion donde se ubica el torrent: ");
                    direccion_torrent = bufferReader.readLine();
                    break;
                case 3:
                    System.out.println("\nIngrese la direccion donde se ubican los archivos que hace referencia el torrent:");
                    direccion_archivos=bufferReader.readLine();

                    System.out.println("\nIngrese la direccion donde se ubica el torrent: ");
                    direccion_torrent = bufferReader.readLine();

                    System.out.println("\nIngrese la direccion ip donde se ubican los archivos del torrent:");
                    direccion_archivos_ip=bufferReader.readLine();

                    System.out.println("\nIngrese el puerto de la direccion ip del tracker:");
                    socket=bufferReader.readLine();

                    String direccion_ip="http://"+direccion_archivos_ip+":"+socket+"/upload";

                    String[] variables={direccion_torrent,direccion_ip,"none","none"};
                    publicar_tracker(variables);
                    System.exit(1);
                    break;
            }

            try {
                TorrentProcessor tp = new TorrentProcessor();
                TorrentFile t = tp.getTorrentFile(tp.parseTorrent(direccion_torrent));
                Constants.SAVEPATH = direccion_archivos; //direccion de los archivos del torrent
                if (t != null) {
                    DownloadManager dm = new DownloadManager(t, Utils.generateID());//Genera ID
                    //System.out.println("Escuchando puertos");
                    dm.startListening(6881, 6889);
                    //System.out.println("Escuchando puertos 2");
                    dm.startTrackerUpdate();
                    //System.out.println("Escuchando puertos 3");
                    dm.blockUntilCompletion(name);
                    //System.out.println("Escuchando puertos 4");
                    dm.stopTrackerUpdate();
                    //System.out.println("Escuchando puertos 5");
                    dm.closeTempFiles();
                    //System.out.println("Escuchando puertos 6");
                } else {
                    System.err.println(
                            "El archivo no es un .torrent valido");
                    System.err.flush();
                    System.exit(1);
                }
            } catch (Exception e) {

                System.out.println("Error al procesar el .torrent");
                //e.printStackTrace();
                System.exit(1);
            }


        }


    }
    public static void main(String[] args) throws IOException{
        new Peer(args);
    }
    public static void publicar_tracker(String [] args){

        File f = new File(args[0]);
        String comment = "";
        for(int i = 4; i < args.length; i++)
            comment += args[i];
        try{
            ConnectionManager.publish(args[0], args[1],
                    args[2], args[3],
                    f.getName(), "", comment, "7");
        }catch(Exception e){
            System.exit(0);
        }
    }

}
