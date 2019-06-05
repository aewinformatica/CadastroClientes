package br.com.sapejtb.sapejtb_admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

@Entity
@Table(name="cliente_foto")
public class Foto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String foto;
	
	@OneToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	
	@Column(name = "content_type")
	private String contentType;
	
	@Transient
	private boolean novaFoto;

	@Transient
	private String urlFoto;

	@Transient
	private String urlThumbnailFoto;
	
	
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "mock-cliente.png";
	}

	public boolean temFoto() {
		return !StringUtils.isEmpty(this.foto);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isNovaFoto() {
		return novaFoto;
	}

	public void setNovaFoto(boolean novaFoto) {
		this.novaFoto = novaFoto;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}

	public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		this.urlThumbnailFoto = urlThumbnailFoto;
	}
	
	public String getFotoPorCodigo(Long codigo) {
		
		return this.foto;
	}
	

}
