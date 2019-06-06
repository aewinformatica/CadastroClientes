package br.com.sapejtb.sapejtb_admin.repository.listener;

import javax.persistence.PostLoad;

import br.com.sapejtb.sapejtb_admin.CadastroClientesApplication;
import br.com.sapejtb.sapejtb_admin.model.Foto;
import br.com.sapejtb.sapejtb_admin.storage.FotoStorage;

public class FotoEntityListener {

	
	@PostLoad
	public void postLoadtwo(final Foto foto) {
		FotoStorage fotoStorage = CadastroClientesApplication.getBean(FotoStorage.class);
		
		foto.setUrlFoto(fotoStorage.getUrl(foto.getFotoOuMock()));
		foto.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + foto.getFotoOuMock()));
	}
}
