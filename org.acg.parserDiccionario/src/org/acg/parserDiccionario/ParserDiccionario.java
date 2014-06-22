package org.acg.parserDiccionario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserDiccionario {
	
	private static ParserDiccionario mParserDiccionario = new ParserDiccionario();
	
	private ParserDiccionario() {

	}

	public static ParserDiccionario getParserDiccionario(){
		return mParserDiccionario;
	}
	
	public void process(String fPalabras, String fXML) {
		FileReader fr = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fPalabras);
			br = new BufferedReader(fr);
			pw = new PrintWriter(new File(fXML + "\\Diccionario.xml"), "UTF-8");
			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			Document doc = null;
			String defString = "";
			String palabra;
			pw.println("<Diccionario>");
			while ((palabra = br.readLine()) != null) {
				doc = Jsoup.connect("http://www.wordreference.com/definicion/" + palabra)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").post();
				pw.println("\t<Definicion>");
				pw.println("\t\t<Letra>" + palabra.toLowerCase().charAt(0) + "</Letra>");
				Element significado = doc.select("ol").first(); // Primera coincidencia
				Elements defs = significado.select("li"); // Significados
				// Elements defsLimpio = defs.not("span"); // Eliminar ejemplos
				pw.println("\t\t<ListaEnunciados>");
				for (Element e : defs) {
					if (!e.children().isEmpty()) {
						// si contiene children con class b
						if (!e.children().first().hasClass("b") && !e.children().first().hasClass("ac")) {
							defString = e.ownText().replace(':', '.');
							pw.println("\t\t\t<Enunciado>" + defString + "</Enunciado>");
//							System.out.println(defString);
						}
					} else {
						defString = e.ownText().replace(':', '.');
						pw.println("\t\t\t<Enunciado>" + defString + "</Enunciado>");
//						System.out.println(defString);
					}
				}
				pw.println("\t\t</ListaEnunciados>");
				// ListaRespuestas (por implementar)
				pw.println("\t\t<ListaRespuestas>");
				pw.println("\t\t\t<Respuesta>" + palabra + "</Respuesta>");
				pw.println("\t\t</ListaRespuestas>");
				pw.println("\t</Definicion>");
			}
			pw.println("</Diccionario>");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.close();
		}
	}
}
