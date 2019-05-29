package br.com.sapejtb.sapejtb_admin.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sapejtb.sapejtb_admin.model.Cliente;
import br.com.sapejtb.sapejtb_admin.repository.Clientes;
import br.com.sapejtb.sapejtb_admin.service.exception.ImpossivelExcluirEntidadeException;
import br.com.sapejtb.sapejtb_admin.storage.FotoStorage;

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
