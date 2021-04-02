package kz.onetech.cv.service;

import kz.onetech.cv.exception.NotFoundException;
import kz.onetech.cv.model.Cv;
import kz.onetech.cv.repo.CvRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CvService {
    private final CvRepo cvRepo;

    public CvService(CvRepo cvRepo) {
        this.cvRepo = cvRepo;
    }

    public Cv saveCV(Long candidateId, byte[] data) {
        Cv cv = cvRepo.findFirstByCandidateId(candidateId).orElse(new Cv());
        cv.setCandidateId(candidateId);
        cv.setData(data);
        return cvRepo.save(cv);
    }

    public byte[] getCvData(Long candidateId) throws NotFoundException {
        Optional<Cv> optionalCv = cvRepo.findFirstByCandidateId(candidateId);
        if (!optionalCv.isPresent()) throw new NotFoundException("CV for candidateId = " + candidateId + " not found");
        return optionalCv.get().getData();
    }
}