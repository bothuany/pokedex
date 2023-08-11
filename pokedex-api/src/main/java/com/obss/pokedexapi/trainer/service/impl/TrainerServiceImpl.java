package com.obss.pokedexapi.trainer.service.impl;

import com.obss.pokedexapi.common.Constants;
import com.obss.pokedexapi.trainer.model.Trainer;
import com.obss.pokedexapi.trainer.repository.TrainerRepository;
import com.obss.pokedexapi.trainer.service.TrainerService;
import com.obss.pokedexapi.user.model.Role;
import com.obss.pokedexapi.user.rules.UserBusinessRules;
import com.obss.pokedexapi.user.security.UserAuthDetails;
import com.obss.pokedexapi.user.security.auth.service.JwtService;
import com.obss.pokedexapi.user.security.auth.service.UserAuthService;
import com.obss.pokedexapi.user.security.dto.AuthenticationResponse;
import com.obss.pokedexapi.user.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import static com.obss.pokedexapi.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final RoleService roleService;
    private final UserBusinessRules userBusinessRules;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthService userAuthService;
    private final JwtService jwtService;


    @Override
    public AuthenticationResponse addTrainer(Trainer trainer) {
        this.userBusinessRules.checkIfEmailExists(trainer.getEmail());
        this.userBusinessRules.checkIfUsernameExists(trainer.getUsername());

        Role trainerRole = roleService.findRoleByName(Constants.Roles.TRAINER);

        trainer.setRole(trainerRole);
        trainer.setBalance(Constants.INITIAL_BALANCE);
        trainer.setCatchList(new ArrayList<>());
        trainer.setWishList(new ArrayList<>());
        trainer.setPassword(passwordEncoder.encode(trainer.getPassword()));

        this.trainerRepository.save(trainer);
        UserAuthDetails userAuthDetails = new UserAuthDetails(trainer);
        var jwtToken = jwtService.generateToken(userAuthDetails);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public Page<Trainer> getAllTrainers(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.trainerRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Trainer getTrainerById(String trainerId) {
        return this.trainerRepository.findTrainerById(trainerId).orElseThrow();
    }

    @Override
    public Trainer getTrainerByUsername(String trainerUsername) {
        return this.trainerRepository.findTrainerByUsername(trainerUsername).orElseThrow();
    }

    @Override
    public Page<Trainer> searchTrainersByUsername(String username, Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.trainerRepository.findAllByUsernameContaining(username, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public void updateTrainer(String trainerId, Trainer trainer) {
        this.userBusinessRules.checkIfUserExists(trainerId);

        Trainer existingTrainer = this.trainerRepository.findById(trainerId).orElseThrow();
        Set<String> nullPropertyNames = getNullPropertyNames(trainer);
        double existingBalance = existingTrainer.getBalance();

        BeanUtils.copyProperties(trainer, existingTrainer, nullPropertyNames.toArray(new String[0]));

        if (trainer.getBalance() == Constants.ILLEGAL_BALANCE) {
            existingTrainer.setBalance(existingBalance);//if balance is not updated, set it to existing balance
        }

        if (Objects.nonNull(trainer.getPassword())) {
            existingTrainer.setPassword(passwordEncoder.encode(trainer.getPassword()));
        }

        this.trainerRepository.save(existingTrainer);
    }

    @Override
    public void updateTrainerByUsername(String trainerUsername, Trainer trainer) {
        String trainerId = this.trainerRepository.findTrainerByUsername(trainerUsername).orElseThrow().getId();
        this.updateTrainer(trainerId, trainer);
    }

    @Override
    @Transactional
    public void deleteTrainer(String trainerId) {
        Trainer trainer = this.trainerRepository.findById(trainerId).orElseThrow();

        trainer.getCatchList().forEach(pokemon -> {
            pokemon.setTrainer(null);
            pokemon.setIsCaught(false);
        });
        trainer.getWishList().forEach(pokemon -> {
            pokemon.getWisherTrainers().remove(trainer);
        });
        trainer.setCatchList(null);
        trainer.setWishList(null);

        this.trainerRepository.deleteById(trainerId);
    }

    @Override
    @Transactional
    public void deleteTrainerByUsername(String trainerUsername) {
        this.trainerRepository.deleteTrainerByUsername(trainerUsername);
    }

    @Override
    public void updateTrainerMyself(Trainer trainer) {
        String trainerId = this.userAuthService.getActiveUser().getId();
        Trainer existingTrainer = this.trainerRepository.findById(trainerId).orElseThrow();

        trainer.setBalance(existingTrainer.getBalance()); //to prevent balance change
        this.updateTrainer(trainerId, trainer);
    }

    @Override
    public void deleteTrainerMyself() {
        String trainerId = this.userAuthService.getActiveUser().getId();
        this.deleteTrainer(trainerId);
    }
}
