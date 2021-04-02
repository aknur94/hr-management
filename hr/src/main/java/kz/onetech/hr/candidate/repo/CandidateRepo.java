package kz.onetech.hr.candidate.repo;

import kz.onetech.hr.candidate.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CandidateRepo extends JpaRepository<Candidate, Long>, PagingAndSortingRepository<Candidate, Long> {


    List<Candidate> getCandidatesByTechStackIsLike(String name);
}