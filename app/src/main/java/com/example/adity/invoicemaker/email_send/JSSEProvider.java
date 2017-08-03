package com.example.adity.invoicemaker.email_send;

/**
 * Created by adity on 8/3/2017.
 */
import java.security.AccessController;

import java.security.Provider;



public final class JSSEProvider extends Provider {



    public JSSEProvider() {

        super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");

        AccessController

                .doPrivileged(new java.security.PrivilegedAction<Void>() {

                    public Void run() {

                        put("SSLContext.TLS",

                                "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");

                        put("Alg.Alias.SSLContext.TLSv1", "TLS");

                        put("KeyManagerFactory.X509",

                                "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");

                        put("TrustManagerFactory.X509",

                                "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");

                        return null;

                    }

                });

    }

}