package pl.edu.pw.elka.pszt.knapsack.model;

import lombok.Getter;

@Getter
public class Setting {
    Setting(String name, Object value){
        this.name = name;
        this.value = value;
    }
    public void setValue(Object value){
        if(defaultValue){
            defaultValue = false;
            this.value = value;
        }
    }
    private final String name;
    private Object value;
    private boolean defaultValue = true;
}
