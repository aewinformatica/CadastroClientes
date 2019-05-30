package br.com.sapejtb.sapejtb_admin.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

	
	
	public final String THUMBNAIL_PREFIX = "thumbnail.";

	public String salvar(MultipartFile[] files);

	public byte[] recuperar(String file);
	
	public byte[] recuperarThumbnail(String file);

	public void excluir(String file);

	public String getUrl(String file);
	
	default String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

}