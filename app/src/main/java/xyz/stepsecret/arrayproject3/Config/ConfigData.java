package xyz.stepsecret.arrayproject3.Config;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import xyz.stepsecret.arrayproject3.R;

/**
 * Created by stepsecret on 21/6/2559.
 */

public class ConfigData {

    public static String API = "https://stepsecret.xyz";
    public static String Logo = API+"/array/api/logo_brand/";
    public static String Promotion = API+"/array/api/img_promotion/";


    public static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // Loading CAs from an InputStream
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        // I'm using Java7. If you used Java6 close it manually with finally.

        InputStream caInput = context.getResources().openRawResource(R.raw.my_cert);

        try {

            ca = cf.generateCertificate(caInput);

        } finally {

            caInput.close();

        }

        // Creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore   = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = getSSLConfig(context);
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }



}
