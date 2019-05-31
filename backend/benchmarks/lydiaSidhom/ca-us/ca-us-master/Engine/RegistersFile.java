package Engine;

import java.util.Random;
import java.util.HashMap;

public class RegistersFile {
	String readAddress1;
	String readAddress2;
	String writeAddress;
	String inputData;
	String outputRegister1;
	String outputRegister2;
	int WriteSignal;
	
	public RegistersFile(){
	}
	public void read() throws Exception{
		Random rand = new Random();
		String outputRegister2;
		String outputRegister1;
		if (rand.nextBoolean()){
			throw new Exception("Wrong Address read");
		}
		{
		}
		{
		}
	}
	public void setWritingSignal(int input){
		WriteSignal = input;
	}
	public void write() throws Exception{
		if(this.WriteSignal==1){
		}
	}
	public void printall() {

	}
}
