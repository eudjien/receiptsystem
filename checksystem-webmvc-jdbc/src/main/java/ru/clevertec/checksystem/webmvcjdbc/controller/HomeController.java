package ru.clevertec.checksystem.webmvcjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.clevertec.checksystem.webmvcjdbc.ReceiptDataSource;
import ru.clevertec.checksystem.webmvcjdbc.constant.Attributes;
import ru.clevertec.checksystem.webmvcjdbc.model.HomeModel;

import javax.validation.Valid;

@Controller
public class HomeController {

    private final ReceiptDataSource receiptService;

    @Autowired
    public HomeController(ReceiptDataSource receiptDataSource) {
        this.receiptService = receiptDataSource;
    }

    @GetMapping("/")
    public String home(@Valid HomeModel homeModel, Model model) {
        var receipts = receiptService.findAll(homeModel.getSource());
        model.addAttribute(Attributes.RECEIPTS_ATTRIBUTE, receipts);
        model.addAttribute(Attributes.SOURCE_ATTRIBUTE, homeModel.getSource());
        return "home";
    }
}
