package com.metaway.petshop.backing;

import com.google.gson.reflect.TypeToken;
import com.metaway.petshop.dto.request.RecoverPasswordRequestDTO;
import com.metaway.petshop.dto.request.UserInternalRequestDTO;
import com.metaway.petshop.dto.request.UserInternalUpdateRequestDTO;
import com.metaway.petshop.dto.response.AddressResponseDTO;
import com.metaway.petshop.dto.response.PetResponseDTO;
import com.metaway.petshop.dto.response.UserResponseDTO;
import com.metaway.petshop.dto.search.CustomerSearch;
import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean
public class CustomerBacking extends AbstractBacking implements Serializable {

    private CustomerSearch search;

    private UserResponseDTO userRead;
    private List<UserResponseDTO> customersList;

    private UserInternalRequestDTO user;
    private UserInternalUpdateRequestDTO userUpdate;
    private RecoverPasswordRequestDTO recoverPassword;

    @PostConstruct
    public void init() {
        validateProfileAdmin();

        loadCustomers(false);
    }

    public String searchCustomer() {
        loadCustomers(true);
        return "";
    }

    private void loadCustomers(boolean search) {
        try {
            this.setCustomersList(new ArrayList<>());

            String params = search ? getParams() : "";

            String url = String.format(RoutesUtil.USER_GET_ALL_BY_ROLE, RolesEnum.CUSTOMER.getId()) + params
                    .replace("'", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29")
                    .replace(" ", "%20");

            String result = ServiceUtil.get(url, true, getToken());

            if (!PetShopUtil.isEmpty(result)) {

                if (!result.contains("error")) {
                    TypeToken<List<UserResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    List<UserResponseDTO> users = gson.fromJson(result, typeToken.getType());
                    this.setCustomersList(users);

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar clientes!");
        }
    }

    public String actionDeleteCustomer() {
        try {
            Integer customerId = (Integer) PetShopUtil.getObjectSession("customerDelete");

            String result = ServiceUtil.delete(String.format(RoutesUtil.USER_DELETE, customerId), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente deletado com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar cliente!");
        }

        loadCustomers(false);
        PetShopUtil.removeObjectSession("customerDelete");
        updateField("frm:listCustomer");
        return "";
    }

    public String actionEditCustomer() {
        try {

            getUserUpdate().setCpf(PetShopUtil.removeCharacter(getUserUpdate().getCpf()));

            Integer customerId = (Integer) PetShopUtil.getObjectSession("customerEdit");

            String result = ServiceUtil.put(String.format(RoutesUtil.USER_INTERNAL_UPDATE, customerId), gson.toJson(getUserUpdate()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente editado com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao editar cliente!");
        }

        loadCustomers(false);
        PetShopUtil.removeObjectSession("customerEdit");
        return "";
    }

    public String actionCreateCustomer() {
        try {
            getUser().setCpf(PetShopUtil.removeCharacter(getUser().getCpf()));

            if (getUser().getRoleId() == null) {
                getUser().setRoleId(RolesEnum.CUSTOMER.getId());
            }

            String result = ServiceUtil.post(RoutesUtil.USER_INTERNAL_CREATE, gson.toJson(getUser()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente cadastrado com sucesso!");
                    this.setUser(new UserInternalRequestDTO());
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao cadastrar cliente!");
        }

        loadCustomers(false);
        updateField("frm:listCustomer");
        return "";
    }

    public String actionUpdatePassword() {
        try {
            getRecoverPassword().setOldPassword(getRecoverPassword().getNewPassword());

            Integer customerId = (Integer) PetShopUtil.getObjectSession("customerRecoverPassword");
            String url = String.format(RoutesUtil.USER_CHANGE_PASSWORD_BY_ID, customerId);

            String result = ServiceUtil.patch(url, gson.toJson(getRecoverPassword()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Senha atualizada com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar senha!");
        }

        PetShopUtil.removeObjectSession("customerRecoverPassword");
        loadCustomers(false);
        updateField("frm:listCustomer");
        return "";
    }

    public void resetCustomer() {
        this.setUser(new UserInternalRequestDTO());
        this.setUserUpdate(new UserInternalUpdateRequestDTO());
        PetShopUtil.removeObjectSession("customerEdit");

        openDialog("createCustomer");
    }

    public void actionReadCustomer(UserResponseDTO user) {
        try {
            this.setUserRead(user);
            List<PetResponseDTO> pets = loadPets(user.getId(), "");
            this.getUserRead().setPets(pets);

            List<AddressResponseDTO> addresses = loadAddresses(user.getId());
            this.getUserRead().setAddresses(addresses);

            updateField("frm:readCustomer");
            openDialog("readCustomer");
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar cliente!");
        }
    }

    public void setCustomerRecoverPassword(UserResponseDTO user) {
        if (user != null) {
            PetShopUtil.addObjectSession("customerRecoverPassword", user.getId());

            updateField("frm:passwordCustomer");
            openDialog("passwordCustomer");
        }
    }

    public void setCustomerEdit(UserResponseDTO user) {
        if (user != null) {
            PetShopUtil.addObjectSession("customerEdit", user.getId());

            getUserUpdate().setCpf(user.getCpf());
            getUserUpdate().setName(user.getName());
            getUserUpdate().setRoleId(user.getRoleId());
            getUserUpdate().setEnabled(user.isEnabled());

            updateField("frm:editCustomer");
            openDialog("editCustomer");
        }
    }

    public void setCustomerDelete(UserResponseDTO user) {
        if (user != null) {
            PetShopUtil.addObjectSession("customerDelete", user.getId());

            openDialog("deleteCustomer");
        }
    }

    private String getParams() {
        StringBuilder params = new StringBuilder();

        if (!PetShopUtil.isEmpty(getSearch().getName())) {
            params.append(params.length() == 0 ? "?" : "&").append("name=").append(getSearch().getName());
        }

        if (!PetShopUtil.isEmpty(getSearch().getCpf())) {
            params.append(params.length() == 0 ? "?" : "&").append("cpf=").append(PetShopUtil.removeCharacter(getSearch().getCpf()));
        }

        if (!PetShopUtil.isEmpty(getSearch().getCreatedAtStart())) {
            params.append(params.length() == 0 ? "?" : "&").append("createdAtStart=").append(getSearch().getCreatedAtStart());
        }

        if (!PetShopUtil.isEmpty(getSearch().getCreatedAtEnd())) {
            params.append(params.length() == 0 ? "?" : "&").append("createdAtEnd=").append(getSearch().getCreatedAtEnd());
        }

        return params.toString();
    }

    public List<UserResponseDTO> getCustomersList() {
        if (customersList == null) customersList = new ArrayList<>();
        return customersList;
    }

    public void setCustomersList(List<UserResponseDTO> customersList) {
        this.customersList = customersList;
    }

    public UserInternalRequestDTO getUser() {
        if (user == null) user = new UserInternalRequestDTO();
        return user;
    }

    public void setUser(UserInternalRequestDTO user) {
        this.user = user;
    }

    public UserInternalUpdateRequestDTO getUserUpdate() {
        if (userUpdate == null) userUpdate = new UserInternalUpdateRequestDTO();
        return userUpdate;
    }

    public void setUserUpdate(UserInternalUpdateRequestDTO userUpdate) {
        this.userUpdate = userUpdate;
    }

    public CustomerSearch getSearch() {
        if (search == null) search = new CustomerSearch();
        return search;
    }

    public void setSearch(CustomerSearch search) {
        this.search = search;
    }

    public UserResponseDTO getUserRead() {
        return userRead;
    }

    public void setUserRead(UserResponseDTO userRead) {
        this.userRead = userRead;
    }

    public RecoverPasswordRequestDTO getRecoverPassword() {
        if (recoverPassword == null) recoverPassword = new RecoverPasswordRequestDTO();
        return recoverPassword;
    }

    public void setRecoverPassword(RecoverPasswordRequestDTO recoverPassword) {
        this.recoverPassword = recoverPassword;
    }
}
