package com.metaway.petshop.util;

public class RoutesUtil {

//    private static final String URL_SERVICE = "http://localhost:8081/api/v1";
    private static final String URL_SERVICE = "http://petshop-backend:8081/api/v1";

    // Serviço de usuários
    public static final String USER_UPDATE = URL_SERVICE + "/users/update";
    public static final String USER_INTERNAL_UPDATE = URL_SERVICE + "/users/internal/update/%d";
    public static final String USER_CREATE = URL_SERVICE + "/users";
    public static final String USER_INTERNAL_CREATE = URL_SERVICE + "/users/internal";
    public static final String USER_CHANGE_PASSWORD = URL_SERVICE + "/users/change-password";
    public static final String USER_CHANGE_PASSWORD_BY_ID = URL_SERVICE + "/users/change-password/%d";
    public static final String USER_GET_BY_ID = URL_SERVICE + "/users/%d";
    public static final String USER_DELETE = URL_SERVICE + "/users/%d";
    public static final String USER_ME = URL_SERVICE + "/users/me";
    public static final String USER_GET_BY_CPF = URL_SERVICE + "/users/cpf/%s";
    public static final String USER_GET_ALL_BY_ROLE = URL_SERVICE + "/users/all/%d";

    // Serviço de atendimento
    public static final String PET_CARE_GET_BY_ID = URL_SERVICE + "/petsCare/%s";
    public static final String PET_CARE_UPDATE = URL_SERVICE + "/petsCare/%s";
    public static final String PET_CARE_DELETE = URL_SERVICE + "/petsCare/%s";
    public static final String PET_CARE_GET_ALL = URL_SERVICE + "/petsCare";
    public static final String PET_CARE_CREATE = URL_SERVICE + "/petsCare";
    public static final String PET_CARE_GET_BY_PET_ID = URL_SERVICE + "/petsCare/pet/%d";
    public static final String PET_CARE_GET_ALL_ENTRIES = URL_SERVICE + "/petsCare/all";

    // Serviço de animais de estimação
    public static final String PET_GET_BY_ID = URL_SERVICE + "/pets/%d";
    public static final String PET_UPDATE = URL_SERVICE + "/pets/%d";
    public static final String PET_DELETE = URL_SERVICE + "/pets/%d";
    public static final String PET_GET_ALL = URL_SERVICE + "/pets";
    public static final String PET_CREATE = URL_SERVICE + "/pets";
    public static final String PET_GET_ALL_ENTRIES = URL_SERVICE + "/pets/all";

    // Serviço de contatos
    public static final String CONTACT_GET_BY_ID = URL_SERVICE + "/contacts/%s";
    public static final String CONTACT_UPDATE = URL_SERVICE + "/contacts/%s";
    public static final String CONTACT_DELETE = URL_SERVICE + "/contacts/%s";
    public static final String CONTACT_GET_ALL = URL_SERVICE + "/contacts";
    public static final String CONTACT_CREATE = URL_SERVICE + "/contacts";
    public static final String CONTACT_GET_ALL_ENTRIES = URL_SERVICE + "/contacts/all";

    // Serviço de raças
    public static final String BREED_GET_BY_ID = URL_SERVICE + "/breeds/%d";
    public static final String BREED_UPDATE = URL_SERVICE + "/breeds/%d";
    public static final String BREED_DELETE = URL_SERVICE + "/breeds/%d";
    public static final String BREED_GET_ALL = URL_SERVICE + "/breeds";
    public static final String BREED_CREATE = URL_SERVICE + "/breeds";

    // Serviço de endereços
    public static final String ADDRESS_GET_BY_ID = URL_SERVICE + "/addresses/%d";
    public static final String ADDRESS_UPDATE = URL_SERVICE + "/addresses/%d";
    public static final String ADDRESS_DELETE = URL_SERVICE + "/addresses/%d";
    public static final String ADDRESS_GET_ALL = URL_SERVICE + "/addresses";
    public static final String ADDRESS_CREATE = URL_SERVICE + "/addresses";
    public static final String ADDRESS_GET_ALL_BY_CUSTOMER = URL_SERVICE + "/addresses/all/%d";

    // Serviço de arquivos
    public static final String ARCHIVE_PET = URL_SERVICE + "/archives/pet";
    public static final String ARCHIVE_CUSTOMER = URL_SERVICE + "/archives/customer";
    public static final String ARCHIVED_CUSTOMER_DELETE = URL_SERVICE + "/archives/customer";
    public static final String ARCHIVED_PET_DELETE = URL_SERVICE + "/archives/pet/%d";

    // Serviço de informações
    public static final String INFOS_ADMIN = URL_SERVICE + "/infos/admin";
    public static final String INFOS_CUSTOMER = URL_SERVICE + "/infos/customer";

    // Serviço de autenticação
    public static final String AUTH_LOGIN = URL_SERVICE + "/auth/login";

    // Serviço de Buscar CEP
    public static final String VIA_CEP_SERVICE = "https://viacep.com.br/ws/%s/json";

}
