package ceng453.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "properties")
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PropertyGame> properties = new ArrayList<>();
    private String name;

    public Property() {
    }

    public Property(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertyGame> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyGame> properties) {
        this.properties = properties;
    }
}