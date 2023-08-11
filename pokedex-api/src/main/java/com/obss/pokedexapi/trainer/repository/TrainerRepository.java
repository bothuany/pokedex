package com.obss.pokedexapi.trainer.repository;

import com.obss.pokedexapi.trainer.model.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, String> {
    Optional<Trainer> findTrainerByUsername(String username);

    Optional<Trainer> findTrainerById(String trainerId);
    Page<Trainer> findAllByUsernameContaining(String username, PageRequest pageRequest);

    void deleteTrainerByUsername(String trainerUsername);
}
