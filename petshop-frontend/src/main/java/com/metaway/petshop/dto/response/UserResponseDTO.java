package com.metaway.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Integer id;
    private String cpf;
    private String name;
    private boolean enabled;
    private Integer roleId;
    private ArchiveResponseDTO archive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private transient List<PetResponseDTO> pets;
    private transient List<AddressResponseDTO> addresses;

}
