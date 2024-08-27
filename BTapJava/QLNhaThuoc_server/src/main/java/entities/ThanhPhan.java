package entities;

import java.io.Serializable;

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
public class ThanhPhan implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String maTP;
	private String tenTP;
	private String chucNang;
	
}
