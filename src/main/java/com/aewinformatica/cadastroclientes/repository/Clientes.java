package com.aewinformatica.cadastroclientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aewinformatica.cadastroclientes.model.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente,Long> {

}
