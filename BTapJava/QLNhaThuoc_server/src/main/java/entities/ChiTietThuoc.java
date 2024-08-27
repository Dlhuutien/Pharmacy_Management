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
public class ChiTietThuoc implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String ma;
	@ManyToOne
	@JoinColumn(name = "maThuoc", referencedColumnName = "maThuoc")
	private Thuoc thuoc;
	@ManyToOne
	@JoinColumn(name = "maTP", referencedColumnName = "maTP")
	private ThanhPhan thanhphan;
	public ChiTietThuoc(String ma) {
		super();
		this.ma = ma;
	}
	
	
}
