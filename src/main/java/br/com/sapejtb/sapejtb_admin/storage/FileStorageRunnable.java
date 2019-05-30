package br.com.sapejtb.sapejtb_admin.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.sapejtb.sapejtb_admin.dto.FileDTO;



public class FileStorageRunnable implements Runnable {

	
	
	private MultipartFile[] files;
	private DeferredResult<FileDTO> resultado;
	private FileStorage fileStorage;
	
	public FileStorageRunnable(MultipartFile[] files, DeferredResult<FileDTO> resultado, FileStorage fileStorage) {
		this.files = files;
		this.resultado = resultado;
		this.fileStorage = fileStorage;
	}

	@Override
	public void run() {
		String nomeFile = this.fileStorage.salvar(files);
		String contentType = files[0].getContentType();
		resultado.setResult(new FileDTO(nomeFile, contentType, fileStorage.getUrl(nomeFile)));
	}

}