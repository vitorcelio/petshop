package com.metaway.petshop.backing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.metaway.petshop.config.adapter.LocalDateAdapter;
import com.metaway.petshop.config.adapter.LocalDateTimeAdapter;
import com.metaway.petshop.dto.request.ArchiveRequestDTO;
import com.metaway.petshop.dto.response.AddressResponseDTO;
import com.metaway.petshop.dto.response.BreedResponseDTO;
import com.metaway.petshop.dto.response.PetResponseDTO;
import com.metaway.petshop.dto.response.UserResponseDTO;
import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AbstractBacking implements Serializable {

    private ArchiveRequestDTO archive;

    protected static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    protected String getToken() {
        return (String) PetShopUtil.getObjectSession(PetShopUtil.TOKEN_SESSION);
    }

    protected Integer getUserId() {
        return (Integer) PetShopUtil.getObjectSession(PetShopUtil.USER_ID_SESSION);
    }

    public UserResponseDTO getUserLogged() {
        try {

            String result = ServiceUtil.get(RoutesUtil.USER_ME, true, getToken());

            if (!PetShopUtil.isEmpty(result)) {
                return gson.fromJson(result, UserResponseDTO.class);
            }

            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro inesperado aconteceu!");
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro inesperado aconteceu!");
        }

        return null;
    }

    public void logout() {
        PetShopUtil.removeObjectSession(PetShopUtil.TOKEN_SESSION);
        PetShopUtil.removeObjectSession(PetShopUtil.USER_ID_SESSION);
        PetShopUtil.removeObjectSession(PetShopUtil.ROLE_ID_SESSION);
        PetShopUtil.removeObjectSession(PetShopUtil.CPF_SESSION);
        PetShopUtil.removeObjectSession(PetShopUtil.EXPIRATION_SESSION);

        PetShopUtil.session().invalidate();
        try {
            PetShopUtil.redirectPage("/petshop/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<PetResponseDTO> loadPets(Integer customerId, String params) {
        try {
            String url;

            if (isCustomer()) {
                url = RoutesUtil.PET_GET_ALL + params;
            } else {
                url = RoutesUtil.PET_GET_ALL_ENTRIES + (customerId != null ? "?customerId=" + customerId : "");
            }

            String result = ServiceUtil.get(url, true, getToken());

            if (result != null) {
                if (!result.contains("error")) {
                    TypeToken<List<PetResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    return gson.fromJson(result, typeToken.getType());
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar animais do cliente!");
        }

        return new ArrayList<>();
    }

    public List<UserResponseDTO> loadCustomers() {
        try {
            String url = String.format(RoutesUtil.USER_GET_ALL_BY_ROLE, RolesEnum.CUSTOMER.getId());

            String result = ServiceUtil.get(url, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    TypeToken<List<UserResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    return gson.fromJson(result, typeToken.getType());

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar clientes!");
        }

        return new ArrayList<>();
    }

    protected List<BreedResponseDTO> loadBreeds() {
        try {
            String result = ServiceUtil.get(RoutesUtil.BREED_GET_ALL, true, getToken());
            if (!PetShopUtil.isEmpty(result)) {
                if (!result.contains("error")) {
                    TypeToken<List<BreedResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    return gson.fromJson(result, typeToken.getType());
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar raças!");
        }

        return new ArrayList<>();
    }

    protected void uploadArchive(Integer petId, String base64, String base64Mini) {
        try {

            String url = petId != null ? RoutesUtil.ARCHIVE_PET : RoutesUtil.ARCHIVE_CUSTOMER;
            getArchive().setObjectId(petId != null ? petId : getUserId());
            getArchive().setBase64(base64);
            getArchive().setBase64Mini(base64Mini);

            String result = ServiceUtil.post(url, gson.toJson(getArchive()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Arquivo foi salvo com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao realizar upload da Imagem!");
        }

        this.setArchive(new ArchiveRequestDTO());
    }

    protected List<AddressResponseDTO> loadAddresses(Integer customerId) {
        try {

            String url = customerId != null ?
                    String.format(RoutesUtil.ADDRESS_GET_ALL_BY_CUSTOMER, customerId) :
                    RoutesUtil.ADDRESS_GET_ALL;

            String result = ServiceUtil.get(url, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    TypeToken<List<AddressResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    return gson.fromJson(result, typeToken.getType());
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar endereços.");
        }

        return new ArrayList<>();
    }

    protected static void updateField(String field) {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent component = view.findComponent(field);
        if (component != null)
            PrimeFaces.current().ajax().update(field);
    }

    protected static void openDialog(String nomeDlg) {
        PrimeFaces.current().executeScript("PF('" + nomeDlg + "').show();");
    }

    protected boolean isAdmin() {
        Integer roleId = (Integer) PetShopUtil.getObjectSession(PetShopUtil.ROLE_ID_SESSION);
        return RolesEnum.ADMIN.getId().equals(roleId);
    }

    protected boolean isCustomer() {
        Integer roleId = (Integer) PetShopUtil.getObjectSession(PetShopUtil.ROLE_ID_SESSION);
        return RolesEnum.CUSTOMER.getId().equals(roleId);
    }

    public String getPhotoMini() {
        if (getUserLogged().getArchive() == null) return null;
        return getUserLogged().getArchive().getBase64Mini();
    }

    public String getPhoto() {
        if (getUserLogged().getArchive() == null) return null;
        return getUserLogged().getArchive().getBase64();
    }

    public void validateProfileAdmin() {
        if (!isAdmin()) {
            try {
                PetShopUtil.redirectPage("/petshop/pages/home.xhtml");
                PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Erro", "Você não tem permissão para acessar!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void validateProfileCustomer() {
        if (!isCustomer()) {
            try {
                PetShopUtil.redirectPage("/petshop/pages/home.xhtml");
                PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Erro", "Você não tem permissão para acessar!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getGender(String gender) {
        if (gender != null) {

            if (gender.equals("MALE")) {
                return "Masculino";

            } else if (gender.equals("FEMALE")) {
                return "Feminino";
            }

        }
        return "";
    }

    public String firstName(String name) {
        return name.split(" ")[0];
    }

    public ArchiveRequestDTO getArchive() {
        if (archive == null) archive = new ArchiveRequestDTO();
        return archive;
    }

    public void setArchive(ArchiveRequestDTO archive) {
        this.archive = archive;
    }
}
