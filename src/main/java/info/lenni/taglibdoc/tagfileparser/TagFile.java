package info.lenni.taglibdoc.tagfileparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Leonard Ehrenfried *
 */
public class TagFile {

    private static final String JSP_NS = "http://java.sun.com/JSP/Page";

    private List<Directive> directives = new ArrayList<Directive>();
    //private List<Attribute> attributes = new ArrayList<Attribute>();

    public List<Directive> getDirectives() {
        return directives;
    }

    public void addDirective(Directive directive) {
        directives.add(directive);
    }

    public static TagFile parse(InputStream input) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {

            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(input);

            TagFile tagfile = new TagFile();

            NodeList directiveElms = doc.getElementsByTagNameNS(JSP_NS,
                    "directive.attribute");

            for (int i = 0; i < directiveElms.getLength(); i++) {

                Node currentAttribute = directiveElms.item(i);
                NamedNodeMap attrs = currentAttribute.getAttributes();

                String name = attrs.getNamedItem("name").getNodeValue();
                Attribute attribute = new Attribute("name", name);

                Directive directive=new Directive();
                directive.setDirectiveName("attribute");
                directive.addAttribute(attribute);
                tagfile.addDirective(directive);
                
            }

            return tagfile;

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
