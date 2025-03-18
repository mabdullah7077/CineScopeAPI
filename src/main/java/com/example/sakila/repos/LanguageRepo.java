package com.example.sakila.repos;

import com.example.sakila.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepo extends JpaRepository<Language, Short> {
    Optional<Language> findByNameIgnoreCase(String name);

}
