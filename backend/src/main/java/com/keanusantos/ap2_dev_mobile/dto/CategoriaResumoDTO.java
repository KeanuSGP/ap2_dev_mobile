package com.keanusantos.ap2_dev_mobile.dto;

import com.keanusantos.ap2_dev_mobile.entity.Categoria;

import java.util.UUID;

public record CategoriaResumoDTO(
        UUID id, String nome, String cor, Integer lancamentosQtd
) {

    public static CategoriaResumoDTO mapToCategoriaResumoDTO(Categoria categoria) {
        return new CategoriaResumoDTO(
                categoria.getId(), categoria.getNome(), categoria.getCor(), categoria.getTransacoes().size()
        );
    }
}
