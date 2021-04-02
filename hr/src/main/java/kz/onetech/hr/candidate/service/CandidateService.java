package kz.onetech.hr.candidate.service;

import kz.onetech.hr.candidate.dto.CandidateDetailDto;
import kz.onetech.hr.candidate.model.Candidate;
import kz.onetech.hr.candidate.repo.CandidateRepo;
import kz.onetech.hr.exception.NotAllowedException;
import kz.onetech.hr.exception.NotFoundException;
import kz.onetech.hr.techstack.model.TechStack;
import kz.onetech.hr.techstack.repo.TechStackRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepo candidateRepo;
    private final TechStackRepo techStackRepo;
    private final RestTemplate restTemplate;

    public CandidateService(CandidateRepo candidateRepo, TechStackRepo techStackRepo, RestTemplate restTemplate) {
        this.candidateRepo = candidateRepo;
        this.techStackRepo = techStackRepo;
        this.restTemplate = restTemplate;
    }

    @CachePut(value = "candidates", key = "#candidate.id")
    public Candidate registerCandidate(Candidate candidate) throws Exception {
        if (candidate.getId() != null) throw new NotAllowedException("Already has id = " + candidate.getId());
        List<TechStack> set = candidate.getTechStack().stream()
                .map(this::findOrCreateTechStack)
                .collect(Collectors.toList());
        candidate.setTechStack(set);
        return candidateRepo.save(candidate);
    }

    @CachePut(value = "candidates", key = "#detail.id")
    public Candidate updateCandidateDetails(CandidateDetailDto detail) throws Exception {
        Optional<Candidate> candidateOptional = candidateRepo.findById(detail.getId());
        if (!candidateOptional.isPresent()) throw new NotFoundException("No candidate with that id = " + detail.getId());
        Candidate candidate = candidateOptional.get();
        if (!detail.getFullName().isEmpty() && detail.getFullName() != null)
            candidate.setFullName(detail.getFullName());
        if (!detail.getPhone().isEmpty() && detail.getPhone() != null)
            candidate.setPhone(detail.getPhone());
        if (!detail.getCity().isEmpty() && detail.getCity() != null)
            candidate.setCity(detail.getCity());
        return candidateRepo.save(candidate);
    }

    public Iterator<Candidate> getAll(Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        return candidateRepo.findAll(PageRequest.of(page, size)).stream().iterator();
    }

    @CachePut(value = "candidates", key = "#id")
    public Candidate addTechStacks(Long id, List<TechStack> techStack) throws Exception {
        Optional<Candidate> optionalCandidate = candidateRepo.findById(id);
        if (!optionalCandidate.isPresent()) throw new NotFoundException("There is no Candidate with id = " + id);
        Candidate candidate = optionalCandidate.get();
        candidate.getTechStack().addAll(techStack);
        List<TechStack> list = candidate.getTechStack().stream()
                .map(this::findOrCreateTechStack)
                .collect(Collectors.toList());
        candidate.setTechStack(list);

        return candidateRepo.save(candidate);
    }

    @CachePut(value = "candidates", key = "#id")
    public Candidate deleteTechStacks(Long id, List<TechStack> techStack) throws Exception {
        Optional<Candidate> optionalCandidate = candidateRepo.findById(id);
        if (!optionalCandidate.isPresent()) throw new NotFoundException("There is no Candidate with id = " + id);
        Candidate candidate = optionalCandidate.get();
        candidate.getTechStack().removeAll(techStack);
        return candidateRepo.save(candidate);
    }

    public List<Candidate> getByTechStack(String name) {
        Optional<TechStack> techStackOptional = techStackRepo.findByName(name);
        if (!techStackOptional.isPresent()) return Collections.emptyList();
        return techStackOptional.get().getCandidates();
    }

    @CacheEvict(value = "candidates")
    public void deleteCandidate(Long id) {
        candidateRepo.deleteById(id);
    }

    public void saveCV(Long id, MultipartFile file) throws IOException {
        Optional<Candidate> candidateOptional = candidateRepo.findById(id);
        if (!candidateOptional.isPresent()) return;
        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes());
        restTemplate.postForObject(
                "http://CVHOSTER/cv/candidate/" + id,
                entity,
                String.class);
    }

    public byte[] getCV(Long id) {
        Optional<Candidate> candidateOptional = candidateRepo.findById(id);
        if (!candidateOptional.isPresent()) return null;
        return restTemplate.getForObject(
                "http://CVHOSTER/cv/candidate/"+id,
                byte[].class
        );
    }

    @Cacheable(value = "candidates")
    public Candidate getById(Long id) {
        return candidateRepo.findById(id).orElse(null);
    }

    private TechStack findOrCreateTechStack(TechStack techStack) {
        if (techStack.getId() != null) return techStack;
        return techStackRepo.findByName(techStack.getName()).orElse(techStack);
    }
}