package gabriel.investmentportfolio.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabriel.investmentportfolio.model.Portfolio;
import gabriel.investmentportfolio.model.Transaction;
import gabriel.investmentportfolio.repository.PortfolioRepository;
import gabriel.investmentportfolio.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    TransactionRepository transactionRepository;  // Inject TransactionRepository to save/get transactions

    // Method to handle buying an investment
    public Portfolio buyInvestment(UUID portfolioId, Double quantity, Double purchasePrice) {
        Portfolio portfolio = portfolioRepository.getPortfolioDetail(portfolioId); // Fetch portfolio from database
        if (portfolio != null) {
            // Calculate new average price and update portfolio quantity
            Double totalQuantity = portfolio.getQuantity() + quantity;
            Double newAveragePrice = ((portfolio.getQuantity() * portfolio.getPurchasePrice()) + (quantity * purchasePrice)) / totalQuantity;
            portfolio.setQuantity(totalQuantity);
            portfolio.setPurchasePrice(newAveragePrice);

            // Update portfolio in database
            portfolioRepository.savePortfolio(portfolio);

            // Record transaction in history
            Transaction transaction = new Transaction();
            transaction.setTransactionType("BUY");
            transaction.setInvestmentName(portfolio.getInvestmentName());
            transaction.setQuantity(quantity);
            transaction.setPrice(purchasePrice);

            // Save the transaction using the repository
            transactionRepository.saveTransaction(transaction);
        }
        return portfolio;
    }

    // Method to handle selling an investment
    public Portfolio sellInvestment(UUID portfolioId, Double quantity, Double sellPrice) {
        Portfolio portfolio = portfolioRepository.getPortfolioDetail(portfolioId); // Fetch portfolio from database
        if (portfolio != null) {
            portfolio.setQuantity(portfolio.getQuantity() - quantity);
            if (portfolio.getQuantity() <= 0) {
                portfolioRepository.deletePortfolio(portfolioId); // Delete portfolio if quantity reaches 0
            } else {
                portfolioRepository.savePortfolio(portfolio); // Update portfolio in database
            }
            // Record transaction in history
            Transaction transaction = new Transaction();
            transaction.setTransactionType("SELL");
            transaction.setInvestmentName(portfolio.getInvestmentName());
            transaction.setQuantity(quantity);
            transaction.setPrice(sellPrice);

            // Save the transaction using the repository
            transactionRepository.saveTransaction(transaction);
        }
        return portfolio;
    }

    // Method to fetch all transaction history from the repository
    public List<Transaction> getTransactionHistory() {
        return transactionRepository.getTransactionHistory();
    }
}
