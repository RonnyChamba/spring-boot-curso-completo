package com.curso.spring.data.jpa.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	
	private String url;
	private Page<T> page;
	private int totalPaginas;
	private int numeroElementosPorPagia;
	private int paginaActual;
	
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		
		this.url = url;
		this.page = page;
		paginas = new ArrayList<PageItem>();
	
		numeroElementosPorPagia = this.page.getSize();
		totalPaginas = this.page.getTotalPages();
		paginaActual =  this.page.getNumber() +1;
		
		int desde, hasta;
		
		if (totalPaginas<=numeroElementosPorPagia) {
			
			desde = 1;
			hasta = totalPaginas;
		}else  {
			
			if (paginaActual<=numeroElementosPorPagia/2) {
				
				desde= 1;
				hasta = numeroElementosPorPagia;
			}else if (paginaActual>= totalPaginas -numeroElementosPorPagia/2) {
				desde = totalPaginas - numeroElementosPorPagia+1;
				hasta = numeroElementosPorPagia;
			}else {
				desde = paginaActual - numeroElementosPorPagia/2;
				hasta = numeroElementosPorPagia;
				
			}
		}
		
		for (int i=0;i<hasta;i++) {
			
			paginas.add(new PageItem(desde+i, paginaActual ==desde+i));
		}
		
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
	return page.hasPrevious();
	}
}
