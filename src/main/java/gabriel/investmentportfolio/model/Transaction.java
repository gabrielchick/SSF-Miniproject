package gabriel.investmentportfolio.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
    private UUID transactionId;
    private String investmentName;
    private String transactionType;
    private Double quantity;
    private Double price;
    private LocalDateTime timestamp;
    public UUID getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
    public String getInvestmentName() {
        return investmentName;
    }
    public void setInvestmentName(String investmentName) {
        this.investmentName = investmentName;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public Transaction() {
    }
    public Transaction(UUID transactionId, String investmentName, String transactionType, Double quantity, Double price,
            LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.investmentName = investmentName;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "Transaction [transactionId=" + transactionId + ", investmentName=" + investmentName
                + ", transactionType=" + transactionType + ", quantity=" + quantity + ", price=" + price
                + ", timestamp=" + timestamp + "]";
    }
}
