package com.wlanboy.demo.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.wlanboy.demo.model.SimpleObject;
import com.wlanboy.demo.repository.SimpleObjectRepository;

@Service
public class MapsService {

    @Autowired
    SimpleObjectRepository simpledatenbank;

	public SimpleObject searchObjectById(Long identifier) {
		SimpleObject suche = simpledatenbank.findById(identifier).orElse(null);
		return suche;
	}

	public SimpleObject SaveObject(SimpleObject object) {
			String sha256hex = Hashing.sha256()
					  .hashString(object.getSIMPLE_NUMBER(), StandardCharsets.UTF_8)
					  .toString();
			object.setSIMPLE_HASH(sha256hex);

		SimpleObject save = simpledatenbank.save(object);
		return save;
	}

	public Iterable<SimpleObject> findAllObjects() {
		return simpledatenbank.findAll();
	}

	public SimpleObject searchSimpleObjectByNameOrValue(SimpleObject object) {
		Example<SimpleObject> exmaple = Example.of(object);
		return simpledatenbank.findOne(exmaple).orElse(null);
	}

	public void deleteSimpleObject(Long identifier) {
		simpledatenbank.deleteById(identifier);
	}
    
    
}
