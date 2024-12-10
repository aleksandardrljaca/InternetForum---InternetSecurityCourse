package org.unibl.etf.springlearning.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.models.entities.TokenBlackListEntity;
import org.unibl.etf.springlearning.repositories.TokenBlackListEntityRepository;
import org.unibl.etf.springlearning.services.TokenBlackListEntityService;

@Service
@Transactional
public class TokenBlackListEntityBlackListEntityServiceImpl implements TokenBlackListEntityService {
    private final TokenBlackListEntityRepository tokenBlackListRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public TokenBlackListEntityBlackListEntityServiceImpl(TokenBlackListEntityRepository tokenBlackListRepository) {
        this.tokenBlackListRepository = tokenBlackListRepository;
    }

    @Override
    public void blacklist(String token) {
        TokenBlackListEntity blacklistToken=new TokenBlackListEntity();
        blacklistToken.setId(null);
        blacklistToken.setToken(token);
        blacklistToken=tokenBlackListRepository.saveAndFlush(blacklistToken);
        entityManager.refresh(blacklistToken);
    }

    @Override
    public boolean isOnBlackList(String token) {
       return tokenBlackListRepository.existsTokenBlackListEntityByToken(token);
    }
}
