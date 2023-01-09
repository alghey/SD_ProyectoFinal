package MainThreads;

import threads.descarga;
import jBittorrentAPI.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class PeerThreads {
    static final int MAX_T=6; //maximo valor de hilos en el pool de hilos

    descarga Descargas[] =new descarga[MAX_T]; //Array de hilos

    public Peer_hilos(String[] args) throws IOException {
        int opcion=0;
        int iterador=0;
        boolean flag_opcion=true;

        Scanner scanner=new Scanner(System.in);
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        /*Runnable runnable1 = new descarga("descarga1");
        Runnable runnable2 = new descarga("descarga2");
        Runnable runnable3 = new descarga("descarga3");
        Runnable runnable4 = new descarga("descarga4");
        Runnable runnable5 = new descarga("descarga5");
        Runnable runnable6 = new descarga("descarga6");

        ExecutorService pool = Executors.newFixedThreadPool(MAX_T)*/

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
           if (opcion==4)
                break;


            switch(opcion){
                case 1:
                    System.out.println("\nIngrese la direccion donde se ubican los archivos que hace referencia el torrent:");
                    direccion_archivos=bufferReader.readLine();
                    System.out.println("\nIngrese la direccion donde se ubica el torrent: ");
                    direccion_torrent = bufferReader.readLine();
                    if(iterador<=5){
                        //System.out.println(direccion_torrent);
                        //System.out.println(direccion_archivos);.
                        Descargas[iterador]=new descarga();
                        Descargas[iterador].direcciones(direccion_archivos,direccion_torrent);
                        Descargas[iterador].start();
                        Descargas[iterador].setName("Archivo: "+iterador);
                        iterador++;

                    }else{
                        System.out.println("Maximo de Archivos alcanzadas");
                    }
                    break;
                case 2:
                    System.out.println("\nIngrese la direccion donde se van a guardar los archivos:");
                    direccion_archivos=bufferReader.readLine();
                    System.out.println("\nIngrese la direccion donde se ubica el torrent: ");
                    direccion_torrent = bufferReader.readLine();
                    if(iterador<5){
                        Descargas[iterador]=new descarga();
                        Descargas[iterador].direcciones(direccion_archivos,direccion_torrent);
                        Descargas[iterador].start();
                        Descargas[iterador].setName("Archivo: "+iterador);
                        iterador++;

                    }else{
                        System.out.println("Maximo de Archivos alcanzadas");
                    }
                    break;
                case 3:
                    //System.out.println("\nIngrese la direccion donde se ubican los archivos que hace referencia el torrent:");
                    //direccion_archivos=bufferReader.readLine();

                    System.out.println("\nIngrese la direccion donde se ubica el torrent: ");
                    direccion_torrent = bufferReader.readLine();

                    System.out.println("\nIngrese la direccion ip donde se ubican los archivos del torrent:");
                    direccion_archivos_ip=bufferReader.readLine();

                    System.out.println("\nIngrese el puerto de la direccion ip del tracker:");
                    socket=bufferReader.readLine();

                    String direccion_ip="http://"+direccion_archivos_ip+":"+socket+"/upload";

                    String[] variables={direccion_torrent,direccion_ip,"none","none"};
                    publicar_tracker(variables);
                    //break;
            }

        }


    }
    public static void main(String[] args) throws IOException{
        new Peer_hilos(args);
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
            System.out.println("Error, torrent en la BD del tracker");
        }
    }

}
