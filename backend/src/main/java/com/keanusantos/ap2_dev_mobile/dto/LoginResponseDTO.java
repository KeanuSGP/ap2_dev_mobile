package com.keanusantos.ap2_dev_mobile.dto;

import com.keanusantos.ap2_dev_mobile.entity.Usuario;

public record LoginResponseDTO(
        String token, Usuario usuario
) {
}
