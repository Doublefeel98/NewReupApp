/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.smali;

import static data.Constant.COMPONIENT_TYPE.FIELD;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author THAITHANG
 */
public class Field extends Component{

    private String type;
    private String name;
    
    private List<Annotation> annotations;
    
    public Field(String line) {
        
        annotations = new ArrayList<>();
        
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
         name  = arr[arr.length - 1];
        setComponientType(FIELD);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }
    
    @Override
    public void replace(String oldString, String newString){
        if(name.contains(oldString))
            name = name.replace(oldString, newString);
        if(!annotations.isEmpty()){
            for(Annotation ano : annotations){
                ano.replace(oldString, newString);
            }
        }
    }
    
    @Override
    public String toSmali(){
        String smali = ".field ";
        if(type != null)
            smali+= type + " ";
        
        smali+= name;
        if(!annotations.isEmpty()){
            for(int i = 0; i <annotations.size(); i++){
                smali+="\n" + annotations.get(i).toSmali();
                if(i < annotations.size() -1) smali+="\n";
            }
        }
        
        smali +=  "\n.end field";
        return smali;
    }
}
