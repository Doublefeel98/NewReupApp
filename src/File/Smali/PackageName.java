package File.Smali;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author THAITHANG
 */
public class PackageName {
    
    private String textContent;
    private String head;
    private String oldName;
    private String newName;

    public PackageName(String textContent) {
        this.textContent = textContent;
        
        int indexOf =  textContent.indexOf('L');
        head = textContent.substring(0, indexOf -1);
        newName = oldName = textContent.substring(indexOf, textContent.length());
    }

    public String getTextContent() {
        return textContent;
    }

    public String getOldName() {
        return oldName;
    }
    
    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Override
    public String toString() {
        return head + " " + newName;
    }
}
