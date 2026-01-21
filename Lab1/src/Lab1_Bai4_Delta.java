import java.util.Scanner;
public class Lab1_Bai4_Delta {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		//Nhập dữ liệu
		System.out.println("Nhập a: ");
		Float a = scanner.nextFloat();
		System.out.println("Nhập b: ");
		Float b = scanner.nextFloat();
		System.out.println("Nhập c: ");
		Float c = scanner.nextFloat();
		
		
		// In lại phương trình bật 2 
		System.out.print("Phương trình của bạn là: ");
		if(a == Math.floor(a)) {
			System.out.print(a.intValue());
		} else {
			System.out.print(a);
		}
		System.out.print("x^2 ");
		//
		if(b > 0) {
			System.out.print("+ ");
		}else if(b < 0){
			System.out.print("- ");
			b = -b;
		}
		if (b == Math.floor(b)) {
			System.out.print(b.intValue());
		} else {
			System.out.print(b);
		}
		System.out.print("x ");
		//
		if(c > 0) {
			System.out.print("+ ");
		} else if(c < 0) {
			System.out.print("- ");
			c = -c;
		}
		if(c == Math.floor(c)) {
			System.out.print(c.intValue());
		} else {
			System.out.print(c);	
		}
		System.out.println("= 0");
		
		
		float Delta = b * b - 4 * a * c;
		
		if(Delta < 0) {
			System.out.println("Phương trình vô nghiệm vì Delta < 0 .");
		} else {
			System.out.println("Delta là: " + Delta);
			System.out.println("Căn Delta = " + Math.sqrt(Delta));
		}

	}

}
