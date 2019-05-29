package br.com.sapejtb.sapejtb_admin.repository.listener;

import javax.persistence.PostLoad;

import br.com.sapejtb.sapejtb_admin.CadastroClientesApplication;
import br.com.sapejtb.sapejtb_admin.model.Cliente;
import br.com.sapejtb.sapejtb_admin.storage.FotoStorage;

public class ClienteEntityListener {

	@PostLoad
	public void postLoad(final Cliente cliente) {
		FotoStorage fotoStorage = CadastroClientesApplication.getBean(FotoStorage.class);
		
		cliente.setUrlFoto(fotoStorage.getUrl(cliente.getFotoOuMock()));
		cliente.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + cliente.getFotoOuMock()));
	}
}
