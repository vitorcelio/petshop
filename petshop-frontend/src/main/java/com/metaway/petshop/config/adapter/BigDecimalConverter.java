package com.metaway.petshop.config.adapter;

import com.metaway.petshop.util.PetShopUtil;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter {

    private static final Locale BRAZIL = new Locale("pt", "BR");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            NumberFormat format = NumberFormat.getInstance(BRAZIL);
            return new BigDecimal(format.parse(value).toString());
        } catch (Exception e) {
            PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Formato inválido. Use: R$ 99,99");
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(BRAZIL);
        return format.format(value);
    }
}

