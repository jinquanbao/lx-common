package com.laoxin.test.controller;

import com.laoxin.core.annotation.PrintLogAnnotation;
import com.laoxin.core.vo.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/test")
public class TestController {

    @ApiOperation("test")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @PrintLogAnnotation
    public ResponseVO<String> test()  {
        return ResponseVO.ok();
    }
}
