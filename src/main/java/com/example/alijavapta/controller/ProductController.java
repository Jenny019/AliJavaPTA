package com.example.alijavapta.controller;

import com.example.alijavapta.config.CouponRecordStatus;
import com.example.alijavapta.config.RedisDistributeLock;
import com.example.alijavapta.config.ResponseCode;
import com.example.alijavapta.domain.*;
import com.example.alijavapta.mapper.my.ProductMapper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private RedisDistributeLock redisDistributeLock;

    @GetMapping(value = "/category/{categoryID}")
    public ModelAndView getAllProduct(@PathVariable int categoryID) {
        Condition condition = new Condition();
        condition.setCategoryID(categoryID);
        ModelAndView mv = new ModelAndView("product/index");
        mv.addObject("products", productMapper.ListProducts(condition));
        return mv;
    }

    @GetMapping(value = "/id/{productID}")
    public ModelAndView getProduct(@PathVariable String productID) {
        Product product = new Product();
        product.setProductID(productID);
        product.setCategory(new ProductCategory());
        ModelAndView mv = new ModelAndView("product/detail");
        mv.addObject("product", productMapper.GetProduct(product));
        return mv;
    }

    @PostMapping(value = "/findAllProduct")
    public Response getAllProduct(@RequestBody Condition condition) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                productMapper.CountProducts(condition),
                productMapper.ListProducts(condition));
    }

    @PostMapping(value = "/getProduct")
    public Response getProduct(@RequestBody Product product) {
        try {
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    productMapper.GetProduct(product));
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @PostMapping(value = "/findProductVariant")
    public Response getAllProductVariant(@RequestBody Condition condition) {
        try {
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                    productMapper.CountProductVariants(condition),
                    productMapper.ListProductVariants(condition));
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @DeleteMapping("/deleteProduct")
    public Response deleteProduct(@RequestBody Product product) {
        try {
            productMapper.DeleteAllProductVariants(product);
            productMapper.DeleteProduct(product);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    null);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @PutMapping("/updateProduct")
    public Response updateProduct(@RequestBody Product product) {
        try {

            productMapper.UpdateProduct(product);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    product);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @PutMapping("/addProductStock")
    public Response addProductStock(@RequestBody Product product) {
        String uuid = UUID.randomUUID().toString();
        try {
            boolean lock =
                    redisDistributeLock.tryLock("PRODUCT_" + product.getProductID(),
                            uuid, 5, TimeUnit.SECONDS);
            if (lock) {
                int count = productMapper.UpdateProductStock(product);
                if (count > 0) {
                    return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                            product);
                } else {
                    return new Response(ResponseCode.FAIL.ordinal(),
                            "FAIL", 0,
                            "更新库存失败，请重试");
                }
            } else {
                System.out.println("fail to get redis lock, wait next time");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            // 释放锁
            redisDistributeLock.releaseLock("PRODUCT" + product.getProductID(),
                    uuid);
        }
        return new Response(ResponseCode.FAIL.ordinal(),
                "FAIL", 0,
                "更新库存失败，请重试");
    }

    @PostMapping("/createProduct")
    public Response createProduct(@RequestBody Product product) {
        try {
            productMapper.CreateProduct(product);
            List<ProductVariant> variantList = product.getVariantList();
            if (variantList.size() > 0) {
                String[] values1 = variantList.get(0).getValue().split(",");
                for (String v1 : values1) {
                    if (variantList.size() > 1) {
                        String[] values2 =
                                variantList.get(1).getValue().split(",");
                        for (String v2 : values2) {
                            if (variantList.size() > 2) {
                                String[] values3 =
                                        variantList.get(2).getValue().split(
                                                ",");
                                for (String v3 : values3) {
                                    ProductVariant pv = new ProductVariant();
                                    pv.setName1(variantList.get(0).getName());
                                    pv.setValue1(v1);
                                    pv.setName2(variantList.get(1).getName());
                                    pv.setValue2(v2);
                                    pv.setName3(variantList.get(2).getName());
                                    pv.setValue3(v3);
                                    pv.setProduct(product);

                                    productMapper.CreateProductVariant(pv);
                                }

                            } else {
                                ProductVariant pv = new ProductVariant();
                                pv.setName1(variantList.get(0).getName());
                                pv.setValue1(v1);
                                pv.setName2(variantList.get(1).getName());
                                pv.setValue2(v2);
                                pv.setProduct(product);

                                productMapper.CreateProductVariant(pv);
                            }
                        }
                    } else {
                        ProductVariant pv = new ProductVariant();
                        pv.setName1(variantList.get(0).getName());
                        pv.setValue1(v1);
                        pv.setProduct(product);

                        productMapper.CreateProductVariant(pv);
                    }
                }
            }
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    product);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @PostMapping("/createProductVariant")
    public Response createProductVariant(@RequestBody ProductVariant variant) {
        try {
            productMapper.CreateProductVariant(variant);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    variant);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @PutMapping("/updateProductVariant")
    public Response updateProductVariant(@RequestBody ProductVariant variant) {
        try {
            productMapper.UpdateProductVariant(variant);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    variant);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @DeleteMapping("/deleteProductVariant")
    public Response deleteProductVariant(@RequestBody ProductVariant variant) {
        try {
            productMapper.DeleteProductVariant(variant);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0, null);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @PostMapping(value="findAllProductCategories")
    public Response getAllProductCategories(@RequestBody ProductCategory category) {
        return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS",
                productMapper.CountProductCategories(category),
                productMapper.ListProductCategories(category));
    }

    @PostMapping("/createProductCategory")
    public Response createProductCategory(@RequestBody ProductCategory category) {
        try {
            productMapper.CreateProductCategory(category);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    category);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @PutMapping("/updateProductCategory")
    public Response updateProductCategory(@RequestBody ProductCategory category) {
        try {
            productMapper.UpdateProductCategory(category);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0,
                    category);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }

    @DeleteMapping("/deleteProductCategory")
    public Response deleteProductCategory(@RequestBody ProductCategory category) {
        try {
            productMapper.DeleteProductCategory(category);
            return new Response(ResponseCode.SUCCESS.ordinal(), "SUCCESS", 0, null);
        } catch (Exception ex) {
            return new Response(ResponseCode.FAIL.ordinal(), "FAIL", 0,
                    ex.getMessage());
        }
    }
}
