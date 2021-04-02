package kz.onetech.cv.controller;

import kz.onetech.cv.exception.NotFoundException;
import kz.onetech.cv.model.Cv;
import kz.onetech.cv.service.CvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cv")
public class CvController {

    private final CvService cvService;

    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    @GetMapping("/candidate/{candidateId}")
    public byte[] getCvDataByCandidateId(@PathVariable("candidateId") Long candidateId) throws NotFoundException {
        return cvService.getCvData(candidateId);
    }

    @PostMapping("/candidate/{candidateId}")
    public Cv saveCvData(@PathVariable("candidateId") Long candidateId,
                         @RequestBody byte[] data) {
        return cvService.saveCV(candidateId, data);
    }

}