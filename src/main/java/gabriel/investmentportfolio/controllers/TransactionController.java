package gabriel.investmentportfolio.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import gabriel.investmentportfolio.service.TransactionService;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/buy/{portfolioId}")
    public String buyTransaction(@PathVariable UUID portfolioId, @RequestParam Double quantity, @RequestParam Double purchasePrice) {
        transactionService.buyInvestment(portfolioId, quantity, purchasePrice);
        return "redirect:/transaction/history";  // Redirect back to the portfolio list page after the transaction
    }

    @GetMapping("/sell/{portfolioId}")
    public String sellTransaction(@PathVariable UUID portfolioId, @RequestParam Double quantity, @RequestParam Double sellPrice) {
        transactionService.sellInvestment(portfolioId, quantity, sellPrice);
        return "redirect:/transaction/history";  // Redirect back to the portfolio list page after the transaction
    }

    @GetMapping("/history")
    public String transactionHistory(Model model) {
        model.addAttribute("transactions", transactionService.getTransactionHistory());
        return "transaction-history";
    }

}
