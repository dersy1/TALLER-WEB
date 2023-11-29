package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;

public class ItemsPartido {
    private Boolean driblear = false;
    private Boolean tirar = false;
    private Boolean pasar = false;
    private Boolean robar = false;
    private Boolean tapar = false;
    private Boolean interceptar = false;

    public Boolean getDriblear() {
        return driblear;
    }

    public void setDriblear(Boolean driblear) {
        this.driblear = driblear;
    }

    public Boolean getTirar() {
        return tirar;
    }

    public void setTirar(Boolean tirar) {
        this.tirar = tirar;
    }

    public Boolean getPasar() {
        return pasar;
    }

    public void setPasar(Boolean pasar) {
        this.pasar = pasar;
    }

    public Boolean getRobar() {
        return robar;
    }

    public void setRobar(Boolean robar) {
        this.robar = robar;
    }

    public Boolean getTapar() {
        return tapar;
    }

    public void setTapar(Boolean tapar) {
        this.tapar = tapar;
    }

    public Boolean getInterceptar() {
        return interceptar;
    }

    public void setInterceptar(Boolean interceptar) {
        this.interceptar = interceptar;
    }

    public void vaciarTodos(){
        this.setInterceptar(false);
        this.setPasar(false);
        this.setDriblear(false);
        this.setTapar(false);
        this.setRobar(false);
        this.setTirar(false);
    }
    public void setearTrue(String nombre){
        if (nombre.equalsIgnoreCase("tirar + 5")) {
            setTirar(true);
        } else if (nombre.equalsIgnoreCase("pasar + 5")) {
            setPasar(true);
        } else if (nombre.equalsIgnoreCase("driblear + 5")) {
            setDriblear(true);
        } else if (nombre.equalsIgnoreCase("robar + 5")) {
            setRobar(true);
        } else if (nombre.equalsIgnoreCase("tapar + 5")) {
            setTapar(true);
        } else if (nombre.equalsIgnoreCase("interceptar + 5")) {
            setInterceptar(true);
        }
    }

    public List<Long> traerLosFalse(){
        List<Long> falsos = new ArrayList<>();
        if(Boolean.FALSE.equals(this.driblear)){
            falsos.add(3L);
        }
        if (Boolean.FALSE.equals(this.tirar)) {
            falsos.add(1L);
        }
        if (Boolean.FALSE.equals(this.pasar)) {
            falsos.add(2L);
        }
        if (Boolean.FALSE.equals(this.robar)) {
            falsos.add(4L);
        }
        if (Boolean.FALSE.equals(this.tapar)) {
            falsos.add(5L);
        }
        if (Boolean.FALSE.equals(this.interceptar)) {
            falsos.add(6L);
        }
        return falsos;
    }
    public List<String> traerLosTrue(){
        List<String> verdaderos = new ArrayList<>();
        if(this.driblear){
            verdaderos.add("drible");
        }
        if (this.tirar) {
            verdaderos.add("tirar");
        }
        if (this.pasar) {
            verdaderos.add("pasar");
        }
        if (this.robar) {
            verdaderos.add("robar");
        }
        if (this.tapar) {
            verdaderos.add("tapar");
        }
        if (this.interceptar) {
            verdaderos.add("interceptar");
        }
        return verdaderos;
    }

}
