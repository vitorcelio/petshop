package com.metaway.petshop.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metaway.petshop.config.exceptions.ExceptionDetails;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetShopUtil {

    public static final String TOKEN_SESSION = "token@petshop";
    public static final String USER_ID_SESSION = "userId@petshop";
    public static final String EXPIRATION_SESSION = "expiration@petshop";
    public static final String CPF_SESSION = "cpf@petshop";
    public static final String ROLE_ID_SESSION = "roleId@petshop";
    public static final String UPDATE_CPF_COOKIE = "cpf-cookie-petshop";

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    private static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static HttpSession session() {
        HttpServletRequest req = (HttpServletRequest) getExternalContext().getRequest();
        return req.getSession();
    }

    public static HttpServletRequest request() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public static void addObjectSession(String key, Object object) {
        if (session().getAttribute(key) != null) {
            session().removeAttribute(key);
        }

        session().setAttribute(key, object);
    }

    public static Object getObjectSession(String key) {
        return session().getAttribute(key);
    }

    public static void removeObjectSession(String key) {
        session().removeAttribute(key);
    }

    public static void setCookie(String name, String value, int expiry) {
        var response = (HttpServletResponse) getExternalContext().getResponse();

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public static Cookie getCookie(String name) {
        var request = (HttpServletRequest) getExternalContext().getRequest();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    public static String getImageBase64(Part image) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(getInputStreamToBytes(image.getInputStream())));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, image.getContentType().split("/")[1], baos);
        Base64.Encoder encoder = Base64.getEncoder();

        return "data:" + image.getContentType() + ";base64,"
               + encoder.encodeToString(baos.toByteArray());
    }

    public static String getImageBase64Mini(Part image) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(getInputStreamToBytes(image.getInputStream())));

        int type = bi.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bi.getType();

        int width = bi.getWidth() / 5;
        int height = bi.getHeight() / 5;

        BufferedImage shortImage = new BufferedImage(width, height, type);

        Graphics2D graphics = shortImage.createGraphics();
        graphics.drawImage(bi, 0, 0, width, height, null);
        graphics.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(shortImage, image.getContentType().split("/")[1], baos);
        Base64.Encoder encoder = Base64.getEncoder();

        return "data:" + image.getContentType() + ";base64," + encoder.encodeToString(baos.toByteArray());
    }

    // Converter InputStream para array de bytes (Para os arquivos)
    public static byte[] getInputStreamToBytes(InputStream is) throws IOException {
        @SuppressWarnings("unused")
        int len;
        int size = 1024;
        byte[] buf = null;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, size);
            }

            buf = bos.toByteArray();
        }

        return buf;
    }

    public static void addMessage(FacesMessage.Severity severity, String title, String details) {
        FacesMessage message = new FacesMessage(severity, title, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void redirectPage(String page) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(page);
    }

    public static void showMessagesErrors(Gson gson, String result) {
        TypeToken<java.util.List<ExceptionDetails>> typeToken = new TypeToken<>() {
        };

        if (!isEmpty(result)) {
            List<ExceptionDetails> errors = gson.fromJson(result, typeToken.getType());
            errors.forEach(er -> {
                PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", er.getMessage());
            });
        }
    }

    public static String removeCharacter(String text) {

        if (isEmpty(text))
            return "";

        text = removeAccents(text);
        text = text.replaceAll("[^aA-zZ-Z0-9 ]", "");
        return text;
    }

    public static String removeAccents(String a) {
        if (isEmpty(a))
            return "";

        return a.replace("ã", "a")
                .replace("í", "i")
                .replace("á", "a")
                .replace("õ", "o")
                .replace("ç", "c")
                .replace("é", "e")
                .replace("ó", "o")
                .replace("ê", "e")
                .replace("ô", "o")
                .replace("ú", "u")
                .replace("â", "a")
                .replace("ñ", "n")
                .replace("Ã", "A")
                .replace("Í", "I")
                .replace("Á", "A")
                .replace("Õ", "O")
                .replace("Ç", "C")
                .replace("É", "E")
                .replace("Ó", "O")
                .replace("Ê", "E")
                .replace("Ô", "O")
                .replace("Ú", "U")
                .replace("Â", "A")
                .replace("Ñ", "N")
                .replace("ä", "a")
                .replace("Ä", "A")
                .replace("`", "");
    }

    public static boolean matchCharacter(String str) {
        String regex = "\\p{M}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find() || str.contains(" ");
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email);
    }

    public static boolean isValidNumber(String str) {
        return str != null && str.matches("\\d{11}");
    }

}
