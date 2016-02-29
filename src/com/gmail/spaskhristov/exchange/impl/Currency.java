package com.gmail.spaskhristov.exchange.impl;

public class Currency {
    private CurrencyType type;
    private float rateBuy;
    private float rateSell;

    public Currency(CurrencyType type, float rateBuy, float rateSell) {
	this.type = type;
	this.rateBuy = rateBuy;
	this.rateSell = rateSell;
    }

    public CurrencyType getName() {
	return type;
    }

    public void setName(CurrencyType type) {
	this.type = type;
    }

    public float getRateBuy() {
	return rateBuy;
    }

    public void setRateBuy(float rateBuy) {
	this.rateBuy = rateBuy;
    }

    public float getRateSell() {
	return rateSell;
    }

    public void setRateSell(float rateSell) {
	this.rateSell = rateSell;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[" + type + ", " + rateBuy + ", " + rateSell + "]");
	return sb.toString();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((type == null) ? 0 : type.hashCode());
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
	Currency other = (Currency) obj;
	if (type != other.type)
	    return false;
	return true;
    }

}
