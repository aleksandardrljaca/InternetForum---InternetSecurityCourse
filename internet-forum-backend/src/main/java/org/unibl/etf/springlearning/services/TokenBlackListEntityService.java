package org.unibl.etf.springlearning.services;

public interface TokenBlackListEntityService {
    void blacklist(String token);
    boolean isOnBlackList(String token);
}
