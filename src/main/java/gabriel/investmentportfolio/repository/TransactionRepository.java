package gabriel.investmentportfolio.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import gabriel.investmentportfolio.model.Transaction;

import java.util.List;

@Repository
public class TransactionRepository {

	 @Autowired
	 RedisTemplate redisTemplate;

    // Save the transaction into Redis (you can use another persistence if you need)
    public void saveTransaction(Transaction transaction) {
        // Save transaction to the Redis list named "transactions"
        redisTemplate.opsForList().rightPush("transactions", transaction);
    }

    // Retrieve the transaction history from Redis
    public List<Transaction> getTransactionHistory() {
        // Get all transactions from the Redis list
        return redisTemplate.opsForList().range("transactions", 0, -1);
    }
}
