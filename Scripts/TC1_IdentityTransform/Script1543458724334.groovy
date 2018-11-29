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
Path outputXML = projectDir.resolve('build/tmp/TC1/Output.xml')

// create the identity transformer
Transformer tr = CustomKeywords.'XSLTHelper.newIdentityTransformer'()
assert tr != null

// load Input.xml
String input = inputXML.toFile().getText()

// prepare Output.xml
Files.createDirectories(outputXML.getParent())
OutputStream os = new FileOutputStream(outputXML.toFile())

// perform XML Transformation
tr.transform(new StreamSource(new StringReader(input)), new StreamResult(os))

System.out.println("PLS find the result at ${outputXML.toString()}")



