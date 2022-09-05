package com.shopme.admin.self_tests;

import com.shopme.admin.category.CategoryService;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SelfTestRest {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/self_test")
    public List<Category> getHierarchicalCategories(){
        return categoryService.listAll();
    }

    @GetMapping("/self_test/names")
    public List<String> getHierarchicalCategoriesNames(){
        List<String> categoryNames = new ArrayList<>();
        List<Category> categories = categoryService.listAll();
        categories.forEach(cat -> categoryNames.add(cat.getName()));
        categoryNames.add(" ");
        return categoryNames;
    }
}
