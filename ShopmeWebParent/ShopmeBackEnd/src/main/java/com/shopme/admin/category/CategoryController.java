package com.shopme.admin.category;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("categories")
    private String listAll(@Param("sortDir") String sortDir, Model model){
        String reverseSortDir;
        if (sortDir == null || sortDir.isEmpty()){
            sortDir = "asc";
        }

        reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        Iterable<Category> listCategories = categoryService.listAll(sortDir);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("reverseSortDir", reverseSortDir);
        return "categories/categories";
    }

    @GetMapping("categories/new")
    public String newCategory(Model model){
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();
        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Create new Category");
        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category, @RequestParam("fileImage")MultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);

            System.out.println(category.getParent());
            Category savedCategory = categoryService.save(category);
            String uploadDir = "../category-images/" + savedCategory.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }else {
            if (category.getImage().isEmpty()){
                category.setImage(null);
            }
            categoryService.save(category);
        }

        redirectAttributes.addFlashAttribute("message", "The Category has been saved successfully");
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes){
        try {
            Category category = categoryService.get(id);
            List<Category> categories = categoryService.listAll("asc");

            model.addAttribute("category", category);
            model.addAttribute("pageTitle", "Edit Category (ID: "+category.getId()+")");
            model.addAttribute("listCategories", categories);
            return "categories/category_form";
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }

    }
}
