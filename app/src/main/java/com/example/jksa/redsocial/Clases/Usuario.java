package com.example.jksa.redsocial.Clases;

public class Usuario {
    private String nombre,correo,foto_perfil;
    private boolean flag_en_linea;

    public Usuario() {
    }

    public Usuario(String nombre, String correo,String foto_perfil) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto_perfil = foto_perfil;
    }

    public Usuario(String nombre, boolean flag_en_linea){
        this.nombre = nombre = nombre;
        this.flag_en_linea = flag_en_linea;
    }

    public Usuario(String nombre, boolean flag_en_linea,String foto_perfil){
        this.nombre = nombre = nombre;
        this.flag_en_linea = flag_en_linea;
        this.foto_perfil = foto_perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public boolean getFlag_en_linea() {
        return flag_en_linea;
    }

    public void setFlag_en_linea(boolean flag_en_linea) {
        this.flag_en_linea = flag_en_linea;
    }

}
