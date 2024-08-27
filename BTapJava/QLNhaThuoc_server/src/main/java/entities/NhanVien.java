package entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class NhanVien implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String maNV;
	private String tenNV;
	private boolean gioiTinh;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private double luongCoBan;
}
