package com.metaway.petshop;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.metaway.petshop.mappers")
@RequiredArgsConstructor
@SpringBootApplication
public class PetshopBackendApplication /*implements ApplicationRunner*/ {

    /*private final UserRepository userRepository;*/

    public static void main(String[] args) {
        SpringApplication.run(PetshopBackendApplication.class, args);
    }

   /* @Override
    public void run(ApplicationArguments args) throws Exception {
        Integer countAdmin = userRepository.countUserByRoleId(RolesEnum.ADMIN.getId());

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (countAdmin < 1) {
                User user = User.builder()
                        .name("Marciano Metaway")
                        .password(encoder.encode("12345"))
                        .cpf("66543630000")
                        .roleId(RolesEnum.ADMIN.getId())
                        .build();

                userRepository.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/
}
