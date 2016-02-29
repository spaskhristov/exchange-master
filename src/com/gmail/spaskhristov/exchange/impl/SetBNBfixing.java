package com.gmail.spaskhristov.exchange.impl;

import java.util.Set;

public class SetBNBfixing {
    private Set<BNBfixing> fixingBNB;

    public SetBNBfixing(Set<BNBfixing> fixingBNB) {
	this.fixingBNB = fixingBNB;
    }

    public Set<BNBfixing> getFixingBNB() {
	return fixingBNB;
    }

    public void setFixingBNB(Set<BNBfixing> fixingBNB) {
	this.fixingBNB = fixingBNB;
    }

    public float reference(String currType) {
	for (BNBfixing fixing : fixingBNB) {
	    if (fixing.getType().name().equals(currType)) {
		return fixing.getRateReference();
	    }
	}
	return 0.00f;
    }

}
