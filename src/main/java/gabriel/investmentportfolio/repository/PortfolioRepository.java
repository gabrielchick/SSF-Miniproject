package gabriel.investmentportfolio.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import gabriel.investmentportfolio.model.Portfolio;

@Repository
public class PortfolioRepository {
    @Autowired
    RedisTemplate redisTemplate;
    
    public void savePortfolio(Portfolio portfolio){
        redisTemplate.opsForHash().put("portfolios", portfolio.getPortfolioId(), portfolio);
    }

    public Portfolio getPortfolioDetail(UUID portfolioId) {

            return (Portfolio) redisTemplate.opsForHash().get("portfolios",portfolioId); //(Portfolio) cast allows us to convert from json to java object from database (database doesnt know what kind of java object it is)
    }

    public void deletePortfolio(UUID portfolioId) {
            redisTemplate.opsForHash().delete("portfolios", portfolioId);
    }

    //initialising portfolio list, create empty list, map all entries from database, iterate over each entry in the database, then add value to list
    public List<Portfolio> getPortfolioDetails() {
        List<Portfolio> portfolioList = new ArrayList<>();
        Map<Object,Object> entries = redisTemplate.opsForHash().entries("portfolios");
            for(Map.Entry<Object,Object> entry: entries.entrySet()){
                Portfolio portfolio = (Portfolio) entry.getValue(); // cast allows us to convert from json to java object from database
                portfolioList.add(portfolio);
            }
            return portfolioList;
        
    }
}
