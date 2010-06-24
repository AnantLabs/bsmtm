<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:cn="http://www.beesphere.net/xsds/2010/txt"
	xmlns:txt="http://www.beesphere.net/xsds/2010/txt">
	<xsl:output indent="yes" />
	<xsl:param name="com.soa.defaults.requestDate" />
	<xsl:param name="com.beesphere.io.adapters.currentDate" />
	<xsl:param name="com.soa.defaults.remoteUser" />
	<xsl:template match="/">
		<xsl:element name="cn:data">
			<xsl:element name="cn:clients">
				<xsl:for-each
					select="/root/in1/data/companies/company">
					<xsl:element name="cn:company">
						<xsl:element name="cn:name">
							<xsl:value-of select="name" />
						</xsl:element>
						<xsl:element name="cn:contacts">
							<xsl:for-each select="employees/employee">
								<xsl:element name="cn:contact">
									<xsl:element name="cn:name">
										<xsl:value-of select="name" />
									</xsl:element>
									<xsl:element name="cn:email">
										<xsl:value-of select="email" />
									</xsl:element>
								</xsl:element>
							</xsl:for-each>
						</xsl:element>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>