package com.hive.hypermedia.web.resource;

import com.hive.hypermedia.web.controller.CartController;
import com.hive.hypermedia.web.controller.ProductController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class RootResource extends ResourceSupport {

    public RootResource() {
        this.add(linkTo(ProductController.class).withRel("products"));
        this.add(linkTo(CartController.class).withRel("cart"));
    }
}
