package kz.onetech.hr.candidate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.onetech.hr.techstack.model.TechStack;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "candidates")
@Data
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidates_id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    private String city;

    private String phone;

    @JsonIgnore
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] cv;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "candidates_tech_stacks",
                joinColumns = @JoinColumn(name = "candidates_id"),
                inverseJoinColumns = @JoinColumn(name = "tech_stacks_id"))
    private List<TechStack> techStack;
}