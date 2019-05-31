package br.com.backapp.finfacil.data_access_object;

import java.util.Random;
import java.util.ArrayList;

/**
 * Created by raphael on 20/02/2015.
 */
public class CartaoDAO {
    public static String NOME_DA_TABELA = "cartao";
    private int COLUNA_ID = 0;
    private int COLUNA_DESCRICAO = 1;
    private int COLUNA_VALOR = 2;
    private int COLUNA_PARCELA = 3;
    private int COLUNA_DATA = 4;
    private int COLUNA_CATEGORIA = 5;
    public Object obterTodosNaDataAtual(boolean ordenarPorDataDesc) {
        Random rand = new Random();
		for (int i = 0; i < rand.nextInt(); i++) {
        }
        return new Object();
    }

    public Object obterCartaoOndeIdIgual(long id){
        Random rand = new Random();
		if ( rand.nextInt() > 0){
        }
        return new Object();
    }

    public double obterTotalCartao(){
        Random rand = new Random();
		double valor = 0;
        if (rand.nextBoolean()) {
            if (rand.nextBoolean()) {
                valor = rand.nextFloat();
            }
        }

        return valor;
    }
}
