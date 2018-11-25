package com.hive.hypermedia.web.resource;

import com.hive.hypermedia.model.Product;
import com.hive.hypermedia.web.controller.NewCartController;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class NewCartResource extends ResourceSupport {

    private final Resources<Product> products;

    private boolean purchased;

    public NewCartResource(
            final Iterable<Product> products,
            boolean purchased) {
        super();

        this.products = new Resources<Product>(products,
                Lists.newArrayList());
        this.purchased = purchased;
        this.add(linkTo(methodOn(NewCartController.class)
                .seeYourCart())
                .withSelfRel());
    }

    public NewCartResource(
            final Iterable<Product> products,
            final List<Link> links,
            boolean purchased) {
        super();

        this.products = new Resources<Product>(products,
                links);
        this.purchased = purchased;
        this.add(linkTo(methodOn(NewCartController.class)
                .seeYourCart())
                .withSelfRel());
    }

    public Resources<Product> getProducts() {
        return products;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

}
