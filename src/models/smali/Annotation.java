/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.smali;

import static data.Constant.COMPONIENT_TYPE.ANOTATION;


/**
 *
 * @author THAITHANG
 */
public class Annotation extends Component{
    String textContent;

    public Annotation(String textContent) {
        this.textContent = textContent;
        setComponientType(ANOTATION);
    }

    public String getTextContent() {
        return textContent;
    }
    
    @Override
    public String toSmali(){
        return textContent;
    }

    @Override
    public void replace(String oldString, String newString) {
        if(textContent.contains(oldString))
            textContent = textContent.replace(oldString, newString);
    }
}
