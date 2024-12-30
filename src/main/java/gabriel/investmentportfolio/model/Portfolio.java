package gabriel.investmentportfolio.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.UUID;

public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID portfolioId;

    @NotBlank(message = "Investment name is required")
    private String investmentName;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive number")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0.")
    private Double quantity;

    @NotNull(message = "Purchase price is required")
    @Positive(message = "Purchase price must be a positive number")
    @DecimalMin(value = "0.01", message = "Purchase price must be greater than 0.")
    private Double purchasePrice;

    @NotNull(message = "Current price is required")
    @Positive(message = "Current price must be a positive number")
    @DecimalMin(value = "0.01", message = "Current price must be greater than 0.")
    private Double currentPrice;
    

	public UUID getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(UUID portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getInvestmentName() {
		return investmentName;
	}

	public void setInvestmentName(String investmentName) {
		this.investmentName = investmentName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public String toString() {
		return "Portfolio [portfolioId=" + portfolioId + ", investmentName=" + investmentName + ", quantity=" + quantity
				+ ", purchasePrice=" + purchasePrice + ", currentPrice=" + currentPrice + "]";
	}

	public Portfolio(UUID portfolioId, @NotBlank(message = "Investment name is required") String investmentName,
			@NotNull(message = "Quantity is required") @Positive(message = "Quantity must be a positive number") Double quantity,
			@NotNull(message = "Purchase price is required") @Positive(message = "Purchase price must be a positive number") Double purchasePrice,
			@NotNull(message = "Current price is required") @Positive(message = "Current price must be a positive number") Double currentPrice) {
		super();
		this.portfolioId = portfolioId;
		this.investmentName = investmentName;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.currentPrice = currentPrice;
	}

	public Portfolio() {
		super();
	}

    
}
