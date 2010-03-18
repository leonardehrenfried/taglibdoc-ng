package info.lenni.taglibdoc.tagfileparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TagFile {

    List<Directive> directives=new ArrayList<Directive>();
    private static final String JSP_NS="http://java.sun.com/JSP/Page";
    
    public List<Directive> getDirectives(){
        return null;
    }
    
    public void addDirective(Directive directive){
        directives.add(directive);
    }
    
    public static TagFile parse(InputStream input){
        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            
            DocumentBuilder docBuilder=factory.newDocumentBuilder();
            Document doc=docBuilder.parse(input);
            
            TagFile tagfile = new TagFile();
            
            NodeList directiveElms = doc.getElementsByTagNameNS(JSP_NS, "directive.attribute");
            System.out.println("length is");
            System.out.println(directiveElms.getLength());
            
            for (int i=0; i<directiveElms.getLength(); i++){
                Directive directive = new Directive();
                Node currentDirective=directiveElms.item(i);
                NamedNodeMap attrs=currentDirective.getAttributes();
                directive.setDirectiveName(attrs.getNamedItem("name").getNodeValue());
                
                tagfile.addDirective(directive);
            }
            
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
