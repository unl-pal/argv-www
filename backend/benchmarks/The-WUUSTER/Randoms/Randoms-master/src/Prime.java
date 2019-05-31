import java.util.Scanner;

public class Prime 
{
	public static boolean isPrime(int n)
	{
		if (n == 2 || n == 3)
			return true;
		else
		{
			for (int i = 2; i <= (n/2); i++)
			{
				if (n % i == 0)
					return false;
			}
			return true;
		}
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a positive integer: ");
		int input = scan.nextInt();
		int prime = 1, count = 1;
		while (count <= input)
		{
			prime++;
			if (isPrime(prime))
				count++;
		}
		System.out.println(prime + " is prime number " + input);
	}
}
