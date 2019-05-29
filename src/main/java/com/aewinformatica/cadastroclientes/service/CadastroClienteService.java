package com.aewinformatica.cadastroclientes.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aewinformatica.cadastroclientes.model.Cliente;
import com.aewinformatica.cadastroclientes.repository.Clientes;
import com.aewinformatica.cadastroclientes.service.exception.ImpossivelExcluirEntidadeException;
import com.aewinformatica.cadastroclientes.storage.FotoStorage;

@Service
public class CadastroClienteService {
	
	@Autowired
	Clientes clientes;

	@Autowired
	private FotoStorage fotoStorage;
	
	public void salvar(Cliente cliente){
		
		clientes.save(cliente);
	}
	
	public void excluir(Cliente cliente){
		
		try{
			String foto = cliente.getFoto();
			clientes.delete(cliente);
			clientes.flush();
			fotoStorage.excluir(foto);
			
		}catch(PersistenceException e){
			
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o cliente. Já foi usado em alguma operaçao.");
		}
	}
}
