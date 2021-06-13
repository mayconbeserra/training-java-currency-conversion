package com.scalablecapital.currencyservice.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.scalablecapital.currencyservice.models.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ECBService extends CentralBankService {

    private static final String ECB_TAG = "Cube";
    private static final String NAME = "ECB";
    private static final String DEFAULT_CURRENCY = "EUR";

    public ECBService(URL url) {
        super(url);
    }

    @Override
    public List<ExchangeReferenceRate> getRates() {
        NodeList nodeList = getElements(ECB_TAG);

        List<ExchangeReferenceRate> list = new ArrayList<>();

        for (int i = 2; i < nodeList.getLength(); i++) {
            var item = nodeList.item(i);

            String currency = item.getAttributes().item(0).getNodeValue();
            BigDecimal rate = new BigDecimal(item.getAttributes().item(1).getNodeValue(), MathContext.DECIMAL32);

            ExchangeReferenceRate quote = new ExchangeReferenceRate(DEFAULT_CURRENCY, currency, rate);
            list.add(quote);
        }

        return list;
    }

    private NodeList getElements(String tag) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();

            return doc.getElementsByTagName(tag);

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            throw new ECBException(e);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDefaultCurrency() {
        return DEFAULT_CURRENCY;
    }
}
