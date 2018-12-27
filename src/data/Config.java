/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import models.smali.Clazz;
import utils.FileXML;

/**
 *
 * @author THAITHANG
 */
public class Config {
    public static String projectDir;
    public static String pathSmali;
    public static String pathRes;
    public static String pathManifest;
    
    public static Vector<String> listPathFileSmali = new Vector<>();
    public static Vector<String> listPathFolderChange = new Vector<>();
    public static Vector<String> listPathClazzChange = new Vector<>();
    public static Vector<String> listPathFileRes = new Vector<>();
    public static Vector<String> listFolderFilesChange = new Vector<>();// hiện thị lên màn hình
    
    public static List<String> methodsDeFault = new ArrayList<>();
    
    public static int countMethodChange = 0;
    
    public static long timeShare = 0;
    
    public static String packageNameToPath(String packageName) {
        String path = pathSmali +"\\";
        String temp = packageName.substring(1, packageName.length() -1);
        String[] arr = temp.split("/");
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                path += arr[i] + "\\";
            } 
            else {
                path += arr[i] + ".smali";
            }
        }
        return path;
    }
    
    public static String pathToPackageName(String path){
        int start = path.indexOf("smali\\") + 6;
        int end = path.indexOf(".smali");
        String temp = path.substring(start, end);
        String[] arr = temp.split(Pattern.quote("\\"));
        
        String packageName ="L";
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                packageName += arr[i] + "/";
            } 
            else {
                packageName += arr[i] + ";";
            }
        }
        
        return packageName;
    }
    
    public static String packageSmaliToPackageXML(String packageName) {
        String packXML = "";
        String temp = packageName.substring(1, packageName.length() - 1);
        String[] arr = temp.split("/");
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                packXML += arr[i] + ".";
            } 
            else {
                packXML += arr[i];
            }
        }
        return packXML;
    }
    
    //kiểm tra file xml
    public static boolean isFileXML(String name){
        int indexOf = name.indexOf(".xml");
        if(indexOf > 0) return true;
        return false;
    }
    
    public static String pathFolderToPackage(String path){
        int start = path.indexOf("smali\\") + 6;
        String temp = path.substring(start, path.length());
        String[] arr = temp.split(Pattern.quote("\\"));
        
        String packageName ="L";
        for (int i = 0; i < arr.length; i++) {
            packageName += arr[i] + "/";
        }
        
        return packageName;
    }
    
    public static boolean isDependentFileSmali(String pathFile){
        for(String path : listPathFileSmali){
            if(path.compareTo(pathFile) == 0)
                return true;
        }
        return false;
    }
    
    public static boolean isDependentClazzChange(String pathFile){
        for(String path : listPathClazzChange){
            if(path.compareTo(pathFile) == 0)
                return true;
        }
        return false;
    }
    
    public static boolean isDefaultMethod(String name) {
        return methodsDeFault.contains(name);
    }
}
