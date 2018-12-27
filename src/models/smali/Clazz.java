/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.smali;

import data.Constant;
import utils.FileFactory;
import File.Smali.*;
import data.Config;
import static data.Config.packageNameToPath;
import java.util.ArrayList;
import java.util.List;
import controller.iProgressCallback;
import static data.Config.isDefaultMethod;
import static data.Config.isDependentClazzChange;
import static data.Config.isDependentFileSmali;

/**
 *
 * @author THAITHANG
 */
public class Clazz {
    
    //
    // luu vao hashmap --> key la packagename va value la array index trong component
    
    private String type;
    private String packageName;
    private String oldPackageName;
    private String superClass;
    private String source;
    
    private List<String> listImplement;
    
    private List<Component> components;

    private String path;
    private String textContent;
    private iProgressCallback listener;

    //2 ham
    //getPackageName va getClazzName
    public Clazz(String path, iProgressCallback listener) {
        this.listener = listener;

        this.path = path;
        read();
        initData();
    }

    private void read() {
        textContent = FileFactory.readFile(path);
    }

    public boolean save() {
        //Config.projectDir+packageName
        //xoa file cu
        FileFactory.deleteIfExists(path);
        
        return FileFactory.saveFile(packageNameToPath(packageName), toSmali());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String getPackage(){
        return packageName.substring(0, packageName.lastIndexOf('/'));
    }
    
    public String getClazzName(){
        return packageName.substring(packageName.lastIndexOf('/') + 1, packageName.length() - 1);
    }
    
    public String getPackageName() {
        return packageName;
    }

    public String getOldPackageName() {
        return oldPackageName;
    }
    
    private void initData() {
        if(listener !=  null)
            System.out.println(path);
        String[] arr = textContent.split("\n");
        String line;
        int i;
        
        listImplement = new ArrayList<>();
        components = new ArrayList<>();
        
        for (i = 0; i < arr.length; i++) {
            line = arr[i].trim();
            if (!line.isEmpty()) {
                if (line.startsWith((".class "))) {
                    initPackageName(line);
                } 
                else if (line.startsWith((".super "))) {
                    initSuperClass(line);
                } 
                else if (line.startsWith((".source "))) {
                    initSource(line);
                } 
                else if (line.startsWith((".implements "))) {
                    listImplement.add(getImplement(line));
                }
                else if (line.startsWith((".annotation "))) {
                    String textAnnotation = new String(line) + "\n";
                    int j = i + 1;
                    while (!arr[j].startsWith(".end annotation")) {
                        textAnnotation += arr[j] + "\n";
                        j++;
                    }
                    textAnnotation += arr[j];
                    components.add(new Annotation(textAnnotation));
                    i = j;
                }
                else if (line.startsWith((".field "))) {
                    Field field = new Field(line);
                    if (i + 1 < arr.length) {
                        if (arr[i + 1].contains(".annotation ")) {
                            List<Annotation> annos = new ArrayList<>();
                            int j = i + 1;
                            String textAnnotation;
                            Annotation anno;
                            while(!arr[j].contains(".end field")){
                                if(arr[j].contains(".annotation ")){
                                    int k = j + 1;
                                    textAnnotation = new String(arr[j]) + "\n";
                                    while (!arr[k].contains(".end annotation")) {
                                        textAnnotation += arr[k] + "\n";
                                        k++;
                                    }
                                    textAnnotation += arr[k];
                                    anno = new Annotation(textAnnotation);
                                    annos.add(anno);
                                    j = k;
                                }
                                j++;
                            }
                            field.setAnnotations(annos);
                            i = j;
                        }
                    }
                    components.add(field);
                }
                else if (line.startsWith((".method "))) {
                    String method = line;
                    List<Line> lines = new ArrayList<>();
                    
                    Line ln;

                    int j = i + 1;
                    while (!arr[j].startsWith(".end method")) {

                        String lineTrim = arr[j].trim();
                        if (!lineTrim.isEmpty()) {
                            ln = new Line(lineTrim);

                            lines.add(ln);
                        }
                        j++;
                    }
                    i = j + 1;
                    components.add(new Method(method, lines));
                }
            }
        }
    }

    private void initPackageName(String line) {
        String []arr = line.split(" ");
        
        if(arr.length == 2){
            type = null;
        }
        else{
            type = arr[1];
            for(int i = 2 ; i < arr.length -1;i++){
                type += " "+arr[i];
            }
        }
        
        oldPackageName = packageName  = arr[arr.length - 1];
    }
    
    private void initSuperClass(String line) {
        String []arr = line.split(" ");
        
        superClass =  arr[1];
    }
    
    private void initSource(String line) {
        String []arr = line.split(" ");
        source = arr[1].substring(1, arr[1].length() -1);
    }
    
    private String getImplement(String line){
        String []arr = line.split(" ");
        return arr[1];
    }

    public void changeClazzName(String newName) {
        oldPackageName = new String(packageName);
        packageName = getPackage()+ "/" + newName +";";
        if (source != null) {
            source = newName +".java";
        }
        listener.changePackageName(oldPackageName, packageName);
    }
    
    public void changePackageName(String newPackage){
        oldPackageName = new String(packageName);
        packageName = newPackage;
        listener.changePackageName(oldPackageName, packageName);
    }

    public void replacePackageName(String oldPack, String newPack) {
        
        if(superClass.compareTo(oldPack) == 0)
            superClass = new String(newPack);
        
        String imple;
        for(int i = 0; i < listImplement.size();i ++){
            imple = listImplement.get(i);
            if(imple.compareTo(oldPack) == 0){
                listImplement.remove(i);
                imple = new String(newPack);
                listImplement.add(i, imple);
            }
        }
        
//        for(String imple : listImplement){
//            System.out.println(packageName);
//            if(imple.compareTo(oldPack) == 0){
//                System.out.println("old package implements:" +imple);
//                imple = new String(newPack);
//                System.out.println("new package implements:" +imple);
//            }
//        }
        
        for(Component component : components){
            component.replace(oldPack, newPack);
        }
    }

    
    public void changeAllMethod() {
        // for het tat ca cac method
        // kt contructor va overide thi ko lam
        // nguoc lai method.replaceName va goi qua progress đổi đổi line và các hàm extend
        
        if(type != null && type.contains("interface")) return;
        
        String newName;
        String oldName;
        String newStr;
        String param;
        for (Component component : components) {
            if (component.getComponientType() == Constant.COMPONIENT_TYPE.METHOD) {
                Method method = (Method) component;
                if(!isDefaultMethod(method.getName())) {
                    if(method.getFlag() == 0){
                        if(superClass.compareTo("Ljava/lang/Object;") == 0){
                            if(isOverrideInterface(method.getFullName())) continue;
                            Config.countMethodChange++;
                            newName = "new"+System.nanoTime();
                            System.out.println(method.getFullName()+ "\t"+newName);
                            oldName = method.getName();
                            param = method.getParam();
                            method.replaceName(newName);
                            method.setFlag(1);
                            listener.changeMethodName(packageName, param, oldName, newName);
                        }
                        else{
                            if(isOverrideClass(method.getFullName(),true)) continue;
                            else if(isOverrideInterface(method.getFullName())) continue;
                            else{
                                Config.countMethodChange++;
                                newName = "new"+System.nanoTime();
                                System.out.println(method.getFullName()+ "\t"+newName);
                                oldName = method.getName();
                                param = method.getParam();
                                method.replaceName(newName);
                                method.setFlag(1);
                                listener.changeMethodName(packageName, param, oldName, newName);
                            }
                        }
                    }
                    else if(method.getFlag() == 1){
                        newName = method.getName();
                        oldName = method.getOldName();
                        param = method.getParam();
                        listener.changeMethodName(packageName, param, oldName, newName);
                    }
                }
            }
        }
    }

    public void replaceMethod(String packageName, String param, String oldName, String newName) {
        //replace all Line method
        //extend = true -- > change name
        
        if(isExtend(packageName)){
            listener.changeMethodName(this.packageName, param, oldName, newName);
        }
        
        String oldStr = packageName + "->" + oldName + param;
        String newStr = packageName + "->" + newName + param;
        
        for (Component component : components) {
            if (component.getComponientType() == Constant.COMPONIENT_TYPE.METHOD) {
                Method method = (Method) component;
                //method.replaceLines(oldStr, newStr);
                if(method.getFullName().compareTo(oldName + param) == 0){
                    if(isExtend(packageName) || isImplements(packageName)){
                        method.replaceName(newName);
                        method.setFlag(1);
                    }
                }
            }
        }
    }

    private boolean isOverrideClass(String methodname, boolean isCurrentClazz) {
        if(isCurrentClazz){
            if(isDependentFileSmali(packageNameToPath(superClass))){
                if(!isDependentClazzChange(packageNameToPath(superClass)))
                    return true;
            }
            else
                return true;
            return new Clazz(packageNameToPath(superClass), null).isOverrideClass(methodname, false);
        }
        else{
            if (superClass.equals("Ljava/lang/Object;")) {
                return hasMethod(methodname);
            }

            //check list package name -- return true
            //kiem tra path co ton tai trong thu muc smali neu co return true 
            //va kiem tra nam trong danh sach class neu co thi return true
            String path = packageNameToPath(packageName);
            if(isDependentFileSmali(path)){
                if(!isDependentClazzChange(path))
                    return true;
            }
            else
                return true;
            
            String pathSuper = packageNameToPath(superClass);
            if(isDependentFileSmali(pathSuper)){
                if(!isDependentClazzChange(pathSuper))
                    return true;
            }
            else
                return true;

            return hasMethod(methodname) || new Clazz(packageNameToPath(superClass), null).isOverrideClass(methodname, false);
        }
    }
    
    //
    private boolean isExtend(String packageName) {
        if (superClass.equals("Ljava/lang/Object;")) {
            return false;
        }
        
        //check list package name -- return true
        //kiem tra path co ton tai trong thu muc smali neu ko return false 
        //va kiem tra nam trong danh sach class neu ko thi return false
        String path = packageNameToPath(packageName);
        if(isDependentFileSmali(path)){
            if(!isDependentClazzChange(path))
                return false;
        }
        else
            return false;

        if (superClass.equals(packageName)) {
            return true;
        }
        
        String pathSuper = packageNameToPath(superClass);
        if(isDependentFileSmali(pathSuper)){
            if(!isDependentClazzChange(pathSuper))
                return false;
        }
        else
            return false;

        return new Clazz(packageNameToPath(superClass), null).isExtend(packageName);
    }
    
    private boolean isOverrideInterface(String methodname) {
        if(listImplement.isEmpty()) return false;
        String path;
        Clazz clazz;
        for(String packageName : listImplement){
            path = packageNameToPath(packageName);
            if(isDependentFileSmali(path)){
                if(isDependentClazzChange(path)){
                    clazz = new Clazz(path, null);
                    if(clazz.hasMethod(methodname)) return true;
                }
                else return true;
            }
            else return true;
        }
        return false;
    }

    private boolean isImplements(String packageName) {
        if(listImplement.isEmpty()) return false;
        
        for(String pack : listImplement){
            if(pack.compareTo(packageName) == 0)
                return true;
        }
        
        return false;
    }
    
    private boolean hasMethod(String methodName) {
        for (Component component : components) {
            if (component.getComponientType() == Constant.COMPONIENT_TYPE.METHOD) {
                Method method = (Method) component;
                if (method.getFullName().compareTo(methodName) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public String toSmali(){
        String smali = ".class ";
        if(type != null)
            smali+= type + " ";
        smali += packageName + "\n" + ".super " + superClass +"\n";
        if(source != null)
            smali += ".source \"" +source+"\"\n";
        
        if(!listImplement.isEmpty()){
            smali += "\n# interfaces\n";
            for(int i = 0; i < listImplement.size();i++){
                smali += ".implements "+listImplement.get(i) + "\n";
            }
        }
        
        if(!components.isEmpty()){
            smali += "\n\n";
            for(int i = 0; i < components.size();i++){
                smali += components.get(i).toSmali() + "\n";
                if(i != components.size() -1)
                    smali += "\n";
            }
        }
        return smali;
    } 
    
}
