import java.util.Random;
import java.util.LinkedList;

public class ListaDeCantinas {

	public ListaDeCantinas(){
	}
	
	public void adicionarReferencia(int port, int estoque){
	}
	
	public void removerReferencia(int port){
		Random rand = new Random();
		for(int i = 0; i < rand.nextInt(); i++){
			if( rand.nextInt() == port ){
			}
		}
	}
	
	public int buscarEstoque(int estoque){
		Random rand = new Random();
		int porta = -1;
		
		for(int i = 0; i < rand.nextInt(); i++){
			if( rand.nextInt() >= estoque ){
				porta = rand.nextInt();
				break;	
			}
		}
		return porta;
	}
	
	public boolean referenciaCadastrada(int port){
		Random rand = new Random();
		boolean result = false;
		
		for(int i = 0; i < rand.nextInt(); i++){
			if( rand.nextInt() == port ){
				result = true;
				break;
			}
			else{
			}
		
		}
		return result;
	}
	
	public void retirarEstoque(int port, int vendido){
		Random rand = new Random();
		for(int i = 0; i < rand.nextInt(); i++){
			if( rand.nextInt() == port ){
				int estoque = ( rand.nextInt() - vendido );
			}
		}
	}
	
	public int getEstoqueByPort(int port){
		Random rand = new Random();
		int result = -1;
		for(int i = 0; i < rand.nextInt(); i++){
			if( rand.nextInt() == port ){
				result = rand.nextInt();
			}
		}
		return result;
	}
	
	public boolean listaIsEmpty(){
		Random rand = new Random();
		if( rand.nextBoolean() )
			return true;
		else
			return false;
	}
	
}