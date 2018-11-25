package com.hive.hypermedia.web.resource;

import com.hive.hypermedia.model.Product;
import com.hive.hypermedia.web.controller.CartController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.springframework.hateoas.mvc.BasicLinkBuilder.linkToCurrentMapping;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CartResource extends ResourceSupport {

    private List<Product> products;

    private boolean purchased;

    public CartResource() {
        super();
    }

    public CartResource(final List<Product> products,
                        final boolean purchased) {
        super();

        this.products = products;
        this.purchased = purchased;

        this.add(linkTo(methodOn(CartController.class)
                .seeYourCart())
                .withSelfRel());

        if (!purchased && !this.products.isEmpty()) {
            Link clearLink = null;
            try {
                clearLink = linkTo(
                        CartController.class,
                        CartController.class
                                .getMethod("clearYourCart"))
                        .withRel("clear");
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            this.add(clearLink);
        }
        if (purchased) {
            this.add(linkToCurrentMapping()
                    .slash("/receipt")
                    .slash(randomAlphanumeric(6))
                    .withRel("receipt"));
        }

    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
