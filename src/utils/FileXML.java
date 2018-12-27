/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import utils.FileFactory;

/**
 *
 * @author THAITHANG
 */
public class FileXML {
    private String path;
    private String textContent;
    private String oldTextContent;
    
    public FileXML(String path) {
        this.path = path;
        read();
    }
    
    public String getPath() {
        return path;
    }
    
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        backup();
        this.textContent = textContent;
    }
    
    private void read() {
        backup();
        textContent = FileFactory.readFile(path);
    }
    
    public boolean save() {
        return FileFactory.saveFile(path, textContent);
    }
    
    private void backup() {
        if (textContent == null) {
            return;
        }
        oldTextContent = String.copyValueOf(textContent.toCharArray());
    }
    
    public void replace(String oldString, String newString) {
        backup();
        textContent = oldTextContent.replace(oldString, newString);
    }
}
