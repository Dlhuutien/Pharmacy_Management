package entity;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
	private String maNV;
	private String tenNV;
	private boolean gioiTinh;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private double luongCoBan;
	//cons
	public NhanVien(String maNV, String tenNV, boolean gioiTinh, String soDienThoai, LocalDate ngaySinh,
			double luongCoBan) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.luongCoBan = luongCoBan;
	}
	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}
	public NhanVien() {
		super();
	}
	
	public NhanVien(String maNV, String tenNV) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
	}
	//gets
	public String getMaNV() {
		return maNV;
	}
	public String getTenNV() {
		return tenNV;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public double getLuongCoBan() {
		return luongCoBan;
	}
	//sets
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public void setLuongCoBan(double luongCoBan) {
		this.luongCoBan = luongCoBan;
	}
	//hash n equal
	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}
	//toString
	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", gioiTinh=" + gioiTinh + ", soDienThoai=" + soDienThoai
				+ ", ngaySinh=" + ngaySinh + ", luongCoBan=" + luongCoBan + "]";
	}
}
