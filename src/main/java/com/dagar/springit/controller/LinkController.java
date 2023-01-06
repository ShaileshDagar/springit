package com.dagar.springit.controller;

import com.dagar.springit.domain.Comment;
import com.dagar.springit.domain.Link;
import com.dagar.springit.service.CommentService;
import com.dagar.springit.service.LinkService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LinkController {

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);
    private final LinkService linkService;
    private final CommentService commentService;

    public LinkController(LinkService linkService, CommentService commentService) {
        this.linkService = linkService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("links", linkService.findAll());
        return "link/list";
    }

    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> optionalLink = linkService.findById(id);
        if(optionalLink.isPresent()){
            Link link = optionalLink.get();
            Comment comment = new Comment();
            comment.setLink(link);
            model.addAttribute("comment", comment);
            model.addAttribute("link",link);
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
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            logger.info("Validation errors were found while submitting a new link.");
            model.addAttribute("link", link);
        }else{
            logger.info(String.valueOf(link.getId()));
            link = linkService.save(link);
            logger.info(String.valueOf(link.getId()));
            logger.info("New link was saved successfully.");
            redirectAttributes
                    .addAttribute("id", link.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/link/{id}";
        }
        return "link/submit";
    }
    @Secured({"ROLE_USER"})
    @PostMapping("/link/comments")
    public String addComment(@Valid Comment comment, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if( bindingResult.hasErrors() ) {
            logger.info("Something went wrong.");
        } else {
            logger.info("New Comment Saved!");
            commentService.save(comment);
            redirectAttributes
                    .addFlashAttribute("commentsuccess", true);
        }
        return "redirect:/link/" + comment.getLink().getId();
    }




    //How to write the following method?
    @PutMapping("/{id}")
    public Link update(@PathVariable Long id, @ModelAttribute Link link) {
        //Stub only
        return null;
    }

}
