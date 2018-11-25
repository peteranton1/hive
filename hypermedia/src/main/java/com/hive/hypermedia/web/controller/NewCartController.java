package com.hive.hypermedia.web.controller;

import com.hive.hypermedia.model.Product;
import com.hive.hypermedia.persistence.ProductRepository;
import com.hive.hypermedia.web.error.Checks;
import com.hive.hypermedia.web.resource.CartResource;
import com.hive.hypermedia.web.resource.MessageConstants;
import com.hive.hypermedia.web.resource.NewCartResource;
import com.hive.hypermedia.web.resource.ProductResource;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/new/cart")
public class NewCartController {

    private List<Product> products;
    private boolean cartPurchased;

    @Autowired
    private ProductRepository productRepo;

    @RequestMapping(method = RequestMethod.GET)
    public NewCartResource seeYourCart() {
        return new NewCartResource(
                initializeProductsInCart(),
                productLinks(products),
                cartPurchased);
    }

    @RequestMapping(method = RequestMethod.POST)
    public NewCartResource addBookToCart(
            @RequestBody final ProductResource product) {
        final String productcode = product.getProduct().getProductcode();
        final Product productToAdd = Checks.checkEntityExists(
                productRepo.findByProductcode(productcode),
                MessageConstants.MESSAGE_PRODUCT_NOT_FOUND + productcode);

        initializeProductsInCart().add(productToAdd);

        return new NewCartResource(products, cartPurchased);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public NewCartResource buy(@RequestBody final CartResource theCart) {
        NewCartResource cart = new NewCartResource(
                initializeProductsInCart(), cartPurchased);

        if (theCart.isPurchased()) {
            cartPurchased = true;
            cart.setPurchased(cartPurchased);
        }

        cart.add(new Link(
                "http://localhost:8081/api/receipt/1")
                .withRel("receipt"));
        return cart;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearYourCart() {
        initializeProductsInCart().clear();
        this.cartPurchased = false;
    }

    List<Product> initializeProductsInCart() {
        if (products == null) {
            this.products = Lists.newArrayList();
        }

        return this.products;
    }

    List<Link> productLinks(final List<Product> theProducts) {
        return theProducts.stream()
                .map(this::getLink)
                .collect(Collectors.toList());
    }

    Link getLink(final Product product) {
        return linkTo(ProductController.class)
                .slash(product.getProductcode())
                .withSelfRel();
    }

}
