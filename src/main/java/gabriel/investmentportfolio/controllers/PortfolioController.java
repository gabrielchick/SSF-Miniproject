package gabriel.investmentportfolio.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gabriel.investmentportfolio.model.Portfolio;
import gabriel.investmentportfolio.service.CurrencyConversionService;
import gabriel.investmentportfolio.service.PortfolioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

	@Autowired
	PortfolioService portfolioService;

	@Autowired
	CurrencyConversionService currencyConversionService;

	@PostMapping("/save")
	public String savePortfolio(@Valid @ModelAttribute("portfolio") Portfolio portfolio, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("portfolio", portfolio);
			return "portfolio-add"; // Return back to the form with errors displayed
		}

		Portfolio savedPortfolio = portfolioService.savePortfolio(portfolio);
		redirectAttributes.addFlashAttribute("message", "Portfolio added successfully!");
		return "redirect:/portfolio/list";
	}

	@GetMapping("/add")
	public String addPortfolio(Model model) {
		model.addAttribute("portfolio", new Portfolio());
		return "portfolio-add";
	}

	@GetMapping("/edit/{portfolioId}")
	public String editPortfolio(@PathVariable UUID portfolioId, Model model) {
		// Fetch the portfolio by ID
		Portfolio portfolio = portfolioService.viewPortfolio(portfolioId);
		if (portfolio != null) {
			model.addAttribute("portfolio", portfolio); // Pass the portfolio to the form
		} else {
			model.addAttribute("message", "Portfolio not found!");
		}
		return "portfolio-edit";
	}

	@PostMapping("/update")
	public String updatePortfolio(@Valid @ModelAttribute("portfolio") Portfolio portfolio, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("portfolio", portfolio);
			return "portfolio-edit"; // Return back to the edit form with errors displayed
		}

		// Perform the portfolio update in the service layer
		Portfolio updatedPortfolio = portfolioService.updatePortfolio(portfolio);

		if (updatedPortfolio != null) {
			redirectAttributes.addFlashAttribute("message", "Portfolio updated successfully!");
		} else {
			redirectAttributes.addFlashAttribute("message", "Portfolio update failed!");
		}

		return "redirect:/portfolio/list"; // Redirect to the list after the update
	}

	@GetMapping("/view/{portfolioId}")
	public String view(@PathVariable UUID portfolioId, Model model) {
		Portfolio portfolio = portfolioService.viewPortfolio(portfolioId);
		model.addAttribute("portfolio", portfolio);
		return "portfolio-view";
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<Portfolio> portfolioList = portfolioService.getAllPortfolioDetails();
		Double conversionRate = Double
				.parseDouble(String.format("%.2f", currencyConversionService.getConversionRate())); // Get the
																									// conversion rate
																									// for SGD to USD
		model.addAttribute("portfolioList", portfolioList);
		model.addAttribute("conversionRate", conversionRate); // Add conversion rate to model
		return "portfolio-list";
	}

	@DeleteMapping("/delete/{portfolioId}")
	public String deletePortfolio(@PathVariable UUID portfolioId, RedirectAttributes redirectAttributes) {
		portfolioService.deletePortfolio(portfolioId);
		redirectAttributes.addFlashAttribute("message", "Portfolio deleted successfully!");
		return "redirect:/portfolio/list";
	}
}
