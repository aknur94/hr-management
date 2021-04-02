package kz.onetech.hr.techstack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.onetech.hr.candidate.model.Candidate;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tech_stacks")
@Data
public class TechStack {

    public TechStack() {
    }

    public TechStack(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_stacks_id")
    private Long id;

    @Column(name = "stack_name")
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "candidates_tech_stacks",
            joinColumns = @JoinColumn(name = "tech_stacks_id"),
            inverseJoinColumns = @JoinColumn(name = "candidates_id"))
    private List<Candidate> candidates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechStack techStack = (TechStack) o;
        return name.equals(techStack.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}