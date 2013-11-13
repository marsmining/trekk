package trekk.server.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(super.hashCode(), id);
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof BaseEntity) {
            BaseEntity that = (BaseEntity) object;
            return Objects.equal(this.id, that.id);
        }
        return false;
    }
}
