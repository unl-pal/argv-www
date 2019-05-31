/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package google;

/**
 *
 * @author Asus
 */
public class Data {
    private int dia;
    private int mes;
    private int ano;
    
    public Data(){
    
    }
    public int getDia(){
        return this.dia;
    }
    public int getMes(){
        return this.mes;
    }
    public int getAno(){
        return this.ano;
    }
    public int setDia(int dia){
        this.dia = dia;
        return dia;
    }
    public int setMes(int mes){
        this.mes = mes;
        return mes;
    }
    public int setAno(int ano){
        this.ano = ano;
        return ano;
    }
    
    public Data(int dia,int mes,int ano){
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
    public String toString(){
        if(dia>=0 && dia<=31 && mes>=0 && mes<=12 && ano>=0){
        return dia + "/" + mes + "/" + ano;
        }
        return "Data Invalida!";
    }
}
