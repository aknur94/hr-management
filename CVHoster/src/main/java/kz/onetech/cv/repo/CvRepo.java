package kz.onetech.cv.repo;

import kz.onetech.cv.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CvRepo extends JpaRepository<Cv, Long> {
    Optional<Cv> findFirstByCandidateId(Long candidateId);
}
