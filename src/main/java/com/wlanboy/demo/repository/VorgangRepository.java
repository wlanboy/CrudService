package com.wlanboy.demo.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlanboy.demo.model.Vorgang;
 
@Repository
public interface VorgangRepository extends JpaRepository<Vorgang, Long>{

}
