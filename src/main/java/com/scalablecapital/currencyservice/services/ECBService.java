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

    private final String ECB_TAG = "Cube";
    private final String NAME = "ECB";
    private final String DEFAULT_CURRENCY = "EUR";

    public ECBService(URL url) {
        super(url);
    }

    @Override
    public List<ExchangeReferenceRate> getRates() throws ECBException {

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

    private NodeList getElements(String tag) throws ECBException {
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();

            return doc.getElementsByTagName(tag);

        } catch (SAXException e) {
            e.printStackTrace();
            throw new ECBException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ECBException(e);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new ECBException(e);
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return this.NAME;
    }

    @Override
    public String getDefaultCurrency() {
        return this.DEFAULT_CURRENCY;
    }
}
