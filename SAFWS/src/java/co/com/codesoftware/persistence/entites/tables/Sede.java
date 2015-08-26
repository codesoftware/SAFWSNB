package co.com.codesoftware.persistence.entites.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="em_tsede")
public class Sede{
	@Id
	@Column(name="sede_sede")
	private Long id;
    @Column(name="sede_nombre")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
