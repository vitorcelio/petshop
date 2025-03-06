package com.metaway.petshop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaway.petshop.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime updatedAt;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.cpf = user.getCpf();
        this.name = user.getName();
        this.enabled = user.isEnabled();
        this.archive = new ArchiveResponseDTO(user.getArchive());
        this.roleId = user.getRoleId();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

}
