package com.aewinformatica.cadastroclientes.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

public class AewDialect extends AbstractProcessorDialect{

	public AewDialect() {
		
		super("Sistema AewInformatica", "AewSis", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		
		final Set<IProcessor> processadores = new HashSet<>();
//		processadores.add(new OrderElementTagProcessor(dialectPrefix));

		return processadores;
	}

}
