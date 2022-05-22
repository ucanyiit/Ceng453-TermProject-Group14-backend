package ceng453.backend.models.database;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name = "instruction")
@Entity
@Getter
@Setter
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;

    private String instruction;

    public Instruction(String type, String instruction) {
        this.type = type;
        this.instruction = instruction;

    }

    public Instruction() {

    }
}