package org.unibl.etf.springlearning.services;

import org.springframework.validation.BindingResult;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;

public interface WafService {
    void check(BindingResult bindingResult) throws UnauthorizedException;
}
