package com.keanusantos.ap2_dev_mobile.repository;

import com.keanusantos.ap2_dev_mobile.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    Categoria findByNome(String nome);
    List<Categoria> findAllByUsuarioId(UUID id);
}
