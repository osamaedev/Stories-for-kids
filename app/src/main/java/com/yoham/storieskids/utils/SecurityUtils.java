package com.yoham.storieskids.utils;

import com.mobapphome.simpleencryptorlib.SimpleEncryptor;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public final class SecurityUtils {

    @NotNull
    public static String getFP() {
        String[] b = AppConstants.TITLE.split("_");
        return b[0] + b[3] + b[1] + b[4] + b[2];
    }

    public static String getSP(String fp) {
        SimpleEncryptor simpleEncryptor = null;
        try {
            simpleEncryptor = SimpleEncryptor.newInstance(fp);
            return simpleEncryptor.encode(fp);
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return "";
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return "";
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decryptString(String tbd, String p) {
        SimpleEncryptor simpleEncryptor = null;
        try {
            simpleEncryptor = SimpleEncryptor.newInstance(p);
            return simpleEncryptor.decode(tbd);
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return "";
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return "";
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
