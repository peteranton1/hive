package com.hive.hypermedia.web.controller;

import com.hive.hypermedia.web.resource.RootResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public RootResource root() {
        return new RootResource();
    }

}
