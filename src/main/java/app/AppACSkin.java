/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author thepu
 */
public class AppACSkin {
    static Scanner teclado = new Scanner(System.in);
    static String rutacars ="G:\\Steam\\steamapps\\common\\assettocorsa\\content\\cars";
    static Map<String,String> rutasskins = new HashMap<String, String>();
    
    public static void init(){
    rutasskins.put("puar",File.separator+"dtp_nissan_r32gtrb\\skins\\puar");
    rutasskins.put("Oscar",File.separator+"dtp_nissan_r32gtrb\\skins\\Oscar" );
    }
    
    public static void main(String[] args) {
        init();
        Util util = new Util();
        //String skin = menuEleccionSkin();
        
        //String rutazip = rutacars+File.separator+rutasskins.get(skin)+skin+".zip";
        //util.ZipSkin(rutacars+File.separator+rutasskins.get(skin),skin+".zip" );
       	
        //ServerAC fs = new ServerAC(2000,rutazip);
        
        ServerAC fs = new ServerAC(2000);
        
		fs.start();
}
    
   
    
   
    
}
