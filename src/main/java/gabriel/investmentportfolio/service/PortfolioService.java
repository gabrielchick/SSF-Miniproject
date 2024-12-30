package gabriel.investmentportfolio.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabriel.investmentportfolio.exception.PortfolioNotFoundException;
import gabriel.investmentportfolio.model.Portfolio;
import gabriel.investmentportfolio.repository.PortfolioRepository;
import jakarta.validation.Valid;

@Service
public class PortfolioService {

	@Autowired
	PortfolioRepository portfolioRepository;

//portfolio will be stored in database
	public Portfolio savePortfolio(Portfolio portfolio) {
		portfolio.setPortfolioId(UUID.randomUUID());
		portfolioRepository.savePortfolio(portfolio);
		return portfolio;
	}

	public Portfolio viewPortfolio(UUID portfolioId) {
		Portfolio portfolio = portfolioRepository.getPortfolioDetail(portfolioId);
	    if (portfolio == null) {
	        throw new PortfolioNotFoundException("Portfolio with ID " + portfolioId + " not found.");
	    }
	    return portfolio;

	}

	public void deletePortfolio(UUID portfolioId) {
		portfolioRepository.deletePortfolio(portfolioId);
	}

	public List<Portfolio> getAllPortfolioDetails() {
		return portfolioRepository.getPortfolioDetails();
	}

	public Portfolio updatePortfolio(Portfolio portfolio) {
		Portfolio existingPortfolio = portfolioRepository.getPortfolioDetail(portfolio.getPortfolioId());
		if (existingPortfolio != null) {
			// Update properties if the portfolio exists
			existingPortfolio.setInvestmentName(portfolio.getInvestmentName());
			existingPortfolio.setPurchasePrice(portfolio.getPurchasePrice());
			existingPortfolio.setQuantity(portfolio.getQuantity());
			existingPortfolio.setCurrentPrice(portfolio.getCurrentPrice());
			portfolioRepository.savePortfolio(existingPortfolio); // Save the updated portfolio
			return existingPortfolio;
		} else {
		    throw new PortfolioNotFoundException("Portfolio with ID " + portfolio.getPortfolioId() + " not found.");
		}
	}
}