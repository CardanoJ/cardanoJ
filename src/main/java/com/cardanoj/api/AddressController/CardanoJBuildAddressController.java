package com.cardanoj.api.AddressController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cardanoj.api.dto.CardanoJAddressData;
import com.cardanoj.api.service.CardanoJAddressService;
import com.cardanoj.api.util.CardanoJRandomNameGenerator;

@RestController
@RequestMapping("/api")
public class CardanoJBuildAddressController {
	@Autowired
	CardanoJAddressService cardanoJAddressService;
	
    @Autowired
    private CardanoJRandomNameGenerator randomNameGenerator;

    @GetMapping("/address")
    public CardanoJAddressData generateAddress() {
        String randomName = randomNameGenerator.generate();
        return cardanoJAddressService.createAndReadAddressData(randomName);
    }
	

   
  
	
	
	


}
