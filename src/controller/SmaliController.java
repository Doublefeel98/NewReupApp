/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.Config;
import static data.Config.listFolderFilesChange;
import static data.Config.listPathFolderChange;
import static data.Config.packageNameToPath;
import static data.Config.pathFolderToPackage;
import static data.Config.pathToPackageName;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import models.smali.Clazz;
import ui.iViewMain;
import utils.FileFactory;
import utils.FileXML;

/**
 *
 * @author THAITHANG
 */
public class SmaliController{
    
    private List<Clazz> clazzes;
    private List<FileXML> listFileRes;
    private FileXML manifest;
    
    private iViewMain callBack;
    private Progress progress;

    public List<Clazz> getClazzs() {
        return clazzes;
    }

    public SmaliController(iViewMain callBack) {
        this.callBack = callBack;
        
        clazzes = new ArrayList<>();
        listFileRes = new ArrayList<>();
        progress = new Progress();
    }

    public SmaliController(final List<String> listPathFileRes, final String pathMani, iViewMain callBack) {
        
        this.callBack = callBack;
        
        clazzes = new ArrayList<>();
        listFileRes = new ArrayList<>();
        progress = new Progress();

        for(String path : listPathFileRes){
            listFileRes.add(new FileXML(path));
        }
        
        manifest = new FileXML(pathMani);
        
        progress.setFileXMLs(listFileRes);
        progress.setManifest(manifest);
        
        showPakageNameInManifest();
    }

    public void setClazzes(final List<String> pathClazzes){
        clazzes.clear();
        for(String path : pathClazzes){
            clazzes.add(new Clazz(path, progress));
        }
        System.out.println("file smali change: " + clazzes.size());
        progress.setClazzs(clazzes);
    }
    
    public void changeNewNameFileClazz(String path, String newName){
        if(clazzes.isEmpty()){
            callBack.showMessage("Bạn chưa add list file cần đổi", false);
            return;
        }
        int i;
        for(i = 0 ; i < clazzes.size(); i++){
            if(clazzes.get(i).getPath().compareTo(path) == 0){
                progress.changeClazzName(i, newName);
                break;
            }
        }
        System.out.println(packageNameToPath(clazzes.get(i).getPackageName()));
    }
    
    public void changeAllMethodFileClazz(String path){
        if(clazzes.isEmpty()){
            callBack.showMessage("Bạn chưa add list file cần đổi", false);
            return;
        }
        int i;
        for(i = 0 ; i < clazzes.size(); i++){
            if(clazzes.get(i).getPath().compareTo(path) == 0){
                progress.changeAllMethodFileClazz(i);
                break;
            }
        }
        //System.out.println(packageNameToPath(clazzes.get(i).getPackageName()));
    }
    
    public void changeAllMethodAllFileClazz(){
        progress.changeAllMethodAllFileClazz();
    }
    
    public void changeFolderName(String oldPath, String newPath){
        
        if(clazzes.isEmpty())
            callBack.showMessage("Bạn chưa add danh sách file", false);
        
        Vector<String> listFileInFolder = new Vector<>();
        FileFactory.getListFiles(oldPath, listFileInFolder);
        
        String oldPackage = pathFolderToPackage(oldPath);
        String newPackage = pathFolderToPackage(newPath);
        
        List<String> listOldPackage = new ArrayList<>();
        List<String> listNewPackage = new ArrayList<>();
        String temp;
        
        for(String path : listFileInFolder){
            temp = pathToPackageName(path);
            listOldPackage.add(temp);
            listNewPackage.add(temp.replace(oldPackage, newPackage));
        }
        
        String oldStr;
        String newStr;
        
        for(int i = 0; i < listOldPackage.size(); i++){
            oldStr = listOldPackage.get(i);
            newStr = listNewPackage.get(i);
            System.out.println(oldStr +"\t"+ newStr);
            
            for(int j = 0; j < clazzes.size(); j++){
                if(clazzes.get(j).getPackageName().compareTo(oldStr) == 0){
                    progress.changePackageName(j, newStr);
                    break;
                }
            }
        }
    }
    
    public void saveAll(){
        //save tất cả các file cần thiết
        boolean result = true;
        for(FileXML xml : listFileRes){
            result = result & xml.save();
        }
        
        manifest.save();
        
        for(Clazz clazz : clazzes){
            result = result & clazz.save();
        }
        
        //xóa tất cả thư mục rỗng trong thư mục smali
        Vector<String> foldersInSmali = new Vector<>();
        FileFactory.getListFolders(Config.pathSmali, foldersInSmali);
        
        String path;
        for(int i = foldersInSmali.size() -1; i>-1 ; i--){
            path = foldersInSmali.get(i);
            FileFactory.deleteIfExists(path);
        }
        callBack.reloadData();
//        if(result)
//            callBack.showMessage("Lưu tất cả các file thành công", true);
//        else
//            callBack.showMessage("Lưu tất cả các file thất bại", false);
    }
    
    private void showPakageNameInManifest() {
        String content = manifest.getTextContent();
        String packageName = getPackageName(content);
        
        formatManifestDefault(packageName, content);
        
        callBack.showPakageNameInManifest(packageName);
    }
    
    //lấy package name từ dòng đầu tiên trong manifest
    private String getPackageName(String line) {
        String packageName = "";
        String[] arr = line.split(" ");
        String temp = "";
        int indexOf = -1;
        for (String function : arr) {
            indexOf = function.indexOf("package=\"");
            if (indexOf != -1) {
                temp = function;
                break;
            }
        }
        if (indexOf == -1) {
            return packageName;
        }
        int lastIndexof = temp.lastIndexOf('"');
        packageName = temp.substring(9, lastIndexof);
        return packageName;
    }
    
    
    public void autoChangeAllClazzName(){
        
//        if(clazzes.isEmpty()){
//            callBack.showMessage("Bạn chưa add list file cần đổi", false);
//            return;
//        }
        
        Map<Clazz,String> map = new HashMap<>();
        String newName;
        
        for(Clazz clazz : clazzes){
            newName = "" + System.nanoTime();
            map.put(clazz, newName);
        }
        
        String iName;
        String jName;
        String iNewName;
        String jNewName;
        
        String pathParent;
        File parent;
        String tempName;
        int end;
        for(Clazz clazz : map.keySet()){
            iName = clazz.getClazzName();
            iNewName = map.get(clazz);
            pathParent = (new File(clazz.getPath())).getParent();
            parent = new File(pathParent);
            
            for(File file : parent.listFiles()){
                tempName = file.getName();
                end = tempName.indexOf(".smali");
                if(end != -1){
                    jName = tempName.substring(0,end);
                    if(!jName.equals(iName)){
                        String result = checkSubFile(iName, jName, iNewName);
                        if(result !=  null){
                            for(Clazz clazz1 : map.keySet()){
                                if(clazz1.getPath().equals(file.getAbsolutePath())){
                                    jNewName = map.get(clazz1);
                                    map.replace(clazz1, jNewName, result);
                                    System.out.println(jName);
                                    System.out.println(result);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        for(Clazz clazz : map.keySet()){
            System.out.println(clazz.getClazzName() +" "+map.get(clazz));
            clazz.changeClazzName(map.get(clazz));
        }
    }
    
    private String checkSubFile(String iName, String jName, String iNewName) {
        String temp = null;
       
        int indexOf1 = jName.indexOf(iName+"$");
        int indexOf2 = jName.indexOf(iName+"_ViewBinding");
//        if(indexOf1 == 0 ){
//            temp = iNewName + jName.substring(indexOf1 + iName.length(), jName.length());
//        }
        if(indexOf2 == 0){
            temp = iNewName + jName.substring(indexOf2 + iName.length(), jName.length());
        }
        
        return temp;
    }
    
    public void autoChangeAllFolderName(){
        if(listPathFolderChange.isEmpty()){
            callBack.showMessage("Bạn chưa add list folder cần đổi", false);
            return;
        }
            
        Vector<String> listPathFolder = new Vector<>();
        
        String path;
        for(int i = 0; i< listPathFolderChange.size() ;i++){
            path = listPathFolderChange.get(i);
            listPathFolder.add(path);
            FileFactory.getListFolders(path, listPathFolder);
        }
        
        for(String temp : listPathFolder){
            System.out.println(temp);
        }
        
        String newName;
        String oldPath;
        String newPath;
        
        for(int i = listPathFolder.size() - 1; i >= 0; i-- ){
            System.out.println("\n"+i);
            newName = "a" + System.nanoTime();
            oldPath = listPathFolder.get(i);
            
            newPath = oldPath.substring(0, oldPath.lastIndexOf('\\')+1) + newName;
            System.out.println(oldPath);
            System.out.println(newPath + "\n");
            
            for(int j = 0; j < listPathFolderChange.size(); j++){
                if(oldPath.compareTo(listPathFolderChange.get(j)) == 0){
                    listPathFolderChange.remove(j);
                    listPathFolderChange.add(j, newPath);
                    
                    listFolderFilesChange.remove(j);
                    listFolderFilesChange.add(j, newName);
                }
            }
            
            changeFolderName(oldPath, newPath);
            saveAll();
        }
        
        callBack.showMessage("Lưu tất cả các file thành công", true);
    }
    
    public void openFileClazz(String path){
        if(clazzes.isEmpty()){
            callBack.showMessage("Bạn chưa add list file cần đổi", false);
            return;
        }
        int i;
        for(i = 0 ; i < clazzes.size(); i++){
            if(clazzes.get(i).getPath().compareTo(path) == 0){
                System.out.println(clazzes.get(i).toSmali());
                break;
            }
        }
    }

    private void formatManifestDefault(String packageName, String content) {
        String []arr = content.split("\n");
        
        List<String> list = new ArrayList<String>(Arrays.asList(arr));
        
        System.out.println(list.size());
        
        String line;
        for(int i = 0; i < list.size() ; i++){
            line = list.get(i);
            if(line.contains("<activity ") || line.contains("<service ") || line.contains("<receiver ")){
                if(!line.contains(packageName) && line.contains("android:name=\".")){
                    line = line.replace("android:name=\".", "android:name=\""+packageName +".");
                    list.remove(i);
                    list.add(i, line);
                    System.out.println(line);
                }
            }
        }
        
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < list.size() ; i++){
            builder.append(list.get(i));
            if(i != arr.length -1)
                builder.append("\n");
        }
        
        //System.out.println(builder.toString());
        manifest.setTextContent(builder.toString());
        manifest.save();
    }
}
