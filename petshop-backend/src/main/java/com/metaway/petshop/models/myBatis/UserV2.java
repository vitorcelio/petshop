package com.metaway.petshop.models.myBatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserV2 implements Principal {

    private Integer id;
    private String cpf;
    private String password;
    private String name;
    private boolean enabled;
    private Integer roleId;
    private Integer archiveId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean accountDeleted;

    private ArchiveV2 archive;
    private RoleV2 role;

}
