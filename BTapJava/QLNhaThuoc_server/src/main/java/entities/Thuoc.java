package entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
public class Thuoc implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String maThuoc;
	private String tenThuoc;
	private String hinhThuoc;
	private boolean keDon;
	@ManyToOne
	@JoinColumn(name = "maNCC", referencedColumnName = "maNCC")
	private NhaCungCap nhaCungCap;
	private String noiSanXuat;
	private LocalDate ngaySanXuat;
	private LocalDate ngayHetHan;
	private String dangBaoChe;
	private double donGiaNhap;
	private double donGiaBan;
	private int soLuongTon;
	
	public boolean kiemTrahanThuoc() {
		if (ngayHetHan.isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}
	
}

