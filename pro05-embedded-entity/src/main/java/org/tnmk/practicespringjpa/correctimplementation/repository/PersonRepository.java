package org.tnmk.practicespringjpa.correctimplementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringjpa.correctimplementation.entity.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
