package com.gmail.spaskhristov.exchange.impl;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class ExchangeInstitution {

    private Set<Currency> setCurrency;
    private String name;
    private ExchangeInstitutionType type;
    private String urlStr;
    private String fullName;
    DecimalFormat myFormatter = new DecimalFormat("###.###");

    public ExchangeInstitution(String name, String fullName, String urlStr) {
	this.name = name;
	this.fullName = fullName;
	this.type = ExchangeInstitutionType.Bank;
	this.urlStr = urlStr;
	this.setCurrency = new HashSet<>();
    }

    public ExchangeInstitution(String name, String fullName, String urlStr, ExchangeInstitutionType type) {
	this.name = name;
	this.fullName = fullName;
	this.urlStr = urlStr;
	this.type = type;
	this.setCurrency = new HashSet<>();
    }

    public String buy(String currType) {
	for (Currency curr : setCurrency) {
	    if (curr.getName().name().equals(currType)) {
		return String.format("%.5f", curr.getRateBuy());
	    }
	}
	return "0";
    }

    public String sell(String currType) {
	for (Currency curr : setCurrency) {
	    if (curr.getName().name().equals(currType)) {
		return String.format("%.5f", curr.getRateSell());
	    }
	}
	return "0";
    }

    public Set<Currency> getSetCurrency() {
	return setCurrency;
    }

    public void setSetCurrency(Set<Currency> setCurrency) {
	this.setCurrency = setCurrency;
    }

    public String getName() {
	return name;
    }

    public void setNameInstitution(String nameInstitution) {
	this.name = nameInstitution;
    }

    public ExchangeInstitutionType getType() {
	return type;
    }

    public void setType(ExchangeInstitutionType type) {
	this.type = type;
    }

    public String getUrlStr() {
	return urlStr;
    }

    public void setUrlStr(String urlStr) {
	this.urlStr = urlStr;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ExchangeInstitution other = (ExchangeInstitution) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(name + ": \n");
	for (Currency currency : setCurrency) {
	    sb.append(currency.toString() + "\n");
	}
	return sb.toString();
    }

}
