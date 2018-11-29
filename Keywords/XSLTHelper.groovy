import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamSource

public class XSLTHelper {

	/**
	 * @param stylesheet as String
	 * @return a javax.xml.transform.Transformer object based on the given stylesheet
	 */
	static Transformer newTransformer(String stylesheet) {
		Transformer transformer = TransformerFactory.newInstance().newTransformer(
				new StreamSource(new StringReader(stylesheet)))
		transformer.setOutputProperty(OutputKeys.INDENT, "yes")
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		return transformer
	}

	/**
	 * @return a javax.xml.transform.Transformer object which just copies input into output
	 */
	static Transformer newIdentityTransformer() {
		String identityStylesheet = '''
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
'''
		return newTransformer(identityStylesheet)
	}
}
