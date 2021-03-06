package com.aewinformatica.cadastroclientes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aewinformatica.cadastroclientes.model.Cliente;
import com.aewinformatica.cadastroclientes.repository.Clientes;
import com.aewinformatica.cadastroclientes.service.CadastroClienteService;
import com.aewinformatica.cadastroclientes.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private CadastroClienteService cadastroClienteService;
	
		//temporario ate fazer o filtro de busca
			@Autowired
			private Clientes clientes;
	
		@RequestMapping(value = "/novo")
		public ModelAndView novo(Cliente cliente){
				
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
	
		return mv;
		}
	
		@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
		public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes) {
			if (result.hasErrors()) {
				return novo(cliente);
			}
			
			cadastroClienteService.salvar(cliente);
			attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
			return new ModelAndView("redirect:/clientes/novo");
		}
		
		@GetMapping
		public ModelAndView pesquisar(Cliente cliente) {
			ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");
			
			List<Cliente>listaClientes = clientes.findAll();
			mv.addObject("todosclientes",listaClientes);

			return mv;
		}
	
		@DeleteMapping("/{codigo}")
		public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cliente cliente) {
			try {
				cadastroClienteService.excluir(cliente);
			} catch (ImpossivelExcluirEntidadeException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			return ResponseEntity.ok().build();
		}
		
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cliente cliente) {
		ModelAndView mv = novo(cliente);
		mv.addObject(cliente);
		return mv;
	}
}
