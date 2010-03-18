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
 * @author Leonard Ehrenfried
 */
public class TagFile {

    private static final String JSP_NS = "http://java.sun.com/JSP/Page";
    // attributes to be read from the <jsp:directive.attribute/> tags
    private static final String[] ATTRIBUTE_ATTRS = { "name", "description",
            "required" };
    // attributes to be read from the <jsp:directive.tag/> tags
    private static final String[] TAG_ATTRS = { "name", "description",
            "required" };

    private List<Directive> directives = new ArrayList<Directive>();

    public static TagFile parse(InputStream input) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {

            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(input);

            TagFile tagfile = new TagFile();

            NodeList attrNodes = doc.getElementsByTagNameNS(JSP_NS,
                    "directive.attribute");
            List<Directive> directives = convertToDirectives(attrNodes,
                    "attribute", ATTRIBUTE_ATTRS);
            tagfile.addDirectives(directives);

            NodeList tagNodes = doc.getElementsByTagNameNS(JSP_NS,
                    "directive.tag");
            List<Directive> tags = convertToDirectives(tagNodes, "tag",
                    TAG_ATTRS);
            tagfile.addDirectives(tags);

            System.out.println(tagfile);
            return tagfile;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Directive> convertToDirectives(NodeList nodeList,
            String directiveName, String[] allowedAttrs) {

        List<Directive> directives = new ArrayList<Directive>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node currentAttribute = nodeList.item(i);
            NamedNodeMap attrs = currentAttribute.getAttributes();

            Directive directive = new Directive();
            directive.setDirectiveName(directiveName);

            for (String name : allowedAttrs) {

                Node node = attrs.getNamedItem(name);
                if (node != null) {
                    String value = node.getNodeValue();
                    Attribute attribute = new Attribute(name, value);
                    directive.addAttribute(attribute);
                }
            }

            directives.add(directive);
        }
        return directives;
    }

    // getters & setters

    public List<Directive> getDirectives() {
        return directives;
    }

    public void addDirective(Directive directive) {
        directives.add(directive);
    }

    public void addDirectives(List<Directive> directives) {
        this.directives.addAll(directives);
    }

}
