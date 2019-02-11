package com.o2o.controller.superadmin;

import com.o2o.entity.Area;
import com.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
   private final Logger LOGGER= LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    @ResponseBody
    private  Map<String,Object> listArea() {
        LOGGER.info("===start===");
        Long startTime=System.currentTimeMillis();
        Map<String,Object> modleMap = new HashMap<>();
        List<Area> list = new ArrayList<Area>();
        try {
            list=areaService.getAreaService();
            modleMap.put("rows",list);
            modleMap.put("total",list.size());
        } catch (Exception e) {
            e.printStackTrace();
            modleMap.put("success",false);
            modleMap.put("errMsg",e.toString());
        }
        LOGGER.error("test error");
        Long endTime=System.currentTimeMillis();
        LOGGER.debug("constTime:({}ms)",startTime-endTime);
        LOGGER.info("===end===");
        return modleMap;
    }
}
