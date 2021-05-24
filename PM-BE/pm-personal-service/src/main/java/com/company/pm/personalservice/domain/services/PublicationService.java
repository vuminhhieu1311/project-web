package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Author;
import com.company.pm.domain.personalservice.Publication;
import com.company.pm.personalservice.domain.repositories.AuthorRepository;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.repositories.PublicationRepository;
import com.company.pm.personalservice.domain.services.dto.PublicationDTO;
import com.company.pm.personalservice.domain.services.mapper.PublicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationService {
    
    private static final String ENTITY_NAME = "publication";
    
    private final PublicationMapper mapper;
    
    private final PublicationRepository publicationRepository;
    
    private final AuthorRepository authorRepository;
    
    private final PersonalProfileRepository profileRepository;
    
    @Transactional(readOnly = true)
    public Flux<Publication> getPubsByProfile(String userId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "")))
            .flatMapMany(profile -> publicationRepository.findByPersonalProfile(profile.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<Publication> getPubByProfile(String userId, Long pubId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> publicationRepository.findByIdAndPersonalProfile(pubId, profile.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            );
    }
    
    @Transactional
    public Mono<Publication> createPubByProfile(String userId, PublicationDTO publicationDTO) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> {
                try {
                    Publication publication = mapper.publicationDTOToPublication(publicationDTO);
                    publication.setPersonalProfile(profile);
                    publication.setPersonalProfileId(profile.getId());
                    
                    return publicationRepository.save(publication)
                        .flatMap(savedPublication -> {
                            Set<Author> authors = savedPublication.getAuthors().stream()
                                .map(author -> {
                                    author.setPublication(savedPublication);
                                    author.setPublicationId(savedPublication.getId());
                                    
                                    return author;
                                })
                                .collect(Collectors.toSet());
                            
                            return authorRepository.saveAll(authors)
                                .collect(Collectors.toSet())
                                .flatMap(savedAuthors -> {
                                    savedPublication.setAuthors(savedAuthors);
                                    
                                    return publicationRepository.save(savedPublication);
                                });
                        });
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Publication> updatePubByProfile(String userId, Long pubId, PublicationDTO publicationDTO) {
        return getPubByProfile(userId, pubId)
            .flatMap(publication -> {
                try {
                    Publication update = mapper.publicationDTOToPublication(publicationDTO);
                    
                    if (update.getTitle() != null) {
                        publication.setTitle(update.getTitle());
                    }
                    if (update.getPublisher() != null) {
                        publication.setPublisher(update.getPublisher());
                    }
                    if (update.getPublicationDate() != null) {
                        publication.setPublicationDate(update.getPublicationDate());
                    }
                    if (update.getUrl() != null) {
                        publication.setUrl(update.getUrl());
                    }
                    if (update.getDescription() != null) {
                        publication.setDescription(update.getDescription());
                    }
                    
                    return publicationRepository.save(publication)
                        .flatMap(savedPublication -> {
                            if (update.getAuthors().size() > 0) {
                                Set<Author> authors = update.getAuthors().stream()
                                    .map(author -> {
                                        author.setPublication(savedPublication);
                                        author.setPublicationId(savedPublication.getId());
                                        
                                        return author;
                                    })
                                    .collect(Collectors.toSet());
                                
                                return authorRepository.saveAll(authors)
                                    .collect(Collectors.toSet())
                                    .flatMap(createdAuthors -> {
                                        savedPublication.getAuthors().addAll(createdAuthors);
                                        
                                        return publicationRepository.save(savedPublication);
                                    });
                            } else {
                                return Mono.just(savedPublication);
                            }
                        });
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Void> deletePubByProfile(String userId, Long pubId) {
        return getPubByProfile(userId, pubId)
            .flatMap(publication -> authorRepository.deleteAll(publication.getAuthors())
                .then(publicationRepository.delete(publication))
            );
    }
}
