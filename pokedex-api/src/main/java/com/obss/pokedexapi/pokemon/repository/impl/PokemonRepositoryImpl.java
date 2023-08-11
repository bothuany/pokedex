package com.obss.pokedexapi.pokemon.repository.impl;

import com.obss.pokedexapi.common.sort.SortCriteria;
import com.obss.pokedexapi.common.sort.SortDirection;
import com.obss.pokedexapi.pokemon.model.Pokemon;
import com.obss.pokedexapi.pokemon.model.Type;
import com.obss.pokedexapi.pokemon.repository.PokemonCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class PokemonRepositoryImpl implements PokemonCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Pokemon> searchPokemonsByFilter(String name,  String type, Pageable pageable, Boolean isAll, boolean isCaught, SortCriteria sortCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pokemon> query = cb.createQuery(Pokemon.class);
        Root<Pokemon> root = query.from(Pokemon.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (type != null && !type.isEmpty()) {
            Join<Pokemon, Type> type1Join = root.join("type1", JoinType.LEFT);
            Join<Pokemon, Type> type2Join = root.join("type2", JoinType.LEFT);

            Predicate type1Predicate = cb.equal(cb.lower(type1Join.get("name")), type.toLowerCase());
            Predicate type2Predicate = cb.equal(cb.lower(type2Join.get("name")), type.toLowerCase());

            predicates.add(cb.or(type1Predicate, type2Predicate));
        }

        if(Objects.nonNull(isAll)){
            if(!isAll){
                if (isCaught) {
                    predicates.add(cb.isTrue(root.get("isCaught")));
                } else {
                    predicates.add(cb.isFalse(root.get("isCaught")));
                }
            }
        }


        query.where(predicates.toArray(new Predicate[0]));

        // Sorting
        if (sortCriteria != null) {
            String sortBy = sortCriteria.getSortBy();
            Sort.Direction sortDirection = sortCriteria.getSortDirection() == SortDirection.ASCENDING ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            Path<?> sortPath = switch (sortBy) {
                case "stats.hp" -> root.join("stats").get("hp");
                case "stats.attack" -> root.join("stats").get("attack");
                case "stats.defense" -> root.join("stats").get("defense");
                case "stats.specialAttack" -> root.join("stats").get("specialAttack");
                case "stats.specialDefense" -> root.join("stats").get("specialDefense");
                case "stats.speed" -> root.join("stats").get("speed");
                case "stats.total" -> root.join("stats").get("total");
                default -> root.get(sortBy);
            };

            query.orderBy(sortDirection == Sort.Direction.ASC ?
                    cb.asc(sortPath) : cb.desc(sortPath));
        }

        TypedQuery<Pokemon> typedQuery = entityManager.createQuery(query);
        long totalItems = typedQuery.getResultList().size();

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Pokemon> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalItems);
    }
}
