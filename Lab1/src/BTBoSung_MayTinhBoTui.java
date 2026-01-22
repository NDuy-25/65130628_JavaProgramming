import java.util.Scanner;
public class BTBoSung_MayTinhBoTui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Nhập a: ");
		Double a = scanner.nextDouble();
		System.out.println("Nhập b: ");
		Double b = scanner.nextDouble();
		System.out.println("Nhập phép toán: ");
		char op = scanner.next().charAt(0);
		
		if( op == '+') {
			System.out.println("Đáp án của bạn là: " + (a + b));
		} else if(op == '-') {
			System.out.println("Đáp án của bạn là: " + (a - b));
		} else if(op == '*') {
			System.out.println("Đáp án của bạ là: " + (a * b));
		} else if(op == '/') {
			if(b == 0) {
				System.out.println("Không thể chia được cho 0.");
			} else {
				System.out.println("Đáp án của bạn là: " + (a / b));
			}
		} else {
			System.out.println("Phép toán không hợp lệ: ");
		}

	}

}
