package entities;

import java.io.Serializable;

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
public class ChiTietHoaDon implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "maThuoc", referencedColumnName = "maThuoc")
	private Thuoc thuoc;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "maHD", referencedColumnName = "maHD")
	private HoaDon hoaDon;
	private int soLuong;
	private double donGia;
	
	public double getThanhTien() {
		return soLuong*donGia;
	}
}
