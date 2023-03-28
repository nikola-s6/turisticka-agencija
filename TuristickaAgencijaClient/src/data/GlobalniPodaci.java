/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import data.type.TipoviPodataka;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nikol
 */
public class GlobalniPodaci {

    private Map<TipoviPodataka, Object> podaci;
    private static GlobalniPodaci instance;

    private GlobalniPodaci() {
        podaci = new HashMap<>();
    }
    
    public static GlobalniPodaci getInstance(){
        if(instance == null){
            instance = new GlobalniPodaci();
        }
        return instance;
    }
    
    public void add(TipoviPodataka tp, Object param){
        podaci.put(tp, param);
    }
    
    public Object get(TipoviPodataka tp){
        return podaci.get(tp);
    }
    
    public void reset(){
        podaci.clear();
    }
}
