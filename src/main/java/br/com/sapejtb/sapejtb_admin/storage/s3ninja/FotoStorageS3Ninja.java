package br.com.sapejtb.sapejtb_admin.storage.s3ninja;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import br.com.sapejtb.sapejtb_admin.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;

//@Profile("!prod")
@Profile("s3-ninja")
@Component
public class FotoStorageS3Ninja implements FotoStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(FotoStorageS3Ninja.class);
	
	@Value("${sistema.foto-storage.bucket}")
	private  String BUCKET;
	
	@Value("${sistema.foto-storage.s3ninja}")
	private String servidor;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	
	@Override
	public String salvar(MultipartFile[] files) {
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			
			try {
				AccessControlList acl = new AccessControlList();
				acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
				
				enviarFoto(novoNome, arquivo, acl);
				enviarThumbnail(novoNome, arquivo, acl);
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando arquivo no S3", e);
			}
		}
		
		return novoNome;
	}

	@Override
	public byte[] recuperar(String foto) {
		InputStream is = amazonS3.getObject(BUCKET, foto).getObjectContent();
		try {
			return IOUtils.toByteArray(is);
		} catch (IOException e) {
			logger.error("Não conseguiu recuperar foto do S3", e);
		}
		return null;
	}

	@Override
	public byte[] recuperarThumbnail(String foto) {
		return recuperar(FotoStorage.THUMBNAIL_PREFIX + foto);
	}

	@Override
	public void excluir(String foto) {
		
		
			amazonS3.deleteObject(new DeleteObjectRequest(BUCKET,foto));
			amazonS3.deleteObject(new DeleteObjectRequest(BUCKET,FotoStorage.THUMBNAIL_PREFIX + foto));
		
	}

	@Override
	public String getUrl(String foto) {
		if (!StringUtils.isEmpty(foto)) {
			return servidor + "/ui/" + BUCKET +"/" + foto;
		}
		
		return null;
	}
	
	private ObjectMetadata enviarFoto(String novoNome, MultipartFile arquivo, AccessControlList acl)
			throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(arquivo.getContentType());
		metadata.setContentLength(arquivo.getSize());
		amazonS3.putObject(new PutObjectRequest(BUCKET, novoNome, arquivo.getInputStream(), metadata)
					.withAccessControlList(acl));
		return metadata;
	}

	private void enviarThumbnail(String novoNome, MultipartFile arquivo, AccessControlList acl)	throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(arquivo.getInputStream()).size(40, 68).toOutputStream(os);
		byte[] array = os.toByteArray();
		InputStream is = new ByteArrayInputStream(array);
		ObjectMetadata thumbMetadata = new ObjectMetadata();
		thumbMetadata.setContentType(arquivo.getContentType());
		thumbMetadata.setContentLength(array.length);
		amazonS3.putObject(new PutObjectRequest(BUCKET, THUMBNAIL_PREFIX + novoNome, is, thumbMetadata)
					.withAccessControlList(acl));
	}
	
	@PostConstruct
	private void CriarBucket() {
		if (!amazonS3.doesBucketExistV2(BUCKET)) {

			// Como o objeto CreateBucketRequest não especifica uma região, o
			// bucket é criado na região especificada no cliente.
			amazonS3.createBucket(new CreateBucketRequest(BUCKET));
			String bucketLocation = amazonS3.getBucketLocation(new GetBucketLocationRequest(BUCKET));

			if (logger.isDebugEnabled()) {
				logger.debug("Bucket criado para salvar fotos.");
				logger.debug("Bucket em: " + bucketLocation);
			}
			String verificaObjeto = "data/thumbnail.mock-cliente.png";
			
			
			if(!amazonS3.doesObjectExist(BUCKET,verificaObjeto)){
				
		     try {
		    	               //utilizado para buscar arquivos do proprio projeto
			      File file = ResourceUtils.getFile("classpath:static/images/mock-cliente.png");
			  
					AccessControlList acl = new AccessControlList();
					acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
					amazonS3.putObject(new PutObjectRequest(BUCKET, "thumbnail.mock-cliente.png", file).withAccessControlList(acl));
			} catch (IOException e) {
				throw new RuntimeException("Erro criando pasta para salvar foto", e);
			}

			}
			
			
	    }
	}
	
}
