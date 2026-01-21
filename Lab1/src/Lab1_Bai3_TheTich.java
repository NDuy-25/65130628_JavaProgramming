import java.util.Scanner;
public class Lab1_Bai3_TheTich {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		//Nhập 3 cạnh: dài, rộng, cao
		System.out.println("Nhập chiều rộng khối chữ nhật: ");
		Float rong = scanner.nextFloat();
		System.out.println("Nhập chiều dài khối chữ nhật: ");
		Float dai = scanner.nextFloat();
		System.out.println("Nhập chiều cao khối chữ nhật: ");
		Float cao = scanner.nextFloat();
		
		float TheTich = dai * rong * cao;
		// Xuất kể quả
		System.out.println("Thể tích khối chữ nhật là: " + TheTich);

	}

}
