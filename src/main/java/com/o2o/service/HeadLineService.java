package com.o2o.service;

import com.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
    List<HeadLine>getHeadLineService(HeadLine headLineCondition)throws IOException;
}
