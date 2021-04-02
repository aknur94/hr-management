package kz.onetech.hr.controller;

import kz.onetech.hr.candidate.dto.CandidateDetailDto;
import kz.onetech.hr.candidate.model.Candidate;
import kz.onetech.hr.candidate.repo.CandidateRepo;
import kz.onetech.hr.candidate.service.CandidateService;
import kz.onetech.hr.techstack.model.TechStack;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateRepo candidateRepo;
    private final CandidateService candidateService;

    public CandidateController(CandidateRepo candidateRepo, CandidateService candidateService) {
        this.candidateRepo = candidateRepo;
        this.candidateService = candidateService;
    }

    @PostMapping("/register")
    public Candidate registerCandidate(@RequestBody Candidate candidate) throws Exception {
        return candidateService.registerCandidate(candidate);
    }

    @PutMapping("/detail")
    public Candidate updateCandidate(@RequestBody CandidateDetailDto detail) throws Exception {
        return candidateService.updateCandidateDetails(detail);
    }

    @PutMapping("/{id}/tech-stack")
    public Candidate addTechStack(@PathVariable("id") Long id, @RequestBody List<TechStack> techStack) throws Exception {
        return candidateService.addTechStacks(id, techStack);
    }

    @DeleteMapping("/{id}/tech-stack")
    public Candidate deleteTechStack(@PathVariable("id") Long id, @RequestBody List<TechStack> techStack) throws Exception {
        return candidateService.deleteTechStacks(id, techStack);
    }

    @GetMapping
    public Iterator<Candidate> getAll(
            @RequestParam
            @Nullable
                    Integer page,
            @RequestParam
            @Nullable
                    Integer size) {

        return candidateService.getAll(page, size);
    }

    @GetMapping("/tech-stack")
    public List<Candidate> getByTechStack(
            @RequestParam
                    String name) {
        return candidateService.getByTechStack(name);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidate(@PathVariable("id") Long id) {
        candidateService.deleteCandidate(id);
    }

    @PostMapping(value = "/{id}/cv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadCV(@PathVariable("id") Long id,
                         @RequestPart(value = "file")
                                 MultipartFile file) throws IOException {
        candidateService.saveCV(id, file);
    }

    @GetMapping(value = "/{id}/cv")
    public byte[] getCV(@PathVariable("id") Long id) {
        return candidateService.getCV(id);
    }

    @GetMapping("/{id}")
    public Candidate getCandidate(@PathVariable("id") Long id) {
        return candidateService.getById(id);
    }
}