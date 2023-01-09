package Main;

import jBittorrentAPI.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CrearTorrent extends JFrame{
    private JPanel mainPanel;
    private JLabel tituloLabel;
    private JPanel formularioPanel;
    private JTextField nombre_torrentTextField;
    private JLabel nombre_torrentLabel;
    private JPanel direccion_torrentPanel;
    private JButton direccion_torrentButton;
    private JTextField direccion_torrentTextField;
    private JPanel archivo_torrentPanel;
    private JTextField archivo_torrentTextField;
    private JButton archivos_torrentButton;
    private JLabel piezasLabel;
    private JLabel autorLabel;
    private JTextField autorTextField;
    private JLabel comentariosLabel;
    private JTextField comentariosTextField;
    private JTextField ipTextField;
    private JTextField puertoTextField;
    private JLabel direcionipLabel;
    private JLabel puertoLabel;
    private JButton crearButton;
    private JButton limpiarButton;
    private JFormattedTextField npiezasformattedTextField;
    private JTextPane variablesTextPane;



    public Crear_torrent(){
        setContentPane(mainPanel);
        setTitle("Crear Torrent");
        setSize(550,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        archivo_torrentTextField.setEnabled(false);
        direccion_torrentTextField.setEnabled(false);
        variablesTextPane.setEnabled(false);

        direccion_torrentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Seleccione directorio");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);//desabilita opcion todos los archivos

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    direccion_torrentTextField.setText(String.valueOf(chooser.getSelectedFile() + "\\"));//Pone direccion en las campos de texto
                else {
                    JFrame frame = new JFrame("");
                    frame.setSize(200, 350);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    JOptionPane.showMessageDialog(frame,"Direccion no seleccionada","Error",JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        archivos_torrentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Seleccione directorio");
                chooser.setAcceptAllFileFilterUsed(false);//desabilita opcion todos los archivos

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    archivo_torrentTextField.setText(String.valueOf(chooser.getSelectedFile()));//Pone direccion en las campos de texto

                }
                else {
                    JFrame frame = new JFrame("");
                    frame.setSize(200, 200);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    JOptionPane.showMessageDialog(frame,"Direccion no seleccionada","Error",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre_torrentTextField.setText("");
                direccion_torrentTextField.setText("");
                archivo_torrentTextField.setText("");
                npiezasformattedTextField.setText("");
                autorTextField.setText("");
                comentariosTextField.setText("");
                ipTextField.setText("");
                puertoTextField.setText("");
                variablesTextPane.setText("");
            }
        });
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> opciones_torrent = new ArrayList<String>(); //donde se guardan las opciones
                String direccion_torrent="";
                String direccion_ip="";
                String puerto="";
                String num_piezas="";
                String direccion_archivo="";
                String autor="";
                String comentarios="";
                String direccion_url="";

                /* Creacion Torrent */
                JFrame frame = new JFrame("");
                frame.setSize(200, 200);
                frame.setLocationRelativeTo(null);
                variablesTextPane.setText("");

                if(direccion_torrentTextField.getText().equals(""))
                    JOptionPane.showMessageDialog(frame,"Direccion no seleccionada","Error",JOptionPane.WARNING_MESSAGE);
                if(nombre_torrentTextField.getText().equals(""))
                    JOptionPane.showMessageDialog(frame,"Nombre del torrent no ingresado","Error",JOptionPane.WARNING_MESSAGE);
                if(archivo_torrentTextField.getText().equals(""))
                    JOptionPane.showMessageDialog(frame,"Archivo no seleccionado","Error",JOptionPane.WARNING_MESSAGE);
                if(npiezasformattedTextField.getText().equals(""))
                    JOptionPane.showMessageDialog(frame,"Numero de piezas no ingresado","Error",JOptionPane.WARNING_MESSAGE);
                if (ipTextField.getText().equals(""))
                    JOptionPane.showMessageDialog(frame,"Direccion ip no ingresada","Error",JOptionPane.WARNING_MESSAGE);
                if (puertoTextField.getText().equals(""))
                    JOptionPane.showMessageDialog(frame,"Puerto no ingresado","Error",JOptionPane.WARNING_MESSAGE);


                direccion_torrent=direccion_torrentTextField.getText()+nombre_torrentTextField.getText()+".torrent";
                direccion_archivo=archivo_torrentTextField.getText();
                num_piezas=npiezasformattedTextField.getText();
                if(autorTextField.getText().equals("")){
                    autor= "";
                }else{
                    autor=autorTextField.getText();
                }
                if(comentariosTextField.getText().equals("")){
                    comentarios= "";
                }else{
                    comentarios=comentariosTextField.getText();
                }
                direccion_ip=ipTextField.getText();
                puerto=puertoTextField.getText();
                direccion_url="http://"+direccion_ip+":"+puerto+"/announce";

                opciones_torrent.add(direccion_torrent);
                opciones_torrent.add(direccion_url);
                opciones_torrent.add(num_piezas);
                opciones_torrent.add(direccion_archivo);
                opciones_torrent.add("");
                opciones_torrent.add(autor);
                opciones_torrent.add("");
                opciones_torrent.add(comentarios);

                TorrentProcessor tp = new TorrentProcessor();
                tp.setAnnounceURL(opciones_torrent.get(1));

                try{
                    tp.setPieceLength(Integer.parseInt(opciones_torrent.get(2)));
                }catch(Exception ex){
                    variablesTextPane.setText("Longitud de las piezas debe de ser un entero");

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

                try {
                    tp.addFiles(files);
                } catch (Exception ex) {
                    variablesTextPane.setText("Error al agregar archivos al torrent, informacion erronea del archivo");
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
                try {
                    variablesTextPane.setText("Realizando el hash de los archivos..");
                    //System.out.println("Realizando el hash de los archivos..");
                    //
                    tp.generatePieceHashes();
                    variablesTextPane.setText(variablesTextPane.getText()+"\nHash Completado... Guardando...");
                    //System.out.println("Hash Completado... Guardando...");
                    FileOutputStream fos = new FileOutputStream(opciones_torrent.get(0));
                    fos.write(tp.generateTorrent());
                    variablesTextPane.setText(variablesTextPane.getText()+"\nTorrent creado");
                    //System.out.println("Torrent creado");
                    fos.close();
                }catch(Exception ex){
                    variablesTextPane.setText(variablesTextPane.getText()+"\nError al generar el .torrent");
                }
                /*Limpiamos*/
                nombre_torrentTextField.setText("");
                direccion_torrentTextField.setText("");
                archivo_torrentTextField.setText("");
                npiezasformattedTextField.setText("");
                autorTextField.setText("");
                comentariosTextField.setText("");
                ipTextField.setText("");
                puertoTextField.setText("");

            }
        });
    }

    public static void main(String[] args){
        Crear_torrent frame= new Crear_torrent();


    }

}
