import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern

import com.kms.katalon.core.configuration.RunConfiguration

Pattern pattern = Pattern.compile('<OrgnlTxId>(\\w+)</OrgnlTxId>')

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path inputXML = projectDir.resolve('src/test/resources/Input.xml')
Path outputXML = projectDir.resolve('build/tmp/TC0/Output.xml')

// load Input.xml into a String
String content = inputXML.toFile().getText()

// do String manipulation
String value = 'VAR3'
String replaced = content.replaceAll(
	'<OrgnlTxId>\\w+</OrgnlTxId>',
	"<OrgnlTxId>${value}</OrgnlTxId>")

// save the String into Output.xml
Files.createDirectories(outputXML.getParent())
outputXML.toFile().write(replaced)

System.out.println("PLS find the output at ${outputXML.toString()}")