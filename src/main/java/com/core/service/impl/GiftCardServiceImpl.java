package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.mongo.data.entity.GiftCard;
import com.core.mongo.data.repository.GiftCardRepository;
import com.core.service.GiftCardService;

@Service
public class GiftCardServiceImpl implements GiftCardService {

    @Autowired
    private GiftCardRepository giftCardRepository;

    

    @Override
	public GiftCard findGiftCardData(String id) {
		return giftCardRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteGiftCard(String id) {
		giftCardRepository.deleteById(id);
		
	}

	@Override
	public GiftCard saveGiftCardData(GiftCard giftCard) {
		GiftCard savediftCardData = giftCardRepository.save(giftCard);
		GiftCard response = new GiftCard();
        response.setIdentifier(savediftCardData.getIdentifier());
        return response;
	}

}
