package com.wlanboy.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.wlanboy.demo.model.SimpleObject;

@CrossOrigin
@Repository
@RepositoryRestResource(path = "SimpleObject")
public interface SimpleObjectRepository extends JpaRepository<SimpleObject, Long>{

}
