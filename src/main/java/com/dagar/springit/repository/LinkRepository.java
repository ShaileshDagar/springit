package com.dagar.springit.repository;

import com.dagar.springit.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long>{
}
