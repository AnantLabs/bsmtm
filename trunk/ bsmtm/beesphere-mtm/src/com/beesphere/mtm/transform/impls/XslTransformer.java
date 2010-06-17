package com.beesphere.mtm.transform.impls;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.StandardURIResolver;
import net.sf.saxon.TransformerFactoryImpl;

import org.xml.sax.InputSource;

public class XslTransformer implements com.beesphere.mtm.transform.Transformer {
	
	private InputStream xsl;
	
	public XslTransformer (InputStream xsl) {
		this.xsl = xsl;
	}
	
	private static Templates createTemplate (InputStream xslStream) throws TransformerConfigurationException {
		TransformerFactory factory = new TransformerFactoryImpl ();
		Configuration configuration = new Configuration ();
		StandardURIResolver resolver = new StandardURIResolver (configuration);
		factory.setURIResolver(resolver);
        return factory.newTemplates(new StreamSource (xslStream));
	}
	
	@Override
	public void transform(InputStream source, OutputStream result) throws com.beesphere.mtm.transform.TransformerException {
		try {
			Templates template = createTemplate (xsl);
			Transformer transformer = template.newTransformer();
			transformer.setParameter("com.me.mycaracteristic", "myCarvalue");
			transformer.setURIResolver (new URIResolver () {

				public Source resolve(String href, String base)
						throws TransformerException {
					// TODO Auto-generated method stub
					return new StreamSource(href);
				}
				
			});
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setParameter("com.soa.defaults.remoteUser", "ABO");
			transformer.transform(new StreamSource (source), new StreamResult (result));
		} catch (Throwable th) {
			throw new com.beesphere.mtm.transform.TransformerException (th);
		}
	}

	@Override
	public void transform(InputSource source, StreamResult result) {
		// TODO Auto-generated method stub
		
	}
}
