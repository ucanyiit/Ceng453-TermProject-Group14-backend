package ceng453.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "properties")
@Entity
@Getter
@Setter
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
}