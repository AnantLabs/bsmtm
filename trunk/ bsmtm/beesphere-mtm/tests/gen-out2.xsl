<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:out2="http://www.beesphere.net/xsds/2010/txt"
	xmlns:txt="http://www.beesphere.net/xsds/2010/txt">
	<xsl:output indent="yes" omit-xml-declaration="yes" />
	<xsl:param name="com.soa.defaults.requestDate" />
	<xsl:param name="com.beesphere.io.adapters.currentDate" />
	<xsl:param name="com.soa.defaults.remoteUser" />
	<xsl:variable name="v1" select="/root/in1/data/rs/r/c_3" />
	<xsl:variable name="v3">true</xsl:variable>
	<xsl:template match="/">
		<xsl:element name="out2:data">
			<xsl:element name="out2:rs">
				<xsl:element name="out2:r">
					<xsl:element name="out2:c_0">
						<xsl:value-of select="/root/in1/data/rs/r/c_0" />
					</xsl:element>
					<xsl:element name="out2:c_1">
						<xsl:value-of select="/root/in1/data/rs/r/c_1" />
					</xsl:element>
					<xsl:element name="out2:c_2">
						<xsl:value-of select="/root/in1/data/rs/r/c_2" />
					</xsl:element>
					<xsl:element name="out2:c_3">
						<xsl:value-of select="$v1" />
					</xsl:element>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>