package ceng453.backend.models.database;

import ceng453.backend.models.enums.TileType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tile> properties = new ArrayList<>();
    private String name;
    private TileType type;

    public Property() {
    }

    public Property(String name, TileType type) {
        this.name = name;
        this.type = type;
    }
}