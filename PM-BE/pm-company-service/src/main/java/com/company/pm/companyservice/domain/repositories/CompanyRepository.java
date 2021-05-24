package com.company.pm.companyservice.domain.repositories;

import com.company.pm.domain.companyservice.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends R2dbcRepository<Company, Long>, CompanyRepositoryInternal {
    @Query("SELECT * FROM companies entity WHERE entity.admin_id = :id")
    Flux<Company> findByAdmin(String id);
    
    @Query("SELECT * FROM companies entity WHERE entity.admin_id IS NULL")
    Flux<Company> findAllWhereAdminIsNull();
    
    @Query("SELECT * FROM companies entity WHERE entity.admin_id = :adminId AND entity.id = :companyId")
    Mono<Company> findByAdminAndId(String adminId, Long companyId);
    
    @Query("SELECT * FROM companies entity WHERE entity.admin_id = :adminId AND entity.name = :name")
    Mono<Company> findByAdminAndName(String adminId, String name);
    
    // just to avoid having unambigous methods
    @Override
    Flux<Company> findAll();

    @Override
    Mono<Company> findById(Long id);

    @Override
    <S extends Company> Mono<S> save(S entity);
}

interface CompanyRepositoryInternal {
    <S extends Company> Mono<S> insert(S entity);
    <S extends Company> Mono<S> save(S entity);
    Mono<Integer> update(Company entity);

    Flux<Company> findAll();
    Mono<Company> findById(Long id);
    Flux<Company> findAllBy(Pageable pageable);
    Flux<Company> findAllBy(Pageable pageable, Criteria criteria);
}
