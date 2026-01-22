import java.util.Scanner;
public class BTBoSung_XepLoaiHocSinh {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Vui lòng nhập điểm trung bình môn Ngữ Văn: ");
		Float Van = scanner.nextFloat();
		System.out.println("Vui lòng nhập điểm trung bình môn Toán: ");
		Float Toan = scanner.nextFloat();
		System.out.println("Vui lòng nhập điểm trung bình môn Tiếng Anh: ");
		Float TiengAnh = scanner.nextFloat();
		System.out.println("Vui lòng nhập điểm trung bình môn Vật Lý: ");
		Float VatLy = scanner.nextFloat();
		System.out.println("Vui lòng nhập điểm trung bình môn Hóa Học: ");
		Float Hoa = scanner.nextFloat();
		System.out.println("Vui lòng nhập điểm trung bình môn Lịch Sử: ");
		Float LichSu = scanner.nextFloat();
		System.out.println("Vui lòng nhập điểm trung bình môn Địa Lí: ");
		Float DiaLi = scanner.nextFloat();
		
		Float DiemTB = ( Van + Toan + TiengAnh + VatLy + Hoa + LichSu + DiaLi  ) / 7;
		System.out.println("Điểm trung bình của bạn là: " + DiemTB);
		
		if (DiemTB >= 8.0) {
			System.out.println("Học lực của bạn xếp loại Giỏi");
		} else if( DiemTB >= 6.5 && DiemTB <= 7.9) {
			System.out.println("Học lực của bạn xếp loại Khá");
		} else if( DiemTB >= 5.0 && DiemTB <= 6.4) {
			System.out.println("Học lực của bạn xếp loại Trung Bình");
		}else if( DiemTB < 5.0) {
			System.out.println("Học lực của bạn xếp loại Yếu");
		}
	}

}
