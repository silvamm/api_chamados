package br.rec.alpha.apichamados;

import java.time.LocalDateTime;

public class Playground {
	
	public static void main(String[] args) {
		LocalDateTime hoje = LocalDateTime.now();
		System.out.println(String.valueOf(hoje.getYear()) + String.valueOf(hoje.getDayOfYear()) + hoje.getHour() + hoje.getMinute() + hoje.getSecond() + hoje.getNano());
		
	}

}
