package br.com.sapejtb.sapejtb_admin.repository.listener;

import javax.persistence.PostLoad;

import br.com.sapejtb.sapejtb_admin.CadastroClientesApplication;
import br.com.sapejtb.sapejtb_admin.model.Cliente;
import br.com.sapejtb.sapejtb_admin.model.Foto;
import br.com.sapejtb.sapejtb_admin.storage.FileStorage;
import br.com.sapejtb.sapejtb_admin.storage.FotoStorage;

public class ClienteEntityListener {

	@PostLoad
	public void postLoad(final Cliente cliente) {
		FotoStorage fotoStorage = CadastroClientesApplication.getBean(FotoStorage.class);
		FileStorage fileStorage = CadastroClientesApplication.getBean(FileStorage.class);
		
		cliente.setUrlFoto(fotoStorage.getUrl(cliente.getFotoOuMock()));
		cliente.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + cliente.getFotoOuMock()));
	}
	
//	@PostLoad
//	public void postLoad(final Foto foto) {
//		
//		FotoStorage fotoStorage = CadastroClientesApplication.getBean(FotoStorage.class);
//		
//		foto.setUrlFoto(fotoStorage.getUrl(foto.getFotoOuMock()));
//		foto.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + foto.getFotoOuMock()));
//	}
}
