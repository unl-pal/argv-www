import java.util.Stack;
public class Calculator {
	public static void main(String[] args){
		Calculator calc = new Calculator();
		System.out.println(calc.calculate("1+3*(5+ 4* (10/5))-6"));
	}
	int getPrecedence(char operator)
	{
		switch(operator)
		{
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
		 return 2;
		case '(':
			return 3;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	int applyOperation(char operator, int operand1, int operand2)
	{
		switch(operator)
		{
		case '+':
			return operand1 + operand2;
		case '-':
			return operand1-operand2;
		case '*':
			return operand1 * operand2;
		case '/' :
			return operand1 / operand2;
		default: 
			throw new IllegalArgumentException();
		}
	}
	
	boolean isOperator(char ch)
	{
		switch(ch)
		{
		case '+':
		case '-':
		case '*':
		case '/':
		case '(':
			return true;
		default:
			return false;
		}
	}
	
	int calculate(String infix)
	{
		int index = 0;
		int length = infix.length();
		char[] infixChars = infix.toCharArray();
		Stack<Integer> operandStack = new Stack<Integer>();
		Stack<Character> operatorStack = new Stack<Character>();
		
		while(index < length)
		{
			//Check for operand
			if(infixChars[index]>='0' && infixChars[index] <='9')
			{
				StringBuilder sb = new StringBuilder();
				while(index < length && infixChars[index]>='0' && infixChars[index] <='9')
				{
					sb.append(infixChars[index]);
					index++;
				}
				operandStack.push(Integer.parseInt(sb.toString()));
				continue;
			}
			
			//Check for empty space
			if(infixChars[index] == Character.SPACE_SEPARATOR)
			{
				index++;
				continue;
			}
			
			//Check for ) char
			if(infixChars[index]  == ')')
			{
				//Pop till operator (
				while(operatorStack.peek() != '(')
				{
					int operand2 = operandStack.pop();
					int operand1 = operandStack.pop();
					char operator = operatorStack.pop();
					
					//Apply operation
					int result = applyOperation(operator, operand1, operand2);
					operandStack.push(result);
				}
				operatorStack.pop();
			}
			
			//Check for operator
			if(isOperator(infixChars[index]))
			{
				int prec = getPrecedence(infixChars[index]);
				if(operatorStack.isEmpty() || prec >= getPrecedence(operatorStack.peek()))
				{
					operatorStack.push(infixChars[index]);
				}
				else
				{
					while(!operatorStack.isEmpty() && 
							operatorStack.peek() != '(' &&
							getPrecedence(operatorStack.peek()) >= prec)
					{
						int operand2 = operandStack.pop();
						int operand1 = operandStack.pop();
						char operator = operatorStack.pop();
						
						//Apply operation
						int result = applyOperation(operator, operand1, operand2);
						operandStack.push(result);
					}
					operatorStack.push(infixChars[index]);
				}
			}
			index++;
		}
		
		//Empty stacks
		while(!operatorStack.empty())
		{
			int operand2 = operandStack.pop();
			int operand1 = operandStack.pop();
			char operator = operatorStack.pop();
			
			//Apply operation
			int result = applyOperation(operator, operand1, operand2);
			operandStack.push(result);
		}
		
		return operandStack.pop();
	}
}
