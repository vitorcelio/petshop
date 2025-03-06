package com.metaway.petshop.dto.response;

import com.metaway.petshop.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryResponseDTO {

    private Integer id;
    private String name;
    private String cpf;
    private ArchiveResponseDTO archive;

    public UserSummaryResponseDTO(User user) {
        if (user == null) {
            return;
        }

        this.id = user.getId();
        this.name = user.getName();
        this.cpf = user.getCpf();
        this.archive = new ArchiveResponseDTO(user.getArchive());
    }

}
