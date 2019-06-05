package br.com.sapejtb.sapejtb_admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sapejtb.sapejtb_admin.model.Foto;

@Repository
public interface Fotos extends JpaRepository<Foto, Long> {

}
