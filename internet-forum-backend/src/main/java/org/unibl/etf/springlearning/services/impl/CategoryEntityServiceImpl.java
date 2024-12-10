package org.unibl.etf.springlearning.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.models.dto.Category;
import org.unibl.etf.springlearning.repositories.CategoryEntityRepository;
import org.unibl.etf.springlearning.services.CategoryEntityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryEntityServiceImpl implements CategoryEntityService {
    private final CategoryEntityRepository categoryEntityRepository;
    private final ModelMapper modelMapper;

    public CategoryEntityServiceImpl(CategoryEntityRepository categoryEntityRepository, ModelMapper modelMapper) {
        this.categoryEntityRepository = categoryEntityRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<Category> findAll(){
        return categoryEntityRepository.findAll().stream().map(c->modelMapper.map(c,Category.class)).collect(Collectors.toList());
    }

    @Override
    public Category findById(Integer id) {
        return modelMapper.map(categoryEntityRepository.findById(id),Category.class);
    }
    @Override
    public Category findByName(String name){
        return modelMapper.map(categoryEntityRepository.findCategoryEntitiesByName(name),Category.class);
    }
}
