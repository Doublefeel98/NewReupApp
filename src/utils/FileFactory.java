/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author THAITHANG
 */
public class FileFactory {

    public static void getListFilesAndFolders(String path, Vector<String> listFolder, Vector<String> listFile) {
        File folderPath = new File(path);
        for (File file : folderPath.listFiles()) {
            if (file.isFile()) {
                listFile.add(file.getAbsolutePath());
            } else {
                listFolder.add(file.getAbsolutePath());
                getListFilesAndFolders(file.getAbsolutePath(), listFolder, listFile);
            }
        }
    }
    
    public static void getListFolders(String path, Vector<String> listFolder) {
        File folderPath = new File(path);
        if(folderPath.exists()){
            for (File file : folderPath.listFiles()) {
                if(file.isDirectory()) {
                    listFolder.add(file.getAbsolutePath());
                    getListFolders(file.getAbsolutePath(), listFolder);
                }
            }
        }
    }
    
    public static void getListFiles(String path,Vector<String> listFile) {
        File folderPath = new File(path);
        if(folderPath.exists()){
            for (File file : folderPath.listFiles()) {
                if (file.isFile()) {
                    listFile.add(file.getAbsolutePath());
                } else {
                    getListFiles(file.getAbsolutePath(), listFile);
                }
            }
        }
    }

    public static Vector<String> getListFileInFolder(String path) {
        Vector<String> listFile = new Vector<>();
        File folderPath = new File(path);
        if(folderPath.exists()){
            if (folderPath.isFile()) {
                return listFile;
            }

            for (File file : folderPath.listFiles()) {
                if (file.isFile()) {
                    listFile.add(file.getAbsolutePath());
                }
            }
        }
        return listFile;
    }

    public static void getTreeNodeFolder(String path, DefaultMutableTreeNode rootNode) {
        File folderPath = new File(path);
        for (File file : folderPath.listFiles()) {
            if (file.isDirectory()) {
                DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
                rootNode.add(treeNode);
                getTreeNodeFolder(file.getAbsolutePath(), treeNode);
            }
        }
    }

//    public static boolean saveFile(String path, ArrayList<String> content) {
//        try {
//            FileOutputStream fos = new FileOutputStream(path);
//            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//            BufferedWriter bw = new BufferedWriter(osw);
//            for (String line : content) {
//                bw.write(line);
//                bw.newLine();
//            }
//            bw.close();
//            osw.close();
//            fos.close();
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return false;
//    }
    
    public static boolean saveFile(String path, String content){
        try {
            File file = new File(path);
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(path);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(content);
            bw.close();
            osw.close();
            fos.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

//    public static ArrayList<String> readFile(String path) {
//        ArrayList<String> content = new ArrayList<>();
//        try {
//            FileInputStream fis = new FileInputStream(path);
//            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//            BufferedReader br = new BufferedReader(isr);
//            String line = br.readLine();
//            while (line != null) {
//                content.add(line);
//                line = br.readLine();
//            }
//            br.close();
//            isr.close();
//            fis.close();
//            return content;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return content;
//    }
    
    public static String readFile(String path){
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                if(builder.toString().length()!=0) 
                    builder.append("\n");
                builder.append(line);
                line = br.readLine();
            }
            br.close();
            isr.close();
            fis.close();
            return builder.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean renameFolderOrFile(String dirPath, String newName) {
        boolean result = false;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            System.err.println("There is no directory @ given path");
            return result;
        } else {
            File newDir = new File(dir.getParent() + "\\" + newName);
            result = dir.renameTo(newDir);
        }
        return result;
    }
    
    public static void deleteIfExists(String path){
        try
        { 
            Files.deleteIfExists(Paths.get(path)); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists");
            return;
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty.");
            return;
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions.");
            return;
        } 
         
        System.out.println("Deletion successful " + new File(path).getName());
    }
}
