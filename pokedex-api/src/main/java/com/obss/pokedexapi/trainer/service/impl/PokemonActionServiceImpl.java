package com.obss.pokedexapi.trainer.service.impl;

import com.obss.pokedexapi.exception.AlreadyHaveException;
import com.obss.pokedexapi.exception.NotEnoughBalanceException;
import com.obss.pokedexapi.exception.NotExistsException;
import com.obss.pokedexapi.pokemon.model.Pokemon;
import com.obss.pokedexapi.pokemon.repository.PokemonRepository;
import com.obss.pokedexapi.pokemon.rules.PokemonBusinessRules;
import com.obss.pokedexapi.trainer.model.Trainer;
import com.obss.pokedexapi.trainer.repository.TrainerRepository;
import com.obss.pokedexapi.trainer.service.PokemonActionService;
import com.obss.pokedexapi.user.rules.UserBusinessRules;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PokemonActionServiceImpl implements PokemonActionService {
    private final UserBusinessRules userBusinessRules;
    private final TrainerRepository trainerRepository;
    private final PokemonBusinessRules pokemonBusinessRules;
    private final PokemonRepository pokemonRepository;
    @Override
    @Transactional
    public void catchPokemon(String pokemonId, String trainerId) {
        Object[] pokemonAndTrainer = getPokemonAndTrainer(pokemonId, trainerId);

        Pokemon pokemon = (Pokemon) pokemonAndTrainer[0];
        Trainer trainer = (Trainer) pokemonAndTrainer[1];

        if (pokemon.getIsCaught()){
            throw new AlreadyHaveException("This pokemon is already caught!");
        }

        if (trainer.getBalance() < pokemon.getPrice()) {
            throw new NotEnoughBalanceException("You don't have enough money to buy this pokemon!");
        }

        if (Objects.isNull(trainer.getCatchList())){
            trainer.setCatchList(new ArrayList<>());
        }

        if (trainer.getCatchList().contains(pokemon)) {
            throw new AlreadyHaveException("You already have this pokemon!");
        }


        trainer.getCatchList().add(pokemon);
        trainer.setBalance(trainer.getBalance() - pokemon.getPrice());

        pokemon.setTrainer(trainer);
        pokemon.setIsCaught(true);

        this.trainerRepository.save(trainer);
        this.pokemonRepository.save(pokemon);
    }

    @Override
    @Transactional
    public void releasePokemon(String pokemonId, String trainerId) {
        Object[] pokemonAndTrainer = getPokemonAndTrainer(pokemonId, trainerId);

        Pokemon pokemon = (Pokemon) pokemonAndTrainer[0];
        Trainer trainer = (Trainer) pokemonAndTrainer[1];

        if (Objects.isNull(trainer.getCatchList())){
            trainer.setCatchList(new ArrayList<>());
        }

        if (!trainer.getCatchList().contains(pokemon)) {
            throw new NotExistsException("You don't have this pokemon!");
        }
        trainer.getCatchList().remove(pokemon);
        trainer.setBalance(trainer.getBalance() + pokemon.getPrice());

        pokemon.setTrainer(null);
        pokemon.setIsCaught(false);

        this.pokemonRepository.save(pokemon);
        this.trainerRepository.save(trainer);
    }

    @Override
    @Transactional
    public void wishPokemon(String pokemonId, String trainerId) {
        Object[] pokemonAndTrainer = getPokemonAndTrainer(pokemonId, trainerId);

        Pokemon pokemon = (Pokemon) pokemonAndTrainer[0];
        Trainer trainer = (Trainer) pokemonAndTrainer[1];


        if (Objects.isNull(trainer.getCatchList())){
            trainer.setCatchList(new ArrayList<>());
        }

        if (trainer.getWishList().contains(pokemon)) {
            throw new AlreadyHaveException("You already wished this pokemon!");
        }

        trainer.getWishList().add(pokemon);
        pokemon.getWisherTrainers().add(trainer);

        this.trainerRepository.save(trainer);
        this.pokemonRepository.save(pokemon);
    }

    @Override
    @Transactional
    public void dislikePokemon(String pokemonId, String trainerId) {
        Object[] pokemonAndTrainer = getPokemonAndTrainer(pokemonId, trainerId);

        Pokemon pokemon = (Pokemon) pokemonAndTrainer[0];
        Trainer trainer = (Trainer) pokemonAndTrainer[1];

        if (Objects.isNull(trainer.getCatchList())){
            trainer.setCatchList(new ArrayList<>());
        }

        if (!trainer.getWishList().contains(pokemon)) {
            throw new NotExistsException("You are not wishing this pokemon!");
        }

        trainer.getWishList().remove(pokemon);
        pokemon.getWisherTrainers().remove(trainer);

        this.trainerRepository.save(trainer);
        this.pokemonRepository.save(pokemon);
    }

    @Override
    public void catchPokemonByName(String pokemonName, String trainerUsername) {
        String pokemonId = this.pokemonRepository.findPokemonByName(pokemonName).orElseThrow().getId();
        String trainerId = this.trainerRepository.findTrainerByUsername(trainerUsername).orElseThrow().getId();

        this.catchPokemon(pokemonId, trainerId);
    }

    @Override
    public void releasePokemonByName(String pokemonName, String trainerUsername) {
        String pokemonId = this.pokemonRepository.findPokemonByName(pokemonName).orElseThrow().getId();
        String trainerId = this.trainerRepository.findTrainerByUsername(trainerUsername).orElseThrow().getId();

        this.releasePokemon(pokemonId, trainerId);
    }

    @Override
    public void wishPokemonByName(String pokemonName, String trainerUsername) {
        String pokemonId = this.pokemonRepository.findPokemonByName(pokemonName).orElseThrow().getId();
        String trainerId = this.trainerRepository.findTrainerByUsername(trainerUsername).orElseThrow().getId();

        this.wishPokemon(pokemonId, trainerId);
    }

    @Override
    public void dislikePokemonByName(String pokemonName, String trainerUsername) {
        String pokemonId = this.pokemonRepository.findPokemonByName(pokemonName).orElseThrow().getId();
        String trainerId = this.trainerRepository.findTrainerByUsername(trainerUsername).orElseThrow().getId();

        this.dislikePokemon(pokemonId, trainerId);
    }

    private Object[] getPokemonAndTrainer(String pokemonId, String trainerId) {
        this.pokemonBusinessRules.checkIfPokemonExists(pokemonId);
        this.userBusinessRules.checkIfUserExists(trainerId);

        Pokemon pokemon = this.pokemonRepository.findPokemonById(pokemonId).orElseThrow();
        Trainer trainer = this.trainerRepository.findTrainerById(trainerId).orElseThrow();

        return new Object[]{pokemon, trainer};
    }

    @Override
    public Page<Pokemon> getAllPokemonsOfTrainerCatchList(String trainerId, Integer page, Integer size) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        List<Pokemon> catchList = trainer.getCatchList();

        return new PageImpl<>(catchList, PageRequest.of(page, size), catchList.size());
    }

    @Override
    public Page<Pokemon> getAllPokemonsOfTrainerWishList(String trainerId, Integer page, Integer size) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        List<Pokemon> wishList = trainer.getWishList();

        return new PageImpl<>(wishList, PageRequest.of(page, size), wishList.size());
    }
}
