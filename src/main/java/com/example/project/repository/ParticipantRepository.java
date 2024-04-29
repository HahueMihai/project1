package com.example.project.repository;

import com.example.project.model.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Integer> {
    Participant findByEmail(String email);
}
