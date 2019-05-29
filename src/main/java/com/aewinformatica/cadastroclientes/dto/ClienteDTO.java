package com.aewinformatica.cadastroclientes.dto;

import org.springframework.util.StringUtils;

public class ClienteDTO {
	private Long codigo;
	private String nome;
	private String foto;
	private String urlThumbnailFoto;
	
	public ClienteDTO(Long codigo,String nome, String foto){
		this.codigo = codigo;
		this.nome = nome;
		this.foto = StringUtils.isEmpty(foto) ? "mock-cliente.png" : foto;
		
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}
	public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		this.urlThumbnailFoto = urlThumbnailFoto;
	}

}
