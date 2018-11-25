package com.wlanboy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.wlanboy.demo.model.Vorgang;
import com.wlanboy.demo.repository.VorgangRepository;

@Service
public class VorgangsService {

    @Autowired
    VorgangRepository vorgangsdatenbank;

	public Vorgang searchVorgangById(Long identifier) {
		Vorgang suche = vorgangsdatenbank.findById(identifier).orElse(null);
		return suche;
	}

	public Vorgang SaveVorgang(Vorgang vorgang) {
		Vorgang save = vorgangsdatenbank.save(vorgang);
		return save;
	}

	public Iterable<Vorgang> findAllVorgaenge() {
		return vorgangsdatenbank.findAll();
	}

	public Vorgang searchVorgangByNameAndStatus(Vorgang vorgang) {
		Example<Vorgang> vorgangexample = Example.of(vorgang);
		return vorgangsdatenbank.findOne(vorgangexample).orElse(null);
	}

	public void deleteVorgang(Long identifier) {
		vorgangsdatenbank.deleteById(identifier);
	}
    
    
}
