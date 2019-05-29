package com.aewinformatica.cadastroclientes.repository.listener;

import javax.persistence.PostLoad;

import com.aewinformatica.cadastroclientes.CadastroClientesApplication;
import com.aewinformatica.cadastroclientes.model.Cliente;
import com.aewinformatica.cadastroclientes.storage.FotoStorage;

public class ClienteEntityListener {

	@PostLoad
	public void postLoad(final Cliente cliente) {
		FotoStorage fotoStorage = CadastroClientesApplication.getBean(FotoStorage.class);
		
		cliente.setUrlFoto(fotoStorage.getUrl(cliente.getFotoOuMock()));
		cliente.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + cliente.getFotoOuMock()));
	}
}
