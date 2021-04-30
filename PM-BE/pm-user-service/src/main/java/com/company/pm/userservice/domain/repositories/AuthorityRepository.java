package com.company.pm.userservice.domain.repositories;

import com.company.pm.domain.userservice.Authority;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AuthorityRepository extends R2dbcRepository<Authority, String> {
}
