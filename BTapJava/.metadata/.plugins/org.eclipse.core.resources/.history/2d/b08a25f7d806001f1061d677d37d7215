package entity;

import java.time.LocalDate;
import java.util.Objects;

public class QuanLy {
	private String maQL;
	private String tenQL;
	private boolean gioiTinh;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private double luongCoBan;
	//cons
	public QuanLy(String maQL, String tenQL, boolean gioiTinh, String soDienThoai, LocalDate ngaySinh,
			double luongCoBan) {
		super();
		this.maQL = maQL;
		this.tenQL = tenQL;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.luongCoBan = luongCoBan;
	}
	public QuanLy() {
		super();
	}
	public QuanLy(String maQL) {
		super();
		this.maQL = maQL;
	}
	public String getMaQL() {
		return maQL;
	}
	public String getTenQL() {
		return tenQL;
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
	public void setMaQL(String maQL) {
		this.maQL = maQL;
	}
	public void setTenQL(String tenQL) {
		this.tenQL = tenQL;
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
	@Override
	public String toString() {
		return "QuanLy [maQL=" + maQL + ", tenQL=" + tenQL + ", gioiTinh=" + gioiTinh + ", soDienThoai=" + soDienThoai
				+ ", ngaySinh=" + ngaySinh + ", luongCoBan=" + luongCoBan + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maQL);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuanLy other = (QuanLy) obj;
		return Objects.equals(maQL, other.maQL);
	}	
}
