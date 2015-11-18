package it.unibz.inf.sparql.stats;

import java.io.*;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.algebra.*;
import org.openrdf.query.parser.*;
import org.openrdf.query.parser.sparql.SPARQLParserFactory;

// TODO: Resolve [Encountered " "str" "str ""] and [Encountered " "count" "count ""]

/**
 * How many SELECT queries are with negation? This program gives you the statistics. 
 * Useful reference: http://codedmi.com/questions/2987903/how-can-i-parse-optional-statements-from-a-sparql-query-using-sesame
 * 
 * @author Fariz Darari (fadirra@gmail.com)
 *
 */
public class NegationStats {

	public static void main(String[] args) {
		
		long lineNum = 0;
		long numOfNegQueries = 0;
		long numOfBadQueries = 0; // malformed queries, POST queries, URI decoding error, etc
		
		// due to performance reason, negation detection must be done on the fly during query parsing
		try{
			// queries from USEWOD datasets
			File file = new File("C:\\Users\\user\\Documents\\Datasets\\usewod2015-dataset\\access.log-20140510");
		    
			// example queries
//			File file = new File("test-queries.txt");
			
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while(((line = br.readLine()) != null)){
		    	
	    		System.out.println("LineNum: " + ++lineNum);
	    		
	    		int getFlag = line.indexOf("query="); // the query must be a value of the parameter query
	    		int postFlag = line.indexOf("] \"POST"); // POST queries are not considered
	    		int constructFlag = line.indexOf("query=CONSTRUCT"); // CONSTRUCT queries are not considered
	    		int describeFlag = line.indexOf("query=DESCRIBE"); // DESCRIBE is not considered
	    		int defineFlag = line.indexOf("query=define"); // define is not considered
	    		if(getFlag >= 0 && postFlag < 0 && constructFlag < 0 && describeFlag < 0 && defineFlag < 0) {
	    			
	    			String refinedString = line.substring(getFlag + 6);
	    			
	    			int httpFlag = refinedString.indexOf("HTTP/");
	    			refinedString = refinedString.substring(0, httpFlag); // get a string up to HTTP/
	    			
	    			if(refinedString.indexOf("&format=") >= 0)
	    				refinedString = refinedString.substring(0, refinedString.indexOf("&format=")); // get a string up to &format=
	    			
	    			if(refinedString.indexOf("&results=") >= 0)
	    				refinedString = refinedString.substring(0, refinedString.indexOf("&results=")); // get a string up to &results=
	    			
	    			try{
	    				String queryString = java.net.URLDecoder.decode(refinedString, "UTF-8");
	    				int negationFlag = withNegation(queryString);
	    				if(negationFlag == 1)
	    					numOfNegQueries++;
	    				else if(negationFlag == -1) {
	    					System.out.println("ERR: Bad query found (Malformed query).");
	    					numOfBadQueries++;
	    				}
	    			} catch(Exception iae) {
	    				System.out.println(iae.getMessage());
	    				System.out.println("ERR: Bad query found (Decoding error).");
	    				numOfBadQueries++;
	    			}
	    			
	    		}
	    		else { 
	    			System.out.println("ERR: Bad query found. (POST or no query or etc)"); 
	    			numOfBadQueries++; 
	    		}
	    		
		    }
		    br.close();
		    fr.close();
		} catch(Exception ex){ ex.printStackTrace(System.out); }
		
		System.out.println("\n STATISTICS \n ----------");
		System.out.println("All Queries = " + lineNum + ", Bad Queries = " + numOfBadQueries +  ", Good Queries = " + (lineNum - numOfBadQueries) + ", Negation Queries = " +  numOfNegQueries);
		System.out.println("Ratio of queries with negation = " + numOfNegQueries + "/" + (lineNum - numOfBadQueries));
		
	}

	/**
	 * Is the query with negation?
	 * 
	 * @param queryStr
	 * @return -1 if malformed query, 0 if without negation, 1 if with negation
	 */
	public static int withNegation(String queryStr) {
		
		SPARQLParserFactory factory = new SPARQLParserFactory();
		QueryParser parser = factory.getParser();
		
		try{
			// default prefixes by DBpedia
//			String defaultPrefixes = "PREFIX a: <http://example.org/> PREFIX address: <http://example.org/> PREFIX admin: <http://example.org/> PREFIX atom: <http://example.org/> PREFIX aws: <http://example.org/> PREFIX b3s: <http://example.org/> PREFIX batch: <http://example.org/> PREFIX bibo: <http://example.org/> PREFIX bif: <http://example.org/> PREFIX bugzilla: <http://example.org/> PREFIX c: <http://example.org/> PREFIX campsite: <http://example.org/> PREFIX cb: <http://example.org/> PREFIX cc: <http://example.org/> PREFIX content: <http://example.org/> PREFIX cv: <http://example.org/> PREFIX cvbase: <http://example.org/> PREFIX dawgt: <http://example.org/> PREFIX dbc: <http://example.org/> PREFIX dbo: <http://example.org/> PREFIX dbp: <http://example.org/> PREFIX dbr: <http://example.org/> PREFIX dc: <http://example.org/> PREFIX dct: <http://example.org/> PREFIX digg: <http://example.org/> PREFIX dul: <http://example.org/> PREFIX ebay: <http://example.org/> PREFIX enc: <http://example.org/> PREFIX exif: <http://example.org/> PREFIX fb: <http://example.org/> PREFIX ff: <http://example.org/> PREFIX fn: <http://example.org/> PREFIX foaf: <http://example.org/> PREFIX freebase: <http://example.org/> PREFIX g: <http://example.org/> PREFIX gb: <http://example.org/> PREFIX gd: <http://example.org/> PREFIX geo: <http://example.org/> PREFIX geonames: <http://example.org/> PREFIX georss: <http://example.org/> PREFIX gml: <http://example.org/> PREFIX go: <http://example.org/> PREFIX hlisting: <http://example.org/> PREFIX hoovers: <http://example.org/> PREFIX hrev: <http://example.org/> PREFIX ical: <http://example.org/> PREFIX ir: <http://example.org/> PREFIX itunes: <http://example.org/> PREFIX ldp: <http://example.org/> PREFIX lgv: <http://example.org/> PREFIX link: <http://example.org/> PREFIX lod: <http://example.org/> PREFIX math: <http://example.org/> PREFIX media: <http://example.org/> PREFIX mesh: <http://example.org/> PREFIX meta: <http://example.org/> PREFIX mf: <http://example.org/> PREFIX mmd: <http://example.org/> PREFIX mo: <http://example.org/> PREFIX mql: <http://example.org/> PREFIX nci: <http://example.org/> PREFIX nfo: <http://example.org/> PREFIX ng: <http://example.org/> PREFIX nyt: <http://example.org/> PREFIX oai: <http://example.org/> PREFIX oai_dc: <http://example.org/> PREFIX obo: <http://example.org/> PREFIX office: <http://example.org/> PREFIX ogc: <http://example.org/> PREFIX ogcgml: <http://example.org/> PREFIX ogcgs: <http://example.org/> PREFIX ogcgsf: <http://example.org/> PREFIX ogcgsr: <http://example.org/> PREFIX ogcsf: <http://example.org/> PREFIX oo: <http://example.org/> PREFIX openSearch: <http://example.org/> PREFIX opencyc: <http://example.org/> PREFIX opl: <http://example.org/> PREFIX opl-gs: <http://example.org/> PREFIX opl-meetup: <http://example.org/> PREFIX opl-xbrl: <http://example.org/> PREFIX oplweb: <http://example.org/> PREFIX ore: <http://example.org/> PREFIX owl: <http://example.org/> PREFIX product: <http://example.org/> PREFIX protseq: <http://example.org/> PREFIX r: <http://example.org/> PREFIX radio: <http://example.org/> PREFIX rdf: <http://example.org/> PREFIX rdfa: <http://example.org/> PREFIX rdfdf: <http://example.org/> PREFIX rdfs: <http://example.org/> PREFIX rev: <http://example.org/> PREFIX review: <http://example.org/> PREFIX rss: <http://example.org/> PREFIX sc: <http://example.org/> PREFIX scovo: <http://example.org/> PREFIX sd: <http://example.org/> PREFIX sf: <http://example.org/> PREFIX sioc: <http://example.org/> PREFIX sioct: <http://example.org/> PREFIX skiresort: <http://example.org/> PREFIX skos: <http://example.org/> PREFIX slash: <http://example.org/> PREFIX sql: <http://example.org/> PREFIX stock: <http://example.org/> PREFIX twfy: <http://example.org/> PREFIX umbel: <http://example.org/> PREFIX umbel-ac: <http://example.org/> PREFIX umbel-rc: <http://example.org/> PREFIX umbel-sc: <http://example.org/> PREFIX uniprot: <http://example.org/> PREFIX units: <http://example.org/> PREFIX usc: <http://example.org/> PREFIX v: <http://example.org/> PREFIX vcard: <http://example.org/> PREFIX vcard2006: <http://example.org/> PREFIX vi: <http://example.org/> PREFIX virt: <http://example.org/> PREFIX virtcxml: <http://example.org/> PREFIX virtpivot: <http://example.org/> PREFIX virtrdf: <http://example.org/> PREFIX void: <http://example.org/> PREFIX wb: <http://example.org/> PREFIX wdrs: <http://example.org/> PREFIX wf: <http://example.org/> PREFIX wfw: <http://example.org/> PREFIX wikicompany: <http://example.org/> PREFIX wikidata: <http://example.org/> PREFIX xf: <http://example.org/> PREFIX xfn: <http://example.org/> PREFIX xhtml: <http://example.org/> PREFIX xhv: <http://example.org/> PREFIX xi: <http://example.org/> PREFIX xml: <http://example.org/> PREFIX xn: <http://example.org/> PREFIX xsd: <http://example.org/> PREFIX xsl10: <http://example.org/> PREFIX xsl1999: <http://example.org/> PREFIX xslwd: <http://example.org/> PREFIX y: <http://example.org/> PREFIX yago: <http://example.org/> PREFIX yago-res: <http://example.org/> PREFIX yt: <http://example.org/> PREFIX zem: <http://example.org/> ";
			
			// fix missing prefixes
			if(!queryStr.contains("PREFIX dbpedia:"))
				queryStr = "PREFIX dbpedia: <http://example.org/> " + queryStr;
			if(!queryStr.contains("PREFIX dbpedia-owl:"))
				queryStr = "PREFIX dbpedia-owl: <http://example.org/> " + queryStr;
			if(!queryStr.contains("PREFIX dbpprop:"))
				queryStr = "PREFIX dbpprop: <http://example.org/> " + queryStr;
			if(!queryStr.contains("PREFIX foaf:"))
				queryStr = "PREFIX foaf: <http://example.org/> " + queryStr;
			if(!queryStr.contains("PREFIX geo:"))
				queryStr = "PREFIX geo: <http://example.org/> " + queryStr;
			
			ParsedQuery parsedQuery = parser.parseQuery(queryStr, null); // if malformed query, then exception
			QueryModelVisitor<NegationException> myVisitor = new NegationDetector();
			try {
				parsedQuery.getTupleExpr().visit(myVisitor); // traverse the query algebra
				System.out.println("Query with negation? No.");
				return 0;
			} catch (NegationException e) {
				System.out.println("Query with negation? Yes.");
				return 1;
			}
		}
		catch(MalformedQueryException mqe){ System.out.println(mqe.getMessage()); return -1; }

	}
	
}