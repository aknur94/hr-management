package kz.onetech.hr.candidate.dto;

import lombok.Data;

@Data
public class CandidateDetailDto {
    private Long id;
    private String fullName;
    private String city;
    private String phone;
}