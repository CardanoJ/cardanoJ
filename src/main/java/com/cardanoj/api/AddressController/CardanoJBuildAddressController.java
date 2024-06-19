package com.cardanoj.api.AddressController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cardanoj.api.dto.CardanojAddressData;
import com.cardanoj.api.service.CardanoJAddressService;
import com.cardanoj.api.util.CardanoJRandomNameGenerator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Address Controller", description = "Operations related to managing Cardano addresses")
public class CardanoJBuildAddressController {
	@Autowired
	CardanoJAddressService cardanoJAddressService;
	
    @Autowired
    private CardanoJRandomNameGenerator randomNameGenerator;

    @GetMapping("/address")
    @Operation(summary = "Generate Address", description = "Generate a CardanoJ address") 
    public CardanojAddressData generateAddress() {
        String randomName = randomNameGenerator.generate();
        return cardanoJAddressService.createAndReadAddressData(randomName);
    }
	
}
