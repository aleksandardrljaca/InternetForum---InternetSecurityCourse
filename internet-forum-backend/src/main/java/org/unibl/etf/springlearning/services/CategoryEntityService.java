package org.unibl.etf.springlearning.services;

import org.springframework.stereotype.Repository;
import org.unibl.etf.springlearning.models.dto.Category;

import java.util.List;
@Repository
public interface CategoryEntityService {
    List<Category> findAll();
    Category findById(Integer id);
    Category findByName(String name);
}
