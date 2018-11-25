package com.hive.hypermedia.web.controller;

import com.hive.hypermedia.model.Cart;
import com.hive.hypermedia.model.Product;
import com.hive.hypermedia.persistence.ProductRepository;
import com.hive.hypermedia.web.error.Checks;
import com.hive.hypermedia.web.resource.CartResource;
import com.hive.hypermedia.web.resource.MessageConstants;
import com.hive.hypermedia.web.resource.NewProductResource;
import com.hive.hypermedia.web.resource.ProductResource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CartController implements InitializingBean {

    private Cart cart;

    @Autowired
    private ProductRepository productRepo;

    @RequestMapping(method = RequestMethod.GET)
    public CartResource seeYourCart() {
        return toResource();
    }

    @RequestMapping(method = RequestMethod.POST)
    public CartResource addProductToCart(
            @RequestBody final ProductResource product) {
        final String productcode = product.getProduct().getProductcode();
        final Product productToAdd = Checks.checkEntityExists(
                productRepo.findByProductcode(productcode),
                MessageConstants.MESSAGE_PRODUCT_NOT_FOUND + productcode);

        this.cart.add(productToAdd);
        return toResource();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public CartResource addNewProductToCart(
            @RequestBody final NewProductResource product) {
        final String productcode = product.getProduct().getProductcode();
        final Product bookToAdd = Checks.checkEntityExists(
                productRepo.findByProductcode(
                        product.getProduct().getProductcode()),
                MessageConstants.MESSAGE_PRODUCT_NOT_FOUND + productcode);

        this.cart.add(bookToAdd);
        return toResource();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public CartResource buy(@RequestBody final CartResource theCart) {
        this.cart.setPurchased(theCart.isPurchased());

        return toResource();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public CartResource clearYourCart() {
        this.cart.getProducts().clear();
        this.cart.setPurchased(false);

        return toResource();
    }


    private CartResource toResource() {
        return new CartResource(this.cart.getProducts(),
                this.cart.isPurchased());
    }

    @Override
    public void afterPropertiesSet() {
        this.cart = new Cart();
    }

}
