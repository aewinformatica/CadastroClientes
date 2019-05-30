package br.com.sapejtb.sapejtb_admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.sapejtb.sapejtb_admin.dto.FileDTO;
import br.com.sapejtb.sapejtb_admin.storage.FileStorage;
import br.com.sapejtb.sapejtb_admin.storage.FileStorageRunnable;


@RestController
@RequestMapping("/arquivos")
public class FilesController {

	@Autowired
	private FileStorage fileStorage;
	
	@PostMapping
	public DeferredResult<FileDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FileDTO> resultado = new DeferredResult<>();

		Thread thread = new Thread(new FileStorageRunnable(files, resultado, fileStorage));
		thread.start();
		
		return resultado;
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return fileStorage.recuperar(nome);
	}
	
	@GetMapping("/delete/{nome:.*}")
	public void deletar(@PathVariable String nome) {
		if (!StringUtils.isEmpty(nome)) {
		 fileStorage.excluir(nome);
		}
	}
}