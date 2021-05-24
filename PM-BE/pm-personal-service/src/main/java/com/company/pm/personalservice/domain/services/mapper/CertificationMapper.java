package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.domain.services.dto.CertificationDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class CertificationMapper {

    private final ModelMapper mapper;
    
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM");
    
    static {
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
    }

    public Certification certDTOToCert(CertificationDTO certificationDTO) throws ParseException {
        Certification certification = mapper.map(certificationDTO, Certification.class);
        String issDate = certificationDTO.getIssYear() + "-" + certificationDTO.getIssMonth();
        certification.setIssDate(df.parse(issDate).toInstant());
    
        if (certificationDTO.getExpMonth() != null && certificationDTO.getExpYear() != null) {
            String expDate = certificationDTO.getExpYear() + "-" + certificationDTO.getExpMonth();
            certification.setExpDate(df.parse(expDate).toInstant());
        }
        
        return certification;
    }
}
