package com.metaway.petshop.backing;

import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.dto.response.AddressResponseDTO;
import com.metaway.petshop.dto.response.ViaCepResponseDTO;
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

@ManagedBean
@ViewScoped
public class AddressBacking extends AbstractBacking implements Serializable {

    private List<AddressResponseDTO> addresses;
    private AddressRequestDTO address;

    @PostConstruct
    public void init() {
        validateProfileCustomer();

        loadAddresses();
    }

    private void loadAddresses() {
        List<AddressResponseDTO> addressList = loadAddresses(null);
        this.setAddresses(addressList);
    }

    public String actionSaveAddress() {
        Integer addressId = (Integer) PetShopUtil.getObjectSession("addressEdit");
        if (addressId != null) {
            return actionEditAddress();
        } else {
            return actionCreateAddress();
        }
    }

    private String actionCreateAddress() {
        try {

            getAddress().setCustomerId(getUserId());
            getAddress().setPostalCode(PetShopUtil.removeCharacter(getAddress().getPostalCode()));
            String result = ServiceUtil.post(RoutesUtil.ADDRESS_CREATE, gson.toJson(getAddress()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Success", "Endereço salvo com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar endereço!");
        }

        loadAddresses();
        updateField("frm:listAddresses");
        return "";
    }

    private String actionEditAddress() {
        try {

            getAddress().setCustomerId(getUserId());
            getAddress().setPostalCode(PetShopUtil.removeCharacter(getAddress().getPostalCode()));

            Integer addressId = (Integer) PetShopUtil.getObjectSession("addressEdit");

            String result = ServiceUtil.put(String.format(RoutesUtil.ADDRESS_UPDATE, addressId),
                    gson.toJson(getAddress()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Endereço editado com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao editar endereço!");
        }

        PetShopUtil.removeObjectSession("addressEdit");
        loadAddresses();
        updateField("frm:listAddresses");
        return "";
    }

    public String actionDeleteAddress() {
        try {

            Integer addressId = (Integer) PetShopUtil.getObjectSession("addressDelete");

            String result = ServiceUtil.delete(String.format(RoutesUtil.ADDRESS_DELETE, addressId), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Endereço deletado com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar endereço!");
        }

        PetShopUtil.removeObjectSession("addressDelete");
        loadAddresses();
        updateField("frm:listAddresses");
        return "";
    }

    public void setAddressEdit(AddressResponseDTO address) {
        if (address != null) {
            PetShopUtil.addObjectSession("addressEdit", address.getId());

            getAddress().setId(address.getId());
            getAddress().setCity(address.getCity());
            getAddress().setState(address.getState());
            getAddress().setComplement(address.getComplement());
            getAddress().setStreet(address.getStreet());
            getAddress().setTag(address.getTag());
            getAddress().setDistrict(address.getDistrict());
            getAddress().setPostalCode(address.getPostalCode());

            updateField("frm:addressesCreate");
            openDialog("addressesCreate");
        }
    }

    public void setAddressDelete(AddressResponseDTO address) {
        if (address != null) {
            PetShopUtil.addObjectSession("addressDelete", address.getId());

            openDialog("addressesDelete");
        }
    }

    public void resetAddresses() {
        PetShopUtil.removeObjectSession("addressEdit");
        this.setAddresses(new ArrayList<>());

        updateField("frm:addressesCreate");
        openDialog("addressesCreate");
    }

    public void searchCEP() {
        try {
            if (!PetShopUtil.isEmpty(getAddress().getPostalCode())) {

                String url = String.format(RoutesUtil.VIA_CEP_SERVICE, getAddress().getPostalCode());

                String result = ServiceUtil.get(url, false, null);

                if (result != null) {

                    if (!result.contains("erro")) {
                        ViaCepResponseDTO viaCep = gson.fromJson(result, ViaCepResponseDTO.class);
                        getAddress().setStreet(viaCep.getLogradouro());
                        getAddress().setDistrict(viaCep.getBairro());
                        getAddress().setComplement(viaCep.getComplemento());
                        getAddress().setCity(viaCep.getLocalidade());
                        getAddress().setState(viaCep.getEstado());

                        updateField("frm:info-address");
                    } else {
                        PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Erro", "Cep não encontrado!");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Erro", "Erro ao buscar CEP, digite manualmente!");
        }
    }

    public List<AddressResponseDTO> getAddresses() {
        if (addresses == null) addresses = new ArrayList<>();
        return addresses;
    }

    public void setAddresses(List<AddressResponseDTO> addresses) {
        this.addresses = addresses;
    }

    public AddressRequestDTO getAddress() {
        if (address == null) address = new AddressRequestDTO();
        return address;
    }

    public void setAddress(AddressRequestDTO address) {
        this.address = address;
    }
}
