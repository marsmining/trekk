package trekk.server.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;
}
