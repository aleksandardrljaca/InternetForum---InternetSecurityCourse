package org.unibl.etf.springlearning.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.models.dto.JwtUser;
import org.unibl.etf.springlearning.repositories.UserEntityRepository;
import org.unibl.etf.springlearning.services.JwtUserDetailsService;
import org.unibl.etf.springlearning.services.UserEntityService;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;

    public JwtUserDetailsServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userEntityRepository.findUserEntityByUsername(username),JwtUser.class);
    }
}
