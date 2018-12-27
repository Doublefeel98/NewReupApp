/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.Config;
import java.util.List;
import models.smali.Clazz;
import utils.FileXML;

/**
 *
 * @author THAITHANG
 */
public class Progress implements iProgressCallback{
    private List<Clazz> clazzes;
    private List<FileXML> fileXMLs;
    private FileXML manifest;

    public void setClazzs(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }

    public void setFileXMLs(List<FileXML> fileXMLs) {
        this.fileXMLs = fileXMLs;
    }

    public void setManifest(FileXML manifest) {
        this.manifest = manifest;
    }
    
    public void changeClazzName(int index, String newName){
        Clazz clazz = clazzes.get(index);
        clazz.changeClazzName(newName);
    }
    
    public void changeAllMethodFileClazz(int index){
        Clazz clazz = clazzes.get(index);
        clazz.changeAllMethod();
    }
    
    public void changePackageName(int index, String newPackage){
        Clazz clazz = clazzes.get(index);
        clazz.changePackageName(newPackage);
    }
    
    public void changeAllMethodAllFileClazz(){
        long start = System.currentTimeMillis();
        Config.countMethodChange = 0;
        Config.timeShare = 0;
        for(Clazz clazz : clazzes){
            //System.out.println(clazz.getPath());
            clazz.changeAllMethod();
        }
        long end = System.currentTimeMillis();
        long t = end - start;
        System.out.println("Tổng thời gian: " + t/60000 + " phút");
        System.out.println("Số method dc đổi là: " + Config.countMethodChange);
        System.out.println("Tổng auto thời gian: " + (t-Config.timeShare)/60000 + " phút");
        System.out.println("Tổng replace thời gian: " + Config.timeShare/60000 + " phút");
    }
    
   @Override
    public void changeMethodName(String packageName, String param, String oldName, String newName) {
        long start = System.currentTimeMillis();
        for(Clazz clazz : clazzes){
            clazz.replaceMethod(packageName, param, oldName, newName);
        }
        long end = System.currentTimeMillis();
        long t = end - start;
        Config.timeShare += t;
    }

    @Override
    public void changePackageName(String oldStr, String newStr) {
        for(Clazz clazz : clazzes){
            clazz.replacePackageName(oldStr, newStr);
        }
        
        String oldPackXML = Config.packageSmaliToPackageXML(oldStr);
        String newPackXML = Config.packageSmaliToPackageXML(newStr);
        
        for(FileXML fxml : fileXMLs){
            fxml.replace(oldPackXML, newPackXML);
        }
        
        manifest.replace(oldPackXML +"\"", newPackXML+"\"");
    }
}
