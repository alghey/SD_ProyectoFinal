package Main;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import jBittorrentAPI.TorrentProcessor;


/*Programa que crea un archivo .torrent*/

public class Torrent {

    public static void main(String[] args) throws IOException {

        int opcion=0;
        Scanner scanner=new Scanner(System.in);
        ArrayList<String> opciones_torrent = new ArrayList<String>();
        boolean seleccion_opcion=true;
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));

        String direccion_torrent="";
        String announce_url="";
        String num_piezas="";
        String direccion_archivo="";
        String creador="";
        String comentarios="";
        String direccion_url="";

        while(seleccion_opcion){
            System.out.println("1.--Crear torrent sin creador y sin comentarios\n" +
                    "2.--Crear torrent con creador y comentarios\n");
            System.out.println("Selecciona la opcion que desees: ");
            opcion = scanner.nextInt();
            if (opcion==1||opcion==2){
                seleccion_opcion =false;
            }
        }
        switch(opcion){
            case 1:
                System.out.println("\nIngrese la direccion donde se ubicara el torrent:");
                direccion_torrent= bufferReader.readLine();
                System.out.println("\nIngrese la direccion ip de anuncio:");
                announce_url= bufferReader.readLine();
                System.out.println("\nIngrese el numero de piezas que tendra el torrent:");
                num_piezas= bufferReader.readLine();
                System.out.println("\nIngrese la direccion donde se ubican los archivos que se usaran para crear el torrent:");
                direccion_archivo= bufferReader.readLine();
                direccion_url="http://"+announce_url+"/announce";

                opciones_torrent.add(direccion_torrent);
                opciones_torrent.add(direccion_url);
                opciones_torrent.add(num_piezas);
                opciones_torrent.add(direccion_archivo);
                opciones_torrent.add("");

                break;

            case 2:
                System.out.println("\nIngrese la direccion donde se ubicara el torrent:");
                direccion_torrent= bufferReader.readLine();
                System.out.println("\nIngrese la direccion ip de anuncio:");
                announce_url= bufferReader.readLine();
                System.out.println("\nIngrese el numero de piezas que tendra el torrent:");
                num_piezas= bufferReader.readLine();
                System.out.println("\nIngrese la direccion donde se ubican los archivos que se usaran para crear el torrent:");
                direccion_archivo= bufferReader.readLine();
                System.out.println("\nIngrese nombre del creador:");
                creador= bufferReader.readLine();
                System.out.println("\nIngrese comentarios:");
                comentarios= bufferReader.readLine();
                direccion_url="http://"+announce_url+"/announce";

                opciones_torrent.add(direccion_torrent);
                opciones_torrent.add(direccion_url);
                opciones_torrent.add(num_piezas);
                opciones_torrent.add(direccion_archivo);
                opciones_torrent.add("");
                opciones_torrent.add(creador);
                opciones_torrent.add("");
                opciones_torrent.add(comentarios);
                break;

        }




        if(opciones_torrent.size() < 5){
            System.err.println("Faltan argumentos\r\n\r\nUso:\r\n" +
                    "Torrent <Direccion del torrent> <announce url> <longitud de pieza(piece)> " +
                    "<Direccion del archivo1> <Direccion del archivo1> ... <..> <Creador> <..> <Comentario>");
            System.exit(0);
        }
        TorrentProcessor tp = new TorrentProcessor();
        tp.setAnnounceURL(opciones_torrent.get(1));
        try{
            tp.setPieceLength(Integer.parseInt(opciones_torrent.get(2)));
        }catch(Exception e){
            System.err.println("Longitud de las piezas debe de ser un entero");
            System.exit(0);
        }
        int i = 3;
        ArrayList<String> files = new ArrayList<String>();
        if(!opciones_torrent.get(i+1).equalsIgnoreCase("")){
            tp.setName(opciones_torrent.get(3));
            i++;
        }
        while(i < opciones_torrent.size()){
            if(opciones_torrent.get(i).equalsIgnoreCase(""))
                break;
            files.add(opciones_torrent.get(i));
            i++;
        }
        try{
            tp.addFiles(files);
        }catch(Exception e){
            System.err.println(
                    "Error al agregar archivos al torrent, informacion erronea del archivo");
            System.exit(0);
        }
        i++;
        String creator = "";
        while(i < opciones_torrent.size()){
            if(opciones_torrent.get(i).equalsIgnoreCase(""))
                break;
            creator += opciones_torrent.get(i);
            i++;
        }
        tp.setCreator(creator);
        i++;
        String comment = "";
        while(i < opciones_torrent.size()){
            if(opciones_torrent.get(i).equalsIgnoreCase(""))
                break;
            comment += opciones_torrent.get(i);
            i++;
        }
        tp.setComment(comment);
        try{
            System.out.println("Realizando el hash de los archivos..");
            System.out.flush();
            tp.generatePieceHashes();
            System.out.println("Hash Completado... Guardando...");
            FileOutputStream fos = new FileOutputStream(opciones_torrent.get(0));
            fos.write(tp.generateTorrent());
            System.out.println("Torrent creado");
        }catch(Exception e){
            System.err.println("Error al generar el .torrent");
            System.exit(1);
        }
    }

}
