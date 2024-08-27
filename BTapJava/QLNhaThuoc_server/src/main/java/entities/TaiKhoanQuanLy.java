package entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class TaiKhoanQuanLy implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String maQL;

	private String taiKhoan;
	private String matKhau;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maQL", referencedColumnName = "maQL", insertable = false, updatable = false)
	private QuanLy quanLy;
	public TaiKhoanQuanLy(String taiKhoan) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau  = taiKhoan;
	}
	
	
}
