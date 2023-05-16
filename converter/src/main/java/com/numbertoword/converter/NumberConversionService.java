package com.numbertoword.converter;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jakarta.xml.soap.SOAPException;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Service
public class NumberConversionService extends WebServiceGatewaySupport {

    public String convertNumberToWords(int number) throws TransformerException, SOAPException {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        SoapMessage request = messageFactory.createWebServiceMessage();

        // Create the SOAP request payload
        Document document = ((SaajSoapMessage) request).getSaajMessage().getSOAPPart().getEnvelope().getOwnerDocument();
        Element rootElement = document.createElementNS("https://www.dataaccess.com/webservicesserver/", "NumberToWords");
        Element numberElement = document.createElement("nNum");
        numberElement.setTextContent(String.valueOf(number));
        rootElement.appendChild(numberElement);
        ((SaajSoapMessage) request).getSaajMessage().getSOAPBody().addDocument(document);

        // Convert the SOAP request payload to a string
        StringWriter stringWriter = new StringWriter();
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(stringWriter));
        String requestPayload = stringWriter.toString();

        // Send the SOAP request and get the response
        String responsePayload = (String) getWebServiceTemplate()
                .marshalSendAndReceive("https://www.dataaccess.com/webservicesserver/NumberConversion.wso", requestPayload,
                        message -> ((SoapMessage) message).setSoapAction("https://www.dataaccess.com/webservicesserver/NumberToWords"));

        return responsePayload;
    }
}



