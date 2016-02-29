package com.gmail.spaskhristov.exchange.impl;

public enum ExchangeInstitutionType {
    Bank("Банка"), Change("Обменно бюро");

    private final String fieldDescription;

    private ExchangeInstitutionType(String value) {
	fieldDescription = value;
    }

    @Override
    public String toString() {
	return fieldDescription;
    }
}
