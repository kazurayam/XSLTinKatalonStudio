import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.xml.transform.Transformer
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

import com.kms.katalon.core.configuration.RunConfiguration

/**
 * This groovy script performs XSLT (Extensible Stylesheet Language Transformation) processing
 * taking <projectDir>/src/test/resource/Input.xml as input,
 * ganerates <projectDir>/build/tmp/Output.xml as output.
 *
 * This script shows an example of identity transformation; that is,
 * the input is simply copied into the output without any modifications.
 *
 * @author kazurayam
 */

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path inputXML = projectDir.resolve('src/test/resources/Input.xml')
Path outputXML = projectDir.resolve('build/tmp/TC2_replaceOrgnlTxId.Output.xml')

// create the identity transformer
String stylesheet = '''
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns="urn:iso:std:iso:20022:tech:xsd:camt.029.001.03">
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:variable name="OrgnlTxId_value" select="'VAR2'" />
  <xsl:template match="OrgnlTxId">
    <OrgnlTxId><xsl:value-of select="OrgnlTxId_value"/><OrgnlTxId>
  </xsl:template>
</xsl:stylesheet>
'''
Transformer tr = CustomKeywords.'XSLTHelper.newTransformer'(stylesheet)
assert tr != null

// load Input.xml
String input = inputXML.toFile().getText()
assert input != null

// prepare Output.xml
Files.createDirectories(outputXML.getParent())
OutputStream os = new FileOutputStream(outputXML.toFile())
assert os != null

// perform XML Transformation
tr.transform(new StreamSource(new StringReader(input)), new StreamResult(os))

System.out.println("PLS find the result at ${outputXML.toString()}")



