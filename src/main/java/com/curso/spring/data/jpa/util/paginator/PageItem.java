package com.curso.spring.data.jpa.util.paginator;

/**
 * Clase que reprensenta una pagina del Paginador
 * @author user
 *
 */
public class PageItem {
	
	// Representa el numero de pagina
	private int numero;
	
	// Verificar si es la pagina actual
	private boolean actual;
	
	public PageItem(int numero, boolean actual) {
	
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isActual() {
		return actual;
	}

	public void setActual(boolean actual) {
		this.actual = actual;
	}

	@Override
	public String toString() {
		return "PageItem [numero=" + numero + ", actual=" + actual + "]";
	}
	
	
	
	
}
