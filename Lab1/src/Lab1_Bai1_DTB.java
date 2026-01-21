import java.util.Scanner;
public class Lab1_Bai1_DTB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		// Nhập họ tên
		System.out.println("Nhập họ và tên sinh viên: ");
		String HoTen = scanner.nextLine();
		
		//Nhập điểm trung bình
		System.out.println("Vui lòng nhập điểm trung bình: ");
		float DiemTB = scanner.nextFloat();
		
		// Xuất kết quả
		System.out.println("Sinh viên: " + HoTen);
		System.out.println("Điểm trung bình: " + DiemTB);

	}

}
