/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author thepu
 */
public class Util {

    public List<String> readDirectory(String path) {
        List<String> directory = new ArrayList<>();

        File fdirectory = new File(path);
        for (String directorio : fdirectory.list()) {
            File checkdir = new File(path + "\\" + directorio);
            if (checkdir.isDirectory()) {
                directory.add(directorio);
            }
        }

        return directory;
    }

    public void ZipSkin(String path, String skinzip) {

        ZipUtils appZip = new ZipUtils(path);
        appZip.generateFileList(new File(path));
        appZip.zipIt(skinzip);

    }

    public void sendListTextSocket(BufferedWriter bw, List<String> texto) {
        for (String cadena : texto) {
            sendLineTextSocket(bw, cadena);

        }
        sendLineTextSocket(bw, "#");

    }

    public void sendLineTextSocket(BufferedWriter bw, String text) {
        text = text.concat("\n");
        try {
            bw.write(text);
            bw.flush();
        } catch (IOException ioe) {
        }
    }

    public void sendSingleLineTextSocket(BufferedWriter bw, String text) {
        sendLineTextSocket(bw, text);
         sendLineTextSocket(bw, "#");
    }

    public String readLineTextSocket(BufferedReader br) {
        String mensaje = "";
        try {
            while ( br.ready() && (mensaje = br.readLine()) != null) {
                System.out.println("Recibido: " + mensaje);
            }
        } catch (IOException ioe) {
        }
        return mensaje;
    }

    public Map.Entry<String, String> getEntryMap(Map<String, String> map, int position) {
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i == position) {
                return entry;
            }else
                i++;

        }
        return null;
    }
    
    public static void leerCliente(BufferedReader br) {
        try {
            String mensaje = "";
            while (!mensaje.equals("#")) {
                while (br.ready() && (mensaje = br.readLine()) != null) {
                    if (!mensaje.equals("#")) {
                        System.out.println(mensaje);
                    }
                }
            }
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    
    public static int leerOpcionCliente(BufferedReader br) {
        int opcion = -1;
        try {
            String mensaje = "";
            while (!mensaje.equals("#")) {
                while (br.ready() && (mensaje = br.readLine()) != null) {
                    try{
                        if(!mensaje.equals("#"))
                        opcion = Integer.parseInt(mensaje);
                    }catch(NumberFormatException nfe){System.out.println("No se ha capturado una opcion");;}
                    if (!mensaje.equals("#")) {
                        System.out.println(mensaje);
                    }
                }
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return opcion;
    }
}
