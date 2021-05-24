package com.company.pm.companyservice.domain.services.mapper;

import com.company.pm.common.enumeration.CompanyType;
import com.company.pm.companyservice.domain.services.dto.CompanyDTO;
import com.company.pm.domain.companyservice.Company;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMapper {
    
    private final ModelMapper mapper;
    
    public Company companyDTOToCompany(CompanyDTO companyDTO) throws NumberFormatException {
        Company company = mapper.map(companyDTO, Company.class);
        company.setCompanyType(CompanyType.valueOf(companyDTO.getCompanyType()));
        company.setCompanySize(Integer.parseInt(companyDTO.getCompanySize()));
        
        return company;
    }
}
