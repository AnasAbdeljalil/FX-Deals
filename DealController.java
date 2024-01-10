package com.progresssoft.fx.deals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fx/deal/")
public class DealController {

	@Autowired
	private Facade facade;

	private Logger logger = LoggerFactory.getLogger(DealController.class);

	////////////////////////////////////////////////////////////////////////////////
	@PostMapping("/import")
	public ResponseEntity<Boolean> importDeal(@RequestBody FxDealRequest fxDealRequest) {
		try {
			logger.info("importDeal()");
			return ResponseEntity.status(200).body(facade.importDeal(fxDealRequest));
		} finally {
			logger.info("/importDeal()");
		}
	}

}
