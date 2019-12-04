package org.tnmk.practicespringjpa.pro01simpleentityuuidfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByPersonId(UUID personId);
}
