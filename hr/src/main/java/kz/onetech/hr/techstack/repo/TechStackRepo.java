package kz.onetech.hr.techstack.repo;

import kz.onetech.hr.techstack.model.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechStackRepo extends JpaRepository<TechStack, Long> {
    Optional<TechStack> findByName(String name);
}