package com.gmail.spaskhristov.exchange.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Named("exchange")
@ApplicationScoped
public class Exchange {

    private List<ExchangeInstitution> listInstitutions = new ArrayList<>();
    private Set<BNBfixing> setFixingBNB = new HashSet<>();

    /*
     * public Exchange() throws IOException { fillInstitutionsBegin();
     * fillInstitutions(); fillFixingBNB(); }
     * 
     * public Exchange(List<ExchangeInstitution> listInstitutions,
     * Set<BNBfixing> setFixingBNB) { this.listInstitutions = listInstitutions;
     * this.setFixingBNB = setFixingBNB; }
     */

    public List<ExchangeInstitution> createListInstitutions() {
	fillInstitutionsBegin();
	try {
	    fillInstitutions();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return listInstitutions;
    }

    public Set<BNBfixing> createSetFixingBNB() {
	try {
	    fillFixingBNB();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return setFixingBNB;
    }

    public void fillInstitutionsBegin() {
	listInstitutions.clear();
	listInstitutions.add(new ExchangeInstitution("unicreditbulbank", "УниКредит Булбанк",
		"http://www.unicreditbulbank.bg/bg/Currency_Rates/Currencies/index.htm"));
	listInstitutions.add(new ExchangeInstitution("fibank", "Първа инвестиционна банка",
		"http://fibank.bg/bg/valutni-kursove/page/461"));
	listInstitutions.add(new ExchangeInstitution("ccbank", "Централна кооперативна банка",
		"http://www.ccbank.bg/bg/valutni-kursove/"));
	listInstitutions.add(new ExchangeInstitution("cibank", "СИБанк", "https://www.cibank.bg/bg/currency"));
	listInstitutions.add(new ExchangeInstitution("procreditbank", "Прокредит Банк",
		"http://www.procreditbank.bg/bg/page/205/"));
	listInstitutions.add(new ExchangeInstitution("dskbank", "Банка ДСК",
		"https://dskbank.bg/page/default.aspx?&xml_id=/bg-BG/.currency"));
	listInstitutions
		.add(new ExchangeInstitution("raiffeisenbank", "Райфайзенбанк",
			"http://www.rbb.bg/bg/corporate-clients/investments-and-fx-market/fx-market-and-instruments/currency-rates/"));
	listInstitutions.add(new ExchangeInstitution("allianzbank", "Алианц Банк",
		"http://www.bank.allianz.bg/index.php?page=currency"));
	listInstitutions.add(new ExchangeInstitution("sgebank", "Сосиете Женерал Експресбанк",
		"http://www.sgeb.bg/bg/byrzi-vryzki/valutni-kursove.html"));
	listInstitutions.add(new ExchangeInstitution("postbank", "Пощенска банка",
		"https://www.postbank.bg/bg-BG/CCYRates"));
	listInstitutions.add(new ExchangeInstitution("dbank", "Търговска Банка Д (Д Банк)",
		"http://www.dbank.bg/bg/currencyrates"));
	listInstitutions.add(new ExchangeInstitution("tavex", "Тавекс", "http://www.tavex.bg/?main=24",
		ExchangeInstitutionType.Change));
	listInstitutions.add(new ExchangeInstitution("varchev", "Варчев - обменни бюра - София",
		"http://exchange.varchev.com/index.php?request=index&subquest=rates_sofia",
		ExchangeInstitutionType.Change));
	listInstitutions.add(new ExchangeInstitution("changepartner", "Партенр чейндж - обменни бюра - София",
		"http://www.changepartner.net/valuta.aspx", ExchangeInstitutionType.Change));
	listInstitutions.add(new ExchangeInstitution("crown", "Crown чейндж - обменни бюра - София",
		"http://crownchange.com/exchange-rates/", ExchangeInstitutionType.Change));
    }

    public void fillInstitutions() throws IOException {
	for (ExchangeInstitution institution : listInstitutions) {
	    Set<Currency> setCurrency = new HashSet<>();
	    URL institutionURL = new URL(institution.getUrlStr());
	    Document doc = Jsoup.parse(institutionURL, 36000);
	    Elements allTD = doc.select("td");
	    for (int index = 0; index < allTD.size(); index++) {
		Element td = allTD.get(index);
		String strTd = td.text().trim().toLowerCase();
		if (strTd.startsWith("usd") || strTd.startsWith("eur") || strTd.startsWith("chf")
			|| strTd.startsWith("gbp") || strTd.startsWith("щатски долар (usd)")
			|| strTd.startsWith("евро (eur)") || strTd.startsWith("швейцарски франк (chf)")
			|| strTd.startsWith("британска лира (gbp)")) {
		    StringBuilder rateBuySb = new StringBuilder();
		    StringBuilder rateSellSb = new StringBuilder();
		    CurrencyType currencyType = CurrencyType.GBP;
		    if (strTd.startsWith("usd") || strTd.startsWith("щатски")) {
			currencyType = CurrencyType.USD;
		    } else if (strTd.startsWith("eur") || strTd.startsWith("евро")) {
			currencyType = CurrencyType.EUR;
		    } else if (strTd.startsWith("chf") || strTd.startsWith("швейцарски")) {
			currencyType = CurrencyType.CHF;
		    }
		    switch (institution.getName()) {
		    case "procreditbank":
		    case "varchev":
		    case "changepartner":
		    case "crown":
			try {
			    Float.parseFloat(allTD.get(index + 1).text());
			} catch (NumberFormatException e) {
			    continue;
			}
			rateBuySb.append(allTD.get(index + 1).text());
			rateSellSb.append(allTD.get(index + 2).text());
			break;
		    case "dskbank":
			rateBuySb.append(allTD.get(index + 2).text());
			rateSellSb.append(allTD.get(index + 3).text());
			break;
		    case "unicreditbulbank":
		    case "fibank":
		    case "ccbank":
		    case "dbank":
			rateBuySb.append(allTD.get(index + 3).text());
			rateSellSb.append(allTD.get(index + 4).text());
			break;
		    case "raiffeisenbank":
		    case "cibank":
			rateBuySb.append(allTD.get(index + 6).text());
			rateSellSb.append(allTD.get(index + 7).text());
			break;
		    case "postbank":
		    case "sgebank":
			rateBuySb.append(allTD.get(index + 4).text());
			rateSellSb.append(allTD.get(index + 5).text());
			break;
		    case "allianzbank":
			String[] rate = allTD.get(index + 1).text().split("/");
			rateBuySb.append(rate[0]);
			rateSellSb.append(rate[1]);
			break;
		    case "tavex":
			rateBuySb.append(allTD.get(index + 2).text());
			rateSellSb.append(allTD.get(index + 3).text());
			break;
		    default:
			break;
		    }
		    Currency currency = new Currency(currencyType, Float.parseFloat(rateBuySb.toString()),
			    Float.parseFloat(rateSellSb.toString()));
		    setCurrency.add(currency);
		}
	    }
	    institution.setSetCurrency(setCurrency);
	}
    }

    public void fillFixingBNB() throws IOException {
	URL urlBNB = new URL("http://www.bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/");
	Document doc = Jsoup.parse(urlBNB, 36000);
	Elements allTD = doc.select("td");
	for (int index = 0; index < allTD.size(); index++) {
	    Element td = allTD.get(index);
	    String strTd = td.text().trim().toLowerCase();

	    if (strTd.startsWith("usd") || strTd.startsWith("chf") || strTd.startsWith("gbp")) {
		StringBuilder rateReference = new StringBuilder();
		CurrencyType currencyType = CurrencyType.GBP;
		if (strTd.startsWith("usd")) {
		    currencyType = CurrencyType.USD;
		} else if (strTd.startsWith("chf")) {
		    currencyType = CurrencyType.CHF;
		}
		rateReference.append(allTD.get(index + 2).text());
		BNBfixing fixingBNB = new BNBfixing(currencyType, Float.parseFloat(rateReference.toString()));
		setFixingBNB.add(fixingBNB);
	    }
	}
	setFixingBNB.add(new BNBfixing(CurrencyType.EUR, 1.955830f));
    }

    public List<ExchangeInstitution> getListInstitutions() {
	return listInstitutions;
    }

    public void setListInstitutions(List<ExchangeInstitution> listInstitutions) {
	this.listInstitutions = listInstitutions;
    }

    public Set<BNBfixing> getSetFixingBNB() {
	return setFixingBNB;
    }

    public void setSetFixingBNB(Set<BNBfixing> setFixingBNB) {
	this.setFixingBNB = setFixingBNB;
    }
}
