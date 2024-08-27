package entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhachHang {
	private String soDienThoai;
	private String tenKH;
	private LocalDate ngaySinh;
	private Boolean gioiTinh;
	private int diem;
	private String hang;

	public String xepHang() {
		if (diem >=0 && diem <= 500000) {
			return "D";
		}
		if (diem >= 500000 && diem <= 1000000) {
			return "C";
		}
		if (diem >= 1000000 && diem <= 10000000) {
			return "B";
		}
		if (diem > 10000000) {
			return "A";
		} 
		return "D";
	}

	// cons
	public KhachHang(String tenKH, LocalDate ngaySinh, Boolean gioiTinh, int diem, String soDienThoai, String hang) {
		super();
		this.tenKH = tenKH;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diem = diem;
		this.soDienThoai = soDienThoai;
		this.hang = hang;
	}

	public KhachHang(String soDienThoai) {
		super();
		this.soDienThoai = soDienThoai;
	}

	public KhachHang() {
		super();
	}

	// gets
	public String getTenKH() {
		return tenKH;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public Boolean getGioiTinh() {
		return gioiTinh;
	}

	public int getDiem() {
		return diem;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public String getHang() {
		return hang;
	}

	// sets
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public void setGioiTinh(Boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public void setDiem(int diem) {
		this.diem = diem;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public void setHang(String hang) {
		this.hang = hang;
	}

	// toString
	@Override
	public String toString() {
		return "KhachHang [tenKH=" + tenKH + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", diem=" + diem
				+ ", soDienThoai=" + soDienThoai + ", hang=" + hang + "]";
	}

	// Hash n equal
	@Override
	public int hashCode() {
		return Objects.hash(soDienThoai);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(soDienThoai, other.soDienThoai);
	}

	// others
}
