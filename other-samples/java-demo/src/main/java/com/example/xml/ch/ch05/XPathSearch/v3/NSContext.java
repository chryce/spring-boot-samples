package com.example.xml.ch.ch05.XPathSearch.v3;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

public class NSContext implements NamespaceContext {

    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new
                    IllegalArgumentException("prefix is null");
        } else if (prefix.equals("tt")) {
            return "http://www.javajeff.ca/";
        } else {
            return null;
        }
    }

    @Override
    public String getPrefix(String uri) {
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String uri) {
        return null;
    }
}
