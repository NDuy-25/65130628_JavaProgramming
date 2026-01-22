import java.util.Scanner;
public class BTBoSung_SoNT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.printf("Nhập n: ");
		Long n = scanner.nextLong();
		
		boolean isPrime = true;
		if(n < 2) {
			isPrime = false;
		}
		for(int i = 2; i <= n-1; i++) {
			if(n % i == 0) {
				isPrime = false;
				break;
			}
		}
		if(isPrime) {
			System.out.println( n + " là số nguyên tố.");
		} else {
			System.out.println( n + " không phải là số nguyên tố.");
		}
	}

}
