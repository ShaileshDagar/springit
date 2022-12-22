package com.dagar.springit.controller;

import com.dagar.springit.domain.Link;
import com.dagar.springit.repository.LinkRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LinkController {

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);
    private final LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("links", linkRepository.findAll());
        return "link/list";
    }

    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> link = linkRepository.findById(id);
        if( link.isPresent() ) {
            model.addAttribute("link",link.get());
            model.addAttribute("success", model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link",new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid @ModelAttribute("link") Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            logger.info("Validation errors were found while submitting a new link.");
            model.addAttribute("link", link);
        }else{
            logger.info(String.valueOf(link.getId()));
            link = linkRepository.save(link);
            logger.info(String.valueOf(link.getId()));
            logger.info("New link was saved successfully.");
            redirectAttributes
                    .addAttribute("id", link.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/link/{id}";
        }
        return "link/submit";
    }


    //How to write the following method?
    @PutMapping("/{id}")
    public Link update(@PathVariable Long id, @ModelAttribute Link link) {
        //Stub only
        return null;
    }
}
