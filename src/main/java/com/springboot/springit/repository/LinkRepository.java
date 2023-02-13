package com.springboot.springit.repository;

import com.springboot.springit.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long>{
}
