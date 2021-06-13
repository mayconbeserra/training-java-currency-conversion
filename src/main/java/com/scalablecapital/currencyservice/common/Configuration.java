package com.scalablecapital.currencyservice.common;

public class Configuration {

    private static final String URL_ECB = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    private Configuration() {
        
    }

    public static String getURL() {
        String url = System.getenv(URL_ECB.getClass().getName());

        if (url == null) {
            return URL_ECB;
        }

        return url;
    }
}
