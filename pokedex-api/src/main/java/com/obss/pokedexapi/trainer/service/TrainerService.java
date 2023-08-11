package com.obss.pokedexapi.trainer.service;

import com.obss.pokedexapi.trainer.model.Trainer;
import com.obss.pokedexapi.user.security.dto.AuthenticationResponse;
import org.springframework.data.domain.Page;

public interface TrainerService {
    AuthenticationResponse addTrainer(Trainer trainer);

    Page<Trainer> getAllTrainers(Integer page, Integer size);

    Trainer getTrainerById(String trainerId);
    Trainer getTrainerByUsername(String trainerUsername);

    Page<Trainer> searchTrainersByUsername(String username, Integer page, Integer size);

    void updateTrainer(String trainerId, Trainer trainer);
    void updateTrainerByUsername(String trainerUsername, Trainer trainer);

    void deleteTrainer(String trainerId);
    void deleteTrainerByUsername(String trainerUsername);

    void updateTrainerMyself(Trainer trainer);

    void deleteTrainerMyself();
}
