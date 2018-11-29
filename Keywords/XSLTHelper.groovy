import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import javax.xml.transform.stream.StreamSource
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory

public class XSLTHelper {

	/**
	 * @param stylesheet as String
	 * @return a javax.xml.transform.Transformer object based on the given stylesheet
	 */
	static Transformer newTransformer(String stylesheet) {
		Transformer transformer = TransformerFactory.newInstance().newTransformer(
				new StreamSource(new StringReader(stylesheet)))
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
