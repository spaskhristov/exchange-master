package com.gmail.spaskhristov.exchange.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.gmail.spaskhristov.exchange.impl.Exchange;
import com.gmail.spaskhristov.exchange.impl.ExchangeInstitution;
import com.gmail.spaskhristov.exchange.impl.SetBNBfixing;

@Named("beanExchange")
@SessionScoped
public class BeanExchange implements Serializable {

    private static final long serialVersionUID = -1115628237907719401L;

    @Inject
    private Exchange exchange;

    private List<ExchangeInstitution> listInstitutions;

    private SetBNBfixing fixingBNB;

    @PostConstruct
    public void init() {
	refresh();
    }

    public void refresh() {
	listInstitutions = exchange.createListInstitutions();
	fixingBNB = new SetBNBfixing(exchange.createSetFixingBNB());
    }

    public SetBNBfixing getFixingBNB() {
	return fixingBNB;
    }

    public List<ExchangeInstitution> getListInstitutions() {
	return listInstitutions;
    }

    public void setExchange(Exchange exchange) {
	this.exchange = exchange;
    }

}
