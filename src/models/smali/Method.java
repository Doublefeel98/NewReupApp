/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.smali;

import static data.Constant.COMPONIENT_TYPE.METHOD;
import java.util.List;

/**
 *
 * @author THAITHANG
 */
public class Method extends Component{
    
    private int flag;
    private String oldName;
    private String type;
    private String name;
    private String param;

    private List<Line> lines;

    public Method(String method, List<Line> lines) {
        flag = 0;
        
        String []arr = method.split(" ");
        
        if(arr.length == 2){
            type = null;
        }
        else{
            type = arr[1];
            for(int i = 2 ; i < arr.length -1;i++){
                type += " "+arr[i];
            }
        }
        
        String end = arr[arr.length - 1];
        
        int indexOf = end.indexOf('(');
        name = end.substring(0, indexOf);
        oldName = new String(name);
        param = end.substring(indexOf, end.length());
        
        this.lines = lines;
        setComponientType(METHOD);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getParam() {
        return param;
    }

    public String getFullName(){
        return name + param;
    }
    
    public List<Line> getLines() {
        return lines;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
    
    public void replaceLines(String oldPack, String newPack){
        for(Line line : lines){
            line.replace(oldPack, newPack);
     
        }
    }
    
    @Override
    public void replace(String oldString, String newString){
        if(param.contains(oldString))
            param = param.replace(oldString, newString);
        for(Line line : lines){
            line.replace(oldString, newString);
        }
    }
    
    @Override
    public String toSmali(){
        String smali = ".method ";
        if(type != null)
            smali+= type + " ";
        smali += name + param + "\n";
        for(Line line : lines){
            smali += "\t" + line.toSmali() +"\n";
        }
        smali +=".end method";
        return smali;
    }
    
    public void replaceName(String newName){
        oldName = new String(name);
        name = newName;
    }
}
