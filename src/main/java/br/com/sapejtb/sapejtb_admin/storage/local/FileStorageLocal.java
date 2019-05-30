package br.com.sapejtb.sapejtb_admin.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.sapejtb.sapejtb_admin.storage.FileStorage;

@Profile("local")
@Component
public class FileStorageLocal implements FileStorage {

	private static final Logger logger = LoggerFactory.getLogger(FileStorageLocal.class);
	private static final String THUMBNAIL_PREFIX = "thumbnail.";
	
	@Value("${sistema.file-storage-local.local}")
	private Path local;
	
	private String urlBase;
	
	@Value("${server.port}")
	private String port;
	
	@Override
	public String salvar(MultipartFile[] files) {
		
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.local.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando o arquivo", e);
			}
		}
		
		return novoNome;
	}

	@Override
	public byte[] recuperar(String file) {

		try {
			return Files.readAllBytes(this.local.resolve(file));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto", e);
		}
	}

	@Override
	public byte[] recuperarThumbnail(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(String file) {
		try {
			Files.deleteIfExists(this.local.resolve(file));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + file));
		} catch (IOException e) {
			logger.warn(String.format("Erro apagando arquivo '%s'. Mensagem: %s", file, e.getMessage()));
		}
		
	}

	@Override
	public String getUrl(String file) {
		try {

			urlBase  = "http://" + InetAddress.getLocalHost().getHostAddress() +":"+ port+"/files/";
			System.out.println(urlBase.toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return urlBase + file;
	}
	
	@PostConstruct
	private void criarPastas() {
			
		try {			
	
			if(!Files.exists(this.local)) {
			
				Files.createDirectories(this.local);
				
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para salvar Arquivos.");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar Arquivos", e);
		}
	}

}
