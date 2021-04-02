package kz.onetech.cv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "cv")
@Data
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private Long id;

    @Column(name = "candidate_id")
    private Long candidateId;

    @JsonIgnore
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;
}