/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veículos;

/**
 *
 * @author Asus
 */
public class Veiculo {
    protected String placa;
    protected String marca;
    protected String modelo;
    protected int ano;
    protected double valorKmRodado;
    protected double kmInicial;
    protected double kmFinal;
    protected double valorLocacao;
    
    public String getPlaca(){
        return this.placa = placa;
    }
    public String getMarca(){
        return this.marca = marca;
    }
    public String getModelo(){
        return this.modelo = modelo;
    }
    public int getAno(){
        return this.ano = ano;
    }
    public double getValorKmRodado(){
        return this.valorKmRodado = valorKmRodado;
    }
    public double getKmInicial(){
        return this.kmInicial = kmInicial;
    }
    public double getKmFinal(){
        return this.kmFinal = kmFinal;
    }
    public double getValorLocacao(){
        return this.valorLocacao = valorLocacao;
    }
    public String setPlaca(String placa){
        this.placa = placa;
        return placa;
    }
    public String setMarca(String marca){
        this.marca = marca;
        return marca;
    }
    public String setModelo(String modelo){
        this.modelo = modelo;
        return modelo;
    }
    public int setAno(int ano){
        this.ano = ano;
        return ano;
    }
    public double setValorKmRodado(double valorKmRodado){
        this.valorKmRodado = valorKmRodado;
        return valorKmRodado;
    }
    public double setKmInicial(double kmInicial){
        this.kmInicial = kmInicial;
        return kmInicial;
    }
    public double setKmFinal(double kmFinal){
        this.kmFinal = kmFinal;
        return kmFinal;
    }
    public double setValorLocacao(double valorLocacao){
        this.valorLocacao = valorLocacao;
        return valorLocacao;
    }
}
