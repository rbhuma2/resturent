package com.core.service;

import com.core.mongo.data.entity.GiftCard;

public interface GiftCardService {

    public GiftCard findGiftCardData(String id);

    public void deleteGiftCard(String id);

    public GiftCard saveGiftCardData(GiftCard giftCard);

    

}
