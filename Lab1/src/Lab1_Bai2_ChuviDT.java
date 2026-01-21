import java.util.Scanner;
public class Lab1_Bai2_ChuviDT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		//Nhập 2 cạnh
		System.out.println("Nhập chiều dài: ");
		float dai = scanner.nextFloat();
		System.out.println("Nhập chiều rộng: ");
		float rong = scanner.nextFloat();
		//Tính chu vi, diện tích, cạnh nhỏ
		float ChuVi = 2 * (dai + rong);
		float DienTich = dai * rong;
		float CanhNho = Math.min(dai, rong);
		//Xuất kết quả
		System.out.println("Chu vi hình chủ nhật là: " + ChuVi);
		System.out.println("Diện tích hình chữ nhật là: " + DienTich);
		System.out.println("Cạnh nhỏ của hình chữ nhật là: " + CanhNho);

	}

}
