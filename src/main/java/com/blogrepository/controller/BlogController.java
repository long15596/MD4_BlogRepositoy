package com.blogrepository.controller;

import com.blogrepository.model.Blog;
import com.blogrepository.repository.BlogRepository;
import com.blogrepository.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("/blog/index");
        modelAndView.addObject("list", blogRepository.findAll());
        return modelAndView;
    }
    @GetMapping("/add")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("listCategory", categoryRepository.findAll());
        modelAndView.addObject("item", new Blog());
        return modelAndView;
    }
    @PostMapping("/save")
    public String save(Blog blog){
        blogRepository.save(blog);
        return "redirect: /blogs";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        modelAndView.addObject("item", blogRepository.findById(id).get());
        modelAndView.addObject("listCategory", categoryRepository.findAll());
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        blogRepository.findById(id).get().setCategory(null);
        blogRepository.deleteById(id);
        return "redirect: /blogs";
    }
    @GetMapping("/search")
    public ModelAndView searchByTitle(@RequestParam String search){
        ModelAndView modelAndView = new ModelAndView("/blog/index");
        modelAndView.addObject("list", blogRepository.findBlogByTitleContaining(search));
        return modelAndView;
    }
}
