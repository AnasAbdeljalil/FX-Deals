package com.xx.fx.deals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Facade {

	@Autowired
	private DataAccess dao;

	private Logger logger = LoggerFactory.getLogger(Facade.class);

	////////////////////////////////////////////////////////////////////////////////
	public boolean importDeal(FxDealRequest fxDealRequest) {
		try {
			logger.info("importDeal({})", fxDealRequest.toString());
			validateDealRequest(fxDealRequest);
			sortCurrencies(fxDealRequest);
			insertIntoDataBase(fxDealRequest);
			return true;
		} finally {
			logger.info("/importDeal({})", fxDealRequest.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	private void insertIntoDataBase(FxDealRequest fxDealRequest) {
		Map<String, Object> map = new HashMap<>();
		map.put("DEAL_UNIQUE_ID", fxDealRequest.getDealUniqueId());
		map.put("DEAL_TO_CURRENCY", fxDealRequest.getToCurrency());
		map.put("DEAL_AMMOUNTS", fxDealRequest.getDealAmmounts().toString());
//		map.put("DEAL_TIMESTAMP", fxDealRequest.getDealTimestamp());
		map.put("DEAL_FROM_CURRENCIES", fxDealRequest.getFromCurrencies().toString());
		dao.insertRequest("FX_DEALS", map);
	}

	////////////////////////////////////////////////////////////////////////////////
	private void sortCurrencies(FxDealRequest fxDealRequest) {
		List<String> fromCurrencies = fxDealRequest.getFromCurrencies();
		List<Double> dealAmmounts = fxDealRequest.getDealAmmounts();
		List<CurrencyAmount> currencyAmountList = new ArrayList<>();

		for (int i = 0; i < fromCurrencies.size(); i++) {
			currencyAmountList.add(new CurrencyAmount(fromCurrencies.get(i), dealAmmounts.get(i)));
		}
		Collections.sort(currencyAmountList, Comparator.comparingDouble(CurrencyAmount::getAmount));
		List<String> sortedCurrencies = new ArrayList<>();
		List<Double> sortedAmounts = new ArrayList<>();
		for (CurrencyAmount ca : currencyAmountList) {
			sortedCurrencies.add(ca.getCurrency());
			sortedAmounts.add(ca.getAmount());
		}
		fxDealRequest.setDealAmmounts(sortedAmounts);
		fxDealRequest.setFromCurrencies(sortedCurrencies);
	}

	////////////////////////////////////////////////////////////////////////////////
	private void validateDealRequest(FxDealRequest fxDealRequest) {
		String dealUniqueId = fxDealRequest.getDealUniqueId();
		String toCurrency = fxDealRequest.getToCurrency();
		if (MsUtil.isAnyEmpty(dealUniqueId, toCurrency)) {
			MsUtil.throww(new FxRequestException("Deal Id or To Cusrrency is Null !!"));
		}

		if (MsUtil.isEmpty(fxDealRequest.getFromCurrencies())) {
			MsUtil.throww(new FxRequestException("From Currencies is Null !!"));
		}

		if (MsUtil.isEmpty(fxDealRequest.getDealAmmounts())) {
			MsUtil.throww(new FxRequestException("Deal Ammounts is Null !!"));
		}

		if (MsUtil.isEmpty(fxDealRequest.getDealTimestamp())) {
			MsUtil.throww(new FxRequestException("Deal Timestamp is Null !!"));
		}

		if (!isTimestampInFormat(fxDealRequest.getDealTimestamp())) {
			MsUtil.throww(new FxRequestException("Deal Timestamp not formatted correctlly!!, Should be yyyy-MM-dd HH:mm:ss"));
		}

		if (!isValidIsoCode(fxDealRequest)) {
			MsUtil.throww(new FxRequestException("Not Valid ISO Code Currencies"));
		}

		if (fxDealRequest.getFromCurrencies().size() != fxDealRequest.getDealAmmounts().size()) {
			MsUtil.throww(new FxRequestException("From Currencies size not equal Deal Ammounts !!"));
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	private boolean isValidIsoCode(FxDealRequest fxDealRequest) {
		List<String> fromCurrencies = fxDealRequest.getFromCurrencies();
		for (String currecny : fromCurrencies) {
			if (MsUtil.isEmpty(currecny)) {
				return false;
			}
			if (currecny.length() != 3) {
				return false;
			}
		}

		String toCurrency = fxDealRequest.getToCurrency();
		if (MsUtil.isEmpty(toCurrency)) {
			return false;
		}

		if (toCurrency.length() != 3) {
			return false;
		}

		return true;
	}

	////////////////////////////////////////////////////////////////////////////////
	private boolean isTimestampInFormat(String timestamp) {
		try {
			String format = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			Date parsedDate = sdf.parse(timestamp);
			return parsedDate != null && timestamp.equals(sdf.format(parsedDate));
		} catch (ParseException e) {
			return false;
		}
	}

}
