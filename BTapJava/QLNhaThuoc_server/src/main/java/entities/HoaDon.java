package entities;

import java.io.Serializable;
import java.time.LocalDate;

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
public class HoaDon implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String maHD;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNV", referencedColumnName = "maNV")
    private NhanVien nv;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soDienThoai", referencedColumnName = "soDienThoai")
    private KhachHang kh;
    private LocalDate ngayMua;
    private double thue;
    private double tongTien;
	
}
