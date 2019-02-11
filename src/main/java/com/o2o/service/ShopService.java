package com.o2o.service;

import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;

import java.io.File;

public interface ShopService {
    ShopExecution addShop(Shop shop, File shopImg);
}
