package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.personalservice.Author;
import com.company.pm.domain.personalservice.Publication;
import com.company.pm.personalservice.domain.services.dto.PublicationDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationMapper {
    
    private final ModelMapper mapper;
    
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    
    static {
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
    }
    
    public Publication publicationDTOToPublication(PublicationDTO publicationDTO) throws ParseException {
        Publication publication = mapper.map(publicationDTO, Publication.class);
        
        if (publicationDTO.getAuthors() != null && !publicationDTO.getAuthors().isBlank()) {
            Set<Author> authors = Arrays.stream(publicationDTO.getAuthors().split(","))
                .map(name -> Author.builder().name(name).build())
                .collect(Collectors.toSet());
            publication.setAuthors(authors);
        }
        
        if (publicationDTO.getPublicationDate() != null) {
            publication.setPublicationDate(df.parse(publicationDTO.getPublicationDate()).toInstant());
        }
        
        return publication;
    }
}
