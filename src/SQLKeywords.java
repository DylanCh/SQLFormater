import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

public final class SQLKeywords {
    private final static Set<String> KEYWORDS = new HashSet<>();

    // default keywords that cannot add to/delete from
    private final static String[] KEYWORDSARRAY = new String[]{
            "SELECT",
            "AS",
            "WHERE",
            "IN",
            "BETWEEN",
            "INSERT",
            "INTO",
            "DELETE",
            "DROP",
            "TABLE",
            "OR",
            "AND",
            "GROUPING",
            "GROUP",
            "SET",
            "UPDATE",
            "TRUNCATE",
            "CREATE",
            "CUBE",
            "VIEW",
            "IS",
            "NULL",
            "CASE",
            "WHEN",
            "THEN",
            "END",
            "LIMIT",
            "DISTINCT",
            "TOP",
            "JOIN",
            "INNER",
            "RIGHT",
            "LEFT",
            "NATURAL",
            "BY",
            "ORDER",
            "HAVING",
            "FROM",
            "ON",
            "LIKE"
    };
    private static List<String> keywords;

    public static Set<String> getKeywords(){
        return new HashSet<>(KEYWORDS);
    }

    public static SQLKeywords getInstance(){
        return new SQLKeywords();
    }

    private SQLKeywords()  {
        keywords = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream("Keywords.xml");
            BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
            String line = buf.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line!=null){
                stringBuilder.append(line).append("\n");
                line = buf.readLine();
            }
            String xmlString = stringBuilder.toString();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource( new StringReader( xmlString) );
            Document doc = builder.parse( is );

            XPath xpath = XPathFactory.newInstance().newXPath();
            //xpath.setNamespaceContext(new PersonalNamespaceContext());
            XPathExpression expr = xpath.compile("//value/text()");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;


            try {
                for (int i = 0; i < nodes.getLength(); i++) {
                    keywords.add(nodes.item(i).getNodeValue());
                    System.out.println(nodes.item(i).getNodeValue());
                }
            }
            catch(UnsupportedOperationException e){
                addToKeywordList();
            }
            KEYWORDS.addAll(keywords);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            addToKeywordList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            addToKeywordList();
        } catch (IOException e) {
            e.printStackTrace();
            addToKeywordList();
        } catch (SAXException e) {
            e.printStackTrace();
            addToKeywordList();
        }
        catch (XPathExpressionException xe){
            xe.printStackTrace();
            addToKeywordList();
        }
    }

    // if reading XML is not successful, use the default keyword list
    // used in catch blocks
    private void addToKeywordList(){
        keywords = Arrays.asList(KEYWORDSARRAY);
    }

}
