package com.company.pm.userservice.domain.repositories;

import com.company.pm.domain.userservice.Authority;
import com.company.pm.domain.userservice.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserRepository extends R2dbcRepository<User, String>, UserRepositoryInternal {
    
    Mono<User> findOneByLogin(String login);
    
    Flux<User> findAllByIdNotNull();
    
    Flux<User> findAllByIdNotNullAndActivatedIsTrue();
    
    Mono<Long> count();
    
    @Query("INSERT INTO users_authorities VALUES(:userId, :authority)")
    Mono<Void> saveUserAuthority(String userId, String authority);
    
    @Query("DELETE FROM users_authorities")
    Mono<Void> deleteAllUserAuthorities();
    
    @Query("DELETE FROM users_authorities WHERE user_id = :userId")
    Mono<Void> deleteUserAuthorities(Long userId);
}

interface UserRepositoryInternal {
    Mono<User> findOneWithAuthoritiesByLogin(String login);
    
    Mono<User> create(User user);
    
    Flux<User> findAllWithAuthorities();
}

@RequiredArgsConstructor
class UserRepositoryInternalImpl implements UserRepositoryInternal {
    
    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final R2dbcConverter r2dbcConverter;
    
    @Override
    public Mono<User> findOneWithAuthoritiesByLogin(String login) {
        return findOneWithAuthoritiesBy("login", login);
    }
    
    @Override
    public Flux<User> findAllWithAuthorities() {
        return db
            .sql("SELECT * FROM users u LEFT JOIN users_authorities ua ON u.id=ua.user_id")
            .map(
                (row, metadata) ->
                    Tuples.of(r2dbcConverter.read(User.class, row, metadata), Optional.ofNullable(row.get("authority_name", String.class)))
            )
            .all()
            .groupBy(t -> t.getT1().getLogin())
            .flatMap(l -> l.collectList().map(t -> updateUserWithAuthorities(t.get(0).getT1(), t)));
    }
    
    @Override
    public Mono<User> create(User user) {
        return r2dbcEntityTemplate.insert(User.class).using(user).defaultIfEmpty(user);
    }
    
    private Mono<User> findOneWithAuthoritiesBy(String fieldName, Object fieldValue) {
        return db
            .sql("SELECT * FROM users u LEFT JOIN users_authorities ua ON u.id=ua.user_id WHERE u." + fieldName + " = :" + fieldName)
            .bind(fieldName, fieldValue)
            .map(
                (row, metadata) ->
                    Tuples.of(r2dbcConverter.read(User.class, row, metadata), Optional.ofNullable(row.get("authority_name", String.class)))
            )
            .all()
            .collectList()
            .filter(l -> !l.isEmpty())
            .map(l -> updateUserWithAuthorities(l.get(0).getT1(), l));
    }
    
    private User updateUserWithAuthorities(User user, List<Tuple2<User, Optional<String>>> tuples) {
        user.setAuthorities(
            tuples
                .stream()
                .filter(t -> t.getT2().isPresent())
                .map(
                    t -> {
                        Authority authority = Authority.builder().build();
                        authority.setName(t.getT2().get());
                        
                        return authority;
                    }
                )
                .collect(Collectors.toSet())
        );
        
        return user;
    }
}
