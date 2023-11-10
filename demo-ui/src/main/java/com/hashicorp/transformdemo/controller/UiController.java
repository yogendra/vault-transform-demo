package com.hashicorp.transformdemo.controller;

import com.hashicorp.transformdemo.service.UiService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UiController implements ErrorController {


  private final UiService uiService;

  public UiController(UiService uiService) {
    this.uiService = uiService;
  }

  @RequestMapping("/error")
  public String handleError() {
        //do something like logging
        return "ui/error";
  }
  @GetMapping(value = "/")
  public String home(Model model) throws Exception {
    model.addAttribute("eusers", this.uiService.getEncryptedUsers());
    model.addAttribute("tusers", this.uiService.getTransformedUsers());
    model.addAttribute("stusers", this.uiService.getSimpleTransformedUsers());
    model.addAttribute("stestusers", this.uiService.getSimplestTransformedUsers());
    model.addAttribute("dtusers", this.uiService.getDefaultTokenizationUsers());
    model.addAttribute("ctusers", this.uiService.getConvergentTokenizationUsers());
    return "ui/index";
  }
//
//  @ExceptionHandler(Exception.class)
//  public String handleException(Exception e) {
//    log.warn("Encountered Error in processing request", e);
//    return "redirect:/";
//  }
//
//  @GetMapping("/error")
//  public String handleError(Model model) {
//    log.warn("Error page accessed");
//    return "redirect:/";
//  }

  @PostMapping(value = "/encrypt")
  public String encrypt(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam String email,
    @RequestParam String creditcard,
    @RequestParam String method) {

    this.uiService.addOneEncryptedUser(
      username,
      password,
      email,
      creditcard,
      method
    );
    return "redirect:/";
  }

  @PostMapping(value = "/decrypt")
  public String decrypt(@RequestParam String username, Model model) {
    var user = this.uiService.getOneDecryptedUser(username);
    model.addAttribute("userinfo", user);
    return "ui/userinfo";
  }

  @PostMapping(value = "/decode")
  public String decode(@RequestParam String username, @RequestParam String flag, Model model) {
    var user = this.uiService.getOneDecodedUser(username, flag);
    model.addAttribute("userinfo", user);
    return "ui/userinfo";
  }
}
