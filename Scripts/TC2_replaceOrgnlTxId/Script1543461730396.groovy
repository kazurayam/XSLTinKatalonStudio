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
Path outputXML = projectDir.resolve('build/tmp/TC2/Output.xml')

// create the identity transformer
String stylesheet = '''
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:CSC="urn:SctSCF:xsd:$SctScfBlkCdtTrf"
    xmlns="urn:iso:std:iso:20022:tech:xsd:camt.029.001.03">
  <xsl:template match="/">
    <xsl:apply-templates select="node()" />
  </xsl:template>
  <xsl:template match="*">
    <xsl:element name="{name()}">
      <xsl:apply-templates select="@* | node()" />
    </xsl:element>
  </xsl:template>
  <xsl:template match="@*">
    <xsl:attribute name="{name()}">
      <xsl:value-of select="." />
    </xsl:attribute>
  </xsl:template>
  <xsl:template match="text()">
    <xsl:if test="normalize-space()">
      <xsl:value-of select="." />
    </xsl:if>
  </xsl:template>
  
  <xsl:variable name="OrgnlTxId_value" select="'VAR2'" />
  <xsl:template match="OrgnlTxId">
    <OrgnlTxId><xsl:value-of select="OrgnlTxId_value"/></OrgnlTxId>
  </xsl:template>

</xsl:stylesheet>
'''
Transformer tr = CustomKeywords.'XSLTHelper.newTransformer'(stylesheet)
assert tr != null

// load Input.xml
String input = inputXML.toFile().getText()

// prepare Output.xml
Files.createDirectories(outputXML.getParent())
OutputStream os = new FileOutputStream(outputXML.toFile())

// perform XML Transformation
tr.transform(new StreamSource(new StringReader(input)), new StreamResult(os))

System.out.println("PLS find the result at ${outputXML.toString()}")



