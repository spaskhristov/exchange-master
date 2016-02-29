package com.gmail.spaskhristov.exchange.impl;

public enum CurrencyType {
    USD("Долар"), EUR("Евро"), CHF("Франк"), GBP("Лира");

    private final String fieldDescription;

    private CurrencyType(String value) {
	fieldDescription = value;
    }

    @Override
    public String toString() {
	return fieldDescription;
    }
}
