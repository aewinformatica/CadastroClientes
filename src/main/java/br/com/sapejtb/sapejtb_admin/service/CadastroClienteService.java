package br.com.sapejtb.sapejtb_admin.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sapejtb.sapejtb_admin.model.Cliente;
import br.com.sapejtb.sapejtb_admin.model.Foto;
import br.com.sapejtb.sapejtb_admin.repository.Clientes;
import br.com.sapejtb.sapejtb_admin.repository.Fotos;
import br.com.sapejtb.sapejtb_admin.service.exception.ImpossivelExcluirEntidadeException;
import br.com.sapejtb.sapejtb_admin.storage.FotoStorage;

@Service
public class CadastroClienteService {
	
	@Autowired
	Clientes clientes;
	
	@Autowired
	Fotos fotos;
	

	@Autowired
	private FotoStorage fotoStorage;
	
	public void salvar(Cliente cliente){
		
		clientes.save(cliente);
	}
	
	public void excluir(Cliente cliente){
	
		Foto foto = fotos.getOne(cliente.getCodigo());
		
		try{
			String nomeFoto = foto.getFoto();
			clientes.delete(cliente);
			clientes.flush();
			fotoStorage.excluir(nomeFoto);
			
		}catch(PersistenceException e){
			
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o cliente. Já foi usado em alguma operaçao.");
		}
	}
}
