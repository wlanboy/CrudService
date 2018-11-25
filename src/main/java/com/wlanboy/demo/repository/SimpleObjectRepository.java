package com.wlanboy.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.wlanboy.demo.model.SimpleObject;

@CrossOrigin
@Repository
@RepositoryRestResource(path = "SimpleObject")
public interface SimpleObjectRepository extends PagingAndSortingRepository<SimpleObject, Long>{

}
