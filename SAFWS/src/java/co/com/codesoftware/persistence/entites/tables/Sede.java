package co.com.codesoftware.persistence.entites.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "em_tsede")
public class Sede implements Serializable {

    @Id
    @Column(name = "sede_sede")
    private Integer id;
    @Column(name = "sede_nombre")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
