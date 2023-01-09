package threads;

import jBittorrentAPI.*;

public class descarga extends Thread {

    String direccion_torrent;
    String direccion_archivos;


    @Override
    public void run() {
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
                dm.blockUntilCompletion(this.getName());
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
    public void direcciones(String direccion_archivos,String direccion_torrent ){
        this.direccion_torrent=direccion_torrent;
        System.out.println(this.direccion_torrent);
        this.direccion_archivos=direccion_archivos;
        System.out.println(this.direccion_archivos);
    }
}
